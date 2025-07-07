import java.util.*;

public class MatrixChainMultiplication {

    // Function to print parenthesis
    static void printParenthesis(int i, int j, int[][] bracket, char[] name) {
        if (i == j) {
            System.out.print(name[i]);
            return;
        }

        System.out.print("(");

        // Split at k
        printParenthesis(i, bracket[i][j], bracket, name);
        printParenthesis(bracket[i][j] + 1, j, bracket, name);

        System.out.print(")");
    }

    public static int matrixChainOrder(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        int[][] bracket = new int[n][n];  // To trace split points

        // Fill dp[i][j] for chains of length >= 2
        for (int len = 2; len < n; len++) {
            for (int i = 1; i < n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j];

                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                        bracket[i][j] = k;  // Store the best split point
                    }
                }
            }
        }

        // Print optimal parenthesization
        System.out.print("Optimal Parenthesization: ");
        char[] name = new char[n];  // A, B, C, D,...
        for (int i = 1; i < n; i++) name[i] = (char)('A' + i - 1);

        printParenthesis(1, n - 1, bracket, name);
        System.out.println();

        return dp[1][n - 1];
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 30};
        int minCost = matrixChainOrder(arr);
        System.out.println("Minimum number of multiplications: " + minCost);
    }
}
