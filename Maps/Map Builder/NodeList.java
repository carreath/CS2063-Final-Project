import java.util.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.*;

public class NodeList {
	private LinkedList<Node> nodes;
	private LinkedList<Node> levelNodes = new LinkedList<Node>();
	private int edgeCount = 0;
	private int radius = 15;
    private char type = 'N';
	private boolean drawingEdge = false;
	private Node startNode;
	private int level;
    private LinkedList<Node> stairs;
    private LinkedList<Node> classes;

    public void keyReleased(KeyEvent e) {
    	System.out.println("Mode Selected: " + (char)e.getKeyCode());
    	if(e.getKeyCode() == 'N') type = (char)e.getKeyCode();
    	if(e.getKeyCode() == 'E') type = (char)e.getKeyCode();
        if(e.getKeyCode() == 'S') type = (char)e.getKeyCode();
    }

	public void mousePressed(MouseEvent e) {
        int x = e.getX() - radius/2, y = e.getY() - radius/2;
        if(type == 'N') {
        	Node n = new Node(nodes.size(), x, y, level);
            nodes.add(n);
            levelNodes.add(n); 
        }
        else if(type == 'E') {
            Node n = getClickedNode(x, y);
            if(n == null) return;

            drawingEdge = true;
            startNode = n;
        }
        else if(type == 'S') {
            Node n = new Node(nodes.size(), x, y, -1);
            nodes.add(n); 
            stairs.add(n);
        }
    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX() - radius/2, y = e.getY() - radius/2;
        if(drawingEdge) {
            Node n = getClickedNode(x, y);
            if(n != null) {
            	int weight = (int)Math.sqrt(Math.pow(startNode.getX() - x, 2) + Math.pow(startNode.getY() - y, 2));
            	startNode.addEdge(n.getID(), weight);
            	edgeCount += (n.addEdge(startNode.getID(), weight))? 2: 0;
            	
            }
        }
        drawingEdge = false;
        startNode = null;
    }

    public int getEdgeCount() {
    	return edgeCount;
    }

    public Node getClickedNode(int x, int y) {
    	for(Node n: levelNodes) {
    		if(Math.pow(n.getX() - x, 2) + Math.pow(n.getY() - y, 2) < Math.pow(radius, 2)) {
    			return n;
    		}
    	}
    	for(Node n: stairs) {
    		if(Math.pow(n.getX() - x, 2) + Math.pow(n.getY() - y, 2) < Math.pow(radius, 2)) {
    			return n;
    		}
    	}
    	return null;
    }

    public void draw(Graphics2D g2d) {
    	g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    	for(Node n: levelNodes) {
    		g2d.setColor(Color.GREEN);
    		for(Integer i: n.getEdges()) {
    			g2d.drawLine(n.getX() + radius/2, n.getY() + radius/2, nodes.get(i).getX() + radius/2, nodes.get(i).getY() + radius/2);
    		}
    	}
    	for(Node n: stairs) {
    		g2d.setColor(Color.RED);
	    	g2d.fillOval(n.getX(), n.getY(), radius + 4 * ((n.getID() + "").length() - 1), radius);
    		g2d.setColor(Color.WHITE);
    		g2d.drawString(n.getID() + "", n.getX() + radius/2 - 4, n.getY() + radius/2 + 4);
    	}
    	for(Node n: levelNodes) {
            if(classes.contains(n)) {
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(n.getX() , n.getY() , radius + 4 * ((n.getID() + "").length() - 1), radius);
                g2d.setColor(Color.BLACK);
                g2d.drawString(n.getID() + "", n.getX() + radius/2 - 4, n.getY() + radius/2 + 4);
            }
        	else {
            	if(!stairs.contains(n)) {
    	    		g2d.setColor(Color.BLUE);
    		    	g2d.fillOval(n.getX() , n.getY(), radius + 4 * ((n.getID() + "").length() - 1), radius);
    	    		g2d.setColor(Color.WHITE);
    	    		g2d.drawString(n.getID() + "", n.getX() + radius/2 - 4, n.getY() + radius/2 + 4);
    	    	}
            }
    	}
    }

    public boolean getDrawingEdge() {
    	return drawingEdge;
    }

    public String toString() {
    	String s = "";
    	for(Node n: nodes) {
    		s += n;
    	}
    	return s;
    }

    public NodeList(LinkedList<Node> stairs, LinkedList<Node> nodes, LinkedList<Node> classes, int level) {
    	this.stairs = stairs;
        this.nodes = nodes;
        this.classes = classes;
        this.level = level;
    }
}