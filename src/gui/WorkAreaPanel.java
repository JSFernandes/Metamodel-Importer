package gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Hashtable;

import javax.swing.JPanel;

public class WorkAreaPanel extends JPanel implements FocusListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1156919994429653969L;
	public Hashtable<Integer, Point> rectangles = new Hashtable<Integer, Point>();
	public Hashtable<Integer, Point[]> lines = new Hashtable<Integer, Point[]>();
	
	public void repaint() {
		
		if(rectangles != null) {
			for(Point p : rectangles.values()) {
				getGraphics().drawRect(p.x - 5, p.y - 5, 150, 30);
	    	}
		}
		
		if(lines != null) {
			for(Point[] p : lines.values()) {
				getGraphics().drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
			}
		}
	}
	
	public void focusGained(FocusEvent fe){
	    repaint();
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
}
