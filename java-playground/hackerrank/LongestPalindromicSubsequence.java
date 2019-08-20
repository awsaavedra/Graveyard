/*
315 - Longest Palindromic Subsequence
Given a string of lowercase characters (a-z), return the length of the 
longest palindromic subsequence.

A palindromic subsequence is a series of characters (not necessarily consecutive) 
that form a palindrome.

Input: {String}
Output: {Integer}
Example
Input:  "vtvvv"
Output: 4

Longest palindromic subsequence here is "vvvv"


Input:  "pwnnb"
Output: 2

Longest palindromic subsequence here is "nn"


Input:  "ttbtctcbt"
Output: 7

Longest palindromic subsequence here is "tbtctbt"

*/

import java.io.*;

class LongestPalindromicSubsequence {

  public static int longestPalindromicSubsequence(String s) {
    if (s == null || s.length() == 0) return 0;
    int n = s.length();
    if (n == 1) return 1;

    int[][] dp = new int[n][n];

    for (int i = n-1; i >=0; i--) {
      dp[i][i] = 1;
      for (int j = i+1; j < n; j++) {
        if (s.charAt(i) == s.charAt(j)) {
          dp[i][j] = 2 + dp[i+1][j-1];
        } else {
          dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
        }
      }
    }
    return dp[0][n-1];
  }
	public static void main (String[] args) {
		System.out.println(longestPalindromicSubsequence("vtvvv"));
    System.out.println(longestPalindromicSubsequence("vvvv"));
    System.out.println(longestPalindromicSubsequence("pwnnb"));
    System.out.println(longestPalindromicSubsequence("ttbtctcbt"));
	}
}

