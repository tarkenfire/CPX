package com.hinodesoftworks.cpxproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.parse.Parse;
import com.parse.ParseAnalytics;

public class LoginActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
