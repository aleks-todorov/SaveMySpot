package com.alekstodorov.savemyspot.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import com.alekstodorov.savemyspot.R;
import com.alekstodorov.savemyspot.SpotListviewActivity;
import com.alekstodorov.savemyspot.ViewSpotActivity;
import com.alekstodorov.savemyspot.data.IReadable;
import com.alekstodorov.savemyspot.data.IUowData;
import com.alekstodorov.savemyspot.data.SpotsDatasource;
import com.alekstodorov.savemyspot.data.UowData;
import com.alekstodorov.savemyspot.models.SpotModel;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class HttpRequester {

	private Context currentContext;

	public HttpRequester(Context context) {

		currentContext = context;
	}

	public void postSpot(SpotModel spot) {

		Gson gson = new Gson();

		String spotAsJson = gson.toJson(spot);

		ProgressBar progressbar = (ProgressBar) ((ViewSpotActivity) currentContext)
				.findViewById(R.id.uploadProgressBar);

		progressbar.setVisibility(View.VISIBLE);

		new HttpPosterAsync().execute(spotAsJson);

		progressbar.setVisibility(View.GONE);
	}

	public void getSpots(String authCode) {

		new HttpGetterAsync().execute(authCode);
	}

	private class HttpGetterAsync extends AsyncTask<String, String, String> {

		private final String GET_URI = "http://savemyspot.apphb.com/api/spots/getSpots?authCode=";

		private final String RESULT_OK = "OK";

		@Override
		protected String doInBackground(String... params) {

			String authCode = params[0];

			IUowData uowData = new UowData(currentContext);
			((IReadable) uowData).open();
			SpotsDatasource spotDatasource = (SpotsDatasource) uowData
					.getSpots();

			try {

				String addressUri = GET_URI + authCode;

				HttpClient httpclient = new DefaultHttpClient(
						new BasicHttpParams());

				HttpGet httpGet = new HttpGet(addressUri);

				httpGet.setHeader("Content-type", "application/json");

				HttpResponse response = httpclient.execute(httpGet);

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					String spotsJsonList = EntityUtils.toString(response
							.getEntity());

					try {

						JSONArray jsonData = new JSONArray(spotsJsonList);

						for (int i = 0; i < jsonData.length(); i++) {

							SpotModel spot = parseJson(jsonData
									.getJSONObject(i));

							spotDatasource.create(spot);
						}

					} catch (JSONException e) {

						Log.e(HelpUtilities.TAG, e.getMessage());
					}

					return RESULT_OK;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private SpotModel parseJson(JSONObject jsonObject) throws JSONException {

			String title = jsonObject.getString("title");
			double latitude = jsonObject.getDouble("latitude");
			double longitude = jsonObject.getDouble("longitude");
			String authCodeString = jsonObject.getString("authCode");

			SpotModel spot = new SpotModel(title, latitude, longitude,
					authCodeString);

			return spot;
		}

		@Override
		protected void onPostExecute(String result) {

			if (result == RESULT_OK) {

				Intent intent = new Intent(currentContext,
						SpotListviewActivity.class);

				currentContext.startActivity(intent);

			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						currentContext);
				alert.setTitle(R.string.http_error_title);
				alert.setMessage(R.string.http_error_text);
				alert.setPositiveButton(R.string.http_error_ok, null);
				alert.create();
				AlertDialog theAlertDialog = alert.create();
				theAlertDialog.show();
			}
		}
	}

	private class HttpPosterAsync extends AsyncTask<String, String, String> {

		private final String POST_URI = "http://savemyspot.apphb.com/api/spots/postSpot";

		private final String RESULT_OK = "OK";

		@Override
		protected String doInBackground(String... params) {

			String userAsJson = params[0];
 
			try {

				HttpClient httpclient = new DefaultHttpClient(
						new BasicHttpParams());

				HttpPost httppost = new HttpPost(POST_URI);

				httppost.setHeader("Content-type", "application/json");

				httppost.setEntity(new StringEntity(userAsJson));

				HttpResponse response = httpclient.execute(httppost);

				String temp = EntityUtils.toString(response.getEntity());

				Log.v(HelpUtilities.TAG, temp);

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					return RESULT_OK;
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			if (result == RESULT_OK) {

				Log.v(HelpUtilities.TAG, "Success uploading spot!");

			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						currentContext);
				alert.setTitle(R.string.http_error_title);
				alert.setMessage(R.string.http_error_text);
				alert.setPositiveButton(R.string.http_error_ok, null);
				alert.create();
				AlertDialog theAlertDialog = alert.create();
				theAlertDialog.show();
			}
		}
	}
}
