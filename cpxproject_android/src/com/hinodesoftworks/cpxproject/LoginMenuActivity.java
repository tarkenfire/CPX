package com.hinodesoftworks.cpxproject;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LoginMenuActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_menu);
		
		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
		
		ParseUser user = ParseUser.getCurrentUser();
		
		ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
		

		
		//check if user is already logged in
		if (user != null)
		{

			
			Log.i("User", "Not Null");
			Intent sender;
			sender = new Intent(this, MainMenuActivity.class);
			startActivity(sender);
			
		}
		
		if (user == null && !isConnected)
		{
			Toast.makeText(this, "Internet connection required for login.", Toast.LENGTH_LONG).show();
			return;
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
		}
	}
}
