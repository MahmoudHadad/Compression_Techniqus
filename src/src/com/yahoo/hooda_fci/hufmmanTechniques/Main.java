package com.yahoo.hooda_fci.hufmmanTechniques;

import java.util.ArrayList;


public class Main {
	
	public static void main (String [] args)
	{
		
		//System.out.println(Integer.toBinaryString( (int)'a' ) );
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(9);
		a.add(5);
		a.add(7);
		ArrayList<String> s = new ArrayList<String>();
		s.add("9");
		s.add("5");
		s.add("7");
		StandardHuffman.quickSort(s, a);
		
		System.out.println( a + "\n" + s);
		
		
		
		
	}
	
	
}
