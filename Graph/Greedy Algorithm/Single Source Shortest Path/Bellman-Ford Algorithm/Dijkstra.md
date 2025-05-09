# Dijkstra's Algorithm

## 📘 Introduction

**Dijkstra's Algorithm** is a well-known shortest path algorithm that computes the minimum distance from a source node to all other nodes in a graph with **non-negative weights**. It uses a **greedy approach** and is typically implemented using a priority queue (min-heap) for efficiency.

---

## ⏱️ Time and Space Complexity

- **Time Complexity**:
  - Using a simple array: `O(V^2)`
  - Using a priority queue (min-heap): `O((V + E) log V)`
- **Space Complexity**: `O(V)`

---

## 🧠 Pseudocode

```text
function Dijkstra(graph, V, source):
    Initialize distance[] array with ∞ for all vertices
    distance[source] ← 0

    Create a priority queue (min-heap)
    Add (0, source) to the queue

    while queue is not empty:
        (dist_u, u) ← extract-min from queue
        for each neighbor v of u:
            if distance[u] + weight(u, v) < distance[v]:
                distance[v] ← distance[u] + weight(u, v)
                add (distance[v], v) to the queue

    Print distance[] for all vertices





