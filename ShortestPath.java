import java.util.*;
import java.util.Arrays;

import static java.lang.System.out;
import java.util.Random;

/**
 * <h1>Shortest Path on the map</h1>
 * This finds out the shortest path between 
 * a start and destination node(vertex) on the graph.
 * As it traverses the nodes on the path one by one, 
 * at every node it checks for an update in 
 * inter-node path values and recalculates it.
 * 
 * @author Gaurav Agarwal
 * @since 2018-04-22
 */
public class ShortestPath {
    /**
     * A utility function to find the vertex with minimum distance value, 
     * from the set of vertices not yet included in shortest path tree
     * 
     * @author geeksforgeeks.org - Aakash Hasija
     * @param dist[] minimum distance of every vertex from the start
     * @param sptSet[] visited vertices
     * @param n total number of vertices
     * @return min_index minimum distance vertex of all the unvisited vertices.
     */
    int minDistance(int dist[], Boolean sptSet[], int n) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 1; v <= n; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    /**
     * Dijkstra's single source shortest path algorithm.
     * 
     * @author geeksforgeeks.org-Aakash Hasija  /  Gaurav Agarwal
     * @param graph[][] the map graph given by the user.
     * @param s starting vertex
     * @param t destination vertex
     * @param n total number of vertices in the graph
     * @return path[] The shortest path available from s to t
     */
    public int[] dijkstra(int graph[][], int s, int t, int n) {

        List<Integer> visitedPath = new ArrayList<Integer>();
        int parent[] = new int[n + 1];
        //-----------
        int dist[] = new int[n + 1];
        Boolean sptSet[] = new Boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        dist[s] = 0;

        for (int count = 1; count < n; count++) {

            int u = minDistance(dist, sptSet, n);

            sptSet[u] = true;
            for (int v = 1; v <= n; v++)

                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            if (u == t)
                break;
        }
        //-----------
        // System.out.println("Distance of t: "+dist[t]);
        // stDist = dist[t];
        visitedPath.add(t);
        for (int a = t; a != s;) {
            visitedPath.add(parent[a]);
            a = parent[a];
        }
        int path[] = new int[visitedPath.size()];
        for (int a = 0; a < visitedPath.size(); a++) {
            path[a] = visitedPath.get(visitedPath.size() - 1 - a);
        }
        return path;
    }

    /**
     * Changes time of an edge (path) 
     * between any two adjacent vertices on the graph.
     * 
     * @author Gaurav Agarwal
     * @param graph[][] the map graph given by the user.
     * @param n total number of vertices in the graph
     * @return changedPath[] The updated edge(u,v) and it's weight. 
     * Returns {-1,-1,-1} representing no change if the random function updates a self edge.
     */
    public int[] timeChange(int graph[][], int n) {
        Random rand = new Random();

        int u = rand.nextInt(n) + 1;
        //n is the maximum and the 1 is our minimum 
        int v = rand.nextInt(n) + 1;
        int w = rand.nextInt(50) + 0;
        // sptSet[u] = false;
        // sptSet[v] = false;

        int changedPath[] = { u, v, w };
        if (u == v) {
            changedPath[0] = -1;
            changedPath[1] = -1;
            changedPath[2] = -1;
        }
        return changedPath;
    }

    /**
     * calculates the total distance of the path
     * 
     * @author Gaurav Agarwal
     * @param graph[][] the map graph given by the user.
     * @param path[] the shortest path currently being followed.
     * @param n total number of vertices in the graph
     * @return dist the total travel distance of the path.
     */
    public int distance(int graph[][], int path[], int n) {
        int dist = 0;
        for (int i = 0; i < path.length - 1; i++) {
            // out.println("Distance "+path[i]+" + "+path[i+1]+" = "+graph[path[i]][path[i+1]]);
            dist += graph[path[i]][path[i + 1]];
        }
        return dist;
    }

