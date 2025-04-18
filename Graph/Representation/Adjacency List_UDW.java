import java.util.*;

class Edge {
    int to, weight;
    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

public class UndirectedWeighted {
    public static void main(String[] args) {
        int V = 4;
        List<List<Edge>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        addEdge(adj, 0, 1, 5);
        addEdge(adj, 0, 2, 3);
        addEdge(adj, 1, 2, 2);
        addEdge(adj, 2, 3, 4);

        System.out.println("Undirected Weighted Graph:");
        printGraph(adj);
    }

    public static void addEdge(List<List<Edge>> adj, int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
        adj.get(v).add(new Edge(u, w)); // Undirected
    }

    public static void printGraph(List<List<Edge>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.print(i + " → ");
            for (Edge e : adj.get(i)) System.out.print("(" + e.to + ", " + e.weight + ") ");
            System.out.println();
        }
    }
}
