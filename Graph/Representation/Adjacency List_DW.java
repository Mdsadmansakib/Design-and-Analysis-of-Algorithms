import java.util.*;

class WEdge {
    int to, weight;
    WEdge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

public class DirectedWeighted {
    public static void main(String[] args) {
        int V = 4;
        List<List<WEdge>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        addEdge(adj, 0, 1, 5);
        addEdge(adj, 0, 2, 3);
        addEdge(adj, 1, 2, 2);
        addEdge(adj, 2, 3, 4);

        System.out.println("Directed Weighted Graph:");
        printGraph(adj);
    }

    public static void addEdge(List<List<WEdge>> adj, int from, int to, int weight) {
        adj.get(from).add(new WEdge(to, weight)); // Only from → to
    }

    public static void printGraph(List<List<WEdge>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.print(i + " → ");
            for (WEdge e : adj.get(i)) System.out.print("(" + e.to + ", " + e.weight + ") ");
            System.out.println();
        }
    }
}
