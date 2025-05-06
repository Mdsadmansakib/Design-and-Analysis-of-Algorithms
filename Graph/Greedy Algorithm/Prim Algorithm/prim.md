# Prim's Algorithm for Minimum Spanning Trees

## Introduction
Prim's algorithm is a greedy algorithm used to find the minimum spanning tree (MST) of a connected, undirected, weighted graph. A minimum spanning tree is a subset of the edges that forms a tree including every vertex, where the total weight of all the edges in the tree is minimized.

## How Prim's Algorithm Works

1. **Initialization**: 
   - Start with any vertex as the initial MST
   - Mark this vertex as visited

2. **Growing the MST**:
   - Repeatedly add the minimum weight edge that connects a vertex in the MST to a vertex outside the MST
   - Mark the newly added vertex as visited

3. **Termination**: 
   - Continue until all vertices are included in the MST

## Key Properties

- Time Complexity: O(E log V) using binary heap or priority queue implementation
- Space Complexity: O(V + E)
- The algorithm always produces a correct MST if the graph is connected
- Works well for dense graphs

# 🌲 Prim's Algorithm (Minimum Spanning Tree)

Prim's algorithm is a **greedy algorithm** that finds a **Minimum Spanning Tree (MST)** for a connected weighted undirected graph. It adds edges one by one while avoiding cycles.

---

## 🧠 Core Idea

- Start with any vertex.
- Grow the MST by **adding the smallest edge** that connects a vertex in the MST to a vertex outside the MST.
- Repeat until all vertices are included.

---

## 🔢 Pseudocode (Min-Heap / Priority Queue approach)

```plaintext
Input: Graph G(V, E) with weights on edges

Initialize:
  - MST = empty set
  - visited[V] = {false, false, ..., false}
  - minHeap = priority queue (stores {weight, from, to})

Start from vertex 0:
  visited[0] = true
  for each edge (0, v) in E:
    insert (weight, 0, v) into minHeap

While minHeap is not empty:
  Pop the edge (w, u, v) with the smallest weight from minHeap
  If v is already visited:
    continue
  Add edge (u, v) to MST
  visited[v] = true

  for each edge (v, x) in E:
    if x is not visited:
      insert (weight, v, x) into minHeap

Return MST


## Java Implementation

Here's a more detailed Java implementation of Prim's algorithm:

// Java implementation of Prim's Algorithm using Adjacency Matrix
public class PrimMST {
    static final int INF = Integer.MAX_VALUE;
    static int V = 5; // Number of vertices

    // Find the vertex with the minimum key value from the set of vertices not yet included in MST
    int minKey(int[] key, boolean[] mstSet) {
        int min = INF, minIndex = -1;
        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // Print the MST stored in parent[]
    void printMST(int[] parent, int[][] graph) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }

    // Main function to construct and print MST
    void primMST(int[][] graph) {
        int[] parent = new int[V];   // To store constructed MST
        int[] key = new int[V];      // Key values used to pick minimum weight edge
        boolean[] mstSet = new boolean[V]; // To represent set of vertices included in MST

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = INF;
            mstSet[i] = false;
        }

        // Start from the first vertex
        key[0] = 0;
        parent[0] = -1; // First node is always the root of MST

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;

            // Update key and parent for adjacent vertices
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        // Print the constructed MST
        printMST(parent, graph);
    }

    public static void main(String[] args) {
        PrimMST t = new PrimMST();
        int[][] graph = {
            { 0, 2, 0, 6, 0 },
            { 2, 0, 3, 8, 5 },
            { 0, 3, 0, 0, 7 },
            { 6, 8, 0, 0, 9 },
            { 0, 5, 7, 9, 0 }
        };

        t.primMST(graph);
    }
}


## Exercise 1 (Easy): Minimum Spanning Tree Weight

**Problem**: Given an undirected weighted graph, implement Prim's algorithm to find the total weight of the minimum spanning tree.

**Input**: A graph represented as an adjacency matrix where `graph[i][j]` is the weight of the edge from vertex `i` to vertex `j`. If there is no edge, the value is 0.

**Output**: The total weight of the minimum spanning tree.

**Pseudocode Solution**:
```java
function calculateMSTWeight(int[][] graph):
    int n = graph.length
    boolean[] inMST = new boolean[n]
    int[] key = new int[n]
    
    // Initialize key values to infinity
    for (int i = 0; i < n; i++):
        key[i] = Integer.MAX_VALUE
    
    // Start with vertex 0
    key[0] = 0
    
    // Find MST with n vertices
    for (int count = 0; count < n; count++):
        // Find minimum key vertex not yet included in MST
        int minKey = Integer.MAX_VALUE
        int minIndex = -1
        
        for (int v = 0; v < n; v++):
            if (!inMST[v] && key[v] < minKey):
                minKey = key[v]
                minIndex = v
        
        // Add the selected vertex to the MST
        inMST[minIndex] = true
        
        // Update key values of adjacent vertices
        for (int v = 0; v < n; v++):
            if (graph[minIndex][v] != 0 && !inMST[v] && graph[minIndex][v] < key[v]):
                key[v] = graph[minIndex][v]
    
    // Calculate total weight
    int totalWeight = 0
    for (int i = 0; i < n; i++):
        totalWeight += key[i]
    
    return totalWeight
