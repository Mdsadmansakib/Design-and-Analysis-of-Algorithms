import java.util.*;

public class AdjacencyListBFS {

    private int v;
    public List<List<Integer>> adjList;

    public AdjacencyListBFS(int v) {
        this.v = v;
        adjList = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            adjList.add(new LinkedList<>());
        }
    }

    public void addEdge(int i, int j) {
        adjList.get(i).add(j);
        adjList.get(j).add(i);
    }

    public void removeEdge(int i, int j) {
        adjList.get(i).remove((Integer) j);
        adjList.get(j).remove((Integer) i);
    }

    public void bfs(int src) {
        boolean[] visited = new boolean[v];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(src);
        visited[src] = true;

        while (queue.size() != 0) {
            int element = queue.poll();
            System.out.print(element + " ");
            for (int i : adjList.get(element)) {
                if (!visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdjacencyListBFS g = new AdjacencyListBFS(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        System.out.println("Enter the source vertex for BFS traversal");
        int src = sc.nextInt();
        System.out.println("BFS Traversal from source vertex " + src + " is:");
        g.bfs(src);
        sc.close();
    }
}
