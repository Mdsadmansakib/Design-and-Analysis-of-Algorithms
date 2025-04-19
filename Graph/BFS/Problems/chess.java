/*
Minimum steps to reach target by a Knight | Set 1
Given a square chessboard of n x n size, the position of the Knight and the position of a target are given. We need to find out the minimum steps a Knight will take to reach the target position.
Ans:
This problem can be seen as the shortest path in an unweighted graph. Therefore, BFS is an appropriate algorithm to solve this problem. 

Steps:

Start with the knight’s initial position and mark it as visited.
Initialize a queue for BFS, where each entry stores the knight’s current position and the distance from the starting position.
Explore all 8 possible moves a knight can make from its current position.
For each move:
Check if the new position is within the board boundaries.
Check if the position has not been visited yet.
If valid, push this new position into the queue with a distance 1 more than its parent.
During BFS, if the current position is the target position, return the distance of that position.
Repeat until the target is found or all possible positions are explored.
*/

import java.util.*;

// Class to store the cell's data
class Cell {
    int x, y, dis;

    // Default constructor
    Cell() {
        this.x = 0;
        this.y = 0;
        this.dis = 0;
    }

    // Parameterized constructor
    Cell(int x, int y, int dis) {
        this.x = x;
        this.y = y;
        this.dis = dis;
    }
}

public class Chess {

    // Utility method to check if (x, y) is inside the board
    static boolean isInside(int x, int y, int n) {
        return x >= 1 && x <= n && y >= 1 && y <= n;
    }

    // Method to return the minimum steps to reach target
    static int minSteps(int[] knightPos, int[] targetPos, int n) {
        
        // x and y direction where a knight can move
        int[] dx = { -2, -1, 1, 2, -2, -1, 1, 2 };
        int[] dy = { -1, -2, -2, -1, 1, 2, 2, 1 };

        // Queue for storing knight's states
        Queue<Cell> q = new LinkedList<>();

        // Push starting position of knight with 0 distance
        q.add(new Cell(knightPos[0], knightPos[1], 0));

        Cell t;
        int x, y;

        // Visit array initialized to false
        boolean[][] visit = new boolean[n + 1][n + 1];

        // Visit starting position
        visit[knightPos[0]][knightPos[1]] = true;

        // Loop until queue is empty
        while (!q.isEmpty()) {
            t = q.poll();

            // If target is reached, return the distance
            if (t.x == targetPos[0] && t.y == targetPos[1])
                return t.dis;

            // Explore all reachable positions
            for (int i = 0; i < 8; i++) {
                x = t.x + dx[i];
                y = t.y + dy[i];

                // If the position is valid and not visited, 
                // push it to the queue
                if (isInside(x, y, n) && !visit[x][y]) {
                    visit[x][y] = true;
                    q.add(new Cell(x, y, t.dis + 1));
                }
            }
        }

        // If no path found, return -1
        return -1;
    }

    // Driver code
    public static void main(String[] args) {
        int n = 30;
        int[] knightPos = { 1, 1 };
        int[] targetPos = { 30, 30 };
        System.out.println(minSteps(knightPos, targetPos, n));
    }
}





