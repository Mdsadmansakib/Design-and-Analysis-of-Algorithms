import java.util.*;

public class AdjacencyMatrixBFS {
    public int[][] adjMatrix;
    public int v;

    public AdjacencyMatrixBFS(int v) {
        this.v = v;
        adjMatrix = new int[v][v];
    }

    public void addEdge(int i, int j) {
        adjMatrix[i][j] = 1;
        adjMatrix[j][i] = 1;
    }

    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = 0;
        adjMatrix[j][i] = 0;
    }


    public void bfs2(int src) {
        boolean[] visited = new boolean[v];
        
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        visited[src] = true;

        while (!queue.isEmpty()) {
            int element = queue.poll();
            System.out.print(element + " ");
            for (int i = 0; i < v; i++) {
                if (adjMatrix[element][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdjacencyMatrixBFS g = new AdjacencyMatrixBFS(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        System.out.println("Enter the source vertex for BFS traversal");
        int src = sc.nextInt(); 

        System.out.println("BFS traversal of graph from vertex " + src + " is ");
        g.bfs2(src);
        sc.close();
    }
}
