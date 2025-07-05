# Longest Common Subsequence (LCS) Algorithm Guide

## 🧠 1. What is Longest Common Subsequence (LCS)?

A **subsequence** is a sequence that appears in the same **order**, but not necessarily **contiguously**.

The **Longest Common Subsequence** between two strings is the longest sequence that appears **in both strings**, in the same order.

## 🍕 2. Simple Example

Let's take two strings:

```
A = "abcde"
B = "ace"
```

Now, find the **longest subsequence common** to both.

✅ Common subsequences:
- "a"
- "ac"
- "ae"
- "ace" ✅ ✅

🎯 So, the LCS = `"ace"` and **length = 3**

## 🪜 3. Step-by-Step Logic

We'll use **Dynamic Programming (DP)** to build the solution.

**Goal:**
Build a `dp` table where `dp[i][j]` means **LCS length** of `A[0...i-1]` and `B[0...j-1]`.

**Steps:**
1. Create a 2D array `dp[m+1][n+1]` where `m = A.length`, `n = B.length`.
2. Initialize all `dp[i][0]` and `dp[0][j]` to `0`. (Empty string has LCS of 0)
3. Loop through each character of A and B:
   - If `A[i-1] == B[j-1]`, then `dp[i][j] = dp[i-1][j-1] + 1`
   - Else, `dp[i][j] = max(dp[i-1][j], dp[i][j-1])`
4. Final answer is in `dp[m][n]`

## 🧾 4. Pseudocode

```
function LCS(A, B):
    m = length of A
    n = length of B
    dp = 2D array of size (m+1) x (n+1), initialized to 0
    
    for i from 1 to m:
        for j from 1 to n:
            if A[i-1] == B[j-1]:
                dp[i][j] = dp[i-1][j-1] + 1
            else:
                dp[i][j] = max(dp[i-1][j], dp[i][j-1])
    
    return dp[m][n]
```

## 💻 5. Java Code

```java
public class LCS {
    public static int longestCommonSubsequence(String A, String B) {
        int m = A.length();
        int n = B.length();
        int[][] dp = new int[m + 1][n + 1];
        
        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1; // characters match
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // skip one
                }
            }
        }
        
        return dp[m][n];
    }
    
    public static void main(String[] args) {
        String A = "abcde";
        String B = "ace";
        int result = longestCommonSubsequence(A, B);
        System.out.println("Length of LCS: " + result); // Output: 3
    }
}
```

## 🔍 6. Dry Run on Example: A = "abcde", B = "ace"

| i/j | "" | a | c | e |
|-----|----|----|----|----|
| ""  | 0  | 0  | 0  | 0  |
| a   | 0  | 1  | 1  | 1  |
| b   | 0  | 1  | 1  | 1  |
| c   | 0  | 1  | 2  | 2  |
| d   | 0  | 1  | 2  | 2  |
| e   | 0  | 1  | 2  | 3  |

🔚 `dp[5][3] = 3` ✅

## ⏱️ 7. Time and Space Complexity

| Metric | Value |
|--------|-------|
| Time Complexity | O(m × n) |
| Space Complexity | O(m × n) |

You can optimize space to O(n) using two rows instead of full matrix.

---

# Finding the Actual LCS String

## 🧠 Goal

Given two strings `A` and `B`, we'll:
- Build the same DP table as before to calculate LCS length
- **Backtrack** from the bottom-right of the table to **reconstruct the actual LCS string**

## 🪜 Step-by-Step Plan

**Step 1:** Build `dp` table as before
- `dp[i][j]` = length of LCS between `A[0..i-1]` and `B[0..j-1]`

**Step 2:** Backtrack to find LCS string
- Start from `dp[m][n]` (end of both strings)
- While `i > 0` and `j > 0`:
  - If `A[i-1] == B[j-1]`: it's part of LCS → add to result and move diagonally (`i--, j--`)
  - Else:
    - If `dp[i-1][j] > dp[i][j-1]`: move up (`i--`)
    - Else: move left (`j--`)
- Reverse the result string

## 🧾 Pseudocode

```
function printLCS(A, B):
    build dp table
    i = length of A
    j = length of B
    result = empty string
    
    while i > 0 and j > 0:
        if A[i-1] == B[j-1]:
            add A[i-1] to result
            i--, j--
        else if dp[i-1][j] > dp[i][j-1]:
            i--
        else:
            j--
    
    return reverse(result)
```

## 💻 Java Code

```java
public class LCS {
    public static String getLCS(String A, String B) {
        int m = A.length();
        int n = B.length();
        int[][] dp = new int[m + 1][n + 1];
        
        // Step 1: Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Step 2: Backtrack to find actual LCS string
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (A.charAt(i - 1) == B.charAt(j - 1)) {
                lcs.append(A.charAt(i - 1)); // Add to result
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--; // Move up
            } else {
                j--; // Move left
            }
        }
        
        return lcs.reverse().toString(); // Reverse to correct order
    }
    
    public static void main(String[] args) {
        String A = "abcde";
        String B = "ace";
        String lcsString = getLCS(A, B);
        System.out.println("LCS string: " + lcsString); // Output: ace
        System.out.println("Length: " + lcsString.length()); // Output: 3
    }
}
```

## 🔍 Example Trace (Dry Run)

```
A = "abcde"
B = "ace"
→ DP table gives us length = 3

Backtrack:
- A[4]=e, B[2]=e → match → add 'e'
- A[3]=d, B[2]=e → skip A (dp[3][2] > dp[4][1])
- A[2]=c, B[1]=c → match → add 'c'
- A[1]=b, B[1]=c → skip A
- A[0]=a, B[0]=a → match → add 'a'

Reverse → "ace"
```
