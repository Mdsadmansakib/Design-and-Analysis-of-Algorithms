# Backtracking Algorithms: Complete Analysis & Implementation Guide

## Overview

Backtracking is a general algorithmic technique for solving problems recursively by trying to build a solution incrementally, one piece at a time, and removing those solutions that fail to satisfy the constraints of the problem at any point in time ("backtrack" and try another path).

## General Backtracking Template

```pseudocode
function backtrack(problem_state):
    if problem_state is a solution:
        return solution  // or return true/success

    for each choice in choices for problem_state:
        if choice is valid:
            make choice
            result = backtrack(updated problem_state)
            if result is a solution:
                return result  // or return true/success
            undo choice  // backtrack

    return failure  // or return false
```

## Classic Backtracking Problems

### 1. N-Queens Problem (8 Queens)

**Goal:** Place N queens on an N×N chessboard so that no two queens threaten each other.

**Pseudocode:**
```pseudocode
function solveNQueens(row):
    if row == N:
        print solution
        return true
    
    for col from 0 to N-1:
        if isSafe(row, col):
            placeQueen(row, col)
            if solveNQueens(row + 1):
                return true
            removeQueen(row, col)  // backtrack
    return false

function isSafe(row, col):
    // Check column conflicts
    for i from 0 to row-1:
        if board[i][col] == 1:
            return false
    
    // Check diagonal conflicts
    for i from 0 to row-1:
        if board[i][col-(row-i)] == 1 or board[i][col+(row-i)] == 1:
            return false
    
    return true
```

**Time Complexity:** O(N!) - In worst case, we try all possible placements
**Space Complexity:** O(N) - Recursion depth and board storage

### 2. Graph Coloring

**Goal:** Assign colors to vertices so that no two adjacent vertices share the same color.

**Pseudocode:**
```pseudocode
function colorGraph(node, numColors):
    if node == totalNodes:
        return true
    
    for color from 1 to numColors:
        if isSafeColor(node, color):
            assign color to node
            if colorGraph(node + 1, numColors):
                return true
            remove color from node  // backtrack
    return false

function isSafeColor(node, color):
    for each adjacent node:
        if adjacent node has same color:
            return false
    return true
```

**Time Complexity:** O(m^V) where m = number of colors, V = number of vertices
**Space Complexity:** O(V) - Recursion depth and color array

### 3. Subset Sum Problem

**Goal:** Find subsets whose sum equals a target value.

**Pseudocode:**
```pseudocode
function subsetSum(nums, target, index, currentSet):
    if target == 0:
        print currentSet
        return true
    if index == length(nums) or target < 0:
        return false
    
    // Include nums[index]
    if subsetSum(nums, target - nums[index], index + 1, currentSet + [nums[index]]):
        return true
    
    // Exclude nums[index]  
    if subsetSum(nums, target, index + 1, currentSet):
        return true
    
    return false
```

**Time Complexity:** O(2^n) - Each element can be included or excluded
**Space Complexity:** O(n) - Recursion depth

### 4. Hamiltonian Cycle

**Goal:** Find a cycle visiting every vertex exactly once and returning to start.

**Pseudocode:**
```pseudocode
function hamiltonianCycle(path, position):
    if position == numVertices:
        if edge exists from path[position-1] to path[0]:
            return true
        else:
            return false
    
    for vertex from 1 to numVertices-1:
        if isSafeHamiltonian(vertex, path, position):
            path[position] = vertex
            if hamiltonianCycle(path, position + 1):
                return true
            path[position] = -1  // backtrack
    return false

function isSafeHamiltonian(vertex, path, position):
    // Check if vertex is adjacent to previous vertex
    if no edge between path[position-1] and vertex:
        return false
    
    // Check if vertex is already in path
    for i from 0 to position-1:
        if path[i] == vertex:
            return false
    
    return true
```

**Time Complexity:** O(N!) - Trying all possible permutations of vertices
**Space Complexity:** O(N) - Path array and recursion depth

### 5. Travelling Salesman Problem (TSP)

**Goal:** Find the shortest possible route that visits each city once and returns to origin.

**Pseudocode:**
```pseudocode
function tsp(currentCity, visitedMask, cost):
    if visitedMask == (1 << numCities) - 1:  // All cities visited
        return cost + distance[currentCity][0]  // Return to start
    
    if memo[currentCity][visitedMask] != -1:
        return memo[currentCity][visitedMask]
    
    minCost = INFINITY
    for nextCity from 0 to numCities-1:
        if (visitedMask & (1 << nextCity)) == 0:  // City not visited
            newCost = tsp(nextCity, visitedMask | (1 << nextCity), 
                         cost + distance[currentCity][nextCity])
            minCost = min(minCost, newCost)
    
    memo[currentCity][visitedMask] = minCost
    return minCost
```

**Time Complexity:** O(2^n × n^2) with memoization, O(n!) without
**Space Complexity:** O(2^n × n) - Memoization table

### 6. Permutation Generation

**Goal:** Generate all possible orderings of a set.

**Pseudocode:**
```pseudocode
function permute(arr, left, right):
    if left == right:
        print arr
        return
    
    for i from left to right:
        swap(arr[left], arr[i])
        permute(arr, left + 1, right)
        swap(arr[left], arr[i])  // backtrack
```

**Time Complexity:** O(n! × n) - n! permutations, each taking O(n) to print
**Space Complexity:** O(n) - Recursion depth

### 7. Combination Generation

**Goal:** Generate all possible groupings of k elements from n elements.

**Pseudocode:**
```pseudocode
function combine(arr, start, k, currentSet):
    if length(currentSet) == k:
        print currentSet
        return
    
    for i from start to length(arr) - 1:
        if length(arr) - i >= k - length(currentSet):  // Pruning
            combine(arr, i + 1, k, currentSet + [arr[i]])
```

**Time Complexity:** O(C(n,k) × k) = O(n!/(k!(n-k)!) × k)
**Space Complexity:** O(k) - Current set and recursion depth

### 8. Maze Pathfinding

**Goal:** Find a path from start to exit in a maze.

**Pseudocode:**
```pseudocode
function findPath(maze, x, y):
    if (x, y) is outside maze or is a wall or already visited:
        return false
    if (x, y) is the exit:
        return true

    mark (x, y) as visited

    for each direction in [up, down, left, right]:
        newX, newY = move in direction
        if findPath(maze, newX, newY):
            return true

    unmark (x, y) as visited  // backtrack
    return false
```

**Time Complexity:** O(4^(m×n)) in worst case, O(m×n) in practice
**Space Complexity:** O(m×n) - Visited array and recursion depth

## Optimization Techniques

1. **Pruning:** Eliminate branches early when they can't lead to valid solutions
2. **Constraint Propagation:** Use problem constraints to reduce search space
3. **Heuristics:** Use domain knowledge to guide search order
4. **Memoization:** Cache results to avoid redundant computations
5. **Iterative Deepening:** Combine benefits of BFS and DFS

## When to Use Backtracking

- **Constraint Satisfaction Problems:** When you need to find solutions that satisfy multiple constraints
- **Optimization Problems:** When you need to find the best solution among many possibilities  
- **Enumeration Problems:** When you need to generate all possible solutions
- **Decision Problems:** When you need to determine if a solution exists

## Implementation Tips

1. **State Representation:** Choose efficient data structures for problem state
2. **Validity Check:** Implement efficient constraint checking functions
3. **Early Termination:** Return immediately when solution is found
4. **Memory Management:** Properly undo changes during backtracking
5. **Base Case:** Clearly define termination conditions
