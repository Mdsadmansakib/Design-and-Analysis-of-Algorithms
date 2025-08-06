# Rock Climbing Problem Analysis

## Problem Overview

The rock climbing problem is a classic **dynamic programming** optimization problem where we need to find the minimum-cost path from the bottom of a wall to the top.

### Key Characteristics:
- **Grid-based movement**: 2D matrix representing wall blocks
- **Directional constraints**: Can only move up (3 directions)
- **Optimization goal**: Minimize total danger/cost
- **Optimal substructure**: Each subproblem's solution contributes to the global optimum

## Problem Formulation

### Input:
- `C[i][j]`: Danger rating matrix (n rows × m columns)
- Bottom row (index 0) = starting positions
- Top row (index n-1) = destination

### Movement Rules:
From position `(i, j)`, climber can move to:
- `(i+1, j-1)` - Up-left diagonal
- `(i+1, j)` - Straight up  
- `(i+1, j+1)` - Up-right diagonal

### Objective:
Find path with minimum sum of danger ratings from any bottom cell to any top cell.

## Dynamic Programming Solution

### State Definition:
`DP[i][j]` = minimum danger to reach cell `(i, j)` from any starting position in bottom row

### Recurrence Relation:
```
DP[i][j] = C[i][j] + min(
    DP[i-1][j-1],  // from up-left
    DP[i-1][j],    // from directly below  
    DP[i-1][j+1]   // from up-right
)
```

### Base Case:
```
DP[0][j] = C[0][j]  // First row initialization
```

### Boundary Conditions:
- Out-of-bounds cells treated as infinite cost
- `DP[i][-1] = DP[i][m] = ∞`

## Algorithm Complexity

- **Time Complexity**: `O(n × m)` where n = rows, m = columns
- **Space Complexity**: `O(n × m)` for DP table, can be optimized to `O(m)`
- **Optimal**: Yes, finds globally optimal solution

## Example Walkthrough

### Input Matrix:
```
C = [
    [2, 8, 9, 5, 8],  // Bottom (row 0)
    [4, 4, 6, 2, 3],  // Row 1
    [5, 7, 5, 6, 1],  // Row 2  
    [3, 2, 5, 4, 8]   // Top (row 3)
]
```

### DP Table Construction:

**Step 1: Initialize bottom row**
```
DP[0] = [2, 8, 9, 5, 8]
```

**Step 2: Fill subsequent rows**
```
DP[1][0] = C[1][0] + min(∞, DP[0][0], DP[0][1]) = 4 + min(∞, 2, 8) = 6
DP[1][1] = C[1][1] + min(DP[0][0], DP[0][1], DP[0][2]) = 4 + min(2, 8, 9) = 6
DP[1][2] = C[1][2] + min(DP[0][1], DP[0][2], DP[0][3]) = 6 + min(8, 9, 5) = 11
...
```

**Final DP Table:**
```
DP = [
    [2,  8,  9,  5,  8],   // Row 0
    [6,  6, 11,  7, 11],   // Row 1  
    [11,12, 11, 13,  8],   // Row 2
    [14,14, 16, 19, 16]    // Row 3
]
```

**Answer**: `min(DP[3]) = 14`

## Pseudocode

