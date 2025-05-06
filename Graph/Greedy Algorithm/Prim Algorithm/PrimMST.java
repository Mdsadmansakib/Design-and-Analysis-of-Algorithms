// Java implementation of Prim's Algorithm using Adjacency Matrix
public class PrimMST {
    static final int INF = Integer.MAX_VALUE;
    static int V = 5; // Number of vertices

    // Find the vertex with the minimum key value from the set of vertices not yet included in MST
    int minKey(int[] key, boolean[] mstSet) {
        int min = INF, minIndex = -1;
        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // Print the MST stored in parent[]
    void printMST(int[] parent, int[][] graph) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }

    // Main function to construct and print MST
    void primMST(int[][] graph) {
        int[] parent = new int[V];   // To store constructed MST
        int[] key = new int[V];      // Key values used to pick minimum weight edge
        boolean[] mstSet = new boolean[V]; // To represent set of vertices included in MST

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = INF;
            mstSet[i] = false;
        }

        // Start from the first vertex
        key[0] = 0;
        parent[0] = -1; // First node is always the root of MST

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;

            // Update key and parent for adjacent vertices
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        // Print the constructed MST
        printMST(parent, graph);
    }

    public static void main(String[] args) {
        PrimMST t = new PrimMST();
        int[][] graph = {
            { 0, 2, 0, 6, 0 },
            { 2, 0, 3, 8, 5 },
            { 0, 3, 0, 0, 7 },
            { 6, 8, 0, 0, 9 },
            { 0, 5, 7, 9, 0 }
        };

        t.primMST(graph);
    }
}
