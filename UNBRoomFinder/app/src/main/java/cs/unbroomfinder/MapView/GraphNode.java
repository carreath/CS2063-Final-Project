package cs.unbroomfinder.MapView;

import android.graphics.Point;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;

import static cs.unbroomfinder.MainActivity.DEBUG;
import static cs.unbroomfinder.MainActivity.DEBUG_TAG;

/**
 * Created by carre on 2017-04-03.
 */

public class GraphNode {
    public LinkedList<GraphNode> neighbours = new LinkedList<GraphNode>();
    public HashMap<GraphNode, Integer> weights = new HashMap<GraphNode, Integer>();
    public int id = -1;
    public Point coords = new Point(-1, -1);


    public GraphNode(int id) {
        this.id = id;
    }

    public void setCoordinates(int x, int y) {
        this.coords = new Point(x + BuildingMapActivity.PATH_RADIUS, y + BuildingMapActivity.PATH_RADIUS);
    }

    public void addNeighbour(GraphNode node, int weight) {
        neighbours.add(node);
        weights.put(node, weight);
    }

    public String toString() {
        String ret = "";
        for(GraphNode g: neighbours) {
            ret += id + " " + g.id + " " + weights.get(g) + " " + coords.x + " " + coords.y + "\n";
        }
        return ret;
    }
}
