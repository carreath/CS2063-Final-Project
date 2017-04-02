package cs.unbroomfinder.MapView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by carre on 2017-03-30.
 */

public class Map {
    private HashMap<Integer, Integer[]> vQ = new HashMap<Integer, Integer[]>();
    Node pQ = null;
    public Graph graph;

    public Map(InputStream mapName) {
        graph = new Graph(mapName);
    }

    public LinkedList<Integer[]> getShortestPath(int start, int end) {
        int current = start;
        int pathLength = 0;
        int prev = -1;

        Integer[] next = {current, prev, pathLength};

        while(current != end) {
            int[] neighbours = graph.getNeighbours(current);
            int[] weights = graph.getWeights(current);

            for(int i=0; i<neighbours.length; i++) {
                Integer[] priority = new Integer[3];
                priority[0] = neighbours[i];
                priority[1] = current;
                priority[2] = pathLength + weights[i];
                pQadd(priority);
            }

            addVisited(next);

            next = pQ.data;
            if(pQ.next != null) pQ = pQ.next;
            current = next[0];
            prev = next[1];
            pathLength = next[2];
        }
        Integer[] priority = new Integer[3];
        priority[0] = current;
        priority[1] = prev;
        priority[2] = pathLength;

        LinkedList<Integer[]> path = new LinkedList<Integer[]>();

        while(priority[1] != -1) {
            path.addLast(priority);
            priority = vQ.get(priority[1]);
        }
        path.addLast(priority);

        return path;
    }

    private void pQadd(Integer[] priority) {
        Node prev = pQ;
        Node current = pQ;
        while(current != null) {
            if(priority[2] < current.data[2]) {
                Node node = new Node(priority, current);
                if(prev != current) prev.next = node;
                else pQ = node;

                return;
            }
            if(current.data[0] == priority[0]) return;

            prev = current;
            current = current.next;
        }
        Node node = new Node(priority, null);
        if(prev != null) prev.next = node;
        else pQ = node;
    }

    public void addVisited(Integer[] arr) {
        if(vQ.containsKey(arr[0])) {
            Integer[] vArr = vQ.get(arr[0]);
            if(vArr[2] > arr[2]) {
                vQ.put(arr[0], arr);
            }
        }
        else {
            vQ.put(arr[0], arr);
        }
    }
}
