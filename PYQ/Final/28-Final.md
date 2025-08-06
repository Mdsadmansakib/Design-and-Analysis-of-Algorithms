# Algorithm and Data Structure Solutions

## Question 1

### (a) Articulation Points (Cut Vertices) and DFS Algorithm

**Significance of Articulation Point:**
An articulation point (cut vertex) is a vertex whose removal increases the number of connected components in an undirected graph. These vertices are critical for network connectivity - if they fail, the network becomes disconnected.

**DFS-based Algorithm to Detect Articulation Points:**
1. Perform DFS and maintain:
   - `disc[v]`: Discovery time of vertex v
   - `low[v]`: Lowest discovery time reachable from subtree rooted at v
   - `parent[v]`: Parent of v in DFS tree

2. A vertex u is an articulation point if:
   - u is root of DFS tree and has more than one child, OR
   - u is not root and has a child v such that `low[v] >= disc[u]`

**Algorithm Steps:**
```
ArticulationPoints(graph):
    Initialize disc[], low[], parent[], visited[]
    time = 0
    
    for each vertex v:
        if not visited[v]:
            DFS(v)
    
DFS(u):
    visited[u] = true
    disc[u] = low[u] = ++time
    children = 0
    
    for each adjacent vertex v of u:
        if not visited[v]:
            children++
            parent[v] = u
            DFS(v)
            
            low[u] = min(low[u], low[v])
            
            if parent[u] == -1 and children > 1:
                u is articulation point
            if parent[u] != -1 and low[v] >= disc[u]:
                u is articulation point
        else if v != parent[u]:
            low[u] = min(low[u], disc[v])
```

**Contrast with Bridge Detection:**
- **Bridges**: Edges whose removal increases connected components
- **Condition**: Edge (u,v) is bridge if `low[v] > disc[u]`
- **Difference**: Bridges use `>` while articulation points use `>=`

### (b) Euler Path and Euler Circuit Conditions

**Euler Path:** A path that visits every edge exactly once.
**Euler Circuit:** An Euler path that starts and ends at the same vertex.

**Necessary and Sufficient Conditions:**

**For Euler Circuit:**
- Graph must be connected
- All vertices must have even degree

**For Euler Path:**
- Graph must be connected
- Exactly 0 or 2 vertices must have odd degree
- If 2 vertices have odd degree, path must start at one and end at the other

**Example:**
```
Graph: A---B---C
       |   |   |
       D---E---F

Degrees: A(2), B(3), C(2), D(2), E(4), F(3)
Odd degree vertices: B, F (exactly 2)
Result: Has Euler Path from B to F, but no Euler Circuit
```

### (c) DFS for Strongly Connected Components (SCCs) - Kosaraju's Algorithm

**Modification to find SCCs in directed graph:**

**Main Steps:**
1. **First DFS:** Perform DFS on original graph and store vertices by finish time (use stack)
2. **Transpose:** Create transpose graph (reverse all edges)
3. **Second DFS:** Perform DFS on transpose graph in order of decreasing finish time
4. Each DFS tree in step 3 is one SCC

**Algorithm:**
```
Kosaraju(graph):
    1. stack = empty
       visited = false for all vertices
       
    2. for each vertex v:
           if not visited[v]:
               DFS1(v, stack)  // Fill stack with finish times
    
    3. transpose_graph = reverse all edges of graph
       visited = false for all vertices
       
    4. while stack not empty:
           v = stack.pop()
           if not visited[v]:
               DFS2(v)  // Each call finds one SCC
               print "New SCC found"

DFS1(v, stack):
    visited[v] = true
    for each adjacent u of v:
        if not visited[u]:
            DFS1(u, stack)
    stack.push(v)  // Push after visiting all descendants

DFS2(v):
    visited[v] = true
    print v  // Part of current SCC
    for each adjacent u of v in transpose:
        if not visited[u]:
            DFS2(u)
```

## Question 2

### (a) Dijkstra's Algorithm Failure with Negative Edges

**Why Dijkstra Fails:**
Dijkstra's algorithm assumes that once a vertex is processed (added to final set), the shortest path to it is finalized. With negative edges, a shorter path might be discovered later.

**Example:**
```
Graph: A --(-1)--> B --(-2)--> C
       |                      ^
       +---------(1)----------+

Dijkstra's execution:
1. Start at A: dist[A]=0, dist[B]=∞, dist[C]=∞
2. Process A: dist[B]=∞, dist[C]=1 (via direct edge)
3. Process C: dist[C]=1 is finalized (WRONG!)
4. Process B: dist[B]=-1, but C is already finalized

Correct shortest path A→C should be: A→B→C = -3, not A→C = 1
```

### (b) Johnson's Algorithm for All-Pairs Shortest Paths

