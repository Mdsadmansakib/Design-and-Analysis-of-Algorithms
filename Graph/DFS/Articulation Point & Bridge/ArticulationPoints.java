/*

// Main algorithm to find Articulation Points in the graph
ArticulationPoints: Pseudocode

// Step 1: Initialize graph data structures
Data: adj[V] (Adjacency list representation of the graph)
      visited[V] (To keep track of visited vertices)
      disc[V] (Discovery time of each vertex)
      low[V] (Lowest discovery time reachable from each vertex)
      parent[V] (Parent vertex in DFS tree)
      ap[V] (Articulation points flag)

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

// Step 4: Start finding articulation points
findArticulationPoints()  
{
    // Initialize visited, discovery time, low values, and parent arrays
    for each vertex u in V
    {
        visited[u] = false      // Mark all vertices as unvisited
        parent[u] = -1          // Initially no parent
        disc[u] = -1            // Discovery time is unknown
        low[u] = -1             // Low-link value is unknown
        ap[u] = false           // Initially, no vertex is an articulation point
    }

    // Step 5: Perform DFS starting from all unvisited vertices
    for each vertex u from 0 to V-1
    {
        if (visited[u] == false)  // If the vertex is unvisited
        {
            // Start DFS traversal to find articulation points from vertex u
            dfsAP(u, visited, disc, low, parent, ap)
        }
    }

    // Step 6: Print the articulation points
    Print "Articulation Points:"
    for each vertex u from 0 to V-1
    {
        if (ap[u] == true)       // If the vertex is an articulation point
        {
            Print u              // Output the articulation point
        }
    }
}

// Step 7: DFS utility function to find articulation points
dfsAP(u, visited, disc, low, parent, ap)  
{
    // Mark current vertex u as visited and record its discovery time
    visited[u] = true
    disc[u] = low[u] = ++time  // Discovery time is incremented
    children = 0  // Counter for the number of children of u

    // Step 8: Explore all adjacent vertices v of u
    for each vertex v in adj[u]
    {
        if (visited[v] == false)  // If vertex v is not visited
        {
            parent[v] = u          // Set the parent of v as u
            children++              // Increment the children counter of u
            dfsAP(v, visited, disc, low, parent, ap)  // Recur for vertex v

            // Update the low-link value of u based on the low-link value of v
            low[u] = min(low[u], low[v])

            // Check if u is an articulation point
            if (parent[u] == -1 && children > 1)  
            {
                ap[u] = true    // u is an articulation point if it's root and has >1 child
            }
            else if (parent[u] != -1 && low[v] >= disc[u])  
            {
                ap[u] = true    // u is an articulation point if no back edge connects v to u's ancestor
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

public class ArticulationPoints {
    static class Graph {
        int V, time = 0;
        List<List<Integer>> adj;

        // Constructor to initialize graph with V vertices
        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++)
                adj.add(new ArrayList<>());  // Initialize adjacency list for each vertex
        }

        // Method to add an edge between vertices u and v (undirected graph)
        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);  // Since it's an undirected graph, add both directions
        }

        // Method to find articulation points in the graph
        void findArticulationPoints() {
            boolean[] visited = new boolean[V];  // To track visited vertices
            int[] disc = new int[V];             // Discovery times of visited vertices
            int[] low = new int[V];              // Lowest points reachable from a vertex
            int[] parent = new int[V];           // Parent vertices in DFS tree
            boolean[] ap = new boolean[V];       // To mark articulation points

            Arrays.fill(parent, -1);  // Initialize parent of all vertices to NIL (-1)

            // Run DFS for all vertices to find articulation points
            for (int u = 0; u < V; u++) {
                if (!visited[u]) {  // If the vertex is not yet visited
                    dfsAP(u, visited, disc, low, parent, ap);  // Call DFS for articulation points
                }
            }

            // Output the articulation points found in the graph
            System.out.println("Articulation Points:");
            for (int u = 0; u < V; u++) {
                if (ap[u])  // If the vertex is an articulation point
                    System.out.println(u);
            }
        }

        // DFS utility function to find articulation points
        void dfsAP(int u, boolean[] visited, int[] disc, int[] low, int[] parent, boolean[] ap) {
            visited[u] = true;  // Mark the current vertex as visited
            disc[u] = low[u] = ++time;  // Assign discovery time and low-link value
            int children = 0;  // Track the number of children in the DFS tree

            // Explore all adjacent vertices to u
            for (int v : adj.get(u)) {
                if (!visited[v]) {  // If the adjacent vertex v is not visited
                    parent[v] = u;  // Set parent of v as u
                    children++;      // Increment the children count of u
                    dfsAP(v, visited, disc, low, parent, ap);  // Recursively call DFS on v
                    low[u] = Math.min(low[u], low[v]);  // Update the low-link value of u

                    // If u is root and has more than one child, it's an articulation point
                    if (parent[u] == -1 && children > 1) {
                        ap[u] = true;
                    }
                    // If u is not root and low[v] >= disc[u], then u is an articulation point
                    else if (parent[u] != -1 && low[v] >= disc[u]) {
                        ap[u] = true;
                    }
                } else if (v != parent[u]) {  // Back edge (v is already visited, but not the parent of u)
                    low[u] = Math.min(low[u], disc[v]);  // Update low[u] considering back edge
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create a graph with 5 vertices
        Graph g = new Graph(5);
        // Add edges to the graph
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        // Find and print articulation points in the graph
        g.findArticulationPoints();
    }
}

