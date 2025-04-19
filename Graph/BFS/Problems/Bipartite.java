/*
Check whether a given graph is Bipartite or not

Given a graph with V vertices numbered from 0 to V-1 and a list of edges, determine whether the graph is bipartite.

Note: A bipartite graph is a type of graph where the set of vertices can be divided into two disjoint sets, say U and V, such that every edge connects a vertex in U to a vertex in V, there are no edges between vertices within the same set.

Example:

Input: V = 4, edges[][]= [[0, 1], [0, 2], [1, 2], [2, 3]]
Output: false
Explanation: 


Detect-cycle-in-an-undirected-graph-1
node 1 and node 2 have same color while coloring

The graph is not bipartite because no matter how we try to color the nodes using two colors, there exists a cycle of odd length (like 1–2–0–1), which leads to a situation where two adjacent nodes end up with the same color. This violates the bipartite condition, which requires that no two connected nodes share the same color.


Input: V = 4, edges[][] = [[0, 1], [1, 2], [2, 3]] 
Output: true
Explanation: 


Detect-cycle-in-an-undirected-graph-2
The given graph can be colored in two colors so, it is a bipartite graph.



A common and efficient way to solve this is by using Breadth-First Search (BFS). The idea is to traverse the graph level by level and assign colors alternately to the vertices as we proceed.


Step-by-step approach:

Start BFS from any uncolored vertex and assign it color 0.
For each vertex, color its uncolored neighbors with the opposite color (1 if current is 0, and vice versa)
Check if a neighbor already has the same color as the current vertex, return false (graph is not bipartite).
If BFS completes without any conflicts, return true (graph is bipartite).
Below is the implementation of the above approach:

*/

import java.util.*;

class Bipartite {

    // Function to construct the adjacency list from edges
    static ArrayList<ArrayList<Integer>> constructadj(int V, int[][] edges) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        return adj;
    }

    // Function to check if the graph is Bipartite using BFS
    static boolean isBipartite(int V,int[][]  edges) {
        
        int[] color = new int[V];
        Arrays.fill(color, -1); 
        
        // create adjacency list
        ArrayList<ArrayList<Integer>> adj = constructadj(V,edges);
        
        for (int i = 0; i < V; i++) {
            if (color[i] == -1) {
                Queue<Integer> q = new LinkedList<>();
                color[i] = 0;
                q.offer(i);

                while (!q.isEmpty()) {
                    int u = q.poll();

                    for (int v : adj.get(u)) {
                        if (color[v] == -1) {
                            color[v] = 1 - color[u];
                            q.offer(v);
                        } else if (color[v] == color[u]) {
                            return false; // Conflict found
                        }
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int V = 4;

        // Edges of the graph
        int[][] edges = {{0, 1}, {0, 2}, {1, 2}, {2, 3}};

        // Check if the graph is bipartite
        System.out.println(isBipartite(V, edges));
    }
}

