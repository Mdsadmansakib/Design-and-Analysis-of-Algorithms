import java.util.*;

public class BFSAdjMatrix {

    // BFS from source node (default 0)
  
    static List<Integer> bfs(int[][] adjMatrix) {
        int V = adjMatrix.length;
        boolean[] visited = new boolean[V];
        List<Integer> result = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();

        visited[0] = true; // source = 0
        q.add(0);

        while (!q.isEmpty()) {
            int node = q.poll();
            result.add(node);

            for (int i = 0; i < V; i++) {
                if (adjMatrix[node][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    q.add(i);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int V = 5;
        int[][] adj = new int[V][V];

        // ---- Add Edges ----
        // Example: add edge 0-1 and 0-2
        // For undirected graph:
        adj[0][1] = 1;
        adj[1][0] = 1;

        adj[0][2] = 1;
        adj[2][0] = 1;

        adj[1][3] = 1;
        adj[3][1] = 1;

        adj[2][4] = 1;
        adj[4][2] = 1;
  /*
          For directed graph, only one side:
      
        adj[0][1] = 1;
        adj[0][2] = 1;
        adj[1][3] = 1;
        adj[2][4] = 1;
        */

        List<Integer> bfsResult = bfs(adj);
        for (int node : bfsResult) {
            System.out.print(node + " ");
        }
    }
}
