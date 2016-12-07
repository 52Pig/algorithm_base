package com.youku.data.cleaner;


/**
 * str="sdjkdngjskndsgfngj"
 * 判断字符串ng在str中出现的次数
 */
public class SubStr {

	public static void main(String[] args) {
		String str = "sdjkdngjskndsgfngj";
		String subStr = "ng";
		System.out.println(findSubStr(str, subStr));
	}
	
	public static int findSubStr(String str, String subStr){
		int result ;
		int subStrLen = subStr.length();
		int strLen = str.length() - str.replace(subStr, "").length();
		result = strLen / subStrLen;
		return result;
	}
}