    /**
     * prints the shortest path to be followed
     * 
     * @author Gaurav Agarwal
     * @param path[] the shortest path currently being followed.
     */
    public void printPath(int path[]) {
        for (int p : path) {
            out.print(p + " --> ");
        }
        out.println();
    }

    /**
     * Takes in user input of the graph, starting and the destination node.
     * It starts traversing the nodes of the graph 
     * till the destination node is reached.
     * The graph is updated periodically when a change in an edge is detected.
     * 
     * @author Gaurav Agarwal
     * @param args not being used.
     */
    public static void main(String[] args) {
        int n, s, t;
        Scanner sc = new Scanner(System.in);
        out.print("n= ");
        n = sc.nextInt();
        out.print("s= ");
        s = sc.nextInt();
        out.print("t= ");
        t = sc.nextInt();
        out.println();
        if (s < 1 || s > n || t < 1 || t > n) {
            out.println("Please enter vertices within the range (1 to " + n + ")");
            System.exit(4);
        }
        sc.nextLine();
        int graph[][] = new int[n + 1][n + 1];
        String[] edges;
        for (int i = 1; i <= n; i++) {
            edges = sc.nextLine().replaceAll("\\s+", "").split(",");
            for (int j = 1; j <= n; j++) {
                graph[i][j] = Integer.parseInt(edges[j - 1]);
                if (graph[i][j] == 0)
                    graph[i][j] = 9999;
            }
        }
        out.println();
        // displaying the graph
        // for (int i = 1; i <= n; i++) {
        //     for (int j = 1; j <= n; j++) {
        //         out.print(graph[i][j]+"\t");
        //     }
        //     out.println();
        // }
        ShortestPath sp = new ShortestPath();
        int noChange[] = { -1, -1, -1 };

        int path[] = sp.dijkstra(graph, s, t, n);
        out.println("Shortest path from current position " + path[0] + ":");
        sp.printPath(path);
        out.println("Distance = " + sp.distance(graph, path, n));

        while (path.length > 2) {
            int changedPath[] = new int[3];
            changedPath = sp.timeChange(graph, n);
            // out.println("Changed path: "+Arrays.equal);
            if (Arrays.equals(changedPath, noChange)) {
                // for(int r = 0; r < path.length; r++){
                //     path[r] = path[r+1];
                // }
                path = Arrays.copyOfRange(path, 1, path.length);
                out.println("Shortest path from current position " + path[0] + ":");
                sp.printPath(path);
            } else {
                out.println("Traffic change: (" + changedPath[0] + ", " + changedPath[1] + ") = " + changedPath[2]);
                graph[changedPath[0]][changedPath[1]] = changedPath[2];
                graph[changedPath[1]][changedPath[0]] = changedPath[2];
                path = sp.dijkstra(graph, path[1], t, n);
                out.println("Shortest path from current position " + path[0] + ":");
                sp.printPath(path);
                // int eliminate = path[0];
                // for (int i = 1; i <= n; i++) {
                //     for (int j = 1; j <= n; j++) {
                //         graph[eliminate][j] = 9999;
                //         graph[j][eliminate] = 9999;
                //     }
                // }
                // sptSet[eliminate] = true;
            }
            out.println("Distance = " + sp.distance(graph, path, n));
            out.println();
        }
    }
}

/*
Graph input to test:
n=5
s=1
t=5

0,23,8,35,90              
23,0,15,7,10
8,15,0,28,17
35,7,28,0,6
90,10,17,6,0
---------------------------------

n=9
s=2
t=8

0, 4, 50, 3, 40, 20, 95, 8, 0
4, 0, 8, 7, 9, 21, 4, 11, 33
50, 8, 0, 7, 10, 41, 40, 3, 2
3, 7, 7, 0, 9, 14, 12, 10, 0
40, 9, 10, 9, 0, 10, 91, 48, 50
20, 21, 41, 14, 10, 0, 2, 8, 70
95, 4, 40, 12, 91, 2, 0, 1, 6
8, 11, 3, 10, 48, 8, 1, 0, 7
0, 33, 2, 0, 50, 70, 6, 7, 0

*/