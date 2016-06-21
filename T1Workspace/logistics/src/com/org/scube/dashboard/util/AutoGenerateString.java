package com.org.scube.dashboard.util;

import java.util.Random;

public class AutoGenerateString {

	private static String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	
    public static String randomString( int len ){
    	  
   	   StringBuilder sb = new StringBuilder( len );
   	   for( int i = 0; i < len; i++ ) 
   	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
   	   return sb.toString();
   	}
}
