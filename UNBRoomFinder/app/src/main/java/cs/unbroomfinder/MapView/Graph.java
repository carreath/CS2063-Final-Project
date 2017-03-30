package cs.unbroomfinder.MapView;

import java.util.*;
import java.io.*;

public class Graph {
    int[] offsets, nodes, weights, edges;
    int numNodes, numEdges;

    private int nC = 0; //nodeCount
    private int eC = 0; //edgeCount

    public Graph(String mapName) {
        openMap(mapName);
    }

    private boolean openMap(String mapName) {
        try {
            File file = new File(mapName);

            Scanner sc = new Scanner(file);
            numNodes = sc.nextInt();
            numEdges = sc.nextInt();
            offsets = new int[numNodes];
            weights = new int[numEdges];
            edges = new int[numEdges];

            int currentNode = 0;
            for(int i=0; i<numEdges; i++) {
                sc.nextLine();
                int node = sc.nextInt();
                int edge = sc.nextInt();
                int weight = sc.nextInt();

                if(currentNode < node) {
                    currentNode = node;
                    offsets[currentNode] = i;
                }

                edges[i] = edge;
                weights[i] = weight;
            }
        }catch(IOException e) {
            return false;
        }
        return true;
    }

    public int[] getNeighbours(int i) {
        return (i < nC - 1)? Arrays.copyOfRange(edges, offsets[i], offsets[i + 1]) : Arrays.copyOfRange(edges, offsets[i], eC);
    }

    public int[] getWeights(int i) {
        return (i < nC - 1)? Arrays.copyOfRange(weights, offsets[i], offsets[i + 1]) : Arrays.copyOfRange(weights, offsets[i], eC);
    }
}
