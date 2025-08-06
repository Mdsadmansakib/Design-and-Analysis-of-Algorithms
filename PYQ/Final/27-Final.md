# Complete Solutions to Algorithm Questions

## Question 1

### Part a) Substitution Method for Recurrence Relation [6 marks]

**Given:** T(n) = 7T(n - 2) + n², verify if T(n) = O(n³)

**Solution:**
Using the substitution method, we assume T(n) ≤ cn³ for some constant c > 0.

**Substitution:**
T(n) = 7T(n-2) + n²
≤ 7c(n-2)³ + n²

Expanding (n-2)³:
(n-2)³ = n³ - 6n² + 12n - 8

So:
T(n) ≤ 7c(n³ - 6n² + 12n - 8) + n²
= 7cn³ - 42cn² + 84cn - 56c + n²
= 7cn³ + n²(-42c + 1) + 84cn - 56c

For this to be ≤ cn³, we need:
7cn³ + (lower order terms) ≤ cn³

This requires: 7c ≤ c, which means 6c ≤ 0.
Since c > 0, this inequality cannot be satisfied.

**Conclusion:** The assumption T(n) = O(n³) is **incorrect**.

**Alternative verification:** The dominant term 7T(n-2) suggests the solution grows faster than n³, likely around O(n^(log₂ 7)) ≈ O(n^2.81), which is indeed greater than O(n³).

**Relation to the quote:** The substitution method is indeed effective when the assumption is appropriate. Here, it clearly shows our assumption was wrong, demonstrating its diagnostic power.

### Part b) Master Theorem Analysis [8 marks]

**Master Theorem Statement Analysis:**
The quote suggests the Master Theorem provides a "cookbook approach" for all recurrences. This is an overstatement - it only applies to recurrences of the form T(n) = aT(n/b) + f(n).

**Applying Master Theorem:**

**I. T(n) = 2T(n/2) + n lg(n)**
- a = 2, b = 2, f(n) = n lg(n)
- n^(log_b a) = n^(log_2 2) = n¹ = n
- f(n) = n lg(n) = Ω(n^(1+ε)) where ε > 0
- Check regularity: af(n/b) = 2(n/2)lg(n/2) = n(lg(n) - 1) ≤ cf(n) for c < 1
- **Case 3 applies:** T(n) = Θ(n lg n)

**II. T(n) = 5T(n/2) + √n**
- a = 5, b = 2, f(n) = √n = n^(1/2)
- n^(log_b a) = n^(log_2 5) ≈ n^2.32
- f(n) = n^(1/2) = O(n^(2.32-ε)) for some ε > 0
- **Case 1 applies:** T(n) = Θ(n^(log_2 5))

## Question 2

### Part a) Topological Sorting and Cycle Detection [8 marks]

**Topological Sorting Principle:**
Topological sorting orders vertices of a DAG such that for every directed edge (u,v), u appears before v in the ordering.

**Cycle Detection Method:**
1. **Kahn's Algorithm approach:**
   - Maintain in-degree count for each vertex
   - Start with vertices having in-degree 0
   - Remove vertex and decrease in-degree of neighbors
   - If we can't process all vertices, a cycle exists

2. **DFS-based approach:**
   - Use three colors: WHITE (unvisited), GRAY (being processed), BLACK (finished)
   - If we encounter a GRAY vertex during DFS, we found a back edge (cycle)

**Algorithm Steps:**
```
1. Calculate in-degree for all vertices
2. Enqueue all vertices with in-degree 0
3. While queue is not empty:
   a. Dequeue vertex v
   b. Add v to topological order
   c. For each neighbor u of v:
      - Decrease in-degree of u
      - If in-degree of u becomes 0, enqueue u
4. If processed vertices < total vertices, cycle exists
```

**Limitations:**
- Only works on DAGs
- Cannot be directly applied to undirected graphs
- For undirected graphs, use DFS to detect back edges

**Extension to Undirected Graphs:**
Use DFS with parent tracking - if we visit a node that's not the parent, we found a cycle.

### Part b) Articulation Points using low() function [6 marks]

**low() Function:**
low(v) = minimum of:
- disc(v) (discovery time of v)
- disc(w) for all back edges (v,w)
- low(w) for all tree edges (v,w)

