// Total number of ways to make amount using coins (unlimited coins)
public class TotalNumberWays_Coins {

    public static int countWays(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }

        return dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 3};
        int amount = 4;
        System.out.println("Total ways to make amount: " + countWays(coins, amount));
    }
}

/*
🧠 Logic:
- dp[i] = total ways to form amount i
- For each coin, update ways for all amounts ≥ coin

⏱ Time Complexity: O(n * amount)
🧠 Space Complexity: O(amount)
*/
