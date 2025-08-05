# ✅ Breadth-First Search (BFS) – Complete Guide

## 1. What is BFS?
Breadth-First Search (BFS) is a fundamental algorithm for traversing or searching a graph or tree. It explores all nodes at the current depth before moving on to the next depth level.

---

## 2. BFS Logic – Step by Step

1. **Start at the Source Node**
    - Pick a starting node in the graph.

2. **Mark as Visited**
    - Mark the start node as visited so you don't visit it again.

3. **Enqueue the Node**
    - Place the node into a queue (FIFO order).

4. **While the Queue is Not Empty:**
    - Dequeue the front node.
    - For each unvisited neighbor:
        - Mark as visited.
        - Enqueue the neighbor.

---

## 3. Graph Example – Adjacency List

Here’s an undirected graph as an adjacency list:

```python
adj = [
    [1, 2],       # 0 connected to 1 and 2
    [0, 2],       # 1 connected to 0 and 2
    [0, 1, 3, 4], # 2 connected to 0, 1, 3, 4
    [2],          # 3 connected to 2
    [2]           # 4 connected to 2
]
```

### Unique Edges (undirected)

- 0 — 1  
- 0 — 2  
- 1 — 2  
- 2 — 3  
- 2 — 4  

### Diagram

```
     0
    / \
   1---2
       |\
       3 4
```

---

## 4. BFS Pseudocode

```plaintext
BFS(Graph, start):
    Create a queue Q
    Mark start as visited and enqueue it into Q

    while Q is not empty:
        node = Q.dequeue()
        for each neighbor of node:
            if neighbor is not visited:
                Mark neighbor as visited
                Q.enqueue(neighbor)
```

---

## 5. BFS Step-by-Step Trace (Start at 0)

| Step | Queue      | Visited              | Node Popped | New Nodes Added |
|------|------------|----------------------|-------------|-----------------|
| 0    | [0]        | [T, F, F, F, F]      | -           | 0 (start)       |
| 1    | [1, 2]     | [T, T, T, F, F]      | 0           | 1, 2            |
| 2    | [2]        | [T, T, T, F, F]      | 1           | —               |
| 3    | [3, 4]     | [T, T, T, T, T]      | 2           | 3, 4            |
| 4    | [4]        | [T, T, T, T, T]      | 3           | —               |
| 5    | []         | [T, T, T, T, T]      | 4           | —               |

**BFS Traversal Order:**  
`0 → 1 → 2 → 3 → 4`

---

## 6. BFS Python Code

```python
from collections import deque

adj = [
    [1, 2],       # 0 connected to 1 and 2
    [0, 2],       # 1 connected to 0 and 2
    [0, 1, 3, 4], # 2 connected to 0, 1, 3, 4
    [2],          # 3 connected to 2
    [2]           # 4 connected to 2
]

def bfs(start):
    n = len(adj)
    visited = [False] * n
    queue = deque()
    order = []

    visited[start] = True
    queue.append(start)

    while queue:
        node = queue.popleft()
        order.append(node)
        for neighbor in adj[node]:
            if not visited[neighbor]:
                visited[neighbor] = True
                queue.append(neighbor)
    return order

print("BFS Traversal Order:", bfs(0))
# Output: BFS Traversal Order: [0, 1, 2, 3, 4]
```

---

## 7. Why BFS Works Well

- **Shortest Path:** Finds the shortest path in an unweighted graph.
- **Level Order:** Visits all nodes at the same level before going deeper.
- **Component Traversal:** Explores all nodes in a connected component.

---

## 8. Visual Analogy

> Think of BFS like ripples in water:  
> Drop a stone (start node), waves spread outward. Each circle is one "level" of nodes being visited.

---

## 9. Summary Table

| Step | Queue   | Visited             | Node Popped | New Nodes Added |
|------|---------|---------------------|-------------|-----------------|
| 0    | [0]     | [T, F, F, F, F]     | -           | 0 (start)       |
| 1    | [1, 2]  | [T, T, T, F, F]     | 0           | 1, 2            |
| 2    | [2]     | [T, T, T, F, F]     | 1           | —               |
| 3    | [3, 4]  | [T, T, T, T, T]     | 2           | 3, 4            |
| 4    | [4]     | [T, T, T, T, T]     | 3           | —               |
| 5    | []      | [T, T, T, T, T]     | 4           | —               |

---

