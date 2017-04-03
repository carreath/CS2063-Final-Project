package cs.unbroomfinder.MapView;

/**
 * Created by carre on 2017-03-30.
 */

public class PriorityNode {
    GraphNode cur;
    PriorityNode prev;
    int pathLength;
    PriorityNode next;

    public PriorityNode(GraphNode cur, PriorityNode prev, int pathLength, PriorityNode next) {
        this.cur = cur;
        this.prev = prev;
        this.pathLength = pathLength;
        this.next = next;
    }

    public String toString() {
        return cur.id + " <- " + ((prev != null)? prev.toString(): "");
    }
}
