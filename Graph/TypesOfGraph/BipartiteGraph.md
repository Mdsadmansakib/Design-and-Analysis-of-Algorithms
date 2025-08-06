# Bipartite Graph Complete Guide

## What is a Bipartite Graph?

A **bipartite graph** is a special type of graph where the vertices can be divided into two disjoint sets such that no two vertices within the same set are adjacent.

### Key Properties:
- **Two disjoint vertex sets**: U and V
- **Cross-set edges only**: Every edge connects a vertex in U to a vertex in V
- **No intra-set edges**: No edge connects two vertices within the same set

### Formal Definition

A graph G = (V, E) is bipartite if the vertex set V can be split into two sets U and V such that:

```
∀(u,v) ∈ E, u ∈ U, v ∈ V
```

### Example

Consider:
- U = {A, B}
- V = {1, 2}
- Edges: (A,1), (A,2), (B,1), (B,2)

This forms a complete bipartite graph K₂,₂ where every vertex from set U connects to every vertex from set V.

## How to Check if a Graph is Bipartite

### The Coloring Algorithm

The most efficient method uses **2-coloring** with BFS or DFS:

**Concept**: Try to color all vertices using exactly two colors such that no two adjacent vertices have the same color.

- **Success** → Graph is bipartite
- **Failure** → Graph is not bipartite

### Algorithm Steps

1. Initialize a color array with -1 (unvisited) for all nodes
2. For each unvisited node:
   - Color it with 0 (starting color)
   - Perform BFS/DFS:
     - For each neighbor:
       - If unvisited: color with opposite color
       - If already colored with same color → NOT bipartite
3. If all components are successfully colored → Bipartite

### BFS Pseudocode

```
ALGORITHM: IsBipartite(graph, n)
INPUT: graph (adjacency list), n (number of vertices)
OUTPUT: true if bipartite, false otherwise

BEGIN
    INITIALIZE color[0...n-1] to -1  // -1 means unvisited
    
    FOR each vertex start FROM 0 TO n-1 DO
        IF color[start] == -1 THEN
            INITIALIZE queue as empty
            ENQUEUE start into queue
            SET color[start] = 0  // Start with color 0
            
            WHILE queue is not empty DO
                u = DEQUEUE from queue
                
                FOR each neighbor v of u DO
                    IF color[v] == -1 THEN
                        SET color[v] = 1 - color[u]  // Opposite color
                        ENQUEUE v into queue
                    ELSE IF color[v] == color[u] THEN
                        RETURN false  // Same color conflict
                    END IF
                END FOR
            END WHILE
        END IF
    END FOR
    
    RETURN true  // Successfully 2-colored
END
```

### DFS Pseudocode

```
ALGORITHM: IsBipartiteDFS(graph, n)
INPUT: graph (adjacency list), n (number of vertices)
OUTPUT: true if bipartite, false otherwise

BEGIN
    INITIALIZE color[0...n-1] to -1  // -1 means unvisited
    
    FOR each vertex start FROM 0 TO n-1 DO
        IF color[start] == -1 THEN
            IF NOT DFS(graph, start, 0, color) THEN
                RETURN false
            END IF
        END IF
    END FOR
    
    RETURN true
END

FUNCTION DFS(graph, u, currentColor, color[])
BEGIN
    SET color[u] = currentColor
    
    FOR each neighbor v of u DO
        IF color[v] == -1 THEN
            IF NOT DFS(graph, v, 1 - currentColor, color) THEN
                RETURN false
            END IF
        ELSE IF color[v] == color[u] THEN
            RETURN false  // Same color conflict
        END IF
    END FOR
    
    RETURN true
END
```

## Key Insight: Odd Cycles

**Fundamental theorem**: A graph is bipartite if and only if it contains **no odd-length cycles**.

### Examples

**Bipartite (Even Cycle)**:
```
0 - 1
|   |
3 - 2
```
This forms a 4-cycle (even) → ✅ Bipartite

**Not Bipartite (Odd Cycle)**:
```
0 - 1
|\ /|
| X |
|/ \|
2 - 3
```
Contains a 3-cycle (triangle) → ❌ Not Bipartite

## Connectivity in Bipartite Graphs

### Multiple Connections Allowed

**Yes!** A vertex in set U can be connected to multiple (or even all) vertices in set V, as long as:
- All connections are cross-set only
- No intra-set connections exist

### Example
- U = {A}
- V = {1, 2, 3}
- Edges: (A,1), (A,2), (A,3)

This is perfectly valid - vertex A connects to all vertices in V.

## Complete Bipartite Graph

A **complete bipartite graph** Km,n has:
- Set U with m vertices
- Set V with n vertices  
- All possible m × n edges between the sets

### Properties
- **Total edges**: m × n
- **Maximum edges** possible in a bipartite graph with these vertex counts
- **Highly connected** while maintaining bipartite property

## Applications

### Real-World Use Cases

1. **Job Assignment**: Workers (U) ↔ Jobs (V)
2. **Course Scheduling**: Students (U) ↔ Courses (V)
3. **Social Networks**: Users (U) ↔ Posts/Groups (V)
4. **Recommendation Systems**: Users (U) ↔ Items (V)
5. **Matching Problems**: Any two-sided matching scenario

### Algorithmic Applications

- **Maximum Matching**: Find largest set of edges with no shared vertices
- **Minimum Vertex Cover**: Smallest set of vertices covering all edges
- **Network Flow**: Source-sink problems with intermediate layers

## Time Complexity

- **Bipartite Check**: O(V + E) using BFS/DFS
- **Space Complexity**: O(V) for color array and queue

## Important Notes

- Always check **all disconnected components** when testing for bipartiteness
- The **2-coloring approach** is both intuitive and efficient
- **Odd cycles** are the only obstacle to bipartiteness
- **Complete bipartite graphs** represent maximum connectivity while preserving the bipartite property
