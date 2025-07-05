// Longest Common Subsequence (LCS) using Dynamic Programming (Tabulation)
public class LongestCommonSubsequence {

    public static int lcs(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        // Step 1: Create a DP table
        int[][] dp = new int[n + 1][m + 1];

        // Step 2: Fill the table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Characters match, extend the LCS
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // Take max of excluding one character from either string
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][m];  // LCS length
    }

    public static void main(String[] args) {
        String s1 = "abcde";
        String s2 = "ace";

        int result = lcs(s1, s2);
        System.out.println("Length of Longest Common Subsequence: " + result);
    }
}

/*
🧠 Logic:
- Build a 2D DP table dp[i][j] representing LCS of s1[0..i-1] and s2[0..j-1]
- If characters match → 1 + dp[i-1][j-1]
- If not match → take max of (exclude s1[i-1]) or (exclude s2[j-1])

⏱ Time Complexity: O(n * m)
🧠 Space Complexity: O(n * m)
*/
