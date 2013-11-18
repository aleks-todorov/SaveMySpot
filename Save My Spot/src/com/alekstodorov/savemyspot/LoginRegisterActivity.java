package com.alekstodorov.savemyspot;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.alekstodorov.savemyspot.data.IReadable;
import com.alekstodorov.savemyspot.data.IUowData;
import com.alekstodorov.savemyspot.data.UowData;
import com.alekstodorov.savemyspot.data.UsersDatasource;
import com.alekstodorov.savemyspot.models.UserModel;
import com.alekstodorov.savemyspot.utils.HelpUtilities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginRegisterActivity extends Activity {

	private IUowData uowData;
	private UsersDatasource usersDatasourse;
	private EditText userNameEditText;
	private EditText passwordEditText;
	private TextView errorNotificationTextView;
	private TextView loggedUsername;

	Button loginButton;
	Button registerButton;
	Button enterButton;
	Button logoffButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		uowData = new UowData(this);
		((IReadable) uowData).open();
		usersDatasourse = (UsersDatasource) uowData.getUsers();

		decideLayoutModel();
	}

	private void decideLayoutModel() {

		UserModel loggedUser = usersDatasourse.getLoggedUser();

		if (loggedUser != null) {

			setContentView(R.layout.enter_layout);

			initEnterComponents();

			loggedUsername.setText(getResources().getString(
					R.string.currently_logged_user)
					+ " " + loggedUser.getUsername());

			enterButton = (Button) findViewById(R.id.enterButton);
			logoffButton = (Button) findViewById(R.id.logoffButton);

		} else {
			setContentView(R.layout.activity_login_register);

			initLoginComponents();
		}
	}

	private void initLoginComponents() {
		userNameEditText = (EditText) findViewById(R.id.usernameEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		errorNotificationTextView = (TextView) findViewById(R.id.errorTextView);
		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.registerButton);
	}

	private void initEnterComponents() {
		loggedUsername = (TextView) findViewById(R.id.logedUserInfoTextView);
	}

	public void loginUserButtonClicked(View view) {

		clearErrorMessage();

		UserModel user = new UserModel();
		try {

			user = getInfoFromEditFields();

			UserModel existingUser = usersDatasourse.findByName(user
					.getUsername());

			if (existingUser != null) {

				if (user.getPassword().equals(existingUser.getPassword())) {

					user.setLoggedIn(true);

					usersDatasourse.updateContact(user);

					navigateIntoApp();

				} else {
					errorNotificationTextView.setText(getResources().getString(
							R.string.exception_incorect_username_or_password));
				}
			} else {
				errorNotificationTextView.setText(getResources().getString(
						R.string.exception_user_not_exists));
			}
		} catch (NoSuchAlgorithmException e) {
			errorNotificationTextView.setText(getResources().getString(
					R.string.exception_invalid_password));

			clearPasswordField();
		} catch (UnsupportedEncodingException e) {
			errorNotificationTextView.setText(getResources().getString(
					R.string.exception_invalid_password));
			clearPasswordField();

		} catch (SecurityException e) {
			errorNotificationTextView.setText(e.getMessage());
			clearPasswordField();
		}
	}

	private void navigateIntoApp() {

		Intent theIntent = new Intent(getApplication(),
				SpotListviewActivity.class);

		startActivity(theIntent);
	}

	public void registerUserButtonClicked(View view) {
		clearErrorMessage();

		UserModel user = new UserModel();
		try {

			user = getInfoFromEditFields();

			UserModel existingUser = usersDatasourse.findByName(user
					.getUsername());

			if (existingUser == null) {

				user.setLoggedIn(true);

				usersDatasourse.create(user);

				navigateIntoApp();

			} else {
				errorNotificationTextView.setText(getResources().getString(
						R.string.exception_already_exists));
			}

		} catch (NoSuchAlgorithmException e) {
			errorNotificationTextView.setText(getResources().getString(
					R.string.exception_invalid_password));

			clearPasswordField();
		} catch (UnsupportedEncodingException e) {
			errorNotificationTextView.setText(getResources().getString(
					R.string.exception_invalid_password));
			clearPasswordField();

		} catch (SecurityException e) {
			errorNotificationTextView.setText(e.getMessage());
			clearPasswordField();
		}
	}

	public void enterButtonClicked(View view) {

		navigateIntoApp();
	}

	public void logOffButtonClicked(View view) {

		UserModel loggedUser = usersDatasourse.getLoggedUser();

		loggedUser.setLoggedIn(false);

		usersDatasourse.updateContact(loggedUser);

		decideLayoutModel();
	}

	private UserModel getInfoFromEditFields() throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		UserModel user = new UserModel();

		String userName = userNameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		String encodedPassword = null;

		HelpUtilities.validateUsername(userName);
		HelpUtilities.validatePassword(password);

		encodedPassword = HelpUtilities.encodeString(password);
		String authCode = HelpUtilities.encodeString(userName + password);

		user.setUsername(userName);
		user.setPassword(encodedPassword);
		user.setAuthCode(authCode);

		return user;
	}

	private void clearPasswordField() {
		passwordEditText.setText("");
	}

	private void clearErrorMessage() {
		errorNotificationTextView.setText("");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (uowData instanceof IReadable) {
			((IReadable) uowData).open();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (uowData instanceof IReadable) {
			((IReadable) uowData).close();
		}
	}
}
