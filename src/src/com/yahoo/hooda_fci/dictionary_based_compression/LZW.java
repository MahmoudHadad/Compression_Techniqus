package com.yahoo.hooda_fci.dictionary_based_compression;
import java.util.*;

public class LZW {
	
	
	public static String compress(String file )
	{
		StringBuilder result = new StringBuilder("");
		int iterator = 0 ;
		
		ArrayList<String>table = new ArrayList<String>();
		
		for(int i=0;i<128;i++)
			table.add(Character.toString((char)i));
//		for(int i=0;i<128;i++)
//			System.out.println(table.get(i));
	
		for(int i=0;i<file.length();)
		{
			boolean isEnd=true;
			StringBuilder cur = new StringBuilder("");
			iterator = -1;	
			for (int j = i; j < file.length(); j++) 
			{
				cur.setLength(0);
				//cur.append(file.charAt(j));
				cur.append( file.substring(i, j+1) );
				if(! table.contains(cur.toString()))
				{
					isEnd=false;
					table.add(cur.toString());
					break;
				}
				
			}

			if(! isEnd)
				cur.deleteCharAt(cur.length()-1);
				
			for (int k = 0; k < table.size(); k++) {

				if(table.get(k).equals(cur.toString()))
				{
					iterator = k;
					break;
				}	
			}	
			//System.out.println(i+"-"+cur+"-"+iterator);
			
			result.append(Integer.toString(iterator) +",");

			i+=cur.length();
			
		}
		
		return result.toString();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String deCompress(String file )
	{
		ArrayList<String>table = new ArrayList<String>();
		for(int i=0;i<128;i++)
			table.add(Character.toString((char)i));
		
		StringBuilder result = new StringBuilder("");
		
		String []arr = file.split(",");
		int index = -1;
		String cur = "", priv=table.get(Integer.parseInt(arr[0]));
		result.append(priv);
		//System.out.println("0-result-"+result);
		for (int i = 1; i < arr.length; i++) 
		{	
			cur = "";
			index = Integer.parseInt(arr[i]);
			
			if (index >= table.size()) 
				cur = priv + Character.toString( priv.charAt(0) ) ;
			
			else
				cur = table.get(index);
		//	System.out.println("cur-"+cur);
			result.append(cur);
			table.add( priv + Character.toString( cur.charAt(0) ) );
			
			priv = cur;
			//System.out.println(i+"-"+index+"-result-"+result+"-"+table.size());
		}
		
		return result.toString();
	}
	
	
}
