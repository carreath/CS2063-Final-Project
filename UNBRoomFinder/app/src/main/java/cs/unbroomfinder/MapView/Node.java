package cs.unbroomfinder.MapView;

import java.util.LinkedList;

/**
 * Created by carre on 2017-03-30.
 */

public class Node {
    Integer[] data;
    Node next;

    public Node(Integer[] data, Node next) {
        this.data = data;
        this.next = next;
    }
}
