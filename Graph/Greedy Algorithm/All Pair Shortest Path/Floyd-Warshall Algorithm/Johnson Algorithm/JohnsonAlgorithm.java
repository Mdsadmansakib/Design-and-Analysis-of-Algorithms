import java.util.*;

class Edge {
    int from, to, weight;
    Edge(int u, int v, int w) {
        from = u; to = v; weight = w;
    }
}

class Graph {
    int V;
    List<Edge> edges;
    List<List<Edge>> adj;

    Graph(int V) {
        this.V = V;
        edges = new ArrayList<>();
        adj = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    void addEdge(int u, int v, int w) {
        edges.add(new Edge(u, v, w));
        adj.get(u).add(new Edge(u, v, w));
    }

    boolean bellmanFord(int src, int[] h) {
        Arrays.fill(h, Integer.MAX_VALUE);
        h[src] = 0;
        for (int i = 0; i < V; i++) {
            for (Edge e : edges) {
                if (h[e.from] != Integer.MAX_VALUE && h[e.from] + e.weight < h[e.to]) {
                    h[e.to] = h[e.from] + e.weight;
                }
            }
        }
        for (Edge e : edges) {
            if (h[e.from] != Integer.MAX_VALUE && h[e.from] + e.weight < h[e.to])
                return false; // Negative cycle
        }
        return true;
    }

    int[] dijkstra(int src, List<List<Edge>> adjWeighted) {
        int[] dist = new int[V + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0];
            int d = cur[1];

            if (d > dist[u]) continue;

            for (Edge e : adjWeighted.get(u)) {
                if (dist[u] + e.weight < dist[e.to]) {
                    dist[e.to] = dist[u] + e.weight;
                    pq.offer(new int[]{e.to, dist[e.to]});
                }
            }
        }
        return dist;
    }

    void johnson() {
        // Step 1: Add extra vertex s (vertex 0)
        int[] h = new int[V + 1];
        for (int v = 1; v <= V; v++) {
            edges.add(new Edge(0, v, 0));
        }

        // Step 2: Bellman-Ford from vertex 0
        if (!bellmanFord(0, h)) {
            System.out.println("Negative-weight cycle detected.");
            return;
        }

        // Step 3: Reweight edges
        List<List<Edge>> reweightedAdj = new ArrayList<>();
        for (int i = 0; i <= V; i++) reweightedAdj.add(new ArrayList<>());

        for (Edge e : edges) {
            int newWeight = e.weight + h[e.from] - h[e.to];
            if (e.from != 0) {
                reweightedAdj.get(e.from).add(new Edge(e.from, e.to, newWeight));
            }
        }

        // Step 4: Run Dijkstra from each vertex
        for (int u = 1; u <= V; u++) {
            int[] d = dijkstra(u, reweightedAdj);
            System.out.println("Shortest distances from vertex " + u + ":");
            for (int v = 1; v <= V; v++) {
                if (d[v] == Integer.MAX_VALUE)
                    System.out.print("INF ");
                else
                    System.out.print((d[v] + h[v] - h[u]) + " ");
            }
            System.out.println();
        }
    }
}

public class JohnsonAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt();
        int E = sc.nextInt();

        Graph g = new Graph(V);
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(u, v, w);
        }

        g.johnson();
    }
}
