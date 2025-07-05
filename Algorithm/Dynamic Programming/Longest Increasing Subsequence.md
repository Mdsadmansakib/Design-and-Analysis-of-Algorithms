# Longest Increasing Subsequence (LIS) Guide

## 🧠 1. Theory: What is Longest Increasing Subsequence (LIS)?

A **subsequence** means picking elements **in the same order** (but not necessarily continuously) from a sequence.

A **Longest Increasing Subsequence** is the longest set of numbers (from the sequence) that are **strictly increasing**.

You must keep the original order, just skip if needed.

## 🍰 2. Example to Understand

Let's take an array:

```
nums = [10, 9, 2, 5, 3, 7, 101, 18]
```

Find the LIS.

Let's check all increasing subsequences:
- [10]
- [2, 3, 7, 101]
- [2, 3, 7, 18]
- [2, 5, 7, 101]
- [2, 5, 7, 18] ✅ (length = 4)

✅ So, the answer is: **4**

## 🪜 3. Step-by-Step Logic

We'll use **Dynamic Programming (DP)** approach:

1. Create an array `dp[]` where `dp[i]` will store the **length of LIS ending at index i**.
2. Initialize every `dp[i] = 1` because every element is a subsequence of length 1 by itself.
3. For each element `i`, check all previous elements `j` (from `0` to `i-1`).
4. If `nums[j] < nums[i]`, it means we can **extend** the subsequence.
5. So we update: `dp[i] = max(dp[i], dp[j] + 1)`
6. After all iterations, the result is the **max value** in `dp[]`.

## 🧾 4. Pseudocode

```
function lengthOfLIS(nums):
    n = length of nums
    dp = array of size n filled with 1
    
    for i from 1 to n-1:
        for j from 0 to i-1:
            if nums[j] < nums[i]:
                dp[i] = max(dp[i], dp[j] + 1)
    
    return max value in dp
```

## 💻 5. Java Code

```java
import java.util.*;

public class LIS {
    public static int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Step 2: each element alone is length 1
        
        // Step 3-5: Build LIS length for each element
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        
        // Step 6: Get the max in dp[]
        int max = 0;
        for (int len : dp) {
            max = Math.max(max, len);
        }
        return max;
    }
    
    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        int result = lengthOfLIS(nums);
        System.out.println("Length of LIS: " + result); // Output: 4
    }
}
```

## 📦 6. Time and Space Complexity

| Aspect | Value |
|--------|-------|
| Time   | O(n²) |
| Space  | O(n)  |

Because for each `i`, we're looking at all `j < i`.
