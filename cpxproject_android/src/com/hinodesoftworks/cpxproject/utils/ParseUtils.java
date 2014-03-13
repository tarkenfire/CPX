package com.hinodesoftworks.cpxproject.utils;

public final class ParseUtils
{
	private static ParseUtils _instance = null;
	
	private ParseUtils()
	{
	}
	
	public static ParseUtils getInstance()
	{
		if (_instance == null)
		{
			_instance = new ParseUtils();
		}
		
		
		return _instance;
	}
	
	
	
}
