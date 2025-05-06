# Design and Analysis of Algorithms Solutions
*University of Dhaka - CSE-2202: Design and Analysis of Algorithms – I*

## Question 1 [4+2]

**There is a hypothesis that the algorithm for finding strongly connected components would be simpler if it used the original (instead of the transpose) graph in the second depth-first search and scanned the vertices in the order of increasing finishing times.**

**Does this simpler algorithm always produce the correct results? Defend your answer with a sample graph of three nodes (A, B, C) and three directed edges. You need to show all the steps.**

**How can the number of strongly connected components of a graph change if a new edge is added?**

### Solution:

No, this simpler algorithm does not always produce correct results. Let me demonstrate with a sample graph of three nodes.

Consider a directed graph with:
- Vertices: A, B, C
- Edges: (A→B), (B→C), (C→A)

This graph forms a single strongly connected component (SCC) because from any vertex, we can reach any other vertex.

**Steps of standard Kosaraju's algorithm:**
1. Perform DFS on original graph to get finishing times
   - Visit order could be A→B→C
   - Finishing times: A(3), B(2), C(1)
   - Stack after first DFS: C, B, A (top)

2. Perform DFS on transpose graph in decreasing order of finishing times
   - Transpose graph edges: (B→A), (C→B), (A→C)
   - Visit A first (from stack), explore A→C→B
   - Result: One SCC containing {A, C, B}

**Steps of "simpler" algorithm:**
1. Perform DFS on original graph to get finishing times
   - Same as before, stack: C, B, A (top)

2. Perform DFS on original graph (not transpose) in decreasing order of finishing times
   - Visit A first (from stack), explore A→B→C
   - Result: A, B, C would be incorrectly identified as one SCC

This is incorrect because in the original graph, if we start from A, we can reach all nodes. However, this doesn't guarantee that all nodes form a single SCC. The transpose graph is essential because it verifies if all nodes can also reach A, which completes the definition of an SCC.

**How can the number of SCCs change if a new edge is added?**

When a new edge is added to a directed graph, the number of SCCs can:

1. **Decrease**: If the new edge connects two separate SCCs, they may merge into one. For example, if we have two SCCs {A,B} and {C,D}, adding an edge from B→C and another from D→A would merge them into one SCC {A,B,C,D}.

2. **Stay the same**: If the new edge connects vertices within the same SCC or adds a connection that doesn't create new strong connectivity between separate SCCs.

The number of SCCs cannot increase by adding an edge, as adding connections can only enhance connectivity, not reduce it.

## Question 2 [3+3+1]

**Use Floyd Warshall Algorithm to find the shortest path distance between every pair of vertices. Initial three steps have been provided for your convenience.**

[Graph with 6 vertices shown in the image]

**Given that the Floyd-Warshall algorithm provides a solution for the all-pairs shortest path problem with a time complexity of O(V³), it is reported that Johnson's algorithm, which leverages two single-source shortest path algorithms, offers a computationally more efficient approach for certain graph structures. Given this context, determine the computational complexity of Johnson's algorithm when applied to a dense graph. You need to specifically discuss the complexity of each step and use this algorithm to generate the final worst case complexity.**

**In any case, mention the necessity of correct reweighting technique.**

### Solution:

**Completing the Floyd-Warshall Algorithm:**

The image shows the first three iterations of the Floyd-Warshall algorithm. Let's continue with the remaining iterations to compute the all-pairs shortest paths.

The Floyd-Warshall algorithm works by considering each vertex as an intermediate vertex in paths and updates the shortest paths accordingly. The algorithm uses a dynamic programming approach where D^k[i,j] represents the shortest path from vertex i to vertex j using only vertices {1,2,...,k} as intermediate vertices.

Based on the matrices shown in the image, we have completed iterations for k=1, k=2, and k=3. We need to continue for k=4, k=5, and k=6.

For a complete solution, all shortest path distances between every pair of vertices would be found in the final matrix after k=6.

**Johnson's Algorithm Complexity Analysis:**

Johnson's algorithm finds the shortest paths between all pairs of vertices in a weighted graph, which may contain negative edge weights but no negative cycles.

Johnson's algorithm consists of the following steps:

1. **Adding a new vertex and zero-weight edges:**
   - Create a new vertex q and add edges of weight 0 from q to all other vertices
   - Complexity: O(V)

2. **Reweighting using Bellman-Ford:**
   - Run Bellman-Ford algorithm from the new vertex q to compute h(v) for each vertex v
   - This detects any negative cycles and computes potential functions for reweighting
   - Complexity: O(VE)

3. **Edge reweighting:**
   - Reweight each edge (u,v) with weight w(u,v) to w'(u,v) = w(u,v) + h(u) - h(v)
   - This transformation ensures all edge weights are non-negative while preserving shortest paths
   - Complexity: O(E)

4. **Running Dijkstra's algorithm from each vertex:**
   - Run Dijkstra's algorithm from each vertex on the reweighted graph
   - Complexity: O(V × (E log V)) = O(VE log V) using a binary heap
   - With Fibonacci heap: O(V(V log V + E)) = O(V² log V + VE)

5. **Converting distances back to original weights:**
   - Convert shortest path distances to original graph weights
   - Complexity: O(V²)

For a dense graph where E ≈ V², the total complexity:
- Using binary heap: O(VE log V) = O(V³ log V)
- Using Fibonacci heap: O(V² log V + VE) = O(V² log V + V³) = O(V³)

For dense graphs, Johnson's algorithm with Fibonacci heap has the same asymptotic complexity as Floyd-Warshall (O(V³)). However, for sparse graphs where E is much less than V², Johnson's algorithm performs better with O(V² log V + VE).

**Necessity of Correct Reweighting Technique:**

The reweighting technique is crucial in Johnson's algorithm for several reasons:

1. **Handling negative edges:** Dijkstra's algorithm cannot handle negative edge weights directly. Reweighting transforms all edges to non-negative weights.

2. **Preserving shortest paths:** The reweighting must preserve the shortest paths - if P is the shortest path from u to v in the original graph, it must remain the shortest path in the reweighted graph.

3. **Avoiding distortion:** Incorrect reweighting might distort the relative distances and lead to incorrect shortest paths.

4. **No negative cycles:** The reweighting technique helps identify negative cycles, which make the shortest path problem undefined.

The reweighting formula w'(u,v) = w(u,v) + h(u) - h(v) ensures that if a path is shortest in the original graph, it remains shortest in the reweighted graph, thus maintaining the correctness of the algorithm.

## Question 3 [3+3]

**In the realm of graph algorithms, consider the challenges faced by two travel enthusiasts, Emily and Harry. Emily aims to cross every bridge in a city only once and return to her starting point, emulating an Eulerian circuit. Harry, conversely, aspires to visit every city in a country exactly once, representing a Hamiltonian path.**

**Now, relate the problems faced by Emily and Harry to their corresponding graph algorithms, providing the formal algorithmic complexities for each.**

**Which traveller faces a more computationally complex task, and why?**

### Solution:

**Emily's Problem - Eulerian Circuit:**

Emily's challenge corresponds to finding an Eulerian circuit in a graph, where:
- Vertices represent intersections or junctions
- Edges represent bridges
- The goal is to traverse each edge exactly once and return to the starting vertex

**Formal Algorithm and Complexity:**

1. **Algorithm:** Hierholzer's algorithm or Fleury's algorithm can be used to find an Eulerian circuit.

2. **Conditions for existence:**
   - All vertices must have even degree (i.e., an even number of edges connected to each vertex)
   - The graph must be connected (ignoring isolated vertices)

3. **Complexity:** 
   - Hierholzer's algorithm: O(E), where E is the number of edges (bridges)
   - The algorithm is efficient because it simply follows available edges and builds the circuit

4. **Implementation:**
   - Start at any vertex
   - Follow unused edges until returning to the start
   - If the circuit doesn't include all edges, find a vertex in the circuit with unused edges and create a new circuit
   - Merge the circuits until all edges are used

**Harry's Problem - Hamiltonian Path:**

Harry's challenge corresponds to finding a Hamiltonian path in a graph, where:
- Vertices represent cities
- Edges represent direct routes between cities
- The goal is to visit each vertex exactly once

**Formal Algorithm and Complexity:**

1. **Algorithm:** Unlike the Eulerian circuit problem, there is no efficient algorithm for finding a Hamiltonian path in general graphs.

2. **Conditions for existence:**
   - No simple condition like for Eulerian circuits
   - Determining if a Hamiltonian path exists is NP-complete

