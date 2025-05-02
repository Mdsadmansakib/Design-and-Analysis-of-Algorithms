/*

// Main algorithm to find Bridges in the graph
Bridges: Pseudocode

// Step 1: Initialize graph data structures
Data: adj[V] (Adjacency list representation of the graph)
      visited[V] (To keep track of visited vertices)
      disc[V] (Discovery time of each vertex)
      low[V] (Lowest discovery time reachable from each vertex)
      parent[V] (Parent vertex in DFS tree)

// Step 2: Graph construction
Graph Constructor(V)  
{
    // Create adjacency list for graph with V vertices
    for each vertex i from 0 to V-1
    {
        Initialize empty list for each vertex i (Adj[i])
    }
}

// Step 3: Add edges between vertices u and v
addEdge(u, v)  
{
    // Add edge u -> v
    adj[u].add(v)
    
    // Add edge v -> u (since the graph is undirected)
    adj[v].add(u)
}

// Step 4: Start finding bridges
findBridges()  
{
    // Initialize visited, discovery time, low values, and parent arrays
    for each vertex u in V
    {
        visited[u] = false      // Mark all vertices as unvisited
        parent[u] = -1          // Initially no parent
        disc[u] = -1            // Discovery time is unknown
        low[u] = -1             // Low-link value is unknown
    }

    // Step 5: Perform DFS starting from all unvisited vertices
    Print "Bridges:"
    for each vertex u from 0 to V-1
    {
        if (visited[u] == false)  // If the vertex is unvisited
        {
            // Start DFS traversal to find bridges from vertex u
            dfsBridge(u, visited, disc, low, parent)
        }
    }
}

// Step 6: DFS utility function to find bridges
dfsBridge(u, visited, disc, low, parent)  
{
    // Mark current vertex u as visited and record its discovery time
    visited[u] = true
    disc[u] = low[u] = ++time  // Discovery time is incremented

    // Step 7: Explore all adjacent vertices v of u
    for each vertex v in adj[u]
    {
        if (visited[v] == false)  // If vertex v is not visited
        {
            parent[v] = u          // Set the parent of v as u
            dfsBridge(v, visited, disc, low, parent)  // Recur for vertex v

            // Update the low-link value of u based on the low-link value of v
            low[u] = min(low[u], low[v])

            // Check if (u, v) is a bridge
            if (low[v] > disc[u])  
            {
                Print u + " — " + v    // Output the bridge (u, v)
            }
        }
        else if (v != parent[u])  // Back edge case: v is already visited and is not the parent of u
        {
            low[u] = min(low[u], disc[v])  // Update low[u] considering back edge
        }
    }
}


*/



import java.util.*;

public class Bridges {
    static class Graph {
        int V, time = 0;
        List<List<Integer>> adj;

        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++)
                adj.add(new ArrayList<>());
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        void findBridges() {
            boolean[] visited = new boolean[V];
            int[] disc = new int[V];
            int[] low = new int[V];
            int[] parent = new int[V];

            Arrays.fill(parent, -1);

            System.out.println("Bridges:");
            for (int u = 0; u < V; u++) {
                if (!visited[u]) {
                    dfsBridge(u, visited, disc, low, parent);
                }
            }
        }

        void dfsBridge(int u, boolean[] visited, int[] disc, int[] low, int[] parent) {
            visited[u] = true;
            disc[u] = low[u] = ++time;

            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    parent[v] = u;
                    dfsBridge(v, visited, disc, low, parent);
                    low[u] = Math.min(low[u], low[v]);

                    if (low[v] > disc[u]) {
                        System.out.println(u + " — " + v);
                    }
                } else if (v != parent[u]) {
                    low[u] = Math.min(low[u], disc[v]);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        g.findBridges();
    }
}