```

## Exercise 2 (Medium): Island Connection Problem

**Problem**: You are given a set of islands represented as points (x, y) on a 2D plane. The cost of building a bridge between two islands is equal to the Euclidean distance between them. Find the minimum cost required to connect all islands such that there is a path between any two islands.

**Input**: An array of points where each point is a pair (x, y) representing the coordinates of an island.

**Output**: The minimum total cost to connect all islands.

**Pseudocode Solution**:
```java
function connectIslands(int[][] islandCoordinates):
    int n = islandCoordinates.length
    
    // Create a graph where edges represent distances between islands
    int[][] graph = new int[n][n]
    for (int i = 0; i < n; i++):
        for (int j = i+1; j < n; j++):
            // Calculate Euclidean distance
            int x1 = islandCoordinates[i][0]
            int y1 = islandCoordinates[i][1]
            int x2 = islandCoordinates[j][0]
            int y2 = islandCoordinates[j][1]
            
            double distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2))
            graph[i][j] = distance
            graph[j][i] = distance
    
    // Apply Prim's algorithm
    boolean[] inMST = new boolean[n]
    double[] key = new double[n]
    
    // Initialize key values to infinity
    for (int i = 0; i < n; i++):
        key[i] = Double.MAX_VALUE
    
    // Start with first island
    key[0] = 0
    
    double totalCost = 0
    
    // Find MST with n islands
    for (int count = 0; count < n; count++):
        // Find minimum key vertex not yet included in MST
        double minKey = Double.MAX_VALUE
        int minIndex = -1
        
        for (int v = 0; v < n; v++):
            if (!inMST[v] && key[v] < minKey):
                minKey = key[v]
                minIndex = v
        
        // Add the selected vertex to the MST
        inMST[minIndex] = true
        totalCost += key[minIndex]
        
        // Update key values of adjacent vertices
        for (int v = 0; v < n; v++):
            if (graph[minIndex][v] != 0 && !inMST[v] && graph[minIndex][v] < key[v]):
                key[v] = graph[minIndex][v]
    
    return totalCost
```

## Exercise 3 (Hard): Constrained Minimum Spanning Tree

**Problem**: Given an undirected weighted graph, find the minimum spanning tree with the additional constraint that the spanning tree must include a specific subset of edges.

**Input**: 
- A graph represented as an edge list where each edge is a triple (u, v, w) representing an edge between vertices u and v with weight w
- A list of required edges that must be included in the MST

**Output**: The total weight of the minimum spanning tree that includes all required edges, or -1 if no such spanning tree exists.

**Pseudocode Solution**:
```java
function constrainedMST(List<Edge> edges, int vertices, List<Edge> requiredEdges):
    // First, check if the required edges form a valid forest (no cycles)
    DisjointSet ds = new DisjointSet(vertices)
    
    for (Edge edge : requiredEdges):
        if (ds.find(edge.source) == ds.find(edge.destination)):
            // Cycle detected, no valid MST with all required edges
            return -1
        ds.union(edge.source, edge.destination)
    
    // Reset for actual MST calculation
    ds = new DisjointSet(vertices)
    int totalWeight = 0
    
    // First, include all required edges
    for (Edge edge : requiredEdges):
        ds.union(edge.source, edge.destination)
        totalWeight += edge.weight
    
    // Sort remaining edges by weight
    List<Edge> remainingEdges = new ArrayList<>()
    for (Edge edge : edges):
        if (!requiredEdges.contains(edge)):
            remainingEdges.add(edge)
    
    Collections.sort(remainingEdges)
    
    // Apply Kruskal's algorithm for remaining edges
    for (Edge edge : remainingEdges):
        if (ds.find(edge.source) != ds.find(edge.destination)):
            ds.union(edge.source, edge.destination)
            totalWeight += edge.weight
    
    // Check if we have a spanning tree
    int components = 0
    for (int i = 0; i < vertices; i++):
        if (ds.find(i) == i):
            components++
    
    if (components > 1):
        // Not all vertices are connected
        return -1
    
    return totalWeight

// Helper class for disjoint set operations
class DisjointSet:
    int[] parent
    
    DisjointSet(int n):
        parent = new int[n]
        for (int i = 0; i < n; i++):
            parent[i] = i
    
    int find(int x):
        if (parent[x] != x):
            parent[x] = find(parent[x])
        return parent[x]
    
    void union(int x, int y):
        int rootX = find(x)
        int rootY = find(y)
        if (rootX != rootY):
            parent[rootY] = rootX
```

This solution uses a combination of Prim's algorithm principles with Kruskal's algorithm approach to handle the constrained MST problem. The key insight is that we first include all required edges and then grow the MST using the remaining edges, ensuring we maintain the forest property throughout.
