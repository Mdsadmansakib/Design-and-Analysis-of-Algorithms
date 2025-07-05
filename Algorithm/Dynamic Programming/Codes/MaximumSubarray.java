// Maximum Subarray Sum (Kadane's Algorithm)
public class MaximumSubarray {

    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0], currSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currSum = Math.max(nums[i], currSum + nums[i]);
            maxSum = Math.max(maxSum, currSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Maximum subarray sum: " + maxSubArray(arr));
    }
}

/*
🧠 Logic:
- Track current sum and reset if it goes negative
- At each point, store max sum so far

⏱ Time Complexity: O(n)
🧠 Space Complexity: O(1)
*/
