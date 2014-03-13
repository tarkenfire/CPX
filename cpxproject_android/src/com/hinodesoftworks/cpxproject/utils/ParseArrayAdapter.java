package com.hinodesoftworks.cpxproject.utils;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hinodesoftworks.cpxproject.R;
import com.parse.ParseObject;

public class ParseArrayAdapter extends ArrayAdapter<ParseObject>
{
	Context ctx;
	int resId;
	List<ParseObject> objects;
	
	public ParseArrayAdapter(Context context, int resource, List<ParseObject> objects)
	{
		super(context, resource, objects);
		
		this.ctx = context;
		this.resId = resource;
		this.objects = objects;
	}

	@Override
	public ParseObject getItem(int position)
	{
		return objects.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
	           LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
	           convertView = inflater.inflate(resId, parent, false);
		}
		
		ParseObject curItem = getItem(position);
		
		TextView textView = (TextView)convertView.findViewById(R.id.list_item_text);
		textView.setText(curItem.getString("data"));
		
		
		return convertView;
	}
	
	

}
