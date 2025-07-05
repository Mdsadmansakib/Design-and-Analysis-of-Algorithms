// Perfect Squares Sum (Minimum number of perfect squares that sum to n)
public class PerfectSquares {

    public static int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            dp[i] = i;  // worst case: 1+1+1...
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], 1 + dp[i - j * j]);
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int n = 12;
        System.out.println("Minimum perfect squares to sum to " + n + ": " + numSquares(n));
    }
}

/*
🧠 Logic:
- Try all squares ≤ i
- Build solution bottom-up from 0 to n

⏱ Time Complexity: O(n * √n)
🧠 Space Complexity: O(n)
*/