```
ALGORITHM RockClimbingDP(C):
    INPUT: C[n][m] - danger rating matrix
    OUTPUT: minimum danger to reach top
    
    // Initialize DP table
    CREATE DP[n][m]
    
    // Base case - bottom row
    FOR j = 0 to m-1:
        DP[0][j] = C[0][j]
    
    // Fill DP table bottom-up
    FOR i = 1 to n-1:
        FOR j = 0 to m-1:
            
            // Calculate minimum from three possible previous positions
            min_prev = INFINITY
            
            // Check up-left diagonal
            IF j > 0:
                min_prev = MIN(min_prev, DP[i-1][j-1])
            
            // Check directly below
            min_prev = MIN(min_prev, DP[i-1][j])
            
            // Check up-right diagonal  
            IF j < m-1:
                min_prev = MIN(min_prev, DP[i-1][j+1])
            
            // Update current cell
            DP[i][j] = C[i][j] + min_prev
    
    // Find minimum in top row
    result = INFINITY
    FOR j = 0 to m-1:
        result = MIN(result, DP[n-1][j])
    
    RETURN result

ALGORITHM ReconstructPath(C, DP):
    // Backtrack to find actual optimal path
    n = rows(C), m = cols(C)
    path = []
    
    // Find starting position in top row
    min_val = MIN(DP[n-1])
    j = INDEX_OF(DP[n-1], min_val)
    
    // Trace back from top to bottom
    FOR i = n-1 DOWN TO 0:
        path.PREPEND((i, j))
        
        IF i > 0:
            // Find which previous cell led to current minimum
            candidates = []
            IF j > 0: candidates.ADD((DP[i-1][j-1], j-1))
            candidates.ADD((DP[i-1][j], j))
            IF j < m-1: candidates.ADD((DP[i-1][j+1], j+1))
            
            // Choose minimum previous position
            min_candidate = MIN(candidates by value)
            j = min_candidate.column
    
    RETURN path
```

## Key Insights

1. **Optimal Substructure**: The optimal path to any cell depends only on optimal paths to cells in the previous row
2. **Overlapping Subproblems**: Multiple paths may lead to the same cell, but we only need to store the minimum cost
3. **Bottom-up Approach**: More natural than top-down since we're climbing upward
4. **Space Optimization**: Can reduce to O(m) space by keeping only current and previous rows

## Step-by-Step Simulation

Let's apply the DP formula step-by-step using the example matrix:

### Input Matrix C[i][j] (Bottom to Top):
```
Row 0 (Bottom): [2, 8, 9, 5, 8]
Row 1:          [4, 4, 6, 2, 3]  
Row 2:          [5, 7, 5, 6, 1]
Row 3 (Top):    [3, 2, 5, 4, 8]
```

### Step 1: Initialize Base Row (Row 0)
Since row 0 is the starting position:
```
A[0][0] = 2    A[0][1] = 8    A[0][2] = 9    A[0][3] = 5    A[0][4] = 8
```

### Step 2: Fill Row 1 (5 calculations)

**Calculation 1:** `A[1][0] = C[1][0] + min(∞, A[0][0], A[0][1])`
- `A[1][0] = 4 + min(∞, 2, 8) = 4 + 2 = 6`

**Calculation 2:** `A[1][1] = C[1][1] + min(A[0][0], A[0][1], A[0][2])`
- `A[1][1] = 4 + min(2, 8, 9) = 4 + 2 = 6`

**Calculation 3:** `A[1][2] = C[1][2] + min(A[0][1], A[0][2], A[0][3])`
- `A[1][2] = 6 + min(8, 9, 5) = 6 + 5 = 11`

**Calculation 4:** `A[1][3] = C[1][3] + min(A[0][2], A[0][3], A[0][4])`
- `A[1][3] = 2 + min(9, 5, 8) = 2 + 5 = 7`

**Calculation 5:** `A[1][4] = C[1][4] + min(A[0][3], A[0][4], ∞)`
- `A[1][4] = 3 + min(5, 8, ∞) = 3 + 5 = 8`

**DP Table after Row 1:**
```
Row 0: [2,  8,  9,  5,  8]
Row 1: [6,  6, 11,  7,  8]
```

### Step 3: Fill Row 2 (5 calculations)

**Calculation 6:** `A[2][0] = C[2][0] + min(∞, A[1][0], A[1][1])`
- `A[2][0] = 5 + min(∞, 6, 6) = 5 + 6 = 11`

**Calculation 7:** `A[2][1] = C[2][1] + min(A[1][0], A[1][1], A[1][2])`
- `A[2][1] = 7 + min(6, 6, 11) = 7 + 6 = 13`

**Calculation 8:** `A[2][2] = C[2][2] + min(A[1][1], A[1][2], A[1][3])`
- `A[2][2] = 5 + min(6, 11, 7) = 5 + 6 = 11`