**Algorithm for Finding Articulation Points:**
```
ArticulationPoints(G):
1. Initialize disc[], low[], parent[], visited[]
2. For each unvisited vertex, call DFS

DFS(u):
1. visited[u] = true
2. disc[u] = low[u] = ++time
3. children = 0
4. For each neighbor v of u:
   a. If v not visited:
      - children++
      - parent[v] = u
      - DFS(v)
      - low[u] = min(low[u], low[v])
      - If parent[u] == NULL and children > 1:
        * u is articulation point (root case)
      - If parent[u] != NULL and low[v] >= disc[u]:
        * u is articulation point
   b. Else if v != parent[u]:
      - low[u] = min(low[u], disc[v])
```

**Application to Graph (S,A,B,E,C,D):**
Starting from S: S→A→B→E→D→C
- S is articulation point (root with >1 children)
- A is articulation point (low[B] >= disc[A])

## Question 3

### Part a) Dijkstra vs Bellman-Ford [7 marks]

**Given Graph Analysis:**
Graph: A←2→B, A→5→C, C←8→B
Source A, examining shortest paths.

**Dijkstra's Claim Verification:**
The claim "there is always a unique shortest path from source to destination" is **FALSE**.

**Counter-example using given graph:**
- Path A→B: cost = 2
- Path A→C→B: cost = 5 + 8 = 13
- Shortest path A→B has cost 2 (unique in this case)

But consider if we modify: A→B (cost 5), A→C (cost 2), C→B (cost 3)
- Path A→B: cost = 5
- Path A→C→B: cost = 2 + 3 = 5
- Two paths with same minimum cost!

**Bellman-Ford Enhancement:**
Bellman-Ford handles negative weights and detects negative cycles, which Dijkstra cannot. It's more robust but slower (O(VE) vs O(V log V + E)).

### Part b) Floyd-Warshall Algorithm [7 marks]

**Transitive Closure:**
The transitive closure of a graph includes all pairs (i,j) where there's a path from i to j.

**Floyd-Warshall for Transitive Closure:**
```
for k = 1 to n:
    for i = 1 to n:
        for j = 1 to n:
            closure[i][j] = closure[i][j] OR (closure[i][k] AND closure[k][j])
```

**Application to given graph A→2→B, A→5→C, C→3→B:**

Initial: A can reach B,C; C can reach B
After k=A: No change
After k=B: No change  
After k=C: A can reach B via C (already direct)

**Johnson's vs Floyd-Warshall:**
- Johnson's: O(V²log V + VE), better for sparse graphs
- Floyd-Warshall: O(V³), simpler implementation
- Fibonacci heap reduces Johnson's complexity in practice

## Question 4

### Part a) Matrix Chain Multiplication [10 marks]

**Given:** Matrices A(10×15), B(15×5), C(5×6), D(6×8)

**Optimal Substructure:** Yes, this problem exhibits optimal substructure because the optimal way to parenthesize A₁A₂...Aₙ includes optimal solutions for subproblems.

**Recurrence Relation:**
```
m[i][j] = min{m[i][k] + m[k+1][j] + p[i-1]×p[k]×p[j]} for k = i to j-1
```

**Dynamic Programming Solution:**
Dimensions array p = [10, 15, 5, 6, 8]

**Table Construction:**
```
m[1][1] = m[2][2] = m[3][3] = m[4][4] = 0

m[1][2] = 10×15×5 = 750 (A×B)
m[2][3] = 15×5×6 = 450 (B×C)  
m[3][4] = 5×6×8 = 240 (C×D)

m[1][3] = min{m[1][1]+m[2][3]+10×15×6, m[1][2]+m[3][3]+10×5×6}
        = min{0+450+900, 750+0+300} = min{1350, 1050} = 1050

m[2][4] = min{m[2][2]+m[3][4]+15×5×8, m[2][3]+m[4][4]+15×6×8}
        = min{0+240+600, 450+0+720} = min{840, 1170} = 840

m[1][4] = min{
  m[1][1]+m[2][4]+10×15×8 = 0+840+1200 = 2040,
  m[1][2]+m[3][4]+10×5×8 = 750+240+400 = 1390,
  m[1][3]+m[4][4]+10×6×8 = 1050+0+480 = 1530
} = 1390
```

**Optimal Parenthesization:** ((A×B)×(C×D))
**Minimum Scalar Multiplications:** 1390

