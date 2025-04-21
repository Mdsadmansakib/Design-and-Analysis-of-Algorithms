import java.util.*;

public class DFSwithColor {
    static final int WHITE = 0;
    static final int GREY = 1;
    static final int BLACK = 2;

    static class Graph {
        int V;
        List<List<Integer>> adj;
        int[] color, prev, d, f;
        int time;

        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                adj.add(new ArrayList<>());
            }
            color = new int[V];
            prev = new int[V];
            d = new int[V];
            f = new int[V];
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v); // directed edge u -> v
        }

        void DFS() {
            for (int u = 0; u < V; u++) {
                color[u] = WHITE;
                prev[u] = -1;
                d[u] = Integer.MAX_VALUE;
                f[u] = Integer.MAX_VALUE;
            }

            time = 0;
            for (int u = 0; u < V; u++) {
                if (color[u] == WHITE) {
                    DFS_Visit(u);
                }
            }

            printTimes();
        }

        void DFS_Visit(int u) {
            color[u] = GREY;
            time++;
            d[u] = time;

            for (int v : adj.get(u)) {
                if (color[v] == WHITE) {
                    prev[v] = u;
                    DFS_Visit(v);
                }
            }

            color[u] = BLACK;
            time++;
            f[u] = time;
        }

        void printTimes() {
            System.out.println("Vertex | Discovery | Finish | Parent");
            for (int i = 0; i < V; i++) {
                System.out.printf("  %d    |     %d     |   %d   |   %d\n", i, d[i], f[i], prev[i]);
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(6);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);

        g.DFS();
    }
}
