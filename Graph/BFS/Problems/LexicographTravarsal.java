//Traversal of a Graph in lexicographical order using BFS


import java.util.*;

class LexicographTravarsal{

// Function to traverse the graph in
// lexicographical order using BFS

static void LexiBFS(HashMap<Character, Set<Character>> G,
			char S, HashMap<Character, Boolean> vis)
{
	
	// Stores nodes of the graph
	// at each level
	Queue<Character> q = new LinkedList<>();

	// Insert nodes of first level
	q.add(S);

	// Mark S as
	// visited node
	vis.put(S, true);

	// Traverse all nodes of the graph
	while (!q.isEmpty())
	{
		
		// Stores top node of queue
		char top = q.peek();

		// Print visited nodes of graph
		System.out.print(top + " ");

		// Insert all adjacent nodes
		// of the graph into queue
		if (G.containsKey(top))
		{
			for(char i : G.get(top)) 
			{
				
				// If i is not visited
				if (vis.containsKey(i))
				{
					if (!vis.get(i))
					{
						
						// Mark i as visited node
						vis.put(i, true);

						// Insert i into queue
						q.add(i);
					}
				}
				else
				{
					
					// Mark i as visited node
					vis.put(i, true);

					// Insert i into queue
					q.add(i);
				}
			}
		}

		// Pop top element of the queue
		q.remove();
	}
}

// Utility Function to traverse graph
// in lexicographical order of nodes
static void CreateGraph(int N, int M, char S,
						char[][] Edges)
{
	
	// Store all the adjacent nodes
	// of each node of a graph
	HashMap<Character, Set<Character>> G = new HashMap<>();

	// Traverse Edges[][2] array
	for(int i = 0; i < M; i++)
	{
		if (G.containsKey(Edges[i][0]))
		{
			Set<Character> temp = G.get(Edges[i][0]);
			temp.add(Edges[i][1]);
			G.put(Edges[i][0], temp);
		}
		else
		{
			Set<Character> temp = new HashSet<>();
			temp.add(Edges[i][1]);
			G.put(Edges[i][0], temp);
		}
	}
	
	// Check if a node is already visited or not
	HashMap<Character, Boolean> vis = new HashMap<>();

	LexiBFS(G, S, vis);
}

// Driver code
public static void main(String[] args) 
{
	int N = 10, M = 10;
	char S = 'a';
	
	char[][] Edges = { { 'a', 'y' }, { 'a', 'z' },
					{ 'a', 'p' }, { 'p', 'c' },
					{ 'p', 'b' }, { 'y', 'm' },
					{ 'y', 'l' }, { 'z', 'h' },
					{ 'z', 'g' }, { 'z', 'i' } };

	// Function Call
	CreateGraph(N, M, S, Edges);
}
}

// This code is contributed by hritikrommie
