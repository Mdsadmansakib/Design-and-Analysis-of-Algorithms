import java.util.*;

public class TarjanSCC {
    static class Graph {
        int V;
        List<List<Integer>> adj;
        int time = 0; // Global timer to assign discovery times

        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++)
                adj.add(new ArrayList<>());
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v); // Add directed edge from u to v
        }

        void tarjanSCC() {
            int[] disc = new int[V];        // Discovery time of nodes
            int[] low = new int[V];         // Lowest discovery reachable
            boolean[] onStack = new boolean[V]; // Track if node is in current DFS stack
            Stack<Integer> stack = new Stack<>();

            Arrays.fill(disc, -1); // Initialize discovery times as -1 (unvisited)

            // Run DFS for unvisited nodes
            for (int i = 0; i < V; i++) {
                if (disc[i] == -1) {
                    dfs(i, disc, low, onStack, stack);
                }
            }
        }

        void dfs(int u, int[] disc, int[] low, boolean[] onStack, Stack<Integer> stack) {
            // Step 1: Initialize discovery and low time
            disc[u] = low[u] = time++;
            stack.push(u);
            onStack[u] = true;

            // Step 2: Visit all neighbors
            for (int v : adj.get(u)) {
                if (disc[v] == -1) {
                    // If v is not visited, DFS on it
                    dfs(v, disc, low, onStack, stack);
                    low[u] = Math.min(low[u], low[v]); // Update low link
                } else if (onStack[v]) {
                    // If v is on the current DFS stack, update low[u]
                    low[u] = Math.min(low[u], disc[v]);
                }
            }

            // Step 3: If u is head of SCC, pop all nodes till u
            if (low[u] == disc[u]) {
                System.out.print("SCC: ");
                while (true) {
                    int v = stack.pop();
                    onStack[v] = false;
                    System.out.print(v + " ");
                    if (v == u) break;
                }
                System.out.println(); // End of one SCC
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

        g.tarjanSCC(); // Run the algorithm
    }
}
