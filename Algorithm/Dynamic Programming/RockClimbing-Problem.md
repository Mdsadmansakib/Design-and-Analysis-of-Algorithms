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
