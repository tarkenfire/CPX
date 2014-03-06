package com.hinodesoftworks.cpxproject;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity
{
	EditText user;
	EditText pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		user = (EditText)findViewById(R.id.sign_in_username);
		pass = (EditText)findViewById(R.id.sign_in_password);
		
		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
	}
	
	public void onClick(View v)
	{
		ParseUser.logInInBackground(user.getText().toString(), pass.getText().toString(), new LogInCallback()
		{
			@Override
			public void done(ParseUser user, ParseException e)
			{
				if (user != null)
				{
					onSignInSuccess();
				}
				else
				{
					sendAlert("Login Failure", "Login failed. Please check username and password, then try again.");
					return;
				}
			}
		}
		);
	}
	
	public void onSignInSuccess()
	{
		Intent sender = new Intent(this, MainMenuActivity.class);
		startActivity(sender);
	}
	
	public void sendAlert(String title, String message)
	{
		AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
		
		adBuilder.setTitle(title);
		
		adBuilder.setMessage(message);
		
		adBuilder.setCancelable(false);
		adBuilder.setPositiveButton("Ok", 
				new DialogInterface.OnClickListener()
				{	
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						//do nothing
					}
				}
				);
		
		AlertDialog dialog = adBuilder.create();
		dialog.show();
	}


}
