# Kruskal's Algorithm for Minimum Spanning Trees

## Introduction
Kruskal's algorithm is a greedy algorithm used for finding the minimum spanning tree (MST) of a connected, undirected, weighted graph. The algorithm finds a subset of the edges that forms a tree including every vertex, where the total weight of all the edges in the tree is minimized.

## How Kruskal's Algorithm Works

1. **Sort Edges**: 
   - Sort all the edges in non-decreasing order of their weight

2. **Initialize MST**:
   - Create an empty MST
   - Create a disjoint set for each vertex

3. **Build MST**:
   - Pick the smallest edge from the sorted list
   - Check if adding this edge creates a cycle in the MST
   - If no cycle is formed, add the edge to the MST
   - If a cycle would be formed, discard the edge
   - Repeat until MST has (V-1) edges

4. **Cycle Detection**:
   - Use a Disjoint-Set (Union-Find) data structure to efficiently detect cycles

## Algorithm in Simple Terms

1. Sort all edges by weight (from smallest to largest)
2. Start with an empty spanning tree
3. Add the edge with the smallest weight that doesn't create a cycle
4. Repeat step 3 until you have (V-1) edges in your spanning tree

## Java Implementation

Here's a simple Java implementation of Kruskal's algorithm:

```java
import java.util.*;

// Class to represent an edge in the graph
class Edge implements Comparable<Edge> {
    int source, destination, weight;
    
    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
    // Comparing edges based on their weights
    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

// Class to represent a disjoint set for Union-Find
class DisjointSet {
    int[] parent, rank;
    
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        
        // Initialize each element as its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    
    // Find the parent of a node (with path compression)
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    
    // Union of two sets by rank
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return;
        
        // Attach smaller rank tree under root of higher rank tree
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            // If ranks are same, make one as root and increment its rank
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}

// Main class for Kruskal's algorithm
public class KruskalMST {
    private int vertices;
    private ArrayList<Edge> allEdges;
    
    public KruskalMST(int vertices) {
        this.vertices = vertices;
        allEdges = new ArrayList<>();
    }
    
    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        allEdges.add(edge);
    }
    
    public List<Edge> findMST() {
        // List to store the MST
        List<Edge> mst = new ArrayList<>();
        
        // Sort all edges
        Collections.sort(allEdges);
        
        // Create a disjoint set
        DisjointSet ds = new DisjointSet(vertices);
        
        // Process edges in increasing order of weight
        for (Edge edge : allEdges) {
            int sourceRoot = ds.find(edge.source);
            int destRoot = ds.find(edge.destination);
            
            // If including this edge doesn't cause cycle, include it
            if (sourceRoot != destRoot) {
                mst.add(edge);
                ds.union(sourceRoot, destRoot);
            }
            
            // If we have V-1 edges, we found the MST
            if (mst.size() == vertices - 1) {
                break;
            }
        }
        
        return mst;
    }
    
    // Calculate total weight of the MST
    public int getMSTWeight(List<Edge> mst) {
        int totalWeight = 0;
        for (Edge edge : mst) {
            totalWeight += edge.weight;
        }
        return totalWeight;
    }
    
    public static void main(String[] args) {
        // Example graph with 4 vertices
        int vertices = 4;
        KruskalMST graph = new KruskalMST(vertices);
        
        // Add edges
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);
        
        // Find MST
        List<Edge> mst = graph.findMST();
        
        // Print MST
        System.out.println("Edges in the Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println(edge.source + " - " + edge.destination + " : " + edge.weight);
        }
        
        // Print total weight
        System.out.println("Total MST weight: " + graph.getMSTWeight(mst));
    }
}
```

## Example Run

For the graph defined in the main method:

```
Edges in the Minimum Spanning Tree:
2 - 3 : 4
0 - 3 : 5
0 - 1 : 10
Total MST weight: 19
```

## Time and Space Complexity

- **Time Complexity**: O(E log E) or O(E log V)
  - Sorting the edges takes O(E log E) time
  - The find and union operations take O(log V) time with path compression and union by rank
  
- **Space Complexity**: O(V + E)
  - O(V) for the disjoint-set data structure
  - O(E) for storing the edges

## Kruskal's vs. Prim's Algorithm

- **Kruskal's Algorithm**:
  - Works well for sparse graphs
  - Sorts all edges at the beginning
  - Grows forest of trees until they connect
  - Uses Union-Find data structure

- **Prim's Algorithm**:
  - Works better for dense graphs
  - Starts from a single vertex
  - Grows a single tree
  - Uses Priority Queue or Min Heap

## Exercise: Connecting Cities

**Problem**: There are N cities labeled from 1 to N. Some cities are connected by bidirectional roads with different costs. Your task is to build new roads such that all cities are connected with minimum total cost.

**Input**:
- N: Number of cities
- roads: Array of [city1, city2, cost] representing existing roads

**Output**:
- Minimum cost to connect all cities

**Example**:
```
Input:
N = 4
roads = [[1, 2, 3], [1, 3, 5], [2, 3, 1], [3, 4, 2]]

Output:
6
```

**Solution**:
```java
public int minimumCost(int N, int[][] roads) {
    // Create a graph to represent cities and roads
    KruskalMST graph = new KruskalMST(N + 1); // +1 because cities are labeled from 1
    
    // Add all existing roads to the graph
    for (int[] road : roads) {
        int city1 = road[0];
        int city2 = road[1];
        int cost = road[2];
        graph.addEdge(city1, city2, cost);
    }
    
    // Find MST using Kruskal's algorithm
    List<Edge> mst = graph.findMST();
    
    // Calculate total cost
    int totalCost = graph.getMSTWeight(mst);
    
    return totalCost;
}
```

This simple exercise demonstrates how Kruskal's algorithm can be applied to real-world problems like connecting cities with minimum cost infrastructure.
