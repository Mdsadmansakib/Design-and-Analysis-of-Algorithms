# Unbounded Knapsack Problem - Complete Guide

## What is Unbounded Knapsack?

The Unbounded Knapsack Problem is a variation of the 0/1 Knapsack, where:

- **You can include an item multiple times** (unlimited quantity) instead of just once

## Problem Statement

You are given:
- `n` items
- Each item has a `weight[i]` and a `value[i]`
- A knapsack with maximum capacity `W`

**Goal:** Maximize the total value by choosing items (you can take any item any number of times), without exceeding the knapsack capacity `W`.

## Difference from 0/1 Knapsack

| Feature | 0/1 Knapsack | Unbounded Knapsack |
|---------|--------------|-------------------|
| Item Repetition | Only once per item | Unlimited times per item |
| Choice | Take or not take | Take repeatedly or skip |
| Type | Bounded | Unbounded |
| Examples | Job selection, subset sum | Coin Change, Rod Cutting |

## Recurrence Relation (Dynamic Programming)

Let `dp[i][w]` = max value using first `i` items and total capacity `w`

Then:

```
if (weight[i-1] <= w)
    dp[i][w] = max(
        value[i-1] + dp[i][w - weight[i-1]],   // include (again same i → repeat allowed)
        dp[i-1][w]                             // exclude
    )
else
    dp[i][w] = dp[i-1][w]; // skip
```

**Notice:** When we include, we stay on the same row (i) — unlike 0/1 knapsack where we move to i - 1.

## Java Implementation (Tabulation - Bottom-up DP)

```java
public class UnboundedKnapsack {
    public static int unboundedKnapsack(int W, int[] wt, int[] val, int n) {
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (wt[i - 1] <= w) {
                    dp[i][w] = Math.max(
                        val[i - 1] + dp[i][w - wt[i - 1]], // include again
                        dp[i - 1][w]                        // exclude
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        return dp[n][W];
    }

    public static void main(String[] args) {
        int[] wt = {2, 3, 4};
        int[] val = {40, 50, 60};
        int W = 8;
        int n = wt.length;

        System.out.println("Max value: " + unboundedKnapsack(W, wt, val, n));
    }
}
```

## Java Implementation (Recursive + Memoization)

### Recursive Relation

For item `i` and remaining weight `W`:

```
If wt[i] <= W:
    Choose = val[i] + solve(i, W - wt[i])     // include (reuse same i)
    NotChoose = solve(i - 1, W)               // exclude
    dp[i][W] = max(Choose, NotChoose)
Else:
    dp[i][W] = solve(i - 1, W)                // skip
```

### Code Implementation

```java
public class UnboundedKnapsack {
    static int[][] dp; // memoization table

    public static int unboundedKnapsack(int W, int[] wt, int[] val, int n) {
        dp = new int[n + 1][W + 1];

        // Initialize dp with -1
        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= W; j++)
                dp[i][j] = -1;

        return helper(n - 1, W, wt, val);
    }

    // Recursive helper function
    public static int helper(int i, int W, int[] wt, int[] val) {
        // Base Case: if we are at 0th item
        if (i == 0) {
            return (W / wt[0]) * val[0]; // Take as many as fit
        }

        if (dp[i][W] != -1)
            return dp[i][W];

        // Don't take current item
        int notTake = helper(i - 1, W, wt, val);

        // Take current item if it fits (and repeat)
        int take = 0;
        if (wt[i] <= W)
            take = val[i] + helper(i, W - wt[i], wt, val); // notice: `i`, not `i-1`

        return dp[i][W] = Math.max(take, notTake);
    }

    public static void main(String[] args) {
        int[] wt = {2, 3, 4};
        int[] val = {40, 50, 60};
        int W = 8;

        int maxVal = unboundedKnapsack(W, wt, val, wt.length);
        System.out.println("Maximum Value: " + maxVal);
    }
}
```

## Dry Run Example

**Input:**
```
wt =  {2, 3, 4}
val = {40, 50, 60}
W = 8
```

**Analysis:**
- Best option: take item 0 (2kg, 40) four times → 4 × 40 = 160
- Alternative: take two item 1 (3kg, 50) + item 0 → 50+50+40 = 140

**Result:** Maximum value = 160

## Time & Space Complexity

| Approach | Time Complexity | Space Complexity |
|----------|----------------|------------------|
| Tabulation (Bottom-up) | O(n × W) | O(n × W) |
| Recursion + Memoization | O(n × W) | O(n × W) + recursion stack |

**Note:** Space can be optimized to O(W) for tabulation approach using 1D array.

## Real-Life Applications

- **Coin Change Problem** – unlimited coins of each denomination
- **Rod Cutting Problem** – unlimited cuts of each rod length
- **Packing with infinite inventory** – multiple copies of the same item available

## Key Differences from 0/1 Knapsack

1. **Reusability:** Items can be taken multiple times
2. **Recurrence:** When including an item, we stay on the same row/index
3. **Base Case:** For recursive approach, we can take as many items as fit in the remaining capacity
4. **Applications:** More suitable for problems with unlimited supply of items
