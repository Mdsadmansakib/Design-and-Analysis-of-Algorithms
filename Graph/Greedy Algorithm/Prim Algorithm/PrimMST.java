import java.util.*;

// Class to represent an edge with a destination vertex and weight
class Edge implements Comparable<Edge> {
    int vertex;
    int weight;

    Edge(int v, int w) {
        this.vertex = v;
        this.weight = w;
    }

    // For PriorityQueue to compare edges based on weight
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

public class PrimMST{
    
    public static void prims(int V, List<List<Edge>> graph) {
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        int[] weight = new int[V];

        Arrays.fill(weight, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        // Min-heap based on edge weight
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        weight[0] = 0;
        pq.add(new Edge(0, 0)); // Start from vertex 0

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge neighbor : graph.get(u)) {
                int v = neighbor.vertex;
                int w = neighbor.weight;

                if (!visited[v] && w < weight[v]) {
                    weight[v] = w;
                    parent[v] = u;
                    pq.add(new Edge(v, w));
                }
            }
        }

        // Output the MST
        System.out.println("Edge\tWeight");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + weight[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of vertices: ");
        int V = sc.nextInt();
        System.out.print("Enter number of edges: ");
        int E = sc.nextInt();

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        System.out.println("Enter edges as: source destination weight");
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w)); // Undirected graph
        }

        prims(V, graph);
        sc.close();
    }
}
