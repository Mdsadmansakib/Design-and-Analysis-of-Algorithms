import java.util.*;

class BFSAdjList {

    static ArrayList<Integer> bfs(ArrayList<ArrayList<Integer>> adj) {
        int V = adj.size();
        ArrayList<Integer> res = new ArrayList<>();
        boolean[] visited = new boolean[V];
        Queue<Integer> q = new LinkedList<>();

        visited[0] = true;
        q.add(0);

        while (!q.isEmpty()) {
            int curr = q.poll();
            res.add(curr);

            for (int neighbor : adj.get(curr)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.add(neighbor);
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        adj.add(new ArrayList<>(Arrays.asList(1, 2)));     // Node 0
        adj.add(new ArrayList<>(Arrays.asList(0, 2, 3)));  // Node 1  ( Undirected)
        adj.add(new ArrayList<>(Arrays.asList(0, 4)));     // Node 2
        adj.add(new ArrayList<>(Arrays.asList(1, 4)));     // Node 3
        adj.add(new ArrayList<>(Arrays.asList(2, 3)));     // Node 4

        ArrayList<Integer> ans = bfs(adj);
        for (int i : ans) {
            System.out.print(i + " ");
        }
    }
}
