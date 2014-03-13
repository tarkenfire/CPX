package com.hinodesoftworks.cpxproject;

import java.util.List;

import com.hinodesoftworks.cpxproject.utils.ParseArrayAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends Activity
{
	public enum EditMode {MODE_PUBLIC, MODE_PRIVATE}
	
	public static final String DATA_SUFFIX = "_data";
	
	ListView pubDataList;
	ListView userDataList;
	
	//this is crude, but it works.
	EditMode currentMode = EditMode.MODE_PUBLIC;
	int selectedIndex; 

	boolean anonFlag;
	
	ActionMode aMode;
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		//TODO: Refactor name. This isn't really a menu, sounded better in my head
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pubDataList = (ListView)findViewById(R.id.main_public_data_list);
		userDataList = (ListView) findViewById(R.id.main_private_data_list);
		
		pubDataList.setLongClickable(true);
		userDataList.setLongClickable(true);
		
		//set listeners
		pubDataList.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int pos, long id)
			{
				aMode = MainMenuActivity.this.startActionMode(new ItemActionBarCallback());
				
				//brute force tracking system.
				selectedIndex = pos;
				
				currentMode = EditMode.MODE_PUBLIC;
				
				return true;
			}
		});
		
		userDataList.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int pos, long id)
			{
				aMode = MainMenuActivity.this.startActionMode(new ItemActionBarCallback());
				
				//brute force tracking system.
				selectedIndex = pos;
				currentMode = EditMode.MODE_PRIVATE;
				
				return true;
			}
		});

		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
				
		getPublicData();

		//check if anon flag is set, if so, no user data exists.
		
		Intent sender = getIntent();
		anonFlag = sender.getBooleanExtra("flag_anon", false);
		
		if (anonFlag == false)
		{
			getUserData();
		}
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Intent sender = getIntent();
		boolean anonFlag = sender.getBooleanExtra("flag_anon", false);
		
		//hack fix to prevent anon users from getting private data.
		if (anonFlag == true)
		{
			ParseUser.logOut();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_log_out)
		{	
			//log out user
			ParseUser.logOut();
			
			//finally, return to login screen.
			Intent returnIntent = new Intent(this, LoginMenuActivity.class);
			returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			returnIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			startActivity(returnIntent);
			
			//inform user of logout
			Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
			
			return true;
		}
		else
		{
			return super.onOptionsItemSelected(item);
		}
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
				        	Log.i("data", "Public Data Returned");
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
		ParseArrayAdapter adapter = new ParseArrayAdapter(this, R.layout.list_gw_item, data);
		
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
				           Log.i("Data", "User Data returned");
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
		
		ParseArrayAdapter adapter = new ParseArrayAdapter(this, R.layout.list_gw_item, data);
		
		userDataList.setAdapter(adapter);
	}
	
	public void createUserData()
	{
		
		//dummy data
		String[] items = 
			{
				"Glob of Ectoplasm [Exotic Crafting Material] - 37s, 64c", 
				"Superior Rune of Melandru [Exotic Rune] - 4g, 78s",
				"Black Quaggan Tonic [Common Consumable] - 27c"
			};
		
		ParseUser user = ParseUser.getCurrentUser();
		String username = user.getUsername();
		
		for (int i = 0; i < 3; i++)
		{
			ParseObject dummy = new ParseObject(username + DATA_SUFFIX);
			dummy.put("data", items[i]);
			dummy.setACL(new ParseACL(user));
			dummy.saveInBackground();
		}

		Log.i("Data Creation", "Data Created");
		
		getUserData();
	}
	
	protected void deleteItem()
	{
		ParseArrayAdapter adapter = null; 
		boolean pubRefreshFlag = false;
		boolean priRefreshFlag = false;
		
		switch (currentMode)
		{
			case MODE_PRIVATE:
				adapter = (ParseArrayAdapter) userDataList.getAdapter();
				priRefreshFlag = true;
				break;
			case MODE_PUBLIC:
				adapter = (ParseArrayAdapter) pubDataList.getAdapter();
				pubRefreshFlag = true;
				break;
		}
		
		//delete item
		if (adapter != null)
		{
			ParseObject itemToDelete = adapter.getItem(selectedIndex);
			itemToDelete.deleteInBackground();
		}
		
		//refresh lists if needed.
		if (pubRefreshFlag)
			getPublicData();
		
		if (priRefreshFlag)
			getUserData();
		
	}
	
	protected void onItemEdit()
	{
		
	}
	
	class ItemActionBarCallback implements ActionMode.Callback
	{
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item)
		{
			switch(item.getItemId())
			{
				case R.id.action_delete:
					MainMenuActivity.this.deleteItem();
					break;
					
				case R.id.action_edit:
					break;
			}
	
			mode.finish();
			return false;
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{
			mode.getMenuInflater().inflate(R.menu.context_menu, menu);
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode)
		{
			
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu)
		{
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
}