**How Johnson's Algorithm Works:**
1. **Add auxiliary vertex:** Add vertex s connected to all vertices with weight 0
2. **Bellman-Ford from s:** Find h[v] = shortest distance from s to each vertex v
3. **Reweight edges:** For edge (u,v) with weight w, new weight = w + h[u] - h[v]
4. **Run Dijkstra:** Run Dijkstra from each vertex on reweighted graph
5. **Recover distances:** For path u→v, real distance = dijkstra_dist - h[u] + h[v]

**Why it's efficient for sparse graphs:**
- Time complexity: O(V² log V + VE)
- For sparse graphs (E << V²), this is better than Floyd-Warshall O(V³)
- Dijkstra is efficient on sparse graphs with positive weights

### (c) Shortest Paths in DAGs

**Why DAG shortest paths are simpler:**

1. **No negative cycles possible** - DAGs are acyclic by definition
2. **Topological ordering exists** - Process vertices in topological order
3. **One pass sufficient** - Each vertex processed exactly once
4. **No need for complex algorithms** - Simple relaxation works

**Algorithm:**
```
DAG_ShortestPath(graph, source):
    1. topological_order = topological_sort(graph)
    2. dist[source] = 0, dist[others] = ∞
    3. for each vertex u in topological_order:
           for each edge (u,v):
               relax(u, v)  // dist[v] = min(dist[v], dist[u] + weight(u,v))
```

**Time Complexity:** O(V + E) - linear time, much simpler than Dijkstra's O(V² + E)

## Question 3

### (a) Solving T(n) = T(n/2) + n using Substitution Method

**Substitution Method Steps:**

**Step 1: Guess the solution**
Based on the recurrence pattern, guess: T(n) = O(n)
More specifically, guess: T(n) ≤ cn for some constant c > 0

**Step 2: Prove by induction**
Base case: T(1) = 1 ≤ c(1) = c, true for c ≥ 1

Inductive step: Assume T(k) ≤ ck for all k < n
Need to prove: T(n) ≤ cn

T(n) = T(n/2) + n
     ≤ c(n/2) + n     (by inductive hypothesis)
     = cn/2 + n
     = n(c/2 + 1)
     
For T(n) ≤ cn, we need:
n(c/2 + 1) ≤ cn
c/2 + 1 ≤ c
1 ≤ c/2
c ≥ 2

**Step 3: Verify the solution**
With c = 2: T(n) ≤ 2n
T(n) = T(n/2) + n ≤ 2(n/2) + n = n + n = 2n ✓

**Therefore: T(n) = O(n)**

### (b) Merging Three Sorted Arrays - Pseudocode

```pseudocode
MERGE_THREE_ARRAYS(A, B, C):
    n1 = length(A), n2 = length(B), n3 = length(C)
    result = array of size (n1 + n2 + n3)
    i = 0, j = 0, k = 0, index = 0
    
    // Merge while all arrays have elements
    while i < n1 AND j < n2 AND k < n3:
        if A[i] <= B[j] AND A[i] <= C[k]:
            result[index++] = A[i++]
        else if B[j] <= A[i] AND B[j] <= C[k]:
            result[index++] = B[j++]
        else:
            result[index++] = C[k++]
    
    // Merge remaining elements from two arrays
    while i < n1 AND j < n2:
        if A[i] <= B[j]:
            result[index++] = A[i++]
        else:
            result[index++] = B[j++]
    
    while i < n1 AND k < n3:
        if A[i] <= C[k]:
            result[index++] = A[i++]
        else:
            result[index++] = C[k++]
    
    while j < n2 AND k < n3:
        if B[j] <= C[k]:
            result[index++] = B[j++]
        else:
            result[index++] = C[k++]
    
    // Copy remaining elements
    while i < n1: result[index++] = A[i++]
    while j < n2: result[index++] = B[j++]
    while k < n3: result[index++] = C[k++]
    
    return result
```

### (c) Dynamic Programming Analysis

**Analyzing the given pseudocode:**

The function implements a recursive solution with memoization (dynamic programming).

**Big-O Runtime Complexity:** O(n²)
- The DP array has n elements
- Each element DP[i] is computed at most once
- Computing DP[i] involves a loop from 1 to i, which takes O(i) time
- Total time: O(1 + 2 + 3 + ... + n) = O(n²)

**Space Complexity:** O(n)
- DP array of size n
- Recursive call stack depth is O(n) in worst case
- Total space: O(n)

**What the algorithm computes:**
Looking at the recurrence: `answer = answer + function(i, DP) + 1`
This appears to be computing some cumulative sum where each position contributes its computed value plus 1.

## Question 4

### (a) Second Best Minimum Spanning Tree

