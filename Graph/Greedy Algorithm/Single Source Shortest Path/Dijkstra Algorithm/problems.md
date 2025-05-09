Problem-1

Suppose you are given a graph where each edge
represents the path cost and each vertex has also a cost
which represents that, if you select a path using this
node, the cost will be added with the path cost. How can
it be solved using Dijkstra’s algorithm?

Solve:

Input: 
- Graph G with vertices V and edges E
- Edge weights w(u, v)
- Vertex costs c(v)
- Source node s

Initialize:
- dist[v] ← ∞ for all v in V
- dist[s] ← c(s)
- priorityQueue ← empty min-heap
- Insert (dist[s], s) into priorityQueue

While priorityQueue is not empty:
    (currDist, u) ← Extract-Min(priorityQueue)

    For each neighbor v of u:
        newDist ← dist[u] + w(u, v) + c(v)
        If newDist < dist[v]:
            dist[v] ← newDist
            Insert (dist[v], v) into priorityQueue

Output: 
- dist[v] contains the minimum total cost to reach vertex v from source s








Problem-2:

