// Count number of ways to reach the nth step (like climbing stairs)
public class NumberOfWays {

    public static int countWays(int n) {
        if (n == 0 || n == 1) return 1;

        int[] dp = new int[n + 1];

        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println("Number of ways to reach step " + n + ": " + countWays(n));
    }
}

/*
🧠 Logic:
- Like Fibonacci: f(n) = f(n-1) + f(n-2)
- Ways to climb 1 or 2 steps at a time

⏱ Time Complexity: O(n)
🧠 Space Complexity: O(n)
*/