**Calculation 9:** `A[2][3] = C[2][3] + min(A[1][2], A[1][3], A[1][4])`
- `A[2][3] = 6 + min(11, 7, 8) = 6 + 7 = 13`

**Calculation 10:** `A[2][4] = C[2][4] + min(A[1][3], A[1][4], ∞)`
- `A[2][4] = 1 + min(7, 8, ∞) = 1 + 7 = 8`

**DP Table after Row 2:**
```
Row 0: [2,  8,  9,  5,  8]
Row 1: [6,  6, 11,  7,  8]
Row 2: [11,13, 11, 13, 8]
```

### Step 4: Fill Row 3 - Top Row (5 calculations)

**Calculation 11:** `A[3][0] = C[3][0] + min(∞, A[2][0], A[2][1])`
- `A[3][0] = 3 + min(∞, 11, 13) = 3 + 11 = 14`

**Calculation 12:** `A[3][1] = C[3][1] + min(A[2][0], A[2][1], A[2][2])`
- `A[3][1] = 2 + min(11, 13, 11) = 2 + 11 = 13`

**Calculation 13:** `A[3][2] = C[3][2] + min(A[2][1], A[2][2], A[2][3])`
- `A[3][2] = 5 + min(13, 11, 13) = 5 + 11 = 16`

**Calculation 14:** `A[3][3] = C[3][3] + min(A[2][2], A[2][3], A[2][4])`
- `A[3][3] = 4 + min(11, 13, 8) = 4 + 8 = 12`

**Calculation 15:** `A[3][4] = C[3][4] + min(A[2][3], A[2][4], ∞)`
- `A[3][4] = 8 + min(13, 8, ∞) = 8 + 8 = 16`

### Final DP Table:
```
Row 0: [2,  8,  9,  5,  8]
Row 1: [6,  6, 11,  7,  8]
Row 2: [11,13, 11, 13, 8]
Row 3: [14,13, 16, 12, 16]
```

### Step 5: Find Minimum in Top Row
`min(14, 13, 16, 12, 16) = 12`

**Answer: Minimum danger = 12**

## Path Tracing (Backtracking)

Now let's trace back to find the actual optimal path:

### Tracing Steps:

**Step 1:** Start at `A[3][3] = 12` (minimum in top row)

**Step 2:** From `A[3][3]`, check which previous cell gave minimum:
- Previous options: `A[2][2] = 11`, `A[2][3] = 13`, `A[2][4] = 8`
- Minimum: `A[2][4] = 8`
- **Trace:** `A[3][3] ← A[2][4]`

**Step 3:** From `A[2][4]`, check which previous cell gave minimum:
- Previous options: `A[1][3] = 7`, `A[1][4] = 8`, (out of bounds)
- Minimum: `A[1][3] = 7`
- **Trace:** `A[2][4] ← A[1][3]`

**Step 4:** From `A[1][3]`, check which previous cell gave minimum:
- Previous options: `A[0][2] = 9`, `A[0][3] = 5`, `A[0][4] = 8`
- Minimum: `A[0][3] = 5`
- **Trace:** `A[1][3] ← A[0][3]`

### Optimal Path (Bottom to Top):
```
Position (0,3) → Value: 5
Position (1,3) → Value: 2  
Position (2,4) → Value: 1
Position (3,3) → Value: 4
```

**Path verification:** `5 + 2 + 1 + 4 = 12` ✓

### Movement Pattern:
- Start at column 3
- Stay in column 3 (straight up)
- Move right to column 4 (up-right)
- Move left to column 3 (up-left)

This represents the safest climbing path with minimum total danger of 12.

## Applications

- **Route Planning**: Finding least-cost paths in grid-based navigation
- **Resource Allocation**: Optimizing sequential decisions with constraints  
- **Game AI**: Pathfinding in tile-based games
- **Image Processing**: Seam carving and optimal edge detection

## Variations

- **Multiple starting/ending points**: Extend to handle arbitrary start/end positions
- **Weighted movements**: Different costs for different movement types
- **Obstacles**: Handle blocked cells with infinite cost
- **3D climbing**: Extend to three-dimensional climbing problems
