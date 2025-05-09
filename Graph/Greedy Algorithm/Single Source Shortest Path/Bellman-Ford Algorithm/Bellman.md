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



Java Implementation (User Input Version)
java
Copy
Edit
import java.util.*;

class Edge {
    int src, dest, weight;
    Edge(int s, int d, int w) {
        src = s;
        dest = d;
        weight = w;
    }
}

public class BellmanFord {
    public static void bellmanFord(Edge[] edges, int V, int source) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        for (int i = 1; i < V; i++) {
            for (Edge edge : edges) {
                int u = edge.src, v = edge.dest, w = edge.weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        for (Edge edge : edges) {
            int u = edge.src, v = edge.dest, w = edge.weight;
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                System.out.println("Graph contains a negative-weight cycle");
                return;
            }
        }

        for (int i = 0; i < V; i++) {
            System.out.println("Distance from source to " + i + " is " + dist[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int V = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int E = sc.nextInt();

        Edge[] edges = new Edge[E];
        System.out.println("Enter edges in format: source destination weight");
        for (int i = 0; i < E; i++) {
            int src = sc.nextInt();
            int dest = sc.nextInt();
            int weight = sc.nextInt();
            edges[i] = new Edge(src, dest, weight);
        }

        System.out.print("Enter source vertex: ");
        int source = sc.nextInt();

        bellmanFord(edges, V, source);
    }
}
