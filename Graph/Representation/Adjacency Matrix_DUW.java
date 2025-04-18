public class GfG {

    // Add edge in a directed manner
    public static void addEdge(int[][] mat, int from, int to) {
        mat[from][to] = 1; // Only one direction
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
        int[][] mat = new int[V][V]; // All values initialized to 0

        // Adding directed edges (from → to)
        addEdge(mat, 0, 1);
        addEdge(mat, 0, 2);
        addEdge(mat, 1, 2);
        addEdge(mat, 2, 3);

        System.out.println("Directed Unweighted Adjacency Matrix:");
        displayMatrix(mat);
    }
}
