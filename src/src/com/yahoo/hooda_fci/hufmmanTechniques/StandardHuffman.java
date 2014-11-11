package com.yahoo.hooda_fci.hufmmanTechniques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StandardHuffman {

	public static String compress ( String data)
	{
		// to hold each character and its probability
		ArrayList<String>chars = new ArrayList<String>();
		ArrayList<Double>probs = new ArrayList<Double>();
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
				probs.add(1.0);
			}

		}
		// convert number of ocurrance to probabilities
		for(int i=0;i<probs.size();i++)
			probs.set(i, probs.get(i) /  dataLength);

		// sort data 
		quickSort(chars, probs);

		////////////////////////
		// Save each char with his probability



		//////////////////
		// get code for each char


		Map <Character , String> charsAndCodes = generateCodes(chars, probs);

		for (Character key: charsAndCodes.keySet()) {
			System.out.println(key + "/" + charsAndCodes.get(key));
		}

		StringBuilder result = new StringBuilder("");
		// create compressed data
		for (int i = 0; i < data.length(); i++) 
		{
			result.append((String) charsAndCodes.get( data.charAt(i) ) );
		}
		if(result.length() % 8 !=0)
			result.append((String) charsAndCodes.get( null ) );

		while(result.length() % 8 !=0)
			result.append("0");


		return result.toString();
	}






	///////////////////////////////////////////////////////

	// generate map contains each sympole and its code
	private static Map <Character , String> generateCodes( ArrayList<String>chars , ArrayList<Double>probs )
	{
		ArrayList<String>clonechars = ( ArrayList<String> )chars.clone();
		ArrayList<Double>cloneprobs = ( ArrayList<Double> )probs.clone();

		while(clonechars.size() >2)
		{
			updateArrSize(clonechars, cloneprobs);

			for(int i=0;i<clonechars.size();i++)
				System.out.println(clonechars.get(i) + "__" + cloneprobs.get(i));
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
	private static void updateArrSize ( ArrayList<String>chars , ArrayList<Double>probs )
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
	private static void quickSort(ArrayList<String>chars , ArrayList<Double>probs)
	{
		recQuickSort(chars, probs, 0 , probs.size()-1 );
	}

	private static void recQuickSort(ArrayList<String>chars , ArrayList<Double>probs, int l, int r)
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
				Double iTemp = probs.get(i);
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
