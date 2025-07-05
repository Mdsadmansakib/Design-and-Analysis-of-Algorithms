// Minimum Edit Distance between two strings using Dynamic Programming
public class MinimumEditDistance {

    public static int minEditDistance(String s1, String s2) {
        int n = s1.length(), m = s2.length();

        // Step 1: Create DP table
        int[][] dp = new int[n + 1][m + 1];

        // Step 2: Fill base cases
        for (int i = 0; i <= n; i++) dp[i][0] = i;  // delete all
        for (int j = 0; j <= m; j++) dp[0][j] = j;  // insert all

        // Step 3: Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Characters match → no operation
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Choose min of Insert, Delete, Replace
                    dp[i][j] = 1 + Math.min(
                        dp[i - 1][j],        // delete
                        Math.min(
                            dp[i][j - 1],    // insert
                            dp[i - 1][j - 1] // replace
                        )
                    );
                }
            }
        }

        return dp[n][m];  // Minimum operations to convert s1 to s2
    }

    public static void main(String[] args) {
        String s1 = "horse", s2 = "ros";
        System.out.println("Minimum edit distance: " + minEditDistance(s1, s2));
    }
}

/*
🧠 Logic:
- dp[i][j] = min operations to convert s1[0..i-1] to s2[0..j-1]
- Allowed operations: insert, delete, replace

⏱ Time Complexity: O(n * m)
🧠 Space Complexity: O(n * m)
*/
