package com.yahoo.hooda_fci.dictionary_based_compression;

public class LZ77 {
	
	private static int numOfTags = 1 ;
	private static int tagLength = 0 ;
	private static int pointerLength =1; 
	private static int lengthLength =1; 
	
	public LZ77 ()
	{
		
	}
	////////////////////////////
	
	/**
	 * @param s	StringBuilder that I want to get a subString from it 
	 * @param end index that stop on it
	 * @return substring from end-15 (or zero if end<7) to the end
	 */
	private static String subString(String s, int end)
	{
		
		return s.substring( Math.max ( 0 , end-15) ,end);
	}
	
	/**
	 * put information about file before and after compression
	 * @param file 
	 * @param info
	 */
	private static void putInfo(String file, StringBuilder info) {
		
		info.setLength(0);
		
		if(pointerLength<2)
			pointerLength = 1;
		
		else if(pointerLength<4)
			pointerLength = 2;
		
		else if(pointerLength<8)
			pointerLength = 2;
		
		else
			pointerLength = 4;
		
		if(lengthLength<2)
			lengthLength = 1;
		
		else if(lengthLength<4)
			lengthLength = 2;
		
		else if(lengthLength<8)
			lengthLength = 3;
		
		else
			lengthLength = 4;
		
		
		tagLength = 8 + pointerLength + lengthLength ;
		
		String x="";
	
		x+="File size = " + ( file.length() * 8 ) +"\n";
		x+="After compression size = " + ( numOfTags * tagLength ) +"\n";
		x+="Number of tags = " + numOfTags +"\n";
		x+="Tag length = " + tagLength +"\n";
		x+="Pointer's length = " + pointerLength +"\n";
		x+="Length's length  = " + lengthLength +"\n";
		x+="Mixed char's length  = " + 8 +"\n";
		
		info.setLength(0);
		info.append(x);
		
	}
	
	//////////////////////////////////
	private static boolean equalsLastchar(int i, String file)
	{
		return i==-1 ? false: (file.charAt(i) == file.charAt(i-1)) ;	
	}
	////////
	private static int numOfChars(int i, String file)
	{
			int counter = 1;
			char c = file.charAt(i);
			
			for(int j=i+1; j<file.length() && counter<16 ; j++ )
			{
				if( c ==  file.charAt(j) )
					counter++;
				else 
					break;
			}
			
			return counter;
	}
	
	//////////////////////////////////
	/**
	 * @param file a file to compress
	 * @param info hold information about file before and after compression
	 * @return compressed file from tags each tag consists of three fields  pointer - length - mixed char
	 */
	public static String compress(String file, StringBuilder info)
	{
		StringBuilder result = new StringBuilder( "<0~0~" + file.charAt(0) + ">~" ); 
		numOfTags = 1 ;
		
		for(int i=1;i<file.length();i++)
		{
			int pointer = 0;
			int length = 0;
			
			if(equalsLastchar(i, file) && numOfChars(i, file) >=3 )
			{
				pointer=1;
				length=numOfChars(i, file);
				i+=length;
			}
			
			else	
			{
				StringBuilder tag = new StringBuilder("");
				tag.append(file.charAt(i));
				
				String subFile = subString(file, i);
				//System.out.println("subFile = " + subFile);
	
				
				int startOfTag=i;
				while ( subFile.lastIndexOf( tag.toString() ) != -1 )
				{
					//System.out.println("tag is found "+ tag.toString());
					pointer = startOfTag - subFile.lastIndexOf( tag.toString() ) - Math.max(0, startOfTag - 15);
					length = tag.length();
					i++;
					
					if(i<file.length())
						tag.append(file.charAt(i));
					else 
						break;
				}
			}
			
			if(lengthLength<length)
				lengthLength = length ;
			
			if(pointerLength < pointer)
				pointerLength = pointer;
			
				
			result.append("<"+pointer + "~");
			result.append(length + "~");
			
			if(i<file.length())
				result.append(file.charAt(i) + ">~");
			else
				result.append("null" + ">~");
			
			numOfTags++;
		}
		
		putInfo(file,info);
		
		result.setLength(result.length()-1);

		return result.toString();
	}
	////////////////////////////////////////////////////
	
	public static String deCompress(String file, StringBuilder info)
	{
		StringBuilder result = new StringBuilder( "" ); 
		file=file.replaceAll("<", "");
		file=file.replaceAll(">", "");
		String []arr = file.split("~");
		
		//System.out.println("arr \n");
		//
		//for(int i=0;i<arr.length;i++)
		//	System.out.print(arr[i] +"\n" );
		int pointer = 0;
		int length = 0;
		String mixed;
		for(int i=0;i<arr.length;i+=3)
		{
			StringBuilder tag = new StringBuilder("");
			pointer = Integer.parseInt( arr[i] );
			length = Integer.parseInt( arr[i+1] );
			
			if(arr[i+2].equals("null"))
				mixed="";	
			else
				mixed = arr[i+2];
			
			//System.out.println("pointer is " + pointer);
			//System.out.println("length is " + length);
			//System.out.println("res length is "+ result.length());
			//System.out.println(result);
			if(length > pointer )
			{
				for(int k=0;k<length/pointer;k++)
				{
					result.append( result.substring( result.length() - pointer) );
				}
			}
			else
			{
				result.append( result.substring( result.length() - pointer, result.length() - pointer + length ) );
			}
			
			result.append(mixed);
			
			if(length>lengthLength)
				lengthLength = length;
			if(pointer > pointerLength)
				pointerLength = pointer;
			
			
			//result.append("\n");
		}
		numOfTags = arr.length / 3; // because each tag consists of 3 indices
		
		putInfo(result.toString(), info);
		System.out.println("--result---"+result);
		return result.toString();
	}
}
