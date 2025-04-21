import java.util.*;

public class DFSAdjList {
    static class Graph {
        int vertices;
        List<List<Integer>> adjList;

        Graph(int v) {
            vertices = v;
            adjList = new ArrayList<>();
            for (int i = 0; i < v; i++) {
                adjList.add(new ArrayList<>());
            }
        }

        // Add edge (undirected)
        void addEdge(int u, int v) {
            adjList.get(u).add(v);
            adjList.get(v).add(u); // Remove this line for directed graph
        }

        // DFS utility
        void dfsUtil(int node, boolean[] visited) {
            visited[node] = true;
            System.out.print(node + " ");

            for (int neighbor : adjList.get(node)) {
                if (!visited[neighbor]) {
                    dfsUtil(neighbor, visited);
                }
            }
        }

        // DFS traversal
        void dfs(int start) {
            boolean[] visited = new boolean[vertices];
            System.out.print("DFS starting from node " + start + ": ");
            dfsUtil(start, visited);
            System.out.println();
        }
    }
  
    public static void main(String[] args) {
        Graph g = new Graph(5);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);

        g.dfs(0); // Start DFS from node 0
    }
}
