# 0/1 Knapsack Problem - Complete Guide

## 1. What is the 0/1 Knapsack Problem?

The 0/1 Knapsack Problem is a classic optimization problem where you are a thief with a knapsack (bag) that can carry at most **W** weight.

**Given:**
- **n** items
- Each item has:
  - A weight `w[i]`
  - A value `v[i]`

**Your task:** Pick items to maximize total value without exceeding total weight W.

**The catch:** You can either take an item completely or not at all — that's why it's called **0/1**.

## 2. Real Life Analogy

Imagine you're looting a vault and can only carry 10 kg. You see:

- **Gold bar:** 6 kg, $30
- **Diamond:** 3 kg, $14  
- **Painting:** 4 kg, $16

You can't take half a painting. Either take the whole thing or leave it.

## 3. Example

**Given:**
```
weights = [1, 3, 4, 5]
values  = [1, 4, 5, 7]
W = 7
```

**Possible picks:**
- Pick items 2 and 4 (3+4 = 7 kg, value = 4+5 = 9)
- Pick items 2 and 3 → 3+5 > 7 → Not allowed

**Maximum value = 9**

## 4. Dynamic Programming Approach (Bottom-Up)

We build a 2D table `dp[i][w]` where:
- `i` = number of items considered (0 to n)
- `w` = weight capacity from 0 to W

Each cell `dp[i][w]` represents the maximum value you can get using the first `i` items with maximum weight `w`.

## 5. Logic (Transition)

For each item `i`, for every weight `w`:

- **If we don't take the item:** `dp[i][w] = dp[i-1][w]`
- **If we take the item:** `dp[i][w] = value[i-1] + dp[i-1][w - weight[i-1]]`

**Final answer is `dp[n][W]`**

## 6. Pseudocode

```
for i from 0 to n:
    for w from 0 to W:
        if i == 0 or w == 0:
            dp[i][w] = 0
        else if weight[i-1] <= w:
            dp[i][w] = max(
                value[i-1] + dp[i-1][w - weight[i-1]],
                dp[i-1][w]
            )
        else:
            dp[i][w] = dp[i-1][w]
```

## 7. Java Implementation with Item Indices

```java
import java.util.*;

public class Knapsack01 {
    public static int knapsack(int[] weight, int[] value, int W) {
        int n = weight.length;
        int[][] dp = new int[n + 1][W + 1];

        // Step 1: Fill DP table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (weight[i - 1] <= w) {
                    dp[i][w] = Math.max(
                        value[i - 1] + dp[i - 1][w - weight[i - 1]],
                        dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Step 2: Backtrack to find selected item indices
        List<Integer> selectedItems = new ArrayList<>();
        int i = n, w = W;

        while (i > 0 && w > 0) {
            if (dp[i][w] != dp[i - 1][w]) {
                selectedItems.add(i - 1);  // item i-1 is included
                w -= weight[i - 1];
            }
            i--;
        }

        // Reverse for original order (optional)
        Collections.reverse(selectedItems);

        // Print results
        System.out.println("Maximum value: " + dp[n][W]);
        System.out.println("Selected item indices: " + selectedItems);
        return dp[n][W];
    }

    public static void main(String[] args) {
        int[] weight = {1, 3, 4, 5};
        int[] value  = {1, 4, 5, 7};
        int W = 7;
        knapsack(weight, value, W);
    }
}
```

## 8. Backtracking Logic

**How to find selected items:**

1. Start from `dp[n][W]` and go backward
2. If `dp[i][w] == dp[i-1][w]` → item `i-1` was **not taken**
3. Else → item `i-1` was **taken**
   - Add index `(i-1)` to result
   - Subtract its weight from current `w`: `w -= weight[i-1]`

## 9. Example Output

For the given example:
```
weights = [1, 3, 4, 5]
values  = [1, 4, 5, 7]
W = 7
```

**Output:**
```
Maximum value: 9
Selected item indices: [1, 2]
```

This means items at index 1 and 2 (0-based) were taken — i.e., weight 3 and 4, value 4 and 5.

## 10. Time and Space Complexity

| Type  | Complexity |
|-------|------------|
| Time  | O(n × W)   |
| Space | O(n × W)   |

**Note:** Space can be optimized to O(W) using 1D arrays.

## 11. Summary

| Term | Meaning |
|------|---------|
| 0/1 Knapsack | Take or leave each item (no fractions) |
| Goal | Maximize value, weight ≤ W |
| Technique | Dynamic Programming (bottom-up) |
| Key transition | max(take, don't take) decision |
