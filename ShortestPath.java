import java.util.*;

import java.util.Arrays;
// import org.apache.commons.lang3.ArrayUtils;

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
class ShortestPath {
    // A utility function to find the vertex with minimum distance value,
    // from the set of vertices not yet included in shortest path tree
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
     * @author Gaurav Agarwal
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