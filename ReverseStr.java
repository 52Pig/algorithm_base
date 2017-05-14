package org.apache.hadoop.examples;



//输入：I am a student.
//输出:student a am I.
public class ReverseStr {

	public static void main(String[] args){
		String inputStr = "I am a student";
		System.out.println(reverseStr(inputStr));
	}
	
	public static String reverseStr(String inStr){
		String result = "";
		String[] strs = inStr.split(" ");
		
		for(int i=strs.length-1; i>=0; i--){
			result += strs[i] + " ";
		}
		
		
		return result.trim();
	}
	
	
	
}
