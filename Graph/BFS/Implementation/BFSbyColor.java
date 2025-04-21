/*
1. Initialization: (color, prev, d):

color[u] = WHITE (unvisited).

prev[u] = -1 (no predecessor).

d[u] = ∞ (initially unreachable).

2.  Start Node (s):

color[s] = GRAY (processing).

d[s] = 0 (distance to itself is 0).

prev[s] = -1 (no parent).

3.  BFS Loop:

Dequeue a node u and process its neighbors.

If neighbor v is WHITE (unvisited), update its color, d, and prev, then enqueue it.

Mark u as BLACK (fully processed).


Key Notes:
Time Complexity: O(V + E) (visits every vertex and edge once).

Space Complexity: O(V) (for color, prev, d, and queue).

Works for both directed and undirected graphs.

Finds the shortest path in an unweighted graph.

  */


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSbyColor {

    static class Edge {
        int src;
        int dest;

        public Edge(int src, int dest) {
            this.src = src;
            this.dest = dest;
        }
    }

    static void createGraph(ArrayList<Edge> graph[]) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        // Example graph (same as before)
        graph[0].add(new Edge(0, 1));
        graph[0].add(new Edge(0, 2));

        graph[1].add(new Edge(1, 0));
        graph[1].add(new Edge(1, 3));

        graph[2].add(new Edge(2, 0));
        graph[2].add(new Edge(2, 4));

        graph[3].add(new Edge(3, 1));
        graph[3].add(new Edge(3, 4));
        graph[3].add(new Edge(3, 5));

        graph[4].add(new Edge(4, 2));
        graph[4].add(new Edge(4, 3));
        graph[4].add(new Edge(4, 5));

        graph[5].add(new Edge(5, 3));
        graph[5].add(new Edge(5, 4));
        graph[5].add(new Edge(5, 6));

        graph[6].add(new Edge(6, 5));
    }

    public static void bfs(ArrayList<Edge> graph[], int start) {
        int V = graph.length;
        final int WHITE = 0, GRAY = 1, BLACK = 2; // Color states

        int[] color = new int[V];  // WHITE=unvisited, GRAY=processing, BLACK=visited
        int[] prev = new int[V];   // Predecessor (parent) in BFS tree
        int[] d = new int[V];      // Distance from start node

        // Initialize
        for (int u = 0; u < V; u++) {
            if (u == start) continue;
            color[u] = WHITE;
            prev[u] = -1;  // NIL
            d[u] = Integer.MAX_VALUE;  // Infinity
        }

        // Start node
        color[start] = GRAY;
        d[start] = 0;
        prev[start] = -1;

        Queue<Integer> q = new LinkedList<>();
        q.add(start);

        while (!q.isEmpty()) {
            int u = q.poll();

            for (Edge e : graph[u]) {
                int v = e.dest;

                if (color[v] == WHITE) {
                    color[v] = GRAY;
                    d[v] = d[u] + 1;
                    prev[v] = u;
                    q.add(v);
                }
            }

            color[u] = BLACK;
        }

        // Print results
        System.out.println("Vertex | Distance | Predecessor");
        for (int i = 0; i < V; i++) {
            System.out.printf("%6d | %8d | %11d\n", i, d[i], prev[i]);
        }
    }

    public static void main(String[] args) {
        int V = 7;
        ArrayList<Edge> graph[] = new ArrayList[V];
        createGraph(graph);

        System.out.println("BFS Traversal (starting from node 0):");
        bfs(graph, 0);
    }
}
