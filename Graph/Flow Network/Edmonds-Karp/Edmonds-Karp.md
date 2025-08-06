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
# 🔢 Example Step-by-Step Simulation
Let's illustrate the Edmonds-Karp algorithm with a concrete example.

## 🧩 Example Flow Network
Consider the following directed graph with given capacities:

### Edge Capacities:
| From | To | Capacity |
|------|----|----------|
| s    | A  | 10       |
| s    | B  | 10       |
| A    | B  | 15       |
| A    | t  | 5        |
| B    | t  | 10       |


We will track the residual graph, augmenting paths, bottleneck capacities, and updated flow values.

## ✅ Step-by-Step Simulation of Edmonds-Karp

### 🔁 Iteration 1: 
**BFS finds path** `s → A → t`  
**Residual capacities:**
- `s → A = 10`
- `A → t = 5`

➡️ **Augmenting path:** `s → A → t`  
➡️ **Bottleneck:** `min(10,5) = 5`

**Update flows:**
- `flow[s][A] += 5` → now 5
- `flow[A][t] += 5` → now 5

**Residual updates:**
- `s → A` becomes 5
- `A → t` becomes 0
- Reverse edges `A → s = 5` and `t → A = 5` are added

**Total flow so far:** 5

---

### 🔁 Iteration 2: 
**BFS finds path** `s → B → t`  
**Residual capacities:**
- `s → B = 10`
- `B → t = 10`

➡️ **Augmenting path:** `s → B → t`  
➡️ **Bottleneck:** `min(10,10) = 10`

**Update flows:**
- `flow[s][B] += 10` → now 10
- `flow[B][t] += 10` → now 10

**Residual updates:**
- `s → B` becomes 0
- `B → t` becomes 0
- Reverse edges `B → s = 10` and `t → B = 10` are added

**Total flow so far:** 5 + 10 = 15

---

### 🔁 Iteration 3: 
**BFS finds path** `s → A → B → t`  
At this point:
- Direct paths to `t` from `A` and `B` are blocked (`A → t` and `B → t` have 0 residual capacity)
- Reverse edges allow more complex paths

**BFS attempts:**
1. `s → A` (residual capacity 5)
2. `A → B` (residual capacity 15)
3. From `B`, cannot go to `t` directly (residual capacity 0)

🔒 **No augmenting path** to `t` is possible in this residual graph

---

## ✅ Termination
No more augmenting paths exist from `s` to `t` in the residual graph. The algorithm terminates.

## 🔚 Final Flow Values
| Edge      | Flow |
|-----------|------|
| s → A     | 5    |
| s → B     | 10   |
| A → t     | 5    |
| B → t     | 10   |
| A → B     | 0    |



💧 **Maximum Flow from s to t:** 5 + 10 = 15

---

## 🧠 Summary
- Edmonds-Karp uses **BFS** to find shortest augmenting paths
- Guarantees **polynomial runtime** for integral capacities
- Residual graph is dynamically updated with:
  - Forward edge capacity reductions
  - Reverse edges for flow cancellation
- Algorithm stops when no more `s-t` paths exist in residual graph
- Reverse edges enable "flow undo" operations critical for correctness
