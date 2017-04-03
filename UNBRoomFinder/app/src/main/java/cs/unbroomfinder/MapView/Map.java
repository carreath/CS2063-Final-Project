package cs.unbroomfinder.MapView;

import android.util.Log;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import static cs.unbroomfinder.MainActivity.DEBUG;
import static cs.unbroomfinder.MainActivity.DEBUG_TAG;

/**
 * Created by carre on 2017-03-30.
 */

public class Map {
    private HashMap<GraphNode, PriorityNode> vQ = new HashMap<GraphNode, PriorityNode>();
    PriorityNode pQ = null;
    public Graph graph;

    public Map(InputStream mapName) {
        graph = new Graph(mapName);
    }

    public PriorityNode getShortestPath(int start, int end) {
        int curId = start;
        GraphNode currentNode = graph.allNodes.get(curId);
        PriorityNode current = new PriorityNode(currentNode, null, 0, null);
        while (current != null && current.cur.id != end) {
            for (GraphNode node : current.cur.neighbours) {
                PriorityNode next = new PriorityNode(node, current, current.pathLength + current.cur.weights.get(node), null);
                pQadd(next);
            }

            addVisited(current);

            current = pQ;
            pQ = pQ.next;
        }

        return current;
    }

    private void pQadd(PriorityNode next) {
        if(vQ.get(next.cur) != null && vQ.get(next.cur).pathLength < next.pathLength) return;

        PriorityNode prev = pQ;
        PriorityNode current = pQ;
        while(current != null) {
            if(next.pathLength < current.pathLength) {
                next.next = current;
                if(prev != current) prev.next = next;
                else pQ = next;

                return;
            }
            if(current.cur == next.cur) return;

            prev = current;
            current = current.next;
        }
        if(prev != null) prev.next = next;
        else pQ = next;
    }

    public void addVisited(PriorityNode current) {
        if(vQ.containsKey(current.cur)) {
            PriorityNode next = vQ.get(current.cur);
            if(next.pathLength > current.pathLength) {
                vQ.put(current.cur, current);
            }
        }
        else {
            vQ.put(current.cur, current);
        }
    }
}
