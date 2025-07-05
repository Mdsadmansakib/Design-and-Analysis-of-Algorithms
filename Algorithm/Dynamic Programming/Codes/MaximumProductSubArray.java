// Maximum Product Subarray using DP (Kadane's variant)
public class MaximumProductSubArray {

    public static int maxProduct(int[] nums) {
        int n = nums.length;
        int maxProd = nums[0];
        int minHere = nums[0], maxHere = nums[0];

        for (int i = 1; i < n; i++) {
            int temp = maxHere;

            maxHere = Math.max(nums[i], Math.max(maxHere * nums[i], minHere * nums[i]));
            minHere = Math.min(nums[i], Math.min(temp * nums[i], minHere * nums[i]));

            maxProd = Math.max(maxProd, maxHere);
        }

        return maxProd;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, -2, 4};
        System.out.println("Maximum product subarray: " + maxProduct(arr));
    }
}

/*
🧠 Logic:
- Track both max and min product so far
- Swap when negative flips the sign

⏱ Time Complexity: O(n)
🧠 Space Complexity: O(1)
*/