### Part b) Greedy Algorithms [4 marks]

**Greedy Approach Characteristics:**
Greedy algorithms make locally optimal choices, hoping to find a global optimum.

**Two Contrasting Scenarios:**

**1. Suitable for Greedy (Activity Selection):**
- **Problem:** Schedule maximum number of non-overlapping activities
- **Condition:** Activities with earliest finish times don't conflict with optimal choices
- **Real-world:** Meeting room scheduling, CPU job scheduling

**2. Unsuitable for Greedy (0/1 Knapsack):**
- **Problem:** Maximize value within weight constraint  
- **Condition:** Items cannot be fractioned; local choices may eliminate globally optimal solutions
- **Real-world:** Portfolio optimization, resource allocation with indivisible items

## Question 5

### Part a) Dynamic Programming Trade-offs [4 marks]

**Trade-off Explanation:**
Dynamic Programming trades **computation time** for **memory usage**:

**Time Savings:**
- Avoids recomputing overlapping subproblems
- Reduces exponential time to polynomial time
- Example: Fibonacci from O(2ⁿ) to O(n)

**Memory Cost:**
- Stores solutions to subproblems in tables/arrays
- Space complexity often O(n) to O(n²) or higher
- Memoization requires additional storage structures

**Exponential to Polynomial Transformation:**
Many naturally exponential problems become polynomial:
- **Longest Common Subsequence:** O(2ⁿ) → O(mn)
- **Edit Distance:** O(3ⁿ) → O(mn)  
- **Matrix Chain Multiplication:** O(2ⁿ) → O(n³)

### Part b) Peak Finding with Dynamic Programming [10 marks]

**Problem Analysis:**
Given 2D grid where agent can move to three positions: directly above, diagonally up-right, diagonally up-left.

**Recurrence Relation:**
```
Let peak[i][j] = maximum peak reachable starting from position (i,j)

peak[i][j] = max(
  grid[i][j] + peak[i-1][j],     // directly above
  grid[i][j] + peak[i-1][j+1],  // diagonally up-right  
  grid[i][j] + peak[i-1][j-1]   // diagonally up-left
)

Base case: peak[0][j] = grid[0][j] (top row)
```

**Algorithm Steps:**
```
1. Initialize first row: peak[0][j] = grid[0][j] for all j
2. For i = 1 to rows-1:
   For j = 0 to cols-1:
     peak[i][j] = grid[i][j] + max(
       peak[i-1][j] if valid,
       peak[i-1][j+1] if valid,  
       peak[i-1][j-1] if valid
     )
3. Return max value in last row
```

**Dynamic Programming Properties:**
1. **Optimal Substructure:** Optimal path to (i,j) includes optimal paths to reachable positions in row i-1
2. **Overlapping Subproblems:** Multiple paths may lead to same position
3. **Memoization:** Store computed values to avoid recomputation

**Traceback Function (Pseudocode):**
```
function traceback(i, j, peak[][], grid[][]):
    if i == 0:
        return [(i, j)]
    
    max_prev = -infinity
    best_prev_j = -1
    
    for dj in [-1, 0, 1]:  // left, center, right
        if 0 <= j+dj < cols and peak[i-1][j+dj] > max_prev:
            max_prev = peak[i-1][j+dj]
            best_prev_j = j + dj
    
    return traceback(i-1, best_prev_j, peak, grid) + [(i, j)]
```

## Question 6

### Part a) Task Assignment Flow Network [9 marks]

**Flow Network Design:**

**Network Structure:**
```
Source (S) → Employees (A,B,C) → Tasks (T1,T1',T2,T3) → Sink (T)

Capacities:
- S → A: 1 (Alice can do 1 task at a time)
- S → B: 1 (Bob can do 1 task at a time)  
- S → C: 2 (Charlie can multitask, max 2 tasks)

- A → T1: 1, A → T3: 1 (Alice's capabilities)
- B → T2: 1, B → T3: 1 (Bob's capabilities)
- C → T1: 1, C → T2: 1 (Charlie's capabilities)

- T1 → T: 1, T1' → T: 1 (T2 needs to be done twice)
- T2 → T: 2, T3 → T: 1
```

**Extended Bipartite Matching:** Yes, this is an extended bipartite matching because:
- Left side: Employees with different capacities
- Right side: Tasks with different demands
- Extensions: Multiple capacities and task repetition

