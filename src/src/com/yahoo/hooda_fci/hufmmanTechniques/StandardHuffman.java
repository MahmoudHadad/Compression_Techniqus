package com.yahoo.hooda_fci.hufmmanTechniques;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StandardHuffman {

	public static void compress ( String data , File outPut)
	{
		// to hold each character and its probability
		ArrayList<String>chars = new ArrayList<String>();
		ArrayList<Float>probs = new ArrayList<Float>();
		int dataLength = data.length();

		// add chars and their probabilities
		for (int i = 0; i < dataLength; i++) 
		{	
			if( chars.contains( Character.toString(data.charAt(i) ) ) )
			{
				int f = chars.indexOf( Character.toString(data.charAt(i) ) ) ;

				probs.set(f, probs.get(f) + 1 );

			}

			else 
			{
				chars.add( Character.toString(data.charAt(i) ) );
				probs.add((float) 1.0);
			}

		}
		// convert number of ocurrance to probabilities
		for(int i=0;i<probs.size();i++)
			probs.set(i, probs.get(i) /  dataLength);

		// sort data 
		quickSort(chars, probs);

		////////////////////////
		// Save each char with its probability on the file
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try 
		{
			 fos = new FileOutputStream(outPut ,  true);
			 oos = new ObjectOutputStream(fos);
			int s = chars.size();
			oos.writeInt( s ); 
			//System.out.println("size"+s);
			//System.out.println("length after adding size_"+outPut.length());
			for (int i = 0; i < chars.size(); i++) {
				
				oos.writeChar(  chars.get(i).charAt(0));
				oos.writeFloat(probs.get(i));
				//System.out.println("length after adding prob and char_"+outPut.length());
			}	
//			oos.close();
//			fos.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("length after adding all probs_"+outPut.length());
		
		//////////////////
		// get code for each char

		Map <Character , String> charsAndCodes = generateCodes(chars, probs);

//		for (Character key: charsAndCodes.keySet()) {
//			System.out.println(key + "/" + charsAndCodes.get(key));
//		}

		StringBuilder result = new StringBuilder("");
		// create compressed data
		for (int i = 0; i < data.length(); i++) 
		{
			result.append((String) charsAndCodes.get( data.charAt(i) ) );
		}
		
		// resize it to be divisible by 31  to be divided into integers
		if(result.length() % 31 !=0)
			result.append((String) charsAndCodes.get( null ) );

		while(result.length() % 31 !=0)
			result.append("0");
		
	//	System.out.println(result);
		// write on file
		try 
		{
			//FileOutputStream fos = new FileOutputStream(outPut ,  true);
			//ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			for(int i=0;i<result.length();i+=31)
			{
				int comp = Integer.parseInt(result.substring( i, i+31 ),2);
				oos.writeInt(comp);
				//System.out.println(i+"_"+comp);
//				System.out.println("_length after adding comp"+outPut.length());
			}
			oos.close();
			fos.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("result length_"+result.length());
		//System.out.println("length after adding codes "+outPut.length());
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static String deCompress ( File input )
	{
		StringBuilder result = new StringBuilder("");
		StringBuilder codes = new StringBuilder("");
		ArrayList<String>chars = new ArrayList<String>();
		ArrayList<Float>probs = new ArrayList<Float>();
		int numOfsymbols;
		
		try 
		{
			FileInputStream fis = new FileInputStream(input);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			numOfsymbols = ois.readInt();
			
			for (int i = 0; i < numOfsymbols; i++)
			{
//				System.out.println(ois.readChar());
//				System.out.println(ois.readFloat());
				chars.add(String.valueOf(ois.readChar()));
				probs.add(ois.readFloat());
			}
//			System.out.println(outPut.length());
	//		System.out.println("pos_"+fis.getChannel().position());
			
			
			while (true)
			{
				try
				{
					String currentCode = Integer.toBinaryString(ois.readInt() );
					while(currentCode.length()<31)
						currentCode = "0" + currentCode;
					
					codes.append(currentCode);
					
					//System.out.println(Integer.toBinaryString(ois.readInt()));
					//System.out.println( ois.readInt() );
				}
				catch(Exception e)
				{
					break;
				}
			}
			
			ois.close();
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(codes);
		
		// get code for each char
		Map <Character , String> charsAndCodes = generateCodes(chars, probs);
		System.out.println(chars);
		System.out.println(probs);
//		for (Character key: charsAndCodes.keySet()) {
//			System.out.println(key + "/" + charsAndCodes.get(key));
//		}
		
		String curCode="";		
		for(int i=0;i<codes.length();i++)
		{
			curCode = curCode + codes.charAt(i);
			//System.out.println("curcode"+curCode);
			
			if(charsAndCodes.containsValue(curCode))
			{
				Character key2 = null;
				for ( Character key: charsAndCodes.keySet()) {
					if(charsAndCodes.get(key).equals(curCode))
						key2=key;
				}
				
				if(key2 == null)
					break;
				
				result.append(key2);
				curCode="";
			}
		}
		
		
		
		return result.toString();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// generate map contains each sympole and its code
	private static Map <Character , String> generateCodes( ArrayList<String>chars , ArrayList<Float>probs )
	{
		ArrayList<String>clonechars = ( ArrayList<String> )chars.clone();
		ArrayList<Float>cloneprobs = ( ArrayList<Float> )probs.clone();

		while(clonechars.size() >2)
		{
			updateArrSize(clonechars, cloneprobs);

//			for(int i=0;i<clonechars.size();i++)
//				System.out.println(clonechars.get(i) + "__" + cloneprobs.get(i));
		}

		if(clonechars.get(0).length() > clonechars.get(1).length() )
			clonechars.set(0, "null"+clonechars.get(0));
		else
			clonechars.set(1, "null"+clonechars.get(1));
		// final 2 sets 
		//		for(int i=0;i<clonechars.size();i++)
		//			System.out.println(clonechars.get(i) + "__" + cloneprobs.get(i));
		// generate codes
		Map <Character , String> charsAndCodes = new HashMap<Character , String>();

		splitAndCode( clonechars.get(0), "0", charsAndCodes ) ;
		splitAndCode( clonechars.get(1), "1", charsAndCodes ) ;

		return charsAndCodes;
	}


	//////////////////////////////
	// add last to indecies and sort array again
	private static void updateArrSize ( ArrayList<String>chars , ArrayList<Float>probs )
	{
		if( chars.size() < 3 )
			return ;


		//			for(int i=0;i<probs.size();i++)
		//				System.out.println(chars.get(i) + "__" + probs.get(i));


		chars.set( 1 , chars.get( 1 ) +  chars.get( 0 ) );

		probs.set( 1 , probs.get( 1 ) +  probs.get( 0 ) );

		chars.remove(0);
		probs.remove(0);

		quickSort(chars, probs);

	}

	//////////////////////////////
	//sort on probs
	private static void quickSort(ArrayList<String>chars , ArrayList<Float>probs)
	{
		recQuickSort(chars, probs, 0 , probs.size()-1 );
	}

	private static void recQuickSort(ArrayList<String>chars , ArrayList<Float>probs, int l, int r)
	{
		if(l>=r)return;

		int i=l, j=r;
		int piv=(l+r)/2;

		while(i<j)
		{
			while(probs.get(i)<probs.get(piv))
				i++;

			while(probs.get(j)>probs.get(piv))
				j--;

			// swapping

			if(i<=j)
			{
				Float iTemp = probs.get(i);
				probs.set(i, probs.get(j) ) ;
				probs.set(j, iTemp);

				String sTemp = chars.get(i);
				chars.set(i, chars.get(j) ) ;
				chars.set(j, sTemp);
			}

			i++;
			j--;

		}

		if (l < j) 
			recQuickSort(chars,probs , l, j); 
		if (i < r) 
			recQuickSort(chars,probs, i, r);
	}

	/////////////////////////////////


	// split and generate code

	private static void splitAndCode (String set , String code, Map <Character , String> charsAndCodes )
	{
		if(set.length() == 1)
		{
			charsAndCodes.put( set.charAt(0) , code);
			return;
		}
		if(set.equals("null"))
			charsAndCodes.put( null , code);
		else
		{
			splitAndCode( set.substring( 0, set.length() - 1 ) ,code + "1" , charsAndCodes );
			splitAndCode( ""+set.charAt( set.length() - 1 ), code + "0" , charsAndCodes );
		}

	}
	////////////////////////////////////////////

}
