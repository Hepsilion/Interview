package org.interview.algorithm.dp.match;

public class LongestCommonSequence {
	public static void main(String[] args) {
		String str1="abcd";
		String str2="mnp";
		System.out.println(new LongestCommonSequence().LCS1(str1, str2));
		System.out.println(new LongestCommonSequence().LCS2(str1, str2));
	}
	/**
	 * 最长公共子序列
	 * 
	 * <pre>
	 * array[i][j]表示以str1[i]和str2[j]结尾的公共子串的长度<br>
	 * array[i][j]= <br>
	 * 0                                  if i==0 || j==0<br>
	 * array[i-1][j-1]+1                  if str1[i]==str2[j]<br>
	 * max(array[i-1][j], array[i][j-1])  if str1[i]!=str2[j]<br>
	 * </pre>
	 * @param str1=[s1, s2, s3, s4, ..., sm]
	 * @param str2=[s1, s2, s3, s4, ..., sn]
	 * @return
	 */
	public int LCS1(String str1, String str2) {
		int m = str1.length(), n = str2.length();
		int[][] array = new int[m+1][n+1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0)
					array[i][j] = 0;
				else if (str1.charAt(i-1) == str2.charAt(j-1))
					array[i][j] = array[i - 1][j - 1] + 1;
				else
					array[i][j] = Math.max(array[i - 1][j], array[i][j - 1]);
			}
		}
		return array[m][n];
	}

	/**
	 * 最长公共子串
	 * 
	 * <pre>
	 * array[i][j]表示以str1[i]和str2[j]结尾的公共子串的长度<br>
	 * array[i][j]= <br>
	 * 0                   if i==0 || j==0<br>
	 * array[i-1][j-1]+1   if str1[i]==str2[j]<br>
	 * 0                   if str1[i]!=str2[j]<br>
	 * </pre>
	 * @param str1=[s1, s2, s3, s4, ..., sm]
	 * @param str2=[s1, s2, s3, s4, ..., sn]
	 * @return
	 */
	public int LCS2(String str1, String str2) {
		int m = str1.length(), n = str2.length();
		int result = 0;
		int[][] array = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0) {
					array[i][j] = 0;
				} else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					array[i][j] = array[i - 1][j - 1] + 1;
				} else {
					array[i][j] = 0;
				}
				result = Math.max(result, array[i][j]);
			}
		}
		return result;
	}
}