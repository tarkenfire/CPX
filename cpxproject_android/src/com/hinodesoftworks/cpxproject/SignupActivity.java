package com.hinodesoftworks.cpxproject;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends Activity
{
	EditText username;
	EditText pass;
	EditText passConfirm;
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		//grab UI handles
		username = (EditText)findViewById(R.id.signup_username);
		pass = (EditText)findViewById(R.id.signup_password);
		passConfirm = (EditText)findViewById(R.id.signup_password_confirm);
		
		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
	}
	
	public void onClick(View v)
	{
		//alerts aren't great for user feedback, but that's next week not this week.
		
		//check for blank fields, all are required
		if (isEmpty(username) || isEmpty(pass) || isEmpty(passConfirm))
		{
			sendAlert("Empty Field", "All fields must be completed.");
			return;
		}
		
		//check for password matches
		if (pass.getText().toString().toString().equals(passConfirm.getText().toString().trim()))
		{
			//attempt to register user
			ParseUser user = new ParseUser();
			user.setUsername(username.getText().toString());
			user.setPassword(pass.getText().toString());
			
			user.signUpInBackground(new SignUpCallback()
			{
				@Override
				public void done(ParseException e)
				{
					//TODO no user feedback that signup is occuring.
					if (e == null)
					{
						Log.i("Signup", "SUCCESS");
						
					}
					else
					{
						Log.i("Signup", "FAILURE");
					}
					
					Log.i("ParseUser", ParseUser.getCurrentUser() == null ? "Is Null" : "Is Not Null");
				}
				
			});
		}
		else
		{
			sendAlert("Password Mismatch", "Passwords do not match.");
			return;
		}
	}
	
	
	public boolean isEmpty(EditText et)
	{
		return et.getText().toString().trim().length() == 0;
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
