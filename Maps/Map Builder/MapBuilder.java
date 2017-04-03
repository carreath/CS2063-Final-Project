import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;

import java.util.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.*;

public class MapBuilder extends JFrame implements MouseListener, MouseMotionListener, KeyListener, WindowListener {
    public int width = 1200, height = 900;
    private BufferedImage[] images;
    private int imgIndex = 0;
    public int selectedImage = 0;
    private NodeList[] nodeList;
    private LinkedList<Node> stairs = new LinkedList<Node>();
    private double scale = 1;
    private int[] numNodes = new int[1];
    private LinkedList<Node> nodes = new LinkedList<Node>();
    private LinkedList<Node> classes = new LinkedList<Node>();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double swidth = screenSize.getWidth();
    double sheight = screenSize.getHeight();

    public void mouseDragged(MouseEvent e) {}
    public void keyReleased(KeyEvent e) { 
        if(e.getKeyCode() == ']') {
            if(selectedImage < images.length - 1) {
                selectedImage++;
                repaint();
            }
        }
        else if(e.getKeyCode() == '[') {
            if(selectedImage > 0) {
                selectedImage--;
                repaint();
            }
        }
        else {
            nodeList[selectedImage].keyReleased(e);
        }
    }
    public void mousePressed(MouseEvent e)  { 
        nodeList[selectedImage].mousePressed(e);
    }
    public void mouseReleased(MouseEvent e)  { nodeList[selectedImage].mouseReleased(e); repaint(); }

    public void keyPressed(KeyEvent e) {}
    public void keyTyped(KeyEvent e)  {}
    public void mouseMoved(MouseEvent e)  {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e)  {}
    public void mouseClicked(MouseEvent e)  {}
    public void windowOpened(WindowEvent arg0) {}
    public void windowClosed(WindowEvent arg0) {}
    public void windowIconified(WindowEvent arg0) {}
    public void windowDeiconified(WindowEvent arg0) {}
    public void windowActivated(WindowEvent arg0) {}
    public void windowDeactivated(WindowEvent arg0) {}

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

		AffineTransform at = new AffineTransform();

              // 4. translate it to the center of the component
              //at.translate(getWidth() / 2, getHeight() / 2);

              // 3. do the actual rotation
              //at.rotate(-Math.PI/2);

              // 2. just a scale because this image is big

              // 1. translate the object so that you rotate it around the 
              //    center (easier :))
              //at.translate(-(int)(images[selectedImage].getWidth() / scale) / 2, -(int)(images[selectedImage].getHeight() / scale) / 2);

              at.translate(100, 100);
              at.scale(0.5, 0.5);
              
              // draw the image
              g2d.drawImage(images[selectedImage], at, null);
              g.setColor(Color.BLACK);
              g.fillRect(100,100,25, 25);

        nodeList[selectedImage].draw(g2d);
    }

    public boolean loadImage(String image) {
        try{
            images[imgIndex] = ImageIO.read(new File(image));
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        imgIndex++;
        return true;
    }

    public MapBuilder(int selectedImage) {
        this.selectedImage = selectedImage;
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addWindowListener(this);

        int numImages = 0;

        Scanner sc = new Scanner(System.in);
        System.out.print("HeadHall = 0, Carleton = 1: ");
        int choice = sc.nextInt();

        if(choice == 1) {
    		numImages = 5; 
    		images = new BufferedImage[5];
    		nodeList = new NodeList[5];
	        loadImage("Carleton_Complex/C1_Tg1.png");
	        loadImage("Carleton_Complex/C2_TS1.png");
	        loadImage("Carleton_Complex/C3_TS2.png");
	        loadImage("Carleton_Complex/TS3.png");
	        loadImage("Carleton_Complex/TS4.png");
        }
        else {
    		numImages = 5;
    		images = new BufferedImage[5];
    		nodeList = new NodeList[5];
	        loadImage("HeadHall/HeadHall_A-1.png");
	        loadImage("HeadHall/HeadHall_B-1.png");
	        loadImage("HeadHall/HeadHall_C-1.png");
	        loadImage("HeadHall/HeadHall_D-1.png");
	        loadImage("HeadHall/HeadHall_E-1.png");
	    }

        for(int i=0; i<numImages; i++)
            nodeList[i] = new NodeList(stairs, nodes, classes, i);

        double scaleX = images[selectedImage].getWidth() / swidth;
        double scaleY = images[selectedImage].getHeight() / sheight;
        scale = Math.max(scaleX, scaleY);
        setSize((int)(images[selectedImage].getWidth() / scale), (int)(images[selectedImage].getHeight() / scale));
    }

    public void windowClosing(WindowEvent arg0) {
        int nodeCount = nodes.size();
        int edgeCount = 0;
        for(Node n : nodes) {
            edgeCount += n.getEdgeCount();
        }     

        System.out.println(nodeCount + " " + edgeCount);
        for(Node n : nodes) {
            System.out.print(n);
        }
        System.out.println( );
        System.exit(0);
    }

    public static void main(String[] a) {
        MapBuilder builder ;

        System.out.println("Starting on Node mode 'N'");
        System.out.println("click to add Nodes");
        System.out.println();
        System.out.println("Type 'E' to go to edge mode");
        System.out.println("Drag from one node to another to form an edge");
        System.out.println();
        System.out.println("type 'N' to go back to Node mode");
        System.out.println();
        System.out.println("type 'C' to go back to Class mode");
        System.out.println();
        System.out.println("When finished screen cap the graph then close the program with the red 'X'");
        System.out.println("copy output to HeadHall.graph then run Astar.java");
       
        builder = new MapBuilder(0);
        builder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        builder.setVisible(true);
    }
}