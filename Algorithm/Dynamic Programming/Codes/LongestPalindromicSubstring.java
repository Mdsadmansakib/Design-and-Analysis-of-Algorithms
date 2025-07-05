// Longest Palindromic Substring using DP
public class LongestPalindromicSubstring {

    public static String longestPalindrome(String s) {
        int n = s.length();
        if (n == 0) return "";

        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLength = 1;

        // Step 1: All substrings of length 1 are palindromes
        for (int i = 0; i < n; i++) dp[i][i] = true;

        // Step 2: Check for length 2 substrings
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }

        // Step 3: Check longer substrings
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    start = i;
                    maxLength = len;
                }
            }
        }

        return s.substring(start, start + maxLength);
    }

    public static void main(String[] args) {
        String s = "babad";
        System.out.println("Longest Palindromic Substring: " + longestPalindrome(s));
    }
}

/*
🧠 Logic:
- dp[i][j] = true if s[i..j] is a palindrome
- Expand for length 1 → 2 → 3+
- Track the start and max length

⏱ Time Complexity: O(n²)
🧠 Space Complexity: O(n²)
*/
