package cs.unbroomfinder.MapView;

import android.graphics.Point;
import android.util.Log;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import static cs.unbroomfinder.MainActivity.DEBUG_TAG;

/**
 * Created by carre on 2017-04-03.
 */

public class Graph {
    public HashMap<Integer, GraphNode> allNodes = new HashMap<Integer, GraphNode>();
    public HashMap<Integer, GraphNode>[] floors = new HashMap[5];
    public int nC = 0; //nodeCount
    public int eC = 0; //edgeCount

    public Graph(InputStream mapName) {
        for(int i=0; i<5; i++) {
            floors[i] = new HashMap<Integer, GraphNode>();
        }

        openMap(mapName);
    }

    private boolean openMap(InputStream mapName) {
        Scanner sc = new Scanner(mapName);
        nC = sc.nextInt();
        eC = sc.nextInt();

        for(int i=0; i<nC; i++) {
            allNodes.put(i, new GraphNode(i));
        }

        for(int i=0; i<eC; i++) {
            sc.nextLine();
            int id = sc.nextInt();
            GraphNode graphNode = allNodes.get(id);

            int edge = sc.nextInt();
            int weight = sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();
            int level = sc.nextInt();

            graphNode.setCoordinates(x, y);
            graphNode.addNeighbour(allNodes.get(edge), weight);

            if(level != -1) floors[level].put(id, graphNode);
            else {
                for(int j=0; j<floors.length; j++) {
                    floors[j].put(id, graphNode);
                }
            }
        }

        return true;
    }


    public LinkedList<GraphNode> getNeighbours(int i) {
        return allNodes.get(i).neighbours;
    }

    public HashMap<GraphNode, Integer> getWeights(int i) {
        return allNodes.get(i).weights;
    }

    public Point getCoords(int i) {
        return allNodes.get(i).coords;
    }

    public int getNumNodes() {
        return nC;
    }
}
