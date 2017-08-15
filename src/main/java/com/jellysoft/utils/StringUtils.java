package com.jellysoft.utils;

public class StringUtils {

	public static boolean isNullOrEmpty( String text ){
		
		if( text == null || text.length() == 0 ){
			return true;
		}
		return false;
		
	}
	
}	
