import java.util.*;

// 0/1 Knapsack Problem using Dynamic Programming (Bottom-Up)
public class Knapsack_01 {

    public static int knapsack(int[] weight, int[] value, int capacity) {
        int n = weight.length;
        int[][] dp = new int[n + 1][capacity + 1];

        // Step 1: Build the DP table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0; // Base case: 0 items or 0 capacity
                } else if (weight[i - 1] <= w) {
                    // Option 1: Include item i-1
                    // Option 2: Exclude item i-1
                    dp[i][w] = Math.max(
                        value[i - 1] + dp[i - 1][w - weight[i - 1]],
                        dp[i - 1][w]
                    );
                } else {
                    // Can't include item i-1 because it's too heavy
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Step 2: Backtrack to find selected item indices
        List<Integer> selectedItems = new ArrayList<>();
        int i = n, w = capacity;

        while (i > 0 && w > 0) {
            if (dp[i][w] != dp[i - 1][w]) {
                selectedItems.add(i - 1);       // item i-1 is included
                w -= weight[i - 1];             // reduce remaining weight
            }
            i--;
        }

        // Optional: reverse to print in original order
        Collections.reverse(selectedItems);

        // Print selected item indices
        System.out.println("Selected item indices: " + selectedItems);
        return dp[n][capacity]; // Max value that can be carried
    }

    public static void main(String[] args) {
        int[] weight = {1, 3, 4, 5};
        int[] value =  {1, 4, 5, 7};
        int capacity = 7;

        int maxValue = knapsack(weight, value, capacity);
        System.out.println("Maximum value: " + maxValue);
    }
}

/*
🧠 Logic:
- Build a 2D DP table to store max value at each sub-capacity and item
- Use backtracking to find which items were chosen

⏱ Time Complexity: O(n * W)
🧠 Space Complexity: O(n * W)
→ where n = number of items, W = knapsack capacity
*/
