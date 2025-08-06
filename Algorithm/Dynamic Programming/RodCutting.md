# Rod Cutting Problem Analysis

## Problem Overview

The rod cutting problem is a classic **dynamic programming** optimization problem where we need to determine the optimal way to cut a rod into pieces to maximize revenue.

### Key Characteristics:
- **Length-based optimization**: Cut rod of length n into smaller pieces
- **Price table driven**: Each length has a specific price
- **Maximization goal**: Maximize total revenue from all pieces
- **Optimal substructure**: Optimal solution contains optimal solutions to subproblems

## Problem Formulation

### Input:
- `P[i]`: Price array where P[i] = price for rod of length i
- `n`: Length of the rod to be cut
- Rod lengths: 1, 2, 3, ..., n

### Constraints:
- Rod can be cut into any combination of smaller pieces
- Total length of pieces must equal original rod length
- Each piece length must have a corresponding price

### Objective:
Find the combination of cuts that maximizes total revenue.

## Dynamic Programming Solution

### State Definition:
`DP[i]` = maximum revenue obtainable from rod of length i

### Recurrence Relation:
```
DP[i] = max(P[j] + DP[i-j]) for all j from 1 to i
```
This means: "Try cutting at each possible position j, take the price of piece j, and add the optimal solution for remaining length (i-j)"

### Base Case:
```
DP[0] = 0  // No revenue from rod of length 0
```

### Decision at Each Step:
For rod of length i, consider all possible first cuts:
- Cut length 1: revenue = P[1] + DP[i-1]
- Cut length 2: revenue = P[2] + DP[i-2]
- ...
- Cut length i: revenue = P[i] + DP[0]

## Algorithm Complexity

- **Time Complexity**: `O(n²)` where n = rod length
- **Space Complexity**: `O(n)` for DP table
- **Optimal**: Yes, finds globally optimal solution

## Example Walkthrough

### Input:
```
Rod length: n = 8
Price table: P = [0, 1, 5, 8, 9, 10, 17, 17, 20]
             (P[0] unused, P[i] = price for length i)
```

### DP Table Construction:

**Step 1: Base case**
```
DP[0] = 0
```

**Step 2: Length 1**
```
DP[1] = max(P[1] + DP[0]) = max(1 + 0) = 1
Cut: [1]
```

**Step 3: Length 2**
```
DP[2] = max(P[1] + DP[1], P[2] + DP[0])
      = max(1 + 1, 5 + 0) = max(2, 5) = 5
Cut: [2]
```

**Step 4: Length 3**
```
DP[3] = max(P[1] + DP[2], P[2] + DP[1], P[3] + DP[0])
      = max(1 + 5, 5 + 1, 8 + 0) = max(6, 6, 8) = 8
Cut: [3]
```

**Step 5: Length 4**
```
DP[4] = max(P[1] + DP[3], P[2] + DP[2], P[3] + DP[1], P[4] + DP[0])
      = max(1 + 8, 5 + 5, 8 + 1, 9 + 0) = max(9, 10, 9, 9) = 10
Cut: [2, 2]
```

**Step 6: Length 5**
```
DP[5] = max(P[1] + DP[4], P[2] + DP[3], P[3] + DP[2], P[4] + DP[1], P[5] + DP[0])
      = max(1 + 10, 5 + 8, 8 + 5, 9 + 1, 10 + 0)
      = max(11, 13, 13, 10, 10) = 13
Cut: [2, 3] or [3, 2]
```

**Step 7: Length 6**
```
DP[6] = max(P[1] + DP[5], P[2] + DP[4], P[3] + DP[3], P[4] + DP[2], P[5] + DP[1], P[6] + DP[0])
      = max(1 + 13, 5 + 10, 8 + 8, 9 + 5, 10 + 1, 17 + 0)
      = max(14, 15, 16, 14, 11, 17) = 17
Cut: [6]
```

**Step 8: Length 7**
```
DP[7] = max(P[1] + DP[6], P[2] + DP[5], P[3] + DP[4], P[4] + DP[3], P[5] + DP[2], P[6] + DP[1], P[7] + DP[0])
      = max(1 + 17, 5 + 13, 8 + 10, 9 + 8, 10 + 5, 17 + 1, 17 + 0)
      = max(18, 18, 18, 17, 15, 18, 17) = 18
Cut: [1, 6] or [2, 2, 3] or [3, 2, 2] etc.
```

**Step 9: Length 8**
```
DP[8] = max(P[1] + DP[7], P[2] + DP[6], P[3] + DP[5], P[4] + DP[4], P[5] + DP[3], P[6] + DP[2], P[7] + DP[1], P[8] + DP[0])
      = max(1 + 18, 5 + 17, 8 + 13, 9 + 10, 10 + 8, 17 + 5, 17 + 1, 20 + 0)
      = max(19, 22, 21, 19, 18, 22, 18, 20) = 22
Cut: [2, 6] or [6, 2]
```

### Final DP Table:
```
Length:  0  1  2  3  4  5  6  7  8
DP:      0  1  5  8 10 13 17 18 22
```

**Answer**: Maximum revenue = 22

## Step-by-Step Simulation (Detailed Calculations)

### Calculation Details for Length 8:

**Option 1**: Cut length 1 first
- Revenue = P[1] + DP[7] = 1 + 18 = 19

**Option 2**: Cut length 2 first  
- Revenue = P[2] + DP[6] = 5 + 17 = 22 ✓

