package com.hinodesoftworks.cpxproject;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainMenuActivity extends Activity
{
	public static final String DATA_SUFFIX = "_data";
	
	ListView pubDataList;
	ListView userDataList;
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		//TODO: Refactor name. This isn't really a menu, sounded better in my head
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pubDataList = (ListView)findViewById(R.id.main_public_data_list);
		userDataList = (ListView) findViewById(R.id.main_private_data_list);
		
		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
		
		getPublicData();
		
		
		getUserData();
	}
	
	
	public void getPublicData()
	{
		ParseQuery<ParseObject> pubObj = ParseQuery.getQuery("PublicData");
		
		pubObj.findInBackground(new FindCallback<ParseObject>() 
				{
				    public void done(List<ParseObject> data, ParseException e) 
				    {
				        if (e == null) 
				        {
				           onPublicDataRetuned(data);
				        }
				        else 
				        {
				            e.printStackTrace();
				        }
				    }
				});	
	}
	
	public void onPublicDataRetuned(List<ParseObject> data)
	{
		ArrayList<String> pubData = new ArrayList<String>();
		
		for (ParseObject item : data)
		{
			pubData.add(item.getString("data"));
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pubData.toArray(new String[pubData.size()]));
		
		pubDataList.setAdapter(adapter);
	}
	
	public void getUserData()
	{
		ParseUser user = ParseUser.getCurrentUser();
		String username = user.getUsername();
		
		ParseQuery<ParseObject> userObj = ParseQuery.getQuery(username + DATA_SUFFIX);
		
		userObj.findInBackground(new FindCallback<ParseObject>() 
				{
				    public void done(List<ParseObject> data, ParseException e) 
				    {
				        if (e == null) 
				        {
				           onUserDataReturned(data);
				           Log.i("Data", "Data returned");
				        }
				        else 
				        {
				            e.printStackTrace();
				        }
				    }
				});	
		
		
	}
	
	public void onUserDataReturned(List<ParseObject> data)
	{
		if (data.size() == 0)
		{
			//data not created, create data and try again.
			createUserData();
			return;
		}
		
		ArrayList<String> userData = new ArrayList<String>();
		
		for (ParseObject item : data)
		{
			userData.add(item.getString("data"));
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userData.toArray(new String[userData.size()]));
		
		userDataList.setAdapter(adapter);
	}
	
	public void createUserData()
	{
		ParseUser user = ParseUser.getCurrentUser();
		String username = user.getUsername();
		
		for (int i = 0; i < 3; i++)
		{
			ParseObject dummy = new ParseObject(username + DATA_SUFFIX);
			dummy.put("data", "User: " + username + " Private data " + i);
			dummy.saveInBackground();
		}

		Log.i("Data Creation", "Data Created");
		
		getUserData();
	}
	
}
