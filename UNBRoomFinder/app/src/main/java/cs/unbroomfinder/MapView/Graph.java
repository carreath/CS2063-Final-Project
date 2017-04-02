package cs.unbroomfinder.MapView;

import android.graphics.Point;
import android.util.Log;

import java.util.*;
import java.io.*;

public class Graph {
    public int[] offsets, nodes, weights, edges;

    Point[] nodeCoords;

    public int nC = 0; //nodeCount
    public int eC = 0; //edgeCount

    public Graph(InputStream mapName) {
        openMap(mapName);
    }

    private boolean openMap(InputStream mapName) {

        Scanner sc = new Scanner(mapName);
        nC = sc.nextInt();
        eC = sc.nextInt();
        nodeCoords = new Point[nC];
        offsets = new int[nC + 1];
        for(int i=1; i<nC + 1; i++) {
            offsets[i] = eC - 1;
        }
        edges = new int[eC];
        weights = new int[eC];

        int currentNode = 0;
        for(int i=0; i<eC; i++) {
            sc.nextLine();
            int node = sc.nextInt();
            int edge = sc.nextInt();
            int weight = sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();

            if(currentNode < node) {
                currentNode = node;
                offsets[currentNode] = i;
            }

            edges[i] = edge;
            weights[i] = weight;
            nodeCoords[node] = new Point(x + BuildingMapActivity.PATH_RADIUS, y + BuildingMapActivity.PATH_RADIUS);
        }
        return true;
    }

    public int[] getNeighbours(int i) {
        return (i < nC - 1)? Arrays.copyOfRange(edges, offsets[i], offsets[i + 1]) : Arrays.copyOfRange(edges, offsets[i], eC);
    }

    public int[] getWeights(int i) {
        return (i < nC - 1)? Arrays.copyOfRange(weights, offsets[i], offsets[i + 1]) : Arrays.copyOfRange(weights, offsets[i], eC);
    }

    public Point getCoords(int i) {
        return nodeCoords[i];
    }

    public int getNumNodes() {
        return nC;
    }
}
