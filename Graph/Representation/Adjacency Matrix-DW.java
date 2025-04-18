public class GfG {

    // Add edge with weight in a directed graph
    public static void addEdge(int[][] mat, int from, int to, int weight) {
        mat[from][to] = weight; // Only one direction
    }

    public static void displayMatrix(int[][] mat) {
        for (int[] row : mat) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        int V = 4;
        int[][] mat = new int[V][V]; // Initialized to 0 (no edges)

        // Adding directed edges with weights
        addEdge(mat, 0, 1, 5);  // 0 → 1 with weight 5
        addEdge(mat, 0, 2, 3);  // 0 → 2 with weight 3
        addEdge(mat, 1, 2, 2);  // 1 → 2 with weight 2
        addEdge(mat, 2, 3, 4);  // 2 → 3 with weight 4

        System.out.println("Directed Weighted Adjacency Matrix:");
        displayMatrix(mat);
    }
}