**Option 3**: Cut length 3 first
- Revenue = P[3] + DP[5] = 8 + 13 = 21

**Option 4**: Cut length 4 first
- Revenue = P[4] + DP[4] = 9 + 10 = 19

**Option 5**: Cut length 5 first
- Revenue = P[5] + DP[3] = 10 + 8 = 18

**Option 6**: Cut length 6 first
- Revenue = P[6] + DP[2] = 17 + 5 = 22 ✓

**Option 7**: Cut length 7 first
- Revenue = P[7] + DP[1] = 17 + 1 = 18

**Option 8**: No cut (use whole rod)
- Revenue = P[8] + DP[0] = 20 + 0 = 20

**Maximum**: 22 (achieved by cuts [2,6] or [6,2])

## Pseudocode

```
ALGORITHM RodCuttingDP(P, n):
    INPUT: P[1..n] - price array, n - rod length
    OUTPUT: maximum revenue
    
    // Initialize DP table
    CREATE DP[0..n]
    DP[0] = 0
    
    // Fill DP table for each length
    FOR i = 1 to n:
        max_revenue = 0
        
        // Try all possible first cuts
        FOR j = 1 to i:
            current_revenue = P[j] + DP[i-j]
            max_revenue = MAX(max_revenue, current_revenue)
        
        DP[i] = max_revenue
    
    RETURN DP[n]

ALGORITHM RodCuttingWithSolution(P, n):
    // Extended version that also returns the cuts
    CREATE DP[0..n], cuts[0..n]
    DP[0] = 0
    
    FOR i = 1 to n:
        max_revenue = 0
        best_cut = 0
        
        FOR j = 1 to i:
            current_revenue = P[j] + DP[i-j]
            IF current_revenue > max_revenue:
                max_revenue = current_revenue
                best_cut = j
        
        DP[i] = max_revenue
        cuts[i] = best_cut
    
    // Reconstruct solution
    pieces = []
    remaining = n
    WHILE remaining > 0:
        piece = cuts[remaining]
        pieces.ADD(piece)
        remaining = remaining - piece
    
    RETURN DP[n], pieces

ALGORITHM RodCuttingRecursive(P, n):
    // Top-down recursive approach with memoization
    CREATE memo[0..n] initialized to -1
    
    FUNCTION CutRodMemo(P, n, memo):
        IF n == 0: RETURN 0
        IF memo[n] != -1: RETURN memo[n]
        
        max_revenue = 0
        FOR i = 1 to n:
            revenue = P[i] + CutRodMemo(P, n-i, memo)
            max_revenue = MAX(max_revenue, revenue)
        
        memo[n] = max_revenue
        RETURN max_revenue
    
    RETURN CutRodMemo(P, n, memo)
```

## Cut Reconstruction

To find the actual cuts that achieve maximum revenue:

### For Length 8 (Revenue = 22):
```
Starting from DP[8] = 22:
- Best first cut = 2 (since P[2] + DP[6] = 5 + 17 = 22)
- Remaining length = 6
- For DP[6] = 17: best cut = 6 (since P[6] + DP[0] = 17 + 0 = 17)
- Remaining length = 0

Final cuts: [2, 6]
Verification: P[2] + P[6] = 5 + 17 = 22 ✓
```

### Alternative Solution:
```
Starting from DP[8] = 22:
- Best first cut = 6 (since P[6] + DP[2] = 17 + 5 = 22)
- Remaining length = 2  
- For DP[2] = 5: best cut = 2 (since P[2] + DP[0] = 5 + 0 = 5)
- Remaining length = 0

Final cuts: [6, 2]
Verification: P[6] + P[2] = 17 + 5 = 22 ✓
```

## Key Insights

1. **Optimal Substructure**: If we make an optimal first cut of length j, the remaining rod of length (n-j) must also be cut optimally
2. **Overlapping Subproblems**: Many different cutting sequences lead to the same remaining rod lengths
3. **Greedy Fails**: Always choosing the highest price-per-unit piece doesn't guarantee optimal solution
4. **Multiple Solutions**: Different cutting patterns can achieve the same maximum revenue

## Variations

### 1. **Limited Cuts**: Maximum k cuts allowed
```
DP[i][k] = max revenue from rod length i with at most k cuts
```

### 2. **Cut Costs**: Each cut has an associated cost c
```
DP[i] = max(P[j] + DP[i-j] - c) for all j from 1 to i
```

### 3. **Different Rod Types**: Multiple rod types with different price tables
```
DP[i][type] = max revenue from rod type 'type' of length i
```

### 4. **Minimum Pieces**: At least k pieces required
```
DP[i][k] = max revenue from rod length i with at least k pieces
```

## Applications

- **Resource Allocation**: Optimizing division of resources for maximum benefit
- **Manufacturing**: Cutting raw materials to minimize waste and maximize profit  
- **Scheduling**: Dividing time slots for optimal resource utilization
- **Network Bandwidth**: Allocating bandwidth segments for maximum throughput
- **Investment Planning**: Dividing investment amounts across different opportunities

## Time-Space Trade-offs

### Bottom-up DP:
- Time: O(n²), Space: O(n)
- Iterative, usually faster in practice

### Top-down DP (Memoization):
- Time: O(n²), Space: O(n) + recursion stack
- Recursive, more intuitive to write

### Space Optimization:
- Can compute DP[i] using only previously computed values
- No further space optimization possible due to dependency pattern
