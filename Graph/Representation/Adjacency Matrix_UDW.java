public class GfG {

    // Modified addEdge to accept weight
    public static void addEdge(int[][] mat, int i, int j, int weight) {
        mat[i][j] = weight;
        mat[j][i] = weight; // Since the graph is undirected
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
        int[][] mat = new int[V][V]; // All initialized to 0

        // Adding edges with weights
        addEdge(mat, 0, 1, 5); // edge 0-1 with weight 5
        addEdge(mat, 0, 2, 3); // edge 0-2 with weight 3
        addEdge(mat, 1, 2, 2); // edge 1-2 with weight 2
        addEdge(mat, 2, 3, 4); // edge 2-3 with weight 4

        System.out.println("Weighted Adjacency Matrix Representation");
        displayMatrix(mat);
    }
}
