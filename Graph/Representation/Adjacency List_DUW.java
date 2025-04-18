import java.util.*;

public class DirectedUnweighted {
    public static void main(String[] args) {
        int V = 4;
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        addEdge(adj, 0, 1);
        addEdge(adj, 0, 2);
        addEdge(adj, 1, 2);
        addEdge(adj, 2, 3);

        System.out.println("Directed Unweighted Graph:");
        printGraph(adj);
    }

    public static void addEdge(List<List<Integer>> adj, int from, int to) {
        adj.get(from).add(to); // Only one direction
    }

    public static void printGraph(List<List<Integer>> adj) {
        for (int i = 0; i < adj.size(); i++)
            System.out.println(i + " → " + adj.get(i));
    }
}
