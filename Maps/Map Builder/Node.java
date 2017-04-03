import java.util.*;
public class Node {
	private boolean isClass = false;
	private int id;
	private int x;
	private int y;
	private int level;
	private LinkedList<Integer> edges = new LinkedList<Integer>();
	private LinkedList<Integer> weights = new LinkedList<Integer>();

	public boolean addEdge(int src, int weight) {
		if(!edges.contains(src)) {
			edges.add(src);
			weights.add(weight);
			return true;
		}
		return false;
	}

	public String toString() {
		String s = "";
		for(int i=0; i<edges.size(); i++) {
			s += id + " " + edges.get(i) + " " + weights.get(i) + " " + 2*(x-100) + " " + 2*(y-100) + " " + level + "\n";
		}
		return s;
	}

	public LinkedList<Integer> getEdges() {
		return edges;
	}

	public void isClass() {
		isClass = true;
	}
	
	public int getID() {
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public int getEdgeCount() {
		return edges.size();	
	}

	public Node(int id, int x, int y, int level) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.level = level;
	}
}