# Johnson's Algorithm - Analysis and Overview

## What is Johnson's Algorithm?

Johnson's Algorithm is a graph algorithm used to find the **shortest paths between all pairs of vertices** in a **weighted, directed graph**, which may include **negative edge weights** (but **no negative-weight cycles**). The key advantage of Johnson's algorithm is its ability to handle negative weights efficiently while remaining faster than the Floyd-Warshall algorithm on sparse graphs.

It works by:

1. Adding an auxiliary node to the graph.
2. Running the Bellman-Ford algorithm to detect negative cycles and compute a reweighting function.
3. Reweighting all edges so they are non-negative.
4. Running Dijkstra's algorithm from each vertex using the reweighted graph.
5. Adjusting the final distances to account for the initial reweighting.

Johnson's algorithm combines the strengths of Bellman-Ford (for negative weights) and Dijkstra (for efficiency on non-negative weights).

---

## Time Complexity

Let:

* `V` = number of vertices
* `E` = number of edges

The time complexity is:

* **Bellman-Ford**: $O(VE)$
* **Dijkstra's (using binary heap)**: $O((V + E) \log V)$ per run
* **Total Dijkstra runs**: $V$

**Total Time Complexity**:
$O(VE + V(V + E) \log V) = O(VE + V^2 \log V + VE \log V)$

For **sparse graphs** (where $E \approx V$), Johnson’s is more efficient than Floyd-Warshall’s $O(V^3)$.

---

## Pseudocode

```plaintext
JOHNSON(G, w):
1  compute G', where G'.V = G.V ∪ {s},
   G'.E = G.E ∪ {(s, v) : v ∈ G.V}, and
   w(s, v) = 0 for all v ∈ G.V
2  if BELLMAN-FORD(G', w, s) == FALSE
3      print "The input graph contains a negative-weight cycle"
4      return
5  else
6      for each vertex v ∈ G'.V
7          h(v) = δ(s, v) from Bellman-Ford
8      for each edge (u, v) ∈ G'.E
9          ŵ(u, v) = w(u, v) + h(u) - h(v)
10     let D = (d(u, v)) be a new |V| × |V| matrix
11     for each vertex u ∈ G.V
12         run DIJKSTRA(G, ŵ, u) to compute δ̂(u, v) for all v ∈ G.V
13         for each vertex v ∈ G.V
14             d(u, v) = δ̂(u, v) + h(v) - h(u)
15     return D
```

---

## When to Use Johnson's Algorithm

* Graphs with **negative edge weights** (but no negative cycles).
* **Sparse graphs** (low edge-to-node ratio).
* When an **all-pairs shortest path** solution is required.

---

## Comparison with Other Algorithms

| Algorithm      | Handles Negative Weights | Time Complexity      | Best For                        |
| -------------- | ------------------------ | -------------------- | ------------------------------- |
| Dijkstra       | ❌ No                     | $O((V + E) \log V)$  | Single-source (non-negative)    |
| Bellman-Ford   | ✅ Yes                    | $O(VE)$              | Single-source                   |
| Floyd-Warshall | ✅ Yes                    | $O(V^3)$             | Dense graphs                    |
| **Johnson**    | ✅ Yes                    | $O(VE + V^2 \log V)$ | Sparse all-pairs shortest paths |

---

## Summary

Johnson's algorithm is a powerful and efficient tool for solving the all-pairs shortest path problem in graphs with negative edge weights. Its combination of Bellman-Ford and Dijkstra makes it ideal for sparse graphs, providing better performance than Floyd-Warshall while accommodating a wider class of graphs than Dijkstra alone.
