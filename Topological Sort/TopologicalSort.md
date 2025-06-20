# Topological Sort

## Description

Topological sorting is a linear ordering of vertices in a directed acyclic graph (DAG) such that for every directed edge (u, v), vertex u comes before vertex v in the ordering. This algorithm is particularly useful for scheduling tasks, resolving dependencies, and determining the order of operations in various applications.

### Key Properties
- Only works on Directed Acyclic Graphs (DAGs)
- If the graph contains a cycle, topological sorting is not possible
- A DAG can have multiple valid topological orderings
- Time complexity: O(V + E) where V is vertices and E is edges

### Common Applications
- Course prerequisite scheduling
- Build system dependency resolution
- Task scheduling with dependencies
- Deadlock detection in operating systems
- Symbol dependency resolution in compilers

## Algorithm Steps

There are two main approaches to implement topological sorting:

### Approach 1: Depth-First Search (DFS) Based
1. Create a visited array to track processed vertices
2. Create a stack to store the topological order
3. For each unvisited vertex, perform DFS:
   - Mark the current vertex as visited
   - Recursively visit all unvisited adjacent vertices
   - Push the current vertex to the stack after visiting all neighbors
4. Pop all elements from the stack to get the topological order

### Approach 2: Kahn's Algorithm (BFS Based)
1. Calculate the in-degree (number of incoming edges) for each vertex
2. Add all vertices with in-degree 0 to a queue
3. While the queue is not empty:
   - Remove a vertex from the queue and add it to the result
   - For each neighbor of the removed vertex:
     - Decrease its in-degree by 1
     - If in-degree becomes 0, add it to the queue
4. If the result contains all vertices, return the topological order
5. Otherwise, the graph contains a cycle

## Java Pseudocode

### DFS-Based Implementation

```java
import java.util.*;

class TopologicalSort {
    private int vertices;
    private List<List<Integer>> adjacencyList;
    
    public TopologicalSort(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }
    
    // Add directed edge from source to destination
    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
    }
    
    // DFS-based topological sort
    public List<Integer> topologicalSortDFS() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[vertices];
        
        // Perform DFS for all unvisited vertices
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfsUtil(i, visited, stack);
            }
        }
        
        // Pop all elements from stack to get topological order
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return result;
    }
    
    private void dfsUtil(int vertex, boolean[] visited, Stack<Integer> stack) {
        visited[vertex] = true;
        
        // Recursively visit all adjacent vertices
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited, stack);
            }
        }
        
        // Push current vertex to stack after visiting all neighbors
        stack.push(vertex);
    }
}
```

### Kahn's Algorithm Implementation

```java
import java.util.*;

class TopologicalSortKahn {
    private int vertices;
    private List<List<Integer>> adjacencyList;
    
    public TopologicalSortKahn(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }
    
    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
    }
    
    // Kahn's algorithm for topological sort
    public List<Integer> topologicalSortKahn() {
        int[] inDegree = new int[vertices];
        
        // Calculate in-degree for each vertex
        for (int i = 0; i < vertices; i++) {
            for (int neighbor : adjacencyList.get(i)) {
                inDegree[neighbor]++;
            }
        }
        
        // Add all vertices with in-degree 0 to queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < vertices; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        
        // Process vertices with in-degree 0
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);
            
            // Decrease in-degree of all neighbors
            for (int neighbor : adjacencyList.get(current)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // Check for cycle - if result doesn't contain all vertices
        if (result.size() != vertices) {
            throw new RuntimeException("Graph contains a cycle - topological sort not possible");
        }
        
        return result;
    }
}
```

### Usage Example

```java
public class Main {
    public static void main(String[] args) {
        // Create a graph with 6 vertices (0 to 5)
        TopologicalSort graph = new TopologicalSort(6);
        
        // Add edges representing dependencies
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        
        // Get topological ordering
        List<Integer> result = graph.topologicalSortDFS();
        
        System.out.println("Topological Sort Order:");
        for (int vertex : result) {
            System.out.print(vertex + " ");
        }
        // Output: 5 4 2 3 1 0 (one possible valid ordering)
    }
}
```

## Complexity Analysis

- **Time Complexity**: O(V + E) for both DFS and Kahn's algorithm
- **Space Complexity**: O(V) for storing visited array, stack/queue, and result

## When to Use Each Approach

- **DFS-based**: Simpler to implement, good for detecting cycles during traversal
- **Kahn's algorithm**: More intuitive, explicitly handles cycle detection, better for understanding the dependency resolution process
