package gui;

import instances.ClassInstance;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.Map;

import subinstance.ModelSubInstance;

public class InstancesWorkAreaPanel extends WorkAreaPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6769691882034460424L;
	
	ModelSubInstance model;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(rectangles != null) {
			Iterator<Map.Entry<Integer, Point>> it = rectangles.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry<Integer, Point> entry = it.next();
				Point p = entry.getValue();
				g.drawRect(p.x - 5, p.y - 5, 200, 30);
				int id = entry.getKey();
				ClassInstance i = model.classes.get(id).meta;
				g.drawRect(p.x - 5, p.y + 25, 200, 30*(i.attributes.size()+i.enums.size()));
			}
		}
		
		if(lines != null) {
			for(Point[] p : lines.values()) {
				//getGraphics().drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
				drawArrow(g, p[0].x, p[0].y, p[1].x, p[1].y);
			}
		}
	}
	
	public InstancesWorkAreaPanel(ModelSubInstance m) {
		model = m;
		super.process_repaint = false;
	}

}
