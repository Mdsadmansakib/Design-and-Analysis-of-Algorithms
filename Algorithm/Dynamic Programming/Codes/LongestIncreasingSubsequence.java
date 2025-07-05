// Longest Increasing Subsequence using Dynamic Programming (O(n²))
public class LongestIncreasingSubsequence {

    public static int lis(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];

        // Step 1: Initialize all LIS values to 1
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        // Step 2: Build the LIS array
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        // Step 3: Get the maximum LIS value
        int max = 0;
        for (int len : dp) {
            max = Math.max(max, len);
        }

        return max;
    }

    public static void main(String[] args) {
        int[] arr = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Length of Longest Increasing Subsequence: " + lis(arr));
    }
}

/*
🧠 Logic:
- dp[i] stores LIS ending at index i
- For each i, look back at all j < i to check if arr[j] < arr[i]
- Update dp[i] = max(dp[i], dp[j] + 1)

⏱ Time Complexity: O(n²)
🧠 Space Complexity: O(n)
*/
