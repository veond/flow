package com.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matrix {
	public static void main(String[] args) {
		
       
		
		String s = "-11";
		Pattern pattern = Pattern.compile("[1-9][0-9]?");
        Matcher isNum = pattern.matcher(s);
        
        System.out.println(isNum.matches());
        
        
		
		
		
	}

}