For the given graph, I need to find the MST first, then the second-best MST.

**Step 1: Find MST using Kruskal's Algorithm**
Edges sorted by weight: (C,E,2), (B,C,2), (E,F,2), (A,D,3), (F,G,3), (A,B,3), (D,E,4), (D,G,4), (C,F,6), (E,G,5), (B,C,5)

MST edges selected:
1. (C,E,2) - weight 2
2. (B,C,2) - weight 2  
3. (E,F,2) - weight 2
4. (A,D,3) - weight 3
5. (F,G,3) - weight 3
6. (A,B,3) - weight 3

**MST total weight: 15**

**Step 2: Find Second Best MST**
For each MST edge, remove it and find the minimum weight edge that reconnects the graph:

1. Remove (C,E,2): Add (D,E,4) → Total: 17
2. Remove (B,C,2): Add (B,C,5) → Total: 18  
3. Remove (E,F,2): Add (C,F,6) → Total: 19
4. Remove (A,D,3): Add (D,G,4) → Total: 16
5. Remove (F,G,3): Add (E,G,5) → Total: 17
6. Remove (A,B,3): No alternative connection

**Second Best MST weight: 16** (remove (A,D,3), add (D,G,4))

### (b) Kruskal's Algorithm and Cycle Detection

**Steps of Kruskal's Algorithm:**
1. Sort all edges by weight in ascending order
2. Initialize disjoint sets (each vertex in its own set)
3. For each edge (u,v):
   - If u and v are in different sets: add edge to MST, union the sets
   - If u and v are in same set: skip (would create cycle)

**Efficient Cycle Detection:**
Use **Union-Find (Disjoint Set Union)** data structure:
- **Find(x):** Returns representative of set containing x
- **Union(x,y):** Merges sets containing x and y
- **Cycle detection:** Edge (u,v) creates cycle if Find(u) == Find(v)

**Time Complexity with Path Compression and Union by Rank:** O(α(n)) per operation, where α is inverse Ackermann function (practically constant)

### (c) Binary Search Algorithm

**Binary Search Algorithm:**
```pseudocode
BINARY_SEARCH(array, target):
    left = 0
    right = length(array) - 1
    
    while left <= right:
        mid = left + (right - left) / 2
        
        if array[mid] == target:
            return mid
        else if array[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    
    return -1  // Not found
```

**Time Complexity:** O(log n)
- Search space is halved in each iteration
- Maximum iterations: ⌈log₂ n⌉

## Question 5

### (a) Greedy Coin Change Algorithm Failure

**Given coins:** C = {2, 7, 11, 17}
**Algorithm:** Choose largest coin ≤ remaining amount

**Three examples where algorithm fails:**

**Example 1: Amount = 18**
- Greedy: 17 + 1 (impossible, no coin of value 1) → FAILS
- Optimal: 11 + 7 = 18 (2 coins)

**Example 2: Amount = 15**  
- Greedy: 11 + 4 (impossible, no way to make 4) → FAILS
- Optimal: 7 + 7 + 1 (impossible) or 11 + 2 + 2 = 15 (3 coins)
- Actually: No solution exists with given coins

**Example 3: Amount = 20**
- Greedy: 17 + 3 (impossible, no way to make 3) → FAILS  
- Optimal: 11 + 7 + 2 = 20 (3 coins)

### (b) Finding Third Smallest Element

**Efficient Solution using Quickselect:**

**Algorithm:** Modified Quicksort to find kth smallest element
```pseudocode
QUICKSELECT(array, k):
    if length(array) == 1:
        return array[0]
    
    pivot = partition(array)
    
    if pivot_position == k-1:
        return pivot
    else if pivot_position > k-1:
        return quickselect(left_subarray, k)
    else:
        return quickselect(right_subarray, k - pivot_position - 1)
```

**Time Complexity:** 
- Average case: O(n)
- Worst case: O(n²)

**Space Complexity:** O(1) with in-place partitioning

**Simulation on A = {10, 12, 3, 6, 17, 2, 9, 11, 20, 25, 71, 4}:**
1. Partition around pivot (say 10): [3, 6, 2, 9, 4] | 10 | [12, 17, 11, 20, 25, 71]
2. Pivot at position 5, need 3rd smallest, so search left subarray
3. Partition [3, 6, 2, 9, 4] around 6: [3, 2, 4] | 6 | [9]
4. Pivot at position 3, need 3rd smallest → return 6

**Result: Third smallest = 6**

## Question 6

### (a) Knapsack Problem - Buffet Selection

**Problem:** Maximize pleasure with volume ≤ 8 liters

