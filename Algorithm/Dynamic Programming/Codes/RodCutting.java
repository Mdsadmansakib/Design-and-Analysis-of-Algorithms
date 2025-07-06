// Like Unbounded Knapsack 

public static int RodCuting(int[] prices, int n) {
    int[][] dp = new int[n + 1][n + 1]; // dp[i][j]: i lengths, total length j
    int[] lengths = new int[n];
    for (int i = 0; i < n; i++) lengths[i] = i + 1;

    for (int i = 1; i <= n; i++) {
        for (int len = 1; len <= n; len++) {
            if (lengths[i - 1] <= len) {
                dp[i][len] = Math.max(
                    prices[i - 1] + dp[i][len - lengths[i - 1]],
                    dp[i - 1][len]
                );
            } else {
                dp[i][len] = dp[i - 1][len];
            }
        }
    }

    return dp[n][n];
}


// Time Complexity: O(n^2)
// Space Complexity: O(n^2)



/*

// 1D DP Solution (Space Optimized)     Space Complexity: O(n)


    public static int rodCutting1D(int[] prices, int n) {
    int[] dp = new int[n + 1];
    int[] lengths = new int[n];
    for (int i = 0; i < n; i++) lengths[i] = i + 1;

    for (int i = 0; i < n; i++) {
        for (int len = lengths[i]; len <= n; len++) {
            dp[len] = Math.max(dp[len], prices[i] + dp[len - lengths[i]]);
        }
    }

    return dp[n];
}


*/
