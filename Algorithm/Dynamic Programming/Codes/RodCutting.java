// Rod Cutting Problem — Maximize profit from cutting rod
public class RodCutting {

    public static int cutRod(int[] price, int n) {
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] = Math.max(dp[i], price[j - 1] + dp[i - j]);
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int[] price = {1, 5, 8, 9, 10, 17, 17, 20};
        int n = 8;
        System.out.println("Maximum profit from rod: " + cutRod(price, n));
    }
}

/*
🧠 Logic:
- Try every cut from 1 to i
- dp[i] = max(dp[i], price[j-1] + dp[i-j])

⏱ Time Complexity: O(n²)
🧠 Space Complexity: O(n)
*/