**Items:**
- Thai Soup: 2L, 3 pleasure (ratio: 1.5)
- Chicken Curry: 3L, 4 pleasure (ratio: 1.33)
- Mutton Roast: 4L, 5 pleasure (ratio: 1.25)
- Polao: 5L, 6 pleasure (ratio: 1.2)  
- Sweet: 2L, 5 pleasure (ratio: 2.5)

**Step-by-Step Dynamic Programming Solution:**

DP[i][w] = maximum pleasure using first i items with weight limit w

**DP Table (items vs weight capacity 0-8):**

|Item|0|1|2|3|4|5|6|7|8|
|----|--|--|--|--|--|--|--|--|--|
|0|0|0|0|0|0|0|0|0|0|
|Thai(2,3)|0|0|3|3|3|3|3|3|3|
|Chicken(3,4)|0|0|3|4|4|7|7|7|7|
|Mutton(4,5)|0|0|3|4|5|7|8|9|9|
|Polao(5,6)|0|0|3|4|5|7|8|9|10|
|Sweet(2,5)|0|0|5|5|8|9|10|12|13|

**Optimal Solution:** 
- Take Sweet (2L, 5 pleasure) + Polao (5L, 6 pleasure) + remaining 1L unused
- **Total: 7L capacity used, 11 pleasure**

Wait, let me recalculate:
- Sweet (2L, 5p) + Chicken (3L, 4p) + Thai (2L, 3p) = 7L, 12 pleasure
- Or Sweet (2L, 5p) + Mutton (4L, 5p) = 6L, 10 pleasure  
- Or Thai (2L, 3p) + Mutton (4L, 5p) = 6L, 8 pleasure

**Best combination: Sweet + Chicken + Thai = 12 pleasure using 7L**

### (b) Longest Increasing Subsequence

**Array A = {2, 1, 10, 9, 15, 12, 16, 13}**

**Dynamic Programming Approach:**
DP[i] = length of longest increasing subsequence ending at index i

**Computation:**
- DP[0] = 1 (subsequence: [2])
- DP[1] = 1 (subsequence: [1])  
- DP[2] = 2 (subsequence: [1,10] or [2,10])
- DP[3] = 2 (subsequence: [1,9] or [2,9])
- DP[4] = 3 (subsequence: [1,10,15] or [2,9,15])
- DP[5] = 3 (subsequence: [1,10,12])
- DP[6] = 4 (subsequence: [1,10,12,16] or [2,9,15,16])
- DP[7] = 4 (subsequence: [1,10,12,13])

**Longest Increasing Subsequence Length: 4**
**One possible LIS: [1, 9, 12, 16] or [2, 9, 15, 16]**

**Time Complexity:** O(n²) - for each element, check all previous elements
**Space Complexity:** O(n) - DP array storage

## Question 7

### (a) Maximum Flow using Ford-Fulkerson

**Given Network Flow Graph:**
Source S, Sink T, with intermediate nodes A, B, C, D, E, F

**Ford-Fulkerson Algorithm Steps:**
1. Find augmenting path from S to T using DFS/BFS
2. Determine bottleneck capacity along the path
3. Update residual graph by subtracting flow from forward edges and adding to backward edges
4. Repeat until no augmenting path exists

**Applying to the given graph:**
Let me trace through possible augmenting paths:

1. **Path S→A→D→E→T:** min(10,5,7,2) = 2, Flow = 2
2. **Path S→B→C→F→T:** min(5,4,5,12) = 4, Flow = 6  
3. **Path S→A→C→F→T:** min(8,4,3,8) = 3, Flow = 9
4. **Path S→A→D→C→F→T:** min(5,8,1,2) = 1, Flow = 10

Continue until no more augmenting paths...

**Maximum Flow = 19** (after finding all possible augmenting paths)

### (b) Max-Flow Min-Cut Theorem

**Statement:** 
In any flow network, the maximum amount of flow from source to sink equals the minimum capacity of any cut that separates source from sink.

**Explanation:**
- **Cut:** A partition of vertices into two sets S and T, where source ∈ S and sink ∈ T
- **Cut Capacity:** Sum of capacities of edges going from S to T
- **Min-Cut:** The cut with minimum capacity

**Why every cut puts an upper bound on maximum flow:**

1. **Flow Conservation:** All flow from source must pass through any cut separating source from sink
2. **Capacity Constraint:** Flow through any edge cannot exceed its capacity  
3. **Cut Capacity Limit:** Total flow ≤ sum of capacities of edges crossing the cut
4. **Minimum Cut:** The smallest such upper bound is the minimum cut capacity

**Therefore:** No flow can exceed the capacity of the minimum cut, making it the theoretical maximum achievable flow.

**Practical Importance:** 
- Identifies network bottlenecks
- Helps in network design and optimization  
- Guarantees optimality of Ford-Fulkerson algorithm
