package com.hinodesoftworks.cpxproject;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItemActivity extends Activity
{
	boolean isPublic;
	
	EditText itemName, gold, silver, copper;
	Spinner itemType, rarity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		
		Parse.initialize(this, "Mfuibt410lvJAj0eesG0cTdYRRk6LkW9bWoQYvdZ", "NtfbH5hXcVCp1t1GBgK3FxUQpP2rtVLaKsa9FQB2");
		
		ActionBar actionBar = getActionBar();
		isPublic = this.getIntent().getBooleanExtra("flag_is_public", true);
		
		if (isPublic)
		{
			actionBar.setTitle("Add New Public Item");
		}
		else
		{
			actionBar.setTitle("Add New Private Item");
		}
		
		//grab ui handles
		itemName = (EditText)findViewById(R.id.add_item_name);
		gold = (EditText)findViewById(R.id.add_gold);
		silver = (EditText)findViewById(R.id.add_silver);
		copper = (EditText)findViewById(R.id.add_copper);
		
		itemType = (Spinner)findViewById(R.id.add_type_spinner);
		rarity = (Spinner)findViewById(R.id.add_rarity_spinner);
		
		//set spinner data
		ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		ArrayAdapter<CharSequence> rarityAdapter = ArrayAdapter.createFromResource(this, R.array.rarity_array, android.R.layout.simple_spinner_item);
		rarityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		itemType.setAdapter(typeAdapter);
		rarity.setAdapter(rarityAdapter);
		
		//check for edit flag to pre-populate data and item.
	}
	
	public void onClick(View v)
	{
		//validate values. Negative money values and values above 3000 are invalid.
		//inputType is number for the edit texts, so the values will always be numbers.
		
		if (!numberInCorrectRange(Integer.parseInt(gold.getText().toString().trim())))
		{
			Toast.makeText(this, "Gold must be in the range of 0-3000", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (!numberInCorrectRange(Integer.parseInt(silver.getText().toString().trim())))
		{
			Toast.makeText(this, "Silver must be in the range of 0-3000", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (!numberInCorrectRange(Integer.parseInt(copper.getText().toString().trim())))
		{
			Toast.makeText(this, "Copper must be in the range of 0-3000", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//gather remaining form data into parse object.
		if (isPublic)
		{
			ParseObject publicObject = new ParseObject("PublicData");
			publicObject.put("name", itemName.getText().toString().trim());
			publicObject.put("rarity", itemType.getSelectedItem().toString());
			publicObject.put("type", rarity.getSelectedItem().toString());
			publicObject.put("gold", gold.getText().toString().trim());
			publicObject.put("silver", silver.getText().toString().trim());
			publicObject.put("copper", copper.getText().toString().trim());
			
			publicObject.saveInBackground();
			Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
			this.finish();
		}
		else //private user add
		{
			ParseUser user = ParseUser.getCurrentUser();
			ParseObject userObject = new ParseObject(user.getUsername() + MainMenuActivity.DATA_SUFFIX);
			
			userObject.put("name", itemName.getText().toString().trim());
			userObject.put("rarity", itemType.getSelectedItem().toString());
			userObject.put("type", rarity.getSelectedItem().toString());
			userObject.put("gold", gold.getText().toString().trim());
			userObject.put("silver", silver.getText().toString().trim());
			userObject.put("copper", copper.getText().toString().trim());
			
			//set ACL in case it is not set yet
			userObject.setACL(new ParseACL(user));
			
			userObject.saveInBackground();
			Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
			this.finish();
		}
		
	}
	
	boolean numberInCorrectRange(int number)
	{
		return (number > -1 && number < 3001);
	}

}
