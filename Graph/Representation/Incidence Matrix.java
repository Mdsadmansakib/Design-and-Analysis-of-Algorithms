import java.util.*;

public class IncidenceMatrixDemo {

    static class Edge {
        int from, to, weight;
        boolean directed, weighted;

        Edge(int from, int to, int weight, boolean directed, boolean weighted) {
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.directed = directed;
            this.weighted = weighted;
        }
    }

    static void displayMatrix(int[][] matrix, int V, int E) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < E; j++) {
                System.out.printf("%3d ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    static int[][] createIncidenceMatrix(int V, List<Edge> edges) {
        int E = edges.size();
        int[][] matrix = new int[V][E];

        for (int j = 0; j < E; j++) {
            Edge e = edges.get(j);
            if (e.directed) {
                matrix[e.from][j] = e.weighted ? e.weight : 1;  // from vertex
                matrix[e.to][j] = e.weighted ? -e.weight : -1;  // to vertex
            } else {
                // undirected
                matrix[e.from][j] = e.weighted ? e.weight : 1;
                matrix[e.to][j] = e.weighted ? e.weight : 1;
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        int V = 4;

        // Sample edges
        List<Edge> undirectedUnweighted = Arrays.asList(
            new Edge(0, 1, 0, false, false),
            new Edge(0, 2, 0, false, false),
            new Edge(1, 2, 0, false, false),
            new Edge(2, 3, 0, false, false)
        );

        List<Edge> undirectedWeighted = Arrays.asList(
            new Edge(0, 1, 5, false, true),
            new Edge(0, 2, 3, false, true),
            new Edge(1, 2, 2, false, true),
            new Edge(2, 3, 4, false, true)
        );

        List<Edge> directedUnweighted = Arrays.asList(
            new Edge(0, 1, 0, true, false),
            new Edge(0, 2, 0, true, false),
            new Edge(1, 2, 0, true, false),
            new Edge(2, 3, 0, true, false)
        );

        List<Edge> directedWeighted = Arrays.asList(
            new Edge(0, 1, 5, true, true),
            new Edge(0, 2, 3, true, true),
            new Edge(1, 2, 2, true, true),
            new Edge(2, 3, 4, true, true)
        );

        System.out.println("=== Undirected Unweighted ===");
        displayMatrix(createIncidenceMatrix(V, undirectedUnweighted), V, undirectedUnweighted.size());

        System.out.println("\n=== Undirected Weighted ===");
        displayMatrix(createIncidenceMatrix(V, undirectedWeighted), V, undirectedWeighted.size());

        System.out.println("\n=== Directed Unweighted ===");
        displayMatrix(createIncidenceMatrix(V, directedUnweighted), V, directedUnweighted.size());

        System.out.println("\n=== Directed Weighted ===");
        displayMatrix(createIncidenceMatrix(V, directedWeighted), V, directedWeighted.size());
    }
}
