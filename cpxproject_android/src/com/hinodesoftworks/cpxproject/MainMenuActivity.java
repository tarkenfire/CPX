package com.hinodesoftworks.cpxproject;

import java.util.List;

import com.hinodesoftworks.cpxproject.utils.NetworkUtils;
import com.hinodesoftworks.cpxproject.utils.ParseArrayAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends Activity
{
	public enum EditMode {MODE_PUBLIC, MODE_PRIVATE}
	
	public static final String DATA_SUFFIX = "_data";
	
	ListView userDataList;
	
	Button privateButton;
	
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
		
		privateButton = (Button) findViewById(R.id.button_add_private);
		userDataList = (ListView) findViewById(R.id.main_private_data_list);
		userDataList.setLongClickable(true);
		
		
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
		
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		getUserData();
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

	
	public void getUserData()
	{
		ParseUser user = ParseUser.getCurrentUser();
		String username = user.getUsername();
		
		ParseQuery<ParseObject> userObj = ParseQuery.getQuery(username + DATA_SUFFIX);
		
		userObj.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		
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
				        	Toast.makeText(MainMenuActivity.this, "No data could be loaded.",Toast.LENGTH_LONG).show();
				            e.printStackTrace();
				        }
				    }
				});	
		
		
	}
	
	public void onUserDataReturned(List<ParseObject> data)
	{
		if (data.size() == 0) return;
		
		ParseArrayAdapter adapter = new ParseArrayAdapter(this, R.layout.list_gw_item, data);
		
		userDataList.setAdapter(adapter);
	}
	
	protected void deleteItem()
	{
		ParseArrayAdapter adapter = (ParseArrayAdapter) userDataList.getAdapter();
				
		
		//delete item
		if (adapter != null)
		{	
			if (NetworkUtils.isConnected(this))
			{
				ParseObject itemToDelete = adapter.getItem(selectedIndex);
				itemToDelete.deleteInBackground();
			}
			else
			{
				ParseObject itemToDelete = adapter.getItem(selectedIndex);
				itemToDelete.deleteEventually();
			}
		}
		
		//refresh lists.
		getUserData();
		
	}
	
	protected void onItemEdit()
	{
		ParseArrayAdapter adapter = (ParseArrayAdapter) userDataList.getAdapter();
		
		//get item id and send intent with two flags.
		if (adapter != null)
		{
			ParseObject itemToEdit = adapter.getItem(selectedIndex);
			String editID = itemToEdit.getObjectId();
			
			Intent sendingIntent = new Intent(this, AddItemActivity.class);
			sendingIntent.putExtra("editID", editID);
			sendingIntent.putExtra("flag_edit_mode", true);
			
			startActivity(sendingIntent);
		}
	}
	
	public void onClick(View v)
	{
		Intent sender = new Intent(this, AddItemActivity.class);
		
		startActivity(sender);	
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
					onItemEdit();
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
