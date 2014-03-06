package com.hinodesoftworks.cpxproject;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainMenuActivity extends Activity
{
	public static final String DATA_SUFFIX = "_data";
	
	private ParseObject publicObject;
	private ParseObject userObject;
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		//TODO: Refactor name. This isn't really a menu, sounded better in my head
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		ParseObject obj;
		
		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
	}
	
	
	public ParseObject getPublicObject()
	{
		
		return null;
	}
	
	public ParseObject getUserObject()
	{
		return null;
	}
	
}
