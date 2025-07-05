// Rock Climbing — count ways to climb with 1, 2, or 3 steps
public class RockClimbing {

    public static int countWays(int n) {
        if (n == 0) return 1;
        if (n < 0) return 0;

        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1];
            if (i >= 2) dp[i] += dp[i - 2];
            if (i >= 3) dp[i] += dp[i - 3];
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println("Ways to climb rock (1,2,3 steps): " + countWays(n));
    }
}

/*
🧠 Logic:
- f(n) = f(n-1) + f(n-2) + f(n-3)

⏱ Time Complexity: O(n)
🧠 Space Complexity: O(n)
*/
