// Simple Fibonacci using Bottom-Up DP (Tabulation)
public class Fibonacci {
    public static int fibonacci(int n) {
        // Base case: fib(0) = 0, fib(1) = 1
        if (n <= 1) return n;

        // Step 1: Create array to store fib numbers
        int[] dp = new int[n + 1];

        // Step 2: Initialize base cases
        dp[0] = 0;
        dp[1] = 1;

        // Step 3: Fill table from 2 to n
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        // Step 4: Return nth fibonacci number
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println("Fibonacci of " + n + " is: " + fibonacci(n));
    }
}

/*
🧠 Logic:
- Build up Fibonacci numbers from 0 to n
- Store previous results to avoid recomputation

⏱ Time Complexity: O(n)
🧠 Space Complexity: O(n)  → Can be optimized to O(1) if needed
*/
