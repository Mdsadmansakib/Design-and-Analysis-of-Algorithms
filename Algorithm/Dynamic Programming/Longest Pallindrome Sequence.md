# Longest Palindromic Subsequence (LPS) Algorithm Guide

## 🧠 1. What is Longest Palindromic Subsequence (LPS)?

A palindromic subsequence is a subsequence of a string that:

- Reads the same forward and backward
- You can skip characters, but cannot change order

The goal is to find the length (or actual string) of the longest such subsequence.

## 🍕 2. Simple Example

Let's take a string:

```
s = "bbabcbcab"
```

✅ The longest palindromic subsequence is:

"babcbab" or "bacbcab" (length = 7)

✅ So, the answer = 7

## 🔗 3. Connection with LCS

This is key:
💡 **LPS of a string s = LCS of s and reverse(s)**

**Why?**
Because a palindrome reads the same backward and forward.
So the common subsequence between a string and its reverse must be palindromic!

## 🪜 4. Step-by-Step Plan

1. Let `s` be the original string
2. Let `rev` be `reverse(s)`
3. Compute LCS of `s` and `rev`
4. That LCS is the Longest Palindromic Subsequence

## 🧾 5. Pseudocode

```
function longestPalindromicSubsequence(s):
    rev = reverse(s)
    return LCS(s, rev)
```

## 💻 6. Java Code (Using LCS Logic)

```java
public class LPS {
    public static int longestPalindromicSubsequence(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];

        // LCS of s and reverse(s)
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == rev.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][n];  // Length of LPS
    }

    public static void main(String[] args) {
        String s = "bbabcbcab";
        int result = longestPalindromicSubsequence(s);
        System.out.println("Length of LPS: " + result);  // Output: 7
    }
}
```

## 🔁 7. To Print Actual Palindromic Subsequence

Same as LCS backtracking logic (compare original with reverse), just backtrack `dp[i][j]` and collect characters.

---

# Printing the Actual LPS String

## 🧠 Plan Summary

We will:

1. Use LCS between the string and its reverse (as discussed before)
2. Build the DP table using LCS logic
3. Then backtrack from the bottom-right of the table to reconstruct the LPS

## 🔁 1. Reverse Insight

Given a string:

```java
s = "bbabcbcab"
```

We reverse it:

```java
rev = "bacbcbabb"
```

Now find LCS of `s` and `rev`.
That LCS is guaranteed to be a palindromic subsequence.

## 🧾 2. Java Code: Print the Actual LPS

```java
public class LPS {
    public static String getLongestPalindromicSubsequence(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];

        // Step 1: Fill DP table using LCS logic
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == rev.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Backtrack to get the actual string
        StringBuilder lps = new StringBuilder();
        int i = n, j = n;

        while (i > 0 && j > 0) {
            if (s.charAt(i - 1) == rev.charAt(j - 1)) {
                lps.append(s.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lps.reverse().toString(); // reverse to correct order
    }

    public static void main(String[] args) {
        String s = "bbabcbcab";
        String lps = getLongestPalindromicSubsequence(s);
        System.out.println("Longest Palindromic Subsequence: " + lps);  // Output: babcbab (or similar)
        System.out.println("Length: " + lps.length());  // Output: 7
    }
}
```

## 🪜 3. Dry Run Walkthrough

For:

```
s = "bbabcbcab"
rev = "bacbcbabb"
```

🔁 The LCS backtracking will collect characters like:

```
b → a → b → c → b → a → b → (reverse) → "babcbab"
```

✅ Output:

```
Longest Palindromic Subsequence: babcbab
Length: 7
```

## ⏱ 4. Time and Space Complexity

| Metric | Value |
|--------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(n²) |
