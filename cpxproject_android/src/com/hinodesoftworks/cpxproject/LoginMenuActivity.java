package com.hinodesoftworks.cpxproject;

import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoginMenuActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_menu);
		
		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
		
		ParseUser user = ParseUser.getCurrentUser();
		
		//check if user is already logged in
		if (user != null)
		{
			Log.i("User", "Not Null");
			Intent sender;
			sender = new Intent(this, MainMenuActivity.class);
			startActivity(sender);
			
		}
		//else do nothing
	}
	
	public void onClick(View v)
	{
		Intent sender;
		
		switch (v.getId())
		{
		case R.id.login_new_user_btn:
			sender = new Intent(this, SignupActivity.class);
			startActivity(sender);
			break;
			
		case R.id.login_existing_user:
			sender = new Intent(this, LoginActivity.class);
			startActivity(sender);
			break;
			
		case R.id.login_anon_user:
			sender = new Intent(this, MainMenuActivity.class);
			startActivity(sender);
			break;
		}
	}
}
