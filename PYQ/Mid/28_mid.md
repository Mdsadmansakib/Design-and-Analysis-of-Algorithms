# University of Dhaka
## Department of Computer Science and Engineering
### 2nd Year 2nd Semester In-course Examination, 2023
### CSE-2202: Design and Analysis of Algorithms-I (3 Credits)

**Total Marks: 20**  
**Time: 60 minutes**

## Question 1 [8 marks]
**Run Floyd Warshall on the following graph and generate the D^k matrix for k = 0 to 4.**

### Solution:

The Floyd-Warshall algorithm is used to find shortest paths between all pairs of vertices in a weighted graph, which may contain negative edges but no negative cycles.

First, let's set up the initial distance matrix D^0 based on the given graph:

#### Initial Distance Matrix (D^0):

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 0 | ∞ | -4 | ∞ | 3 |
| 1 | ∞ | 0 | ∞ | ∞ | ∞ |
| 2 | ∞ | 16 | 0 | 14 | -2 |
| 3 | ∞ | 6 | ∞ | 0 | ∞ |
| 4 | ∞ | 18 | ∞ | ∞ | 0 |

Where:
- 0 on the diagonal (distance from vertex to itself)
- ∞ where there's no direct edge
- The weight of the edge where one exists

Now, let's compute D^k for each k from 0 to 4:

#### D^1 (using vertex 0 as intermediate):

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 0 | ∞ | -4 | ∞ | 3 |
| 1 | ∞ | 0 | ∞ | ∞ | ∞ |
| 2 | ∞ | 16 | 0 | 14 | -2 |
| 3 | ∞ | 6 | ∞ | 0 | ∞ |
| 4 | ∞ | 18 | ∞ | ∞ | 0 |

No changes because there are no shortcuts through vertex 0 that are better than existing paths.

#### D^2 (using vertex 0 and 1 as intermediates):

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 0 | ∞ | -4 | ∞ | 3 |
| 1 | ∞ | 0 | ∞ | ∞ | ∞ |
| 2 | ∞ | 16 | 0 | 14 | -2 |
| 3 | ∞ | 6 | ∞ | 0 | ∞ |
| 4 | ∞ | 18 | ∞ | ∞ | 0 |

No changes because there are no better paths through vertex 1.

#### D^3 (using vertex 0, 1, and 2 as intermediates):

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 0 | 12 | -4 | 10 | -6 |
| 1 | ∞ | 0 | ∞ | ∞ | ∞ |
| 2 | ∞ | 16 | 0 | 14 | -2 |
| 3 | ∞ | 6 | ∞ | 0 | ∞ |
| 4 | ∞ | 18 | ∞ | ∞ | 0 |

Using vertex 2, we can now reach:
- 0→1 through 0→2→1: (-4) + 16 = 12
- 0→3 through 0→2→3: (-4) + 14 = 10
- 0→4 through 0→2→4: (-4) + (-2) = -6

#### D^4 (using vertex 0, 1, 2, and 3 as intermediates):

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 0 | 12 | -4 | 10 | -6 |
| 1 | ∞ | 0 | ∞ | ∞ | ∞ |
| 2 | ∞ | 16 | 0 | 14 | -2 |
| 3 | ∞ | 6 | ∞ | 0 | ∞ |
| 4 | ∞ | 18 | ∞ | ∞ | 0 |

No changes because there are no better paths through vertex 3.

#### D^5 (final matrix, using all vertices 0, 1, 2, 3, and 4 as intermediates):

|   | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| 0 | 0 | 12 | -4 | 10 | -6 |
| 1 | ∞ | 0 | ∞ | ∞ | ∞ |
| 2 | ∞ | 16 | 0 | 14 | -2 |
| 3 | ∞ | 6 | ∞ | 0 | ∞ |
| 4 | ∞ | 18 | ∞ | ∞ | 0 |

No changes because there are no better paths through vertex 4.

