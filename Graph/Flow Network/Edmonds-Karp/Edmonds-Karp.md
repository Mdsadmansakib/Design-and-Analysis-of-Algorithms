# Edmonds-Karp Algorithm 🧠

The **Edmonds-Karp algorithm** is a specific implementation of the **Ford-Fulkerson method** for computing the maximum flow in a flow network. It distinguishes itself by employing **Breadth-First Search (BFS)** to find the shortest augmenting path in terms of the number of edges.

---

## 🔁 Difference from Ford-Fulkerson

Here's a comparison highlighting the key distinctions between the generic Ford-Fulkerson method and its Edmonds-Karp implementation:

| Feature           | Ford-Fulkerson (Generic) | Edmonds-Karp (Specific) |
| :---------------- | :----------------------- | :---------------------- |
| **Path selection** | Any path (usually DFS)   | **Shortest path (BFS)** |
| **Runtime guarantee** | No (can be infinite)     | **Yes (polynomial)** |
| **Efficiency** | Can be slow              | **More predictable and efficient** |
| **Used in practice?** | Rarely                   | **Yes, often in teaching/practice** |

The use of BFS in Edmonds-Karp guarantees termination for integral capacities and results in a polynomial time complexity.

**Time Complexity**: $O(VE^2)$

---

## 📦 Algorithm Steps (Edmonds-Karp)

The algorithm operates on a flow network $G(V,E)$ with a source node $s$ and a sink node $t$.

### 🧩 Data Structures:

* `capacity[u][v]`: The maximum capacity of the edge from node $u$ to node $v$.
* `flow[u][v]`: The current amount of flow pushed from node $u$ to node $v$.
* `residual[u][v]`: The remaining capacity on the edge from $u$ to $v$, calculated as `capacity[u][v] - flow[u][v]`.

### 🔁 Core Loop:

1.  **While** an augmenting path from $s$ to $t$ exists (found using **BFS**):
    * Use BFS to find the shortest augmenting path from $s$ to $t$.
    * Find the **bottleneck capacity** (minimum residual capacity along the path).
    * **Augment flow** along the path by the bottleneck value.
    * **Update residual capacities** for forward edges and **add reverse edges** with the augmented flow to allow for "undoing" flow if a better path is found later.

---

## ✅ Pseudocode:

```python
function edmonds_karp(capacity, s, t):
    initialize flow[u][v] = 0 for all edges
    max_flow = 0

    while True:
        parent = BFS(capacity, flow, s, t)
        if no path found:
            break
        
        # Find bottleneck capacity
        path_flow = INF # A very large number
        v = t
        while v ≠ s:
            u = parent[v]
            path_flow = min(path_flow, capacity[u][v] - flow[u][v])
            v = u

        # Augment flow
        v = t
        while v ≠ s:
            u = parent[v]
            flow[u][v] += path_flow
            flow[v][u] -= path_flow  # reverse edge (represents flow coming back)
            v = u

        max_flow += path_flow

    return max_flow
```
🔎 BFS Function (to find shortest path):
Python
```
function BFS(capacity, flow, s, t):
    visited = set()
    queue = [s]
    parent = dict() # Stores the path found by BFS
    visited.add(s)

    while queue not empty:
        u = queue.pop(0) # Dequeue
        for v in neighbors(u):
            # Check if v is not visited and there's residual capacity to push more flow
            if v not in visited and capacity[u][v] - flow[u][v] > 0:
                parent[v] = u # Mark u as parent of v
                visited.add(v)
                queue.append(v) # Enqueue v
                if v == t:
                    return parent # Path to sink found
    return None # No path to sink
```
🔢 Example Step-by-Step Simulation
Let's illustrate the Edmonds-Karp algorithm with a concrete example.

🧩 Example Flow Network
Consider the following directed graph with given capacities:

Edge Capacities:

From	To	Capacity
s	A	10
s	B	10
A	B	15
A	t	5
B	t	10

Export to Sheets
We will track the residual graph, augmenting paths, bottleneck capacities, and updated flow values.

✅ Step-by-Step Simulation of Edmonds-Karp
🔁 Iteration 1: BFS finds path s
rightarrowA
rightarrowt
Residual capacities:

s
rightarrowA=10

A
rightarrowt=5

➡️ Augmenting path: s
rightarrowA
rightarrowt

➡️ Bottleneck: 
min(10,5)=5

Update flows:

flow[s][A] += 5 
rightarrow now 5

flow[A][t] += 5 
rightarrow now 5

Residual updates:

s
rightarrowA becomes 5

A
rightarrowt becomes 0

Reverse edges A
rightarrows=5 and t
rightarrowA=5 are added to the residual graph.

Total flow so far: 5

🔁 Iteration 2: BFS finds path s
rightarrowB
rightarrowt
Residual capacities:

s
rightarrowB=10

B
rightarrowt=10

➡️ Augmenting path: s
rightarrowB
rightarrowt

➡️ Bottleneck: 
min(10,10)=10

Update flows:

flow[s][B] += 10 
rightarrow now 10

flow[B][t] += 10 
rightarrow now 10

Residual updates:

s
rightarrowB becomes 0

B
rightarrowt becomes 0

Reverse edges B
rightarrows=10 and t
rightarrowB=10 are added to the residual graph.

Total flow so far: 5+10=15

🔁 Iteration 3: BFS finds path s
rightarrowA
rightarrowB
rightarrowt
At this point, direct paths to t from A and B are blocked in the residual graph (A
rightarrowt and B
rightarrowt have 0 residual capacity). However, the presence of reverse edges allows for more complex paths.

BFS attempts to find a path. It can find:

s
rightarrowA (residual capacity 5)

A
rightarrowB (residual capacity 15)

From B, it cannot go directly to t as B
rightarrowt has 0 residual capacity.

🔒 No augmenting path to t is possible from s in this residual graph that includes B
rightarrowt.

Therefore, in this specific network, after Iteration 2, no more augmenting paths exist from s to t.

✅ Termination
No more augmenting paths exist from s to t in the residual graph. The algorithm terminates.

🔚 Final Flow Values
Edge	Flow
s
rightarrowA	5
s
rightarrowB	10
A
rightarrowt	5
B
rightarrowt	10
A
rightarrowB	0

Export to Sheets
💧 Maximum Flow from s to t: 5+10=15

🧠 Summary
Edmonds-Karp uses BFS to ensure that the shortest augmenting paths are found.

This strategy helps to avoid inefficient long detours and guarantees the algorithm's polynomial runtime for integral capacities.

The residual graph is dynamically updated after every flow augmentation, including the addition of reverse edges to allow for flow cancellations, which is crucial for finding the true maximum flow.

The algorithm stops when no more paths from the source to the sink can be found in the residual graph, at which point the maximum flow is achieved.