3. **Complexity:**
   - The problem is NP-complete
   - Brute force approach: O(n!), where n is the number of vertices (cities)
   - Dynamic programming approach: O(n² · 2^n)

4. **Implementation:**
   - Often solved using backtracking, branch and bound, or approximation algorithms
   - No polynomial-time algorithm is known for the general case

**Which Traveler Faces a More Computationally Complex Task, and Why?**

Harry faces a significantly more computationally complex task for the following reasons:

1. **Complexity Class Difference:**
   - Emily's Eulerian circuit problem can be solved in polynomial time (O(E))
   - Harry's Hamiltonian path problem is NP-complete with exponential worst-case complexity

2. **Algorithmic Efficiency:**
   - There exist efficient algorithms to determine if an Eulerian circuit exists and to find one if it does
   - No known efficient algorithm exists for the general Hamiltonian path problem

3. **Scaling:**
   - As the number of cities/bridges increases, Emily's problem scales linearly
   - Harry's problem becomes exponentially harder with each additional city

4. **Practical Implications:**
   - For large graphs, Emily's problem remains tractable
   - Harry's problem quickly becomes computationally infeasible for large graphs

The stark difference in complexity arises from the fundamental nature of the problems: traversing edges (Emily) versus visiting vertices (Harry). Edge traversal problems often have more structure that can be exploited by algorithms, while vertex traversal introduces combinatorial explosion.

## Question 4

**Consider the above undirected graph, which of the following is/are not the sequence of edges added to the minimum spanning tree using Kruskal's algorithm? Provide justification for your selection(s) and rejection(s). Finally, use any of the valid sequences to produce a minimum spanning tree.**

*Edge weights from the graph:*
- (A,B): 5
- (A,C): 3
- (B,D): 4
- (B,E): 2
- (C,D): 5
- (C,F): 6
- (D,F): 6
- (E,F): 5
- (E,G): 3
- (F,G): 4
- (G,B): 4

**Options:**
1. (B,E), (E,F), (A,C), (B,C), (F,G), (C,D)
2. (B,E), (E,F), (A,C), (F,G), (B,C), (C,D)
3. (B,E), (A,C), (E,F), (B,C), (F,G), (C,D)
4. (B,E), (E,F), (B,C), (A,C), (F,G), (C,D)

### Solution:

Kruskal's algorithm works by sorting all edges in non-decreasing order of weight and then adding edges to the MST one by one if they don't form a cycle with the edges already included.

First, let's sort all edges by weight:
1. (B,E): 2
2. (A,C): 3
3. (E,G): 3
4. (B,D): 4
5. (G,B): 4
6. (F,G): 4
7. (A,B): 5
8. (C,D): 5
9. (E,F): 5
10. (C,F): 6
11. (D,F): 6

Now let's analyze each option to determine if it follows Kruskal's algorithm:

**Option 1:** (B,E), (E,F), (A,C), (B,C), (F,G), (C,D)
- First edge (B,E) with weight 2: Correct
- Second edge (E,F) with weight 5: Incorrect - Kruskal would choose (A,C) or (E,G) with weight 3 next
- This sequence is not valid

**Option 2:** (B,E), (E,F), (A,C), (F,G), (B,C), (C,D)
- First edge (B,E) with weight 2: Correct
- Second edge (E,F) with weight 5: Incorrect - Kruskal would choose (A,C) or (E,G) with weight 3 next
- This sequence is not valid

**Option 3:** (B,E), (A,C), (E,F), (B,C), (F,G), (C,D)
- First edge (B,E) with weight 2: Correct
- Second edge (A,C) with weight 3: Correct
- Third edge (E,F) with weight 5: Incorrect - Kruskal would choose (E,G) with weight 3 next
- This sequence is not valid

**Option 4:** (B,E), (E,F), (B,C), (A,C), (F,G), (C,D)
- First edge (B,E) with weight 2: Correct
- Second edge (E,F) with weight 5: Incorrect - Kruskal would choose (A,C) or (E,G) with weight 3 next
- This sequence is not valid

**Justification for rejections:**
All sequences are invalid because they do not follow Kruskal's algorithm, which always selects the next lowest-weight edge that doesn't create a cycle. In all options, (E,F) with weight 5 is chosen too early, when edges with weights 3 and 4 should be considered first.

