package gui;

import java.awt.Point;
import java.util.Hashtable;

import javax.swing.JPanel;

public class WorkAreaPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1156919994429653969L;
	public Hashtable<Integer, Point> rectangles = new Hashtable<Integer, Point>();
	
	public void repaint() {
		super.repaint();
		
		if(rectangles != null) {
			System.out.println("kek");
			for(Point p : rectangles.values()) {
				System.out.println("kek");
				getGraphics().drawRect(p.x - 5, p.y - 5, 150, 30);
	    	}
		}
	}
}
