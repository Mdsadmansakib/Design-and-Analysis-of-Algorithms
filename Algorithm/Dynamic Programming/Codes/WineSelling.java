// Wine Selling Problem — maximize profit selling wine from ends
public class WineSelling {

    static int[][] dp;

    public static int maxProfit(int[] wine, int i, int j, int year) {
        if (i > j) return 0;
        if (dp[i][j] != -1) return dp[i][j];

        // Option 1: sell left
        int left = wine[i] * year + maxProfit(wine, i + 1, j, year + 1);

        // Option 2: sell right
        int right = wine[j] * year + maxProfit(wine, i, j - 1, year + 1);

        return dp[i][j] = Math.max(left, right);
    }

    public static void main(String[] args) {
        int[] wine = {2, 3, 5, 1, 4};
        int n = wine.length;

        dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Maximum profit: " + maxProfit(wine, 0, n - 1, 1));
    }
}

/*
🧠 Logic:
- Try selling either first or last bottle each year
- Use memoization to avoid recomputation

⏱ Time Complexity: O(n²)
🧠 Space Complexity: O(n²)
*/