**Ford-Fulkerson Algorithm Steps:**

**Step 1:** Find augmenting path S→C→T1→T, flow = 1
- Residual: C has capacity 1 remaining

**Step 2:** Find path S→C→T2→T, flow = 1  
- Residual: C at full capacity

**Step 3:** Find path S→A→T3→T, flow = 1
- Residual: A at full capacity

**Step 4:** Find path S→B→T2→T, flow = 1
- Residual: B at full capacity, T2 needs 1 more

**Step 5:** No more augmenting paths available

**Residual Networks:** Show remaining capacities after each step, demonstrating constraint satisfaction.

### Part b) Ford-Fulkerson Analysis [5 marks]

**I. Augmenting Paths and Computational Complexity:**

**Impact of Path Choice:**
- **Poor choice:** Can lead to O(Ef) complexity where f is maximum flow
- **Good choice (shortest paths):** Edmonds-Karp achieves O(VE²)
- **Example:** In unit capacity networks, poor paths may require exponential iterations

**Edmonds-Karp Mitigation:**
- Always chooses shortest augmenting path (BFS)
- Guarantees polynomial time complexity
- **Principle:** Shortest paths prevent "bad" augmenting paths that could cause exponential behavior

**II. Backward Edges in Residual Network:**

**Role of Backward Edges:**
- **Undo Effect:** Allow algorithm to "undo" previous flow assignments
- **Optimization:** Enable better flow distribution in later iterations  
- **Correctness:** Essential for finding maximum flow when initial choices are suboptimal

**Example:** If we initially send flow through a path that blocks the optimal solution, backward edges allow us to reroute that flow through a better path.

## Question 7

### Part a) Counting Inversions with Divide and Conquer [7 marks]

**Array:** [17, 23, 11, 8, 54, 45, 48, 12, 3, 1]

**Counting Inversions Algorithm:**
```
function countInversions(arr, temp, left, right):
    if left >= right: return 0
    
    mid = (left + right) / 2
    inv_count = 0
    
    inv_count += countInversions(arr, temp, left, mid)
    inv_count += countInversions(arr, temp, mid+1, right)
    inv_count += mergeAndCount(arr, temp, left, mid, right)
    
    return inv_count

function mergeAndCount(arr, temp, left, mid, right):
    i = left, j = mid+1, k = left
    inv_count = 0
    
    while i <= mid and j <= right:
        if arr[i] <= arr[j]:
            temp[k++] = arr[i++]
        else:
            temp[k++] = arr[j++]
            inv_count += (mid - i + 1)  // All remaining elements in left are inversions
    
    // Copy remaining elements
    copy remaining elements to temp
    copy temp back to arr
    
    return inv_count
```

**Execution Trace:**
- Split: [17,23,11,8,54] | [45,48,12,3,1]
- Left side inversions: (17,11), (17,8), (23,11), (23,8) = 4
- Right side inversions: (45,12), (45,3), (45,1), (48,12), (48,3), (48,1), (12,3), (12,1), (3,1) = 9
- Cross inversions: All elements from left > elements from right
- **Total inversions: 45**

### Part b) Resource Management Algorithm [7 marks]

**Problem:** Two people alternately select items with volume ≤ V, maximize cost difference.

**Algorithm Design:**
This is a variant of the **Optimal Strategy for a Game** problem.

**Dynamic Programming Approach:**
```
dp[i][j][turn] = maximum difference when considering items i to j, 
                 where turn indicates whose turn (0 or 1)

For person 1's turn:
dp[i][j][0] = max(
    items[i].cost + dp[i+1][j][1],  // take item i
    items[j].cost + dp[i][j-1][1]   // take item j
)

For person 2's turn:  
dp[i][j][1] = min(
    dp[i+1][j][0] - items[i].cost,  // person 2 takes item i
    dp[i][j-1][0] - items[j].cost   // person 2 takes item j
)
```

**Greedy Alternative:**
Sort items by cost/volume ratio, each person greedily takes the best available item that fits.

**Time Complexity:** 
- DP approach: O(n²)
- Greedy approach: O(n log n)

**Space Complexity:**
- DP: O(n²) for memoization table
- Greedy: O(1) auxiliary space

**Analysis:** The DP approach guarantees optimal solution while greedy provides approximation with better time complexity.