Therefore, the final shortest distance matrix is D^5, which represents the shortest paths between all pairs of vertices in the graph.

## Question 2 [8 marks]
**Run DFS on the following graph in lexicographic order (If a node has 3 adjacent nodes, explore the lexicographically smaller one). You should draw the graph in your script and mark each node with starting time, finishing time and low value. [1.5+1.5+3 marks]**

**Then Find articulation points and bridges based on the low values of those nodes. [2 marks]**

### Solution:

First, I'll represent the graph with its adjacency list:

```
A: [C, E]
B: [D, F, G]
C: [A, I]
D: [B, H]
E: [A]
F: [B]
G: [B]
H: [D, I]
I: [C, H]
```

Running DFS in lexicographic order:

1. Start with vertex A (lowest lexicographically)
2. At each step, visit unvisited neighbors in lexicographic order

#### DFS Execution:

| Node | Start Time | Finish Time | Low Value |
|------|------------|-------------|-----------|
| A    | 1          | 12          | 1         |
| C    | 2          | 11          | 2         |
| I    | 3          | 10          | 3         |
| H    | 4          | 9           | 4         |
| D    | 5          | 8           | 5         |
| B    | 6          | 7           | 6         |
| E    | 13         | 14          | 13        |
| F    | 15         | 16          | 15        |
| G    | 17         | 18          | 17        |

#### Articulation Points:

A node is an articulation point if either:
1. It is the root of the DFS tree and has at least two children, OR
2. It is not the root, and there exists a child v such that no vertex in the subtree rooted at v has a back edge to one of the ancestors of the node.

Based on the low values:
- Node A is an articulation point as it's the root with multiple children
- Node C is an articulation point (removing it would disconnect I from the rest)
- Node H is an articulation point (removing it would disconnect D from the rest)

#### Bridges:

An edge is a bridge if there is no back edge from any descendant of v to an ancestor of u when we have an edge (u, v) in the DFS tree.

Bridges in this graph:
- (A, C)
- (A, E)
- (C, I)
- (H, D)
- (B, F)
- (B, G)

## Question 3 [4 marks]
**Simulate Prim's minimum spanning tree algorithm in the following graph starting from the node 'a' and compute the following table for all nodes in the graph. You can assume that the symbols used in the following table have their usual meaning.**

### Solution:

Prim's algorithm finds a minimum spanning tree for a weighted undirected graph. It starts with a single vertex and grows the tree by adding the minimum-weight edge that connects the tree to a vertex not yet in the tree.

Starting from node 'a', here's how Prim's algorithm works:

1. Initialize:
   - V = {a, b, c, d, e, f, g, h, i}
   - T = {a} (vertices in the tree)
   - Key[a] = 0, Key[all other vertices] = ∞
   - π[a] = NIL, π[all other vertices] = NIL

2. Execute Prim's algorithm:

- Start with vertex 'a'
- Add edge (a, h) with weight 8
- Add edge (h, g) with weight 1
- Add edge (g, f) with weight 2
- Add edge (f, d) with weight 9
- Add edge (f, c) with weight 4
- Add edge (c, b) with weight 8
- Add edge (c, i) with weight 2
- Add edge (c, d) with weight 7
- Add edge (d, e) with weight 10

Final MST consists of edges: (a,h), (h,g), (g,f), (f,c), (c,i), (c,b), (c,d), (d,e)
Total weight: 8 + 1 + 2 + 4 + 2 + 8 + 7 + 10 = 42

Final table:

| V | a | b | c | d | e | f | g | h | i |
|---|---|---|---|---|---|---|---|---|---|
| T | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 |
| Key | 0 | 8 | 4 | 7 | 10 | 2 | 1 | 8 | 2 |
| π | NIL | c | f | c | d | g | h | a | c |

Where:
- V: Vertices in the graph
- T: 1 indicates vertex is in the MST
- Key: Final key value (minimum weight to include the vertex in MST)
- π: Parent of the vertex in the MST
