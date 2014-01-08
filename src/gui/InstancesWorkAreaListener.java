package gui;

import instances.AssociationInstance;
import instances.ModelInstance;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;

import subinstance.AssociationSubInstance;
import subinstance.ClassSubInstance;
import subinstance.ModelSubInstance;

public class InstancesWorkAreaListener implements MouseListener {

	InstancesWorkAreaPanel panel;
	ButtonGroup tool_buttons;
	
	ConnectorState state = ConnectorState.FIRST_CLICK;
	public ClassSubInstance last_clicked_class;
	Point first_click_point;
	AssociationInstance current_assoc;
	
	ModelSubInstance model;
	
	public InstancesWorkAreaListener(InstancesWorkAreaPanel area,
			ButtonGroup buttons) {
		panel = area;
		tool_buttons = buttons;
		model = area.model;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		try {
			UMLButton selected_button = ButtonFinder.getSelectedButton(tool_buttons);
			
			if(selected_button != null && selected_button.class_or_assoc.equals("Class"))
				handleClassPlacement(arg0, selected_button);
			else if(selected_button != null && selected_button.class_or_assoc.equals("Association"))
				handleAssociationPlacement(arg0, selected_button);
		}
		catch(Exception e) {
			return;
		}
	}

	private void handleAssociationPlacement(MouseEvent arg0,
			UMLButton selected_button) {
		Iterator<Map.Entry<Integer, Point>> it = panel.rectangles.entrySet().iterator();
		ClassSubInstance instance = null;
		Point click_point = arg0.getPoint();
		while(it.hasNext()) {
			Map.Entry<Integer, Point> entry = it.next();
			Point p = entry.getValue();
			ClassSubInstance c = model.classes.get(entry.getKey());
			
			if(click_point.x >= (p.x-5) && click_point.x <= (p.x + 195) &&
					click_point.y >= (p.y-5) && 
					click_point.y <= (p.y+25+(c.meta.attributes.size()+c.meta.enums.size())*30)) {
				instance = c;
			}
		}
		if(state == ConnectorState.FIRST_CLICK) {
			if(instance != null) {
				if(checkIfCanConnect(instance)) {
					state = ConnectorState.SECOND_CLICK;
					last_clicked_class = instance;
					first_click_point = click_point;
				}
			}
			else {
				last_clicked_class = null;
				first_click_point = null;
			}
		}
		else{
			if(instance != null) {
				if(checkIfCanConnect(instance)) {
					AssociationSubInstance assoc = new AssociationSubInstance();
					//assoc.assoc_type = EntityMeta.all_associations.get(selected_button.id);
					assoc.source = last_clicked_class;
					assoc.target = instance;
					last_clicked_class.assocs.add(assoc);
					instance.assocs.add(assoc);
					model.assocs.put(assoc.id, assoc);
					
					if(instance.meta.id == current_assoc.source_id) {
						panel.lines.put(assoc.id, new Point[]{click_point, first_click_point});
						assoc.source = instance;
						assoc.target = last_clicked_class;
					}
					else {
						panel.lines.put(assoc.id, new Point[]{first_click_point, click_point});
					}
					
					panel.revalidate();
					panel.repaint();
				}
				
				state = ConnectorState.FIRST_CLICK;
				last_clicked_class = null;
			}
			else {
				state = ConnectorState.FIRST_CLICK;
				last_clicked_class = null;
				first_click_point = null;
			}
		}
	}

	private boolean checkIfCanConnect(ClassSubInstance instance) {
		ModelInstance meta_model = ModelInstance.getInstance();
		if(state == ConnectorState.FIRST_CLICK) {
			for(AssociationInstance assoc : meta_model.instanced_assocs.values()) {
				if(assoc.source_id == instance.meta.id)
					return true;
			}
		}
		else {
			for(AssociationInstance assoc : meta_model.instanced_assocs.values()) {
				if(assoc.source_id == last_clicked_class.meta.id && assoc.target_id == instance.meta.id) {
					current_assoc = assoc;
					return true;
				}
			}
		}
		return false;
	}

	private void handleClassPlacement(MouseEvent arg0, UMLButton selected_button) {
		last_clicked_class = null;
		
		
		JLabel lab = new JLabel(selected_button.getText());
		ClassSubInstance c = new ClassSubInstance(ModelInstance.getInstance().instanced_classes.get(Integer.parseInt(selected_button.id)));
		
		panel.rectangles.put(c.id, arg0.getPoint());
		model.classes.put(c.id, c);
		
		for(int i = 0; i < c.meta.attributes.size(); ++i) {
			JLabel attr_lab = new JLabel(c.meta.attributes.get(i).meta.name + ": ");
			panel.add(attr_lab);
			
			attr_lab.setLocation(new Point(arg0.getPoint().x, arg0.getPoint().y+35+5*i));
			attr_lab.setSize(attr_lab.getPreferredSize());
			
			JLabel val_lab = new JLabel(c.meta.attributes.get(i).value);
			
			panel.add(val_lab);
			val_lab.setLocation(new Point(arg0.getPoint().x + attr_lab.getSize().width, arg0.getPoint().y+35+5*i));
			val_lab.setSize(200-attr_lab.getWidth()-5, val_lab.getPreferredSize().height);
		}
		
		for(int i = 0; i < c.meta.enums.size(); ++i) {
			JLabel enum_lab = new JLabel(c.meta.enums.get(i).meta.name + ": " + c.meta.enums.get(i).chosen_value);
			panel.add(enum_lab);
			
			enum_lab.setLocation(new Point(arg0.getPoint().x, arg0.getPoint().y+35+(c.meta.attributes.size()+i)*5));
			enum_lab.setSize(enum_lab.getPreferredSize());
		}
		
		panel.add(lab);
		lab.setLocation(arg0.getPoint());
		lab.setSize(lab.getPreferredSize());
		
		panel.revalidate();
		panel.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
