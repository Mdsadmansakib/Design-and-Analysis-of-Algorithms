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



## ⏱️ Java Implementation

import java.util.*;

public class Dijkstra {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void dijkstra(List<List<Edge>> adj, int V, int source) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, source});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currDist = curr[0];
            int u = curr[1];

            if (currDist > dist[u]) continue;

            for (Edge edge : adj.get(u)) {
                int v = edge.to;
                int weight = edge.weight;
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new int[]{dist[v], v});
                }
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

        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        System.out.println("Enter edges in format: source destination weight");
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            adj.get(u).add(new Edge(v, w));
            // For undirected graph, also add: adj.get(v).add(new Edge(u, w));
        }

        System.out.print("Enter source vertex: ");
        int source = sc.nextInt();

        dijkstra(adj, V, source);
    }
}


