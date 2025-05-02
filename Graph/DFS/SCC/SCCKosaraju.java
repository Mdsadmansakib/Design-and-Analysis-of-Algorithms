import java.util.*;

public class SCCKosaraju {
    static class Graph {
        int V;
        List<List<Integer>> adj;

        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
        }

        // Step 1: Fill stack with finish times
        void fillOrder(int v, boolean[] visited, Stack<Integer> stack) {
            visited[v] = true;
            for (int neighbor : adj.get(v)) {
                if (!visited[neighbor]) {
                    fillOrder(neighbor, visited, stack);
                }
            }
            stack.push(v);
        }

        // Step 2: Transpose the graph
        Graph getTranspose() {
            Graph g = new Graph(V);
            for (int v = 0; v < V; v++) {
                for (int neighbor : adj.get(v)) {
                    g.addEdge(neighbor, v);
                }
            }
            return g;
        }

        // Step 3: DFS on transposed graph
        void dfs(int v, boolean[] visited) {
            visited[v] = true;
            System.out.print(v + " ");
            for (int neighbor : adj.get(v)) {
                if (!visited[neighbor]) {
                    dfs(neighbor, visited);
                }
            }
        }

        // Main function to print SCCs
        void printSCCs() {
            Stack<Integer> stack = new Stack<>();
            boolean[] visited = new boolean[V];

            // Step 1
            for (int i = 0; i < V; i++) {
                if (!visited[i]) {
                    fillOrder(i, visited, stack);
                }
            }

            // Step 2
            Graph transposed = getTranspose();

            // Step 3
            Arrays.fill(visited, false);
            System.out.println("Strongly Connected Components:");
            while (!stack.isEmpty()) {
                int v = stack.pop();
                if (!visited[v]) {
                    transposed.dfs(v, visited);
                    System.out.println();
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);

        g.printSCCs();
    }
}
