# 🧠 Floyd-Warshall Algorithm

## 📘 Introduction

The **Floyd-Warshall algorithm** is a **dynamic programming** algorithm used to find the **shortest paths between all pairs of vertices** in a **weighted graph**.  
It works for **both directed and undirected** graphs and can handle **negative edge weights**, **but not negative weight cycles**.

It computes the shortest distance from every vertex to every other vertex in **O(n³)** time, where `n` is the number of vertices.

---

## 🔢 Algorithm Steps

1. **Initialize a distance matrix** `dist[][]` such that:
   - `dist[i][j] = weight of edge (i → j)` if it exists
   - `dist[i][j] = ∞` if there is no direct edge from `i` to `j`
   - `dist[i][i] = 0` for all `i` (distance to self is zero)

2. **Iterate through all vertices** as intermediate nodes (k = 0 to n-1):
   - For every pair of vertices `(i, j)`:
     - Update `dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])`

3. **After all iterations**, `dist[i][j]` contains the shortest path from vertex `i` to vertex `j`.

---

## 🧾 Pseudocode

```plaintext
function floydWarshall(dist[][], n):
    for k = 0 to n-1:
        for i = 0 to n-1:
            for j = 0 to n-1:
                if dist[i][k] + dist[k][j] < dist[i][j]:
                    dist[i][j] = dist[i][k] + dist[k][j]

    return dist
