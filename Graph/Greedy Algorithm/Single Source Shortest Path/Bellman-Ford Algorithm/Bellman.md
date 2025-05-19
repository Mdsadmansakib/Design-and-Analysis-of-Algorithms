# Bellman-Ford Algorithm

## 📘 Introduction

The **Bellman-Ford Algorithm** is used to find the shortest path from a single source vertex to all other vertices in a weighted graph. It works with graphs that contain **negative weight edges** and can detect **negative weight cycles**.

---

## ⏱️ Time and Space Complexity

- **Time Complexity**: `O(V * E)`
- **Space Complexity**: `O(V)`

---

## 🧠 Pseudocode

```text
function BellmanFord(edgeList, V, source):
    // Step 1: Initialize distances
    for each vertex v in V:
        distance[v] ← ∞
    distance[source] ← 0

    // Step 2: Relax all edges (V - 1) times
    for i from 1 to V - 1:
        for each edge (u, v, weight) in edgeList:
            if distance[u] + weight < distance[v]:
                distance[v] ← distance[u] + weight

    // Step 3: Check for negative-weight cycles
    for each edge (u, v, weight) in edgeList:
        if distance[u] + weight < distance[v]:
            report "Negative-weight cycle detected"
            return

    // Step 4: Output the shortest distances
    for each vertex v in V:
        print "Distance from source to v is", distance[v]



