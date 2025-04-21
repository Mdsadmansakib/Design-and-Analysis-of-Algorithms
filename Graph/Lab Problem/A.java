import java.util.*;

public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int C = sc.nextInt(); // number of cities
        int R = sc.nextInt(); // number of roads
        int K = sc.nextInt(); // fuel units
        int L = sc.nextInt(); // current city (starting point)

        // Build graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= C; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < R; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        // BFS to find how many cities can be visited with at most K steps
        boolean[] visited = new boolean[C + 1];
        Queue<int[]> queue = new LinkedList<>(); // [city, fuel used]

        queue.add(new int[]{L, 0});
        visited[L] = true;

        int reachableCities = 1; // start city is also counted

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int city = current[0];
            int fuelUsed = current[1];

            if (fuelUsed == K) continue; // can't go further

            for (int neighbor : graph.get(city)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    reachableCities++;
                    queue.add(new int[]{neighbor, fuelUsed + 1});
                }
            }
        }

        System.out.println(reachableCities);
    }
}
