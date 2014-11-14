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

public class ModifiedHuffman {

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
				probs.set(f, probs.get(f) + (float)1 / dataLength  );

			}
			else 
			{
				chars.add( Character.toString(data.charAt(i) ) );
				probs.add((float) 1 / dataLength );
			}

		}
		

		
		float others = 0;
		
		for (int i = 0; i < probs.size(); ) {
			if(probs.get(i)<0.01)
			{
				others += probs.get(i);
				probs.remove(i);
				chars.remove(i);
			}
			else 
				i++;
		}
		
		probs.add(others);
		chars.add("~");



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
		chars.add("null");
		probs.add((float) 0);
		
		// sort data 
		quickSort(chars, probs);
		
		Map <String , String> charsAndCodes = new HashMap<String , String>();
		generateCodes( (ArrayList<String>)chars.clone(), (ArrayList<Float>) probs.clone() , charsAndCodes);
		
		// view each char and its prob
//		System.out.println("lists data");
//		for (int i = 0; i < chars.size(); i++) {
//			System.out.println(chars.get(i) + "_" + probs.get(i));
//		}
		
		
		// // view each char and its code 
		System.out.println("map data");
		for (String key: charsAndCodes.keySet()) {
			System.out.println(key + "/" + charsAndCodes.get(key));
		}

		StringBuilder result = new StringBuilder("");
		// create compressed data
		for (int i = 0; i < data.length(); i++) 
		{
			if(charsAndCodes.containsKey("" + data.charAt(i)))
			{
				result.append((String) charsAndCodes.get( "" + data.charAt(i) ) );
			}
			else
			{
				result.append((String) charsAndCodes.get( "~" ) );
				
				String ascii = Integer.toBinaryString(data.charAt(i)); 
				while(ascii.length()<7)
					ascii="0"+ ascii;
				
				result.append( ascii );
			}
			
		}
		
		// resize it to be divisible by 31  to be divided into integers
		if(result.length() % 31 !=0)
			result.append((String) charsAndCodes.get( "null" ) );
		
		
		while(result.length() % 31 !=0)
			result.append("0");
		
	//	System.out.println(result);
		// write on file
		try 
		{
			
			for(int i=0;i<result.length();i+=31)
			{
				int comp = Integer.parseInt(result.substring( i, i+31 ),2);
				oos.writeInt(comp);
//				System.out.println(i+"_"+comp);
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
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println(codes);
		
		// get code for each char
		chars.add("null");
		probs.add((float) 0);
		quickSort(chars, probs);
		Map <String , String> charsAndCodes = new HashMap<String , String>();
		
		generateCodes(chars, probs,charsAndCodes);
		
		System.out.println(chars);
		System.out.println(probs);
		for (String key: charsAndCodes.keySet()) {
			System.out.println(key + "/" + charsAndCodes.get(key));
		}
		
		String curCode="";		
		for(int i=0;i<codes.length();i++)
		{
			curCode = curCode + codes.charAt(i);
			//System.out.println("curcode"+curCode);
			
			if(charsAndCodes.containsValue(curCode))
			{
				String key2 = null;
				// search for the key for this value
				for ( String key: charsAndCodes.keySet()) {
					if(charsAndCodes.get(key).equals(curCode))
						key2=key;
				}

				if(key2.equals( "null" ))
					break;

				if(key2.equals("~"))
				{
					curCode = codes.substring(i+1, i+8);
					i+=7;
					
					key2 = ""+(char)Integer.parseInt(curCode, 2);
					System.out.println("others_"+key2);
				}
				result.append(key2);
				curCode="";
			}
			
		}

		return result.toString();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// generate map contains each sympole and its code
	private static void generateCodes( ArrayList<String>chars,ArrayList<Float>probs,Map<String,String>codes )
	{
		quickSort(chars, probs);
		
		if(chars.size() <3)
		{
			codes.put(chars.get(0), "0");
			codes.put(chars.get(1), "1");
			return;
		}
		
		String s1 = chars.get(0);
		String s2 = chars.get(1);
		String comp = chars.get(0) + chars.get(1);
		
		probs.set( 1 , probs.get( 0 ) +  probs.get( 1 ) );
		chars.set(1, comp);
		
		chars.remove(0);
		probs.remove(0);
//		for(int i=0;i<probs.size();i++)
//					System.out.println(chars.get(i) + "__" + probs.get(i));
//		System.out.println("---------");
//		
		generateCodes(chars, probs, codes);
		
		String compCode = codes.get(comp);
		
		codes.put(s1, compCode + "0");
		codes.put(s2, compCode + "1");
		codes.remove(comp);
	}


	//////////////////////////////
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


}
