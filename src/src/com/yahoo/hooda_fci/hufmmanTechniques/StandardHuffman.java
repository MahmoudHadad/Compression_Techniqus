package com.yahoo.hooda_fci.hufmmanTechniques;

import java.util.ArrayList;

public class StandardHuffman {
	
	public static String compress ( String data)
	{
		// to hold each character and its probability
		ArrayList<String>chars = new ArrayList<String>();
		ArrayList<Integer>probs = new ArrayList<Integer>();
		
		
		
		
		return new String();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void quickSort(ArrayList<String>chars , ArrayList<Integer>probs)
	{
		recQuickSort(chars, probs, 0 , probs.size()-1);
	}
	
	private static void recQuickSort(ArrayList<String>chars , ArrayList<Integer>probs, int l, int r)
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
				Integer iTemp = probs.get(i);
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












	
	
}
