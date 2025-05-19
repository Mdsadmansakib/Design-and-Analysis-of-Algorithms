import java.util.*;

public class Bellman {

    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    int v;
    List<Edge> edgeList;

    Bellman(int v) {
        this.v = v;
        edgeList = new ArrayList<>();
    }

    void addEdge(int u, int v, int w) {
        edgeList.add(new Edge(u, v, w));
        edgeList.add(new Edge(v, u, w)); // For undirected graph
    }

    void bellmanFord(int src) {
        int[] dist = new int[v + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 1; i < v; i++) {
            for (Edge edge : edgeList) {
                if (dist[edge.u] != Integer.MAX_VALUE &&
                    dist[edge.u] + edge.w < dist[edge.v]) {
                    dist[edge.v] = dist[edge.u] + edge.w;
                }
            }
        }

        for (Edge edge : edgeList) {
            if (dist[edge.u] != Integer.MAX_VALUE &&
                dist[edge.u] + edge.w < dist[edge.v]) {
                System.out.println("Negative weight cycle");
                return;
            }
        }

        for (int i = 1; i <= v; i++) {
            System.out.println("Distance from " + src + " to " + i + ": " +
                    (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int c = sc.nextInt(); //vertices
        int r = sc.nextInt(); //edges

        Bellman g = new Bellman(c);

        for (int i = 0; i < r; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(u, v, w);
        }

        int src = sc.nextInt(); 
        g.bellmanFord(src);
    }
}
