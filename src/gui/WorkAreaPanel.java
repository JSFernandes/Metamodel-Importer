package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.AffineTransform;
import java.util.Hashtable;

import javax.swing.JPanel;

public class WorkAreaPanel extends JPanel implements FocusListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1156919994429653969L;
	public Hashtable<Integer, Point> rectangles = new Hashtable<Integer, Point>();
	public Hashtable<Integer, Point[]> lines = new Hashtable<Integer, Point[]>();
	
	private final int ARR_SIZE = 10;
	
	public void repaint() {
		
		if(rectangles != null) {
			for(Point p : rectangles.values()) {
				getGraphics().drawRect(p.x - 5, p.y - 5, 150, 30);
	    	}
		}
		
		if(lines != null) {
			for(Point[] p : lines.values()) {
				//getGraphics().drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
				drawArrow(getGraphics(), p[0].x, p[0].y, p[1].x, p[1].y);
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
	
	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                      new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }
}
