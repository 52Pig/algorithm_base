package com.youku.data.cleaner;

/**
 * 
 * 在一个字符串中找到第一个只出现一次的字符。如输入abaccdeff，则输出b
 * 输入字符串str，输出第m个只出现过n次的字符，如在字符串gbgkkdehh中找到第2个只出现1次的字符，则输出d
 	在一个字符串中找到第一个只出现一次的字符。如输入abaccdeff,则输出b.
 */
public class AppTest {

	public static void main(String[] args) {
		String str = "gbgkkdehh";
		// findSubStr(str);
//		System.out.println(firstOne(str));
		System.out.println(FindFirstNotRepeat(str));
	}

	public static String findSubStr(String str) {
		String result = "";

		for (int i = 0; i <= str.length() - 1; i++) {

			System.out.println(i + ":" + str.charAt(i));
		}
		return result;
	}

	public static char firstOne(String s) {
		char result = '0';
		char temp;
		int[] num = new int[52];
		for (int i = 0; i < s.length(); i++) {
			temp = s.charAt(i);
			if (temp >= 'a' && temp <= 'z') {
				num[temp - 'a']++;
			} else if (temp >= 'A' && temp <= 'Z') {
				num[temp - 'A' + 26]++;
			}
		}
		for (int i = 0; i < num.length; i++) {
			if (num[i] == 1) {
				if (i >= 0 && i <= 26) {
					result = (char) (i + 'a');
				} else
					result = (char) (i - 26 + 'A');
				break;
			}
		}
		return result;
	}

	public static char FindFirstNotRepeat(String str) {
		if (str == null) {
			System.out.println("输入为空！");
			return ' ';
		}
		int[] hash = new int[256];
		// 初始化hash数组
		for (int i = 0; i < hash.length; i++) {
			hash[i] = 0;
		}
		for (int i = 0; i < str.length(); i++) {
			hash[str.charAt(i)]++;
		}
		for (int i = 0; i < str.length(); i++) {
			if (hash[str.charAt(i)] == 1)
				return str.charAt(i);
		}
		return ' ';
	}
}
