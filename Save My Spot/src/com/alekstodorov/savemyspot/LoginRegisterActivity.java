package com.alekstodorov.savemyspot;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.alekstodorov.savemyspot.models.UserModel;
import com.alekstodorov.savemyspot.utils.DbTools;
import com.alekstodorov.savemyspot.utils.HelpUtilities;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginRegisterActivity extends Activity {

	DbTools dbTools = new DbTools(this);;
	EditText userNameEditText;
	EditText passwordEditText;
	TextView errorNotificationTextView;
	TextView loggedUsername;
	Button loginButton;
	Button registerButton;
	Button logoffButton;
	Button enterButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UserModel loggedUser = dbTools.getLoggedUser();

		if (loggedUser != null) {

			setContentView(R.layout.enter_layout);

			initEnterComponents();

			Log.i(HelpUtilities.TAG,
					"Currently logged user: " + loggedUser.getUsername());

			//loggedUsername.setText("TEST");

		} else {
			setContentView(R.layout.activity_login_register);
			
			initLoginComponents();

			Log.i(HelpUtilities.TAG, "Currently no user is logged in");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_register, menu);
		return true;
	}

	private void initLoginComponents() {
		userNameEditText = (EditText) findViewById(R.id.usernameEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		errorNotificationTextView = (TextView) findViewById(R.id.errorTextView);
		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.registerButton);
	}

	private void initEnterComponents() {
		loggedUsername = (TextView) findViewById(R.id.loggedUsername);
		logoffButton = (Button) findViewById(R.id.logoffButton);
		enterButton = (Button) findViewById(R.id.enterButton);
	}

	public void loginUser(View view) {

		clearErrorMessage();

		UserModel user = new UserModel();

		user = getInfoFromEditFields();

		UserModel existingUser = dbTools.getUserInfo(user.getUsername());

		if (existingUser != null) {

			if (user.getPassword().equals(existingUser.getPassword())) {

				// TODO implement login functionality

				user.setLoggedIn(true);

				dbTools.updateContact(user);

				Log.i(HelpUtilities.TAG, "Logging user " + user.getUsername());

				UserModel loggedUser = dbTools.getLoggedUser();

				if (loggedUser != null) {
					Log.i(HelpUtilities.TAG, "Currently logged user: "
							+ loggedUser.getUsername());
				} else {
					Log.i(HelpUtilities.TAG, "Currently no user is logged in");
				}

			} else {
				errorNotificationTextView
						.setText("Username or password is incorect!");
			}
		} else {
			errorNotificationTextView.setText("User does not exists!");
		}
	}

	public void registerUser(View view) {
		clearErrorMessage();
		getInfoFromEditFields();

		UserModel user = new UserModel();

		user = getInfoFromEditFields();

		UserModel existingUser = dbTools.getUserInfo(user.getUsername());

		if (existingUser == null) {

			user.setLoggedIn(true);
			dbTools.insertUser(user);
			Log.i(HelpUtilities.TAG, "Registering user " + user.getUsername());

			UserModel loggedUser = dbTools.getLoggedUser();

			if (loggedUser != null) {
				Log.i(HelpUtilities.TAG,
						"Currently logged user: " + loggedUser.getUsername());
			} else {
				Log.i(HelpUtilities.TAG, "Currently no user is logged in");
			}

		} else {
			errorNotificationTextView.setText("User already exists!");
		}
	}

	private UserModel getInfoFromEditFields() {

		UserModel user = new UserModel();

		String userName = userNameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		String encodedPassword = null;

		try {
			HelpUtilities.validateUsername(userName);
			HelpUtilities.validatePassword(password);

			try {
				encodedPassword = HelpUtilities.encodePassword(password);
				// Logging results for debugging purposes;
				Log.i(HelpUtilities.TAG, "Username: " + userName);
				Log.i(HelpUtilities.TAG, "Password: " + encodedPassword);

				user.setUsername(userName);
				user.setPassword(encodedPassword);

				return user;

			} catch (NoSuchAlgorithmException e) {
				errorNotificationTextView.setText("Invalid Password");
				clearPasswordField();
			} catch (UnsupportedEncodingException e) {
				errorNotificationTextView.setText("Invalid Password");
				clearPasswordField();
			}
		} catch (SecurityException e) {
			errorNotificationTextView.setText(e.getMessage());
			clearPasswordField();
		}

		return null;
	}

	private void clearPasswordField() {
		passwordEditText.setText("");
	}

	private void clearErrorMessage() {

		errorNotificationTextView.setText("");
	}
}
