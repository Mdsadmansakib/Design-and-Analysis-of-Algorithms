
# Articulation Points and Bridges in Graphs

## 📌 Introduction

In graph theory, **Articulation Points** (cut vertices) and **Bridges** (cut edges) are critical to understanding a graph's structure and connectivity.

- **Articulation Point**: A node whose removal increases the number of connected components.
- **Bridge**: An edge whose removal increases the number of connected components.

These help in analyzing network vulnerability, fault tolerance, and connectivity.

---

## 🧠 Key Concepts

We use **DFS (Depth First Search)** and record:

- `tin[u]`: Time of first visit to node `u`
- `low[u]`: Lowest discovery time reachable from `u`

### Articulation Point Conditions

If during DFS from node `u` to `v`:

- `low[v] >= tin[u]` and `u` is not the root → `u` is an articulation point.
- `u` is root and has two or more DFS children → `u` is an articulation point.

### Bridge Condition

If `low[v] > tin[u]`, then edge `(u, v)` is a **bridge**.

---

## 🔍 Articulation Point Algorithm (Step-by-Step)

### 🧠 Algorithm Steps:

1. **Build DFS Spanning Tree** starting from any unvisited node.
2. **Assign Discovery Time `d(v)`** for each vertex during DFS.
3. **Calculate Lowest Discovery Time `L(v)`** that can be reached from subtree rooted at `v` using back edges.
4. **Articulation Condition**:
   - If `u` is parent of `v` in DFS tree and `L(v) ≥ d(u)` → then `u` is an articulation point.
   - Special Case: If `u` is root and has 2 or more DFS children → `u` is an articulation point.

---

### ✅ Pseudocode / Java-like Logic

```java
void dfs(int u, int parent) {
    visited[u] = true;
    discovery[u] = low[u] = ++time;
    int children = 0;

    for (int v : adj[u]) {
        if (v == parent) continue;

        if (!visited[v]) {
            dfs(v, u);
            low[u] = min(low[u], low[v]);

            // Step 4 condition
            if (parent != -1 && low[v] >= discovery[u])
                articulationPoint[u] = true;

            children++;
        } else {
            // Back edge
            low[u] = min(low[u], discovery[v]);
        }
    }

    // Root node special case
    if (parent == -1 && children > 1)
        articulationPoint[u] = true;
}


## 💻 Java Implementation

### ✅ Articulation Points Code

```java
import java.util.*;

public class ArticulationPoints {
    static int time = 0;

    public static void dfs(int u, int parent, List<List<Integer>> adj, boolean[] visited,
                           int[] tin, int[] low, Set<Integer> articulationPoints) {
        visited[u] = true;
        tin[u] = low[u] = ++time;
        int children = 0;

        for (int v : adj.get(u)) {
            if (v == parent) continue;
            if (visited[v]) {
                low[u] = Math.min(low[u], tin[v]);
            } else {
                dfs(v, u, adj, visited, tin, low, articulationPoints);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] >= tin[u] && parent != -1)
                    articulationPoints.add(u);
                children++;
            }
        }
        if (parent == -1 && children > 1)
            articulationPoints.add(u);
    }

    public static void findArticulationPoints(int n, List<List<Integer>> adj) {
        boolean[] visited = new boolean[n];
        int[] tin = new int[n], low = new int[n];
        Set<Integer> articulationPoints = new HashSet<>();

        for (int i = 0; i < n; i++)
            if (!visited[i])
                dfs(i, -1, adj, visited, tin, low, articulationPoints);

        System.out.println("Articulation Points: " + articulationPoints);
    }
}
```

---

### ✅ Bridges Code

```java
import java.util.*;

public class Bridges {
    static int time = 0;

    public static void dfs(int u, int parent, List<List<Integer>> adj, boolean[] visited,
                           int[] tin, int[] low, List<int[]> bridges) {
        visited[u] = true;
        tin[u] = low[u] = ++time;

        for (int v : adj.get(u)) {
            if (v == parent) continue;
            if (visited[v]) {
                low[u] = Math.min(low[u], tin[v]);
            } else {
                dfs(v, u, adj, visited, tin, low, bridges);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > tin[u])
                    bridges.add(new int[]{u, v});
            }
        }
    }

    public static void findBridges(int n, List<List<Integer>> adj) {
        boolean[] visited = new boolean[n];
        int[] tin = new int[n], low = new int[n];
        List<int[]> bridges = new ArrayList<>();

        for (int i = 0; i < n; i++)
            if (!visited[i])
                dfs(i, -1, adj, visited, tin, low, bridges);

        System.out.println("Bridges:");
        for (int[] bridge : bridges)
            System.out.println(bridge[0] + " - " + bridge[1]);
    }
}
```

---

## 🧩 Exercises

### 🔍 Exercise 1

Graph with 7 nodes (0 to 6) and edges:

```
0-1, 1-2, 2-0, 1-3, 3-4, 3-5, 5-6
```

#### Task:

- Find articulation points.
- Find bridges.

### 🔍 Exercise 2

Graph with 6 nodes (0 to 5) and edges:

```
0-1, 1-2, 2-3, 1-4, 4-5
```

#### Task:

- Find articulation points.
- Find bridges.

---

## ✅ Solution Code (For Both Exercises)

```java
public class MainSolution {
    public static void main(String[] args) {
        int n1 = 7;
        List<List<Integer>> adj1 = new ArrayList<>();
        for (int i = 0; i < n1; i++) adj1.add(new ArrayList<>());
        int[][] edges1 = {{0,1},{1,2},{2,0},{1,3},{3,4},{3,5},{5,6}};
        for (int[] e : edges1) {
            adj1.get(e[0]).add(e[1]);
            adj1.get(e[1]).add(e[0]);
        }

        System.out.println("Graph 1:");
        ArticulationPoints.findArticulationPoints(n1, adj1);
        Bridges.findBridges(n1, adj1);

        int n2 = 6;
        List<List<Integer>> adj2 = new ArrayList<>();
        for (int i = 0; i < n2; i++) adj2.add(new ArrayList<>());
        int[][] edges2 = {{0,1},{1,2},{2,3},{1,4},{4,5}};
        for (int[] e : edges2) {
            adj2.get(e[0]).add(e[1]);
            adj2.get(e[1]).add(e[0]);
        }

        System.out.println("\nGraph 2:");
        ArticulationPoints.findArticulationPoints(n2, adj2);
        Bridges.findBridges(n2, adj2);
    }
}
```

---

## 🧠 Answers Summary

### Exercise 1:
- Articulation Points: `1, 3, 5`
- Bridges: `1-3, 3-4, 3-5, 5-6`

### Exercise 2:
- Articulation Points: `1, 2, 4`
- Bridges: `0-1, 1-2, 2-3, 1-4, 4-5`

---

## 📚 References

- [Tarjan's Algorithm](https://en.wikipedia.org/wiki/Biconnected_component)
- [Cut Points - GFG](https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/)
- [CP-Algorithms](https://cp-algorithms.com/graph/cutpoints.html)

---

Happy Learning! 🚀