**Valid sequence for MST using Kruskal's algorithm:**
1. (B,E): 2
2. (A,C): 3
3. (E,G): 3
4. Choose one of these three (all weight 4): (B,D), (G,B), or (F,G)
   - Let's choose (B,D): 4
5. Choose one of these two (both weight 4): (G,B) or (F,G)
   - If we choose (G,B): 4, we would create a cycle B-E-G-B
   - So we choose (F,G): 4 instead

The minimum spanning tree includes edges: (B,E), (A,C), (E,G), (B,D), (F,G)
Total weight: 2 + 3 + 3 + 4 + 4 = 16

Therefore, none of the given options represent a valid sequence for Kruskal's algorithm.

## Question 5 [6]

**In the context of algorithm design, the greedy approach is often recommended for its efficiency and simplicity. However, it is not universally applicable. Describe three algorithmic conditions or scenarios where greedy algorithms might not be the most suitable or might fail to deliver an optimal solution. Each of the three points should include suitable real-world applications.**

### Solution:

# Scenarios Where Greedy Algorithms Fail to Deliver Optimal Solutions

## 1. Problems Requiring Global Optimization

**Condition:** When a problem requires consideration of the entire solution space to find the global optimum, rather than making locally optimal choices at each step.

**Why Greedy Fails:** Greedy algorithms make decisions based on the current best choice without considering future consequences. They can get trapped in local optima, missing the global optimal solution.

**Real-World Applications:**

- **Traveling Salesman Problem (TSP):** A salesperson needs to visit multiple cities once and return to the origin with minimum total distance. A greedy approach (always choosing the nearest unvisited city) often produces suboptimal routes because the shortest next step might lead to much longer future steps.

- **Investment Portfolio Optimization:** When allocating resources across different investment opportunities with varying risk-return profiles and time horizons, a greedy approach (always choosing the highest immediate return) fails to account for diversification benefits and long-term growth potential.

- **Network Flow Problems:** In designing telecommunications networks with capacity constraints, a greedy approach might prioritize high-traffic routes first but leave insufficient capacity for essential connections later, resulting in suboptimal overall network performance.

## 2. Problems with Interdependent Subproblems

**Condition:** When solutions to subproblems are interdependent, and earlier decisions significantly impact the availability and quality of later choices.

**Why Greedy Fails:** Greedy algorithms cannot backtrack or reconsider previous decisions when new information becomes available, leading to suboptimal solutions when earlier choices constrain later possibilities.

**Real-World Applications:**

- **Job Scheduling with Deadlines and Profits:** When scheduling jobs with different deadlines and profits, a greedy approach (scheduling highest-profit jobs first) might cause higher-value jobs to be missed later due to deadline constraints.

- **Resource Allocation in Cloud Computing:** When allocating computational resources to various applications with different requirements and priorities, a greedy approach might assign resources to high-priority tasks first but leave insufficient resources for critical system functions.

- **Course Registration Systems:** If a university allows students to register for courses in order of seniority, a greedy approach (each student selecting their optimal schedule) might result in some students being unable to fulfill graduation requirements because key courses become filled.

## 3. Problems with Complex Constraints or Objectives

**Condition:** When problems have multiple competing objectives or complex constraints that create non-linear relationships between choices and outcomes.

**Why Greedy Fails:** Greedy algorithms typically optimize for a single objective and struggle with balancing multiple competing goals or handling complex constraints that might invalidate seemingly optimal local choices.

**Real-World Applications:**

- **Knapsack Problem:** When selecting items with different values and weights to maximize total value within a weight constraint, a greedy approach (choosing items with highest value-to-weight ratio) often yields suboptimal solutions compared to dynamic programming approaches.

- **Emergency Response Resource Allocation:** During natural disasters, allocating emergency resources (personnel, equipment, supplies) requires balancing immediate life-saving needs against longer-term recovery requirements. A greedy approach focusing only on immediate casualties might leave critical infrastructure unrepaired, causing more harm over time.

- **Medical Treatment Planning:** When developing treatment plans for patients with multiple conditions, a greedy approach (treating the most severe condition first) might create adverse drug interactions or worsen other conditions, leading to suboptimal patient outcomes compared to a holistic treatment strategy.

In all these scenarios, more sophisticated algorithms like dynamic programming, branch and bound, or genetic algorithms typically outperform greedy approaches, despite being more computationally intensive.
