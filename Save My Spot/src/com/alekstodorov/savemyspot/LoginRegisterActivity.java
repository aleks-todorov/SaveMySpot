package com.alekstodorov.savemyspot;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
   
import com.alekstodorov.savemyspot.data.DbTools;
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
 
	DbTools dbTools = new DbTools(this); 
	EditText userNameEditText;
	EditText passwordEditText;
	TextView errorNotificationTextView;
	Button loginButton;
	Button registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_register);

		initComponents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_register, menu);
		return true;
	}

	private void initComponents() {
		userNameEditText = (EditText) findViewById(R.id.usernameEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		errorNotificationTextView = (TextView) findViewById(R.id.errorTextView);
		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.registerButton);
	}

	public void loginUser(View view) {
		clearErrorMessage();
		getInfoFromEditFields(); 
	}

	public void registerUser(View view) {
		clearErrorMessage();
		getInfoFromEditFields();  
	}

	private void getInfoFromEditFields() {
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
	}

	private void clearPasswordField() {
		passwordEditText.setText("");
	}

	private void clearErrorMessage() {

		errorNotificationTextView.setText("");
	}
}
