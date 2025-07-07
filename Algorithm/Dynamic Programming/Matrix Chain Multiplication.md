# Matrix Chain Multiplication - Complete Guide

## 1. Theory: What is Matrix Chain Multiplication (MCM)?

Matrix Chain Multiplication is a classic dynamic programming problem where we need to find the most efficient way to multiply a sequence of matrices together, minimizing the total number of scalar multiplications.

**Key Insight**: Matrix multiplication is associative, meaning we can change the grouping (parentheses) but not the order of matrices.

### Example of Associativity
- `(A × B) × C` 
- `A × (B × C)`

Both expressions give the same result but may have different computational costs.

## 2. Problem Example

Given: `arr = [10, 20, 30, 40, 30]`

This represents 4 matrices:
- **A**: 10 × 20
- **B**: 20 × 30  
- **C**: 30 × 40
- **D**: 40 × 30

**Goal**: Find minimum scalar multiplications needed to compute A × B × C × D.

### Different Parenthesizations:
1. `((AB)C)D`
2. `(A(BC))D`
3. `(AB)(CD)`
4. `A((BC)D)`

Each parenthesization has a different multiplication cost.

## 3. Dynamic Programming Approach

### Subproblem Definition
Let `dp[i][j]` = minimum cost to multiply matrices from index i to j.

### Recurrence Relation
```
dp[i][j] = min(dp[i][k] + dp[k+1][j] + arr[i-1] * arr[k] * arr[j])
```

**Where**:
- Left subchain: matrices i to k
- Right subchain: matrices k+1 to j  
- Cost of multiplying results: `arr[i-1] * arr[k] * arr[j]`

### Algorithm Steps
1. Build solution from smaller chains to larger chains (bottom-up)
2. Try all possible split points k between i and j
3. Choose the split that gives minimum cost

## 4. Pseudocode

```
function matrixChainMultiplication(arr):
    n = length of arr
    create dp[n][n] and initialize to 0

    for len from 2 to n-1:
        for i from 1 to n - len:
            j = i + len - 1
            dp[i][j] = ∞
            for k from i to j - 1:
                cost = dp[i][k] + dp[k+1][j] + arr[i-1] * arr[k] * arr[j]
                dp[i][j] = min(dp[i][j], cost)

    return dp[1][n-1]
```

## 5. Java Implementation

### Basic Version (Cost Only)
```java
import java.util.*;

public class MatrixChainMultiplication {

    public static int matrixChainOrder(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];

        // Fill dp[i][j] for chain length >= 2
        for (int len = 2; len < n; len++) {
            for (int i = 1; i < n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[1][n - 1];
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 30};
        int result = matrixChainOrder(arr);
        System.out.println("Minimum number of multiplications: " + result);
    }
}
```

### Enhanced Version (With Parenthesization)
```java
import java.util.*;

public class MatrixChainMultiplication {

    // Function to print optimal parenthesization
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
```

### Sample Output
```
Optimal Parenthesization: ((AB)C)D
Minimum number of multiplications: 30000
```

## 6. Complexity Analysis

| Aspect | Value |
|--------|-------|
| **Time Complexity** | O(n³) |
| **Space Complexity** | O(n²) |

### Explanation:
- **Time**: We compute `dp[i][j]` for all i < j, and for each pair we try all possible split points k
- **Space**: We use a 2D DP table of size n×n, plus an additional bracket table for tracing

## 7. Key Takeaways

1. **Problem Type**: Classic interval/range dynamic programming
2. **Core Insight**: Try all possible ways to split the matrix chain and choose the optimal one
3. **Optimization**: The order of matrix multiplication matters for efficiency, not correctness
4. **Tracing**: Use an additional table to reconstruct the optimal parenthesization
5. **Applications**: Compiler optimization, mathematical computations, algorithm design

## 8. Variations and Extensions

- **Recursive + Memoization**: Top-down approach with memoization
- **Space Optimization**: Possible optimizations for space complexity
- **Parallel Processing**: How to parallelize matrix chain multiplication
- **Real-world Applications**: Database query optimization, computational graphics
