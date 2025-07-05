// Minimum Number of Coins to make a target amount using DP
public class MinimumNumberCoins {

    public static int minCoins(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount + 1];

        // Step 1: Initialize DP array
        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        dp[0] = 0;  // 0 coins needed to make amount 0

        // Step 2: Build DP array
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0 && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println("Minimum number of coins: " + minCoins(coins, amount));
    }
}

/*
🧠 Logic:
- dp[i] = minimum coins to make amount i
- Try all coins for each amount

⏱ Time Complexity: O(n * amount)
🧠 Space Complexity: O(amount)
*/
