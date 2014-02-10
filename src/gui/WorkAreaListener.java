package gui;

import instances.AssociationInstance;
import instances.ClassInstance;
import instances.EnumInstance;
import instances.ModelInstance;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import restrictions.FeatureModelRestrictions;
import restrictions.RestrictionException;
import restrictions.Restrictions;
import data.AssociationMeta;
import data.EntityMeta;

public class WorkAreaListener implements MouseListener {
	
	WorkAreaPanel panel;
	ButtonGroup tool_buttons;
	ModelInstance model_instance = ModelInstance.getInstance();
	
	ConnectorState state = ConnectorState.FIRST_CLICK;
	public String last_connector_id = "";
	public ClassInstance last_clicked_class;
	Point first_click_point;
	Hashtable<Integer, ArrayList<JComponent>> class_components = new Hashtable<Integer, ArrayList<JComponent>>();
	Restrictions restrictions = new FeatureModelRestrictions();
	
	public JLabel error = new JLabel();
	
	public void mouseClicked(MouseEvent evt) {
		
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
			if(selected_button != null && selected_button.class_or_assoc == null) {
				if(selected_button.getText().equals("Delete")) {
					deleteAt(arg0);
				}
				else
					return;
			}
			else if(selected_button != null && selected_button.class_or_assoc.equals("Class"))
				handleClassPlacement(arg0, selected_button);
			else if(selected_button != null && selected_button.class_or_assoc.equals("Association"))
				handleAssociationPlacement(arg0, selected_button);
		}
		catch(Exception e) {
			return;
		}
	}

	private void deleteAt(MouseEvent arg0) {
		System.out.println("gonna click");
		Iterator<Map.Entry<Integer, Point>> it = panel.rectangles.entrySet().iterator();
		ClassInstance instance = null;
		Point click_point = arg0.getPoint();
		while(it.hasNext()) {
			Map.Entry<Integer, Point> entry = it.next();
			Point p = entry.getValue();
			ClassInstance c = model_instance.instanced_classes.get(entry.getKey());
			
			if(click_point.x >= (p.x-5) && click_point.x <= (p.x + 195) &&
					click_point.y >= (p.y-5) && 
					click_point.y <= (p.y+25+(c.attributes.size()+c.enums.size())*30)) {
				instance = c;
			}
		}
		
		ArrayList<AssociationInstance> assocs = model_instance.getAllAssocsRelatedTo(instance.id);
		
		for(int i = 0; i < assocs.size(); ++i) {
			panel.lines.remove(assocs.get(i).id);
			model_instance.instanced_assocs.remove(assocs.get(i));
		}
		
		ArrayList<JComponent> associated_components = class_components.get(instance.id);
		
		for(int i = 0; i < associated_components.size(); ++i)
			panel.remove(associated_components.get(i));
		
		class_components.remove(instance.id);
		
		panel.rectangles.remove(instance.id);
		model_instance.instanced_classes.remove(instance.id);
		panel.revalidate();
		panel.repaint();
	}

	private void handleClassPlacement(MouseEvent arg0, UMLButton selected_button) {
		
		last_connector_id = "";
		last_clicked_class = null;
		ArrayList<JComponent> associated_components = new ArrayList<JComponent>();
		
		JLabel lab = new JLabel(selected_button.getText() + ": ");
		
		ClassInstance c = new ClassInstance(EntityMeta.all_classes.get(selected_button.id));
		try {
			restrictions.checkClassAddRestrictionMeta(c, model_instance);
		}
		catch(RestrictionException e) {
			error.setText(e.getMessage());
			error.setLocation(10, 10);
			error.setSize(error.getPreferredSize());
			return;
		}
		panel.rectangles.put(c.id, arg0.getPoint());
		associated_components.add(lab);
		
		model_instance.instanced_classes.put(c.id, c);
		
		panel.add(lab);
		lab.setLocation(arg0.getPoint());
		lab.setSize(lab.getPreferredSize());
		
		JTextField instance_name = new JTextField(c.instance_name);
		panel.add(instance_name);
		instance_name.setLocation(new Point(arg0.getPoint().x + lab.getSize().width, arg0.getPoint().y));
		instance_name.setSize(200-lab.getWidth()-5, instance_name.getPreferredSize().height);
		instance_name.setBorder(null);
		instance_name.addFocusListener(new JTextFieldListener(instance_name, c));
		associated_components.add(instance_name);
		
		for(int i = 0; i < c.attributes.size(); ++i) {
			JLabel attr_lab = new JLabel(c.attributes.get(i).meta.name + ": ");
			panel.add(attr_lab);
			
			attr_lab.setLocation(new Point(arg0.getPoint().x, arg0.getPoint().y+35+5*i));
			attr_lab.setSize(attr_lab.getPreferredSize());
			
			attr_lab.setToolTipText(c.attributes.get(i).meta.type);
			associated_components.add(attr_lab);
			
			JTextField attr_instance_name = new JTextField(c.attributes.get(i).value);
			panel.add(attr_instance_name);
			associated_components.add(attr_instance_name);
			attr_instance_name.setLocation(new Point(arg0.getPoint().x + attr_lab.getSize().width, arg0.getPoint().y+35+20*i));
			attr_instance_name.setSize(200-attr_lab.getWidth()-5, attr_instance_name.getPreferredSize().height);
			attr_instance_name.setBorder(null);
			attr_instance_name.setToolTipText(c.attributes.get(i).meta.type);
			attr_instance_name.addFocusListener(new JTextFieldListener(attr_instance_name, c.attributes.get(i)));
		}
		
		for(int i = 0; i < c.enums.size(); ++i) {
			EnumInstance enum_i = c.enums.get(i);
			JLabel enum_lab = new JLabel(enum_i.meta.name + ": ");
			panel.add(enum_lab);
			associated_components.add(enum_lab);
			enum_lab.setLocation(new Point(arg0.getPoint().x, arg0.getPoint().y+35+(c.attributes.size()+i)*20));
			enum_lab.setSize(enum_lab.getPreferredSize());
			
			String[] values = new String[enum_i.meta.possible_values.size()];
			
			for(int j = 0; j < values.length; ++j)
				values[j] = enum_i.meta.possible_values.get(j);
			
			
			JComboBox<String> enum_values = new JComboBox<String>(values);
			enum_values.addActionListener(new EnumComboBoxListener(enum_i));
			panel.add(enum_values);
			associated_components.add(enum_values);
			enum_values.setLocation(new Point(arg0.getPoint().x+enum_lab.getSize().width, enum_lab.getLocation().y));
			enum_values.setSize(enum_lab.getPreferredSize());
		}
		
		class_components.put(c.id, associated_components);
		panel.revalidate();
		panel.repaint();
	}

	private void handleAssociationPlacement(MouseEvent arg0,
			UMLButton selected_button) {
		
		Iterator<Map.Entry<Integer, Point>> it = panel.rectangles.entrySet().iterator();
		ClassInstance instance = null;
		Point click_point = arg0.getPoint();
		while(it.hasNext()) {
			Map.Entry<Integer, Point> entry = it.next();
			Point p = entry.getValue();
			ClassInstance c = model_instance.instanced_classes.get(entry.getKey());
			
			if(click_point.x >= (p.x-5) && click_point.x <= (p.x + 195) &&
					click_point.y >= (p.y-5) && click_point.y <= (p.y+25+(c.attributes.size()+c.enums.size())*30)) {
				instance = c;
			}
		}
		if(state == ConnectorState.FIRST_CLICK || !last_connector_id.equals(selected_button.id)) {
			last_connector_id = selected_button.id;
			if(instance != null) {
				if(checkIfCanConnect(selected_button, instance)) {
					state = ConnectorState.SECOND_CLICK;
					last_clicked_class = instance;
					first_click_point = click_point;
				}
			}
		}
		else{
			if(instance != null) {
				if(checkIfCanConnect(selected_button, instance)) {
					
					AssociationInstance assoc = new AssociationInstance();
					AssociationMeta a_meta = EntityMeta.all_associations.get(selected_button.id);
					try {
						if(instance.meta.isOrInheritsFrom(a_meta.source.target_id)) {
							restrictions.checkAssocAddRestrictionMeta(instance, last_clicked_class, model_instance);
						}
						else {
							restrictions.checkAssocAddRestrictionMeta(last_clicked_class, instance, model_instance);
						}
					}
					catch(RestrictionException e) {
						error.setText(e.getMessage());
						error.setLocation(10, 10);
						error.setSize(error.getPreferredSize());
						return;
					}
					assoc.assoc_type = EntityMeta.all_associations.get(selected_button.id);
					assoc.source_id = last_clicked_class.id;
					assoc.target_id = instance.id;
					last_clicked_class.assocs.add(assoc);
					instance.assocs.add(assoc);
					model_instance.instanced_assocs.put(assoc.id, assoc);
					
					if(instance.meta.isOrInheritsFrom(a_meta.source.target_id)) {
						panel.lines.put(assoc.id, new Point[]{click_point, first_click_point});
						assoc.source_id = instance.id;
						assoc.target_id = last_clicked_class.id;
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
		}
	}

	private boolean checkIfCanConnect(UMLButton selected_button,
			ClassInstance instance) {
		AssociationMeta assoc = EntityMeta.all_associations.get(selected_button.id);
		return (checkValidClasses(assoc, instance) && checkMultiplicity(assoc, instance));
	}

	private boolean checkValidClasses(AssociationMeta assoc,
			ClassInstance instance) {
		
		if(last_clicked_class == null)
			return instance.meta.isOrInheritsFrom(assoc.source.target_id) || 
					instance.meta.isOrInheritsFrom(assoc.target.target_id);
		
		
		if(last_clicked_class.meta.isOrInheritsFrom(assoc.source.target_id))
			return instance.meta.isOrInheritsFrom(assoc.target.target_id);
		else
			return instance.meta.isOrInheritsFrom(assoc.source.target_id);
	}

	private boolean checkMultiplicity(AssociationMeta assoc,
			ClassInstance instance) {
		String multiplicity_other_end;
		if(instance.meta.id.equals(assoc.source.target_id))
			multiplicity_other_end = assoc.target.multiplicity;
		else
			multiplicity_other_end = assoc.source.multiplicity;
		
		if(multiplicity_other_end.endsWith("*"))
			return true;
		else {
			try {
				int mult = Integer.parseInt(multiplicity_other_end);
				return verify_number_assocs(instance, mult, assoc);
			}
			catch(Exception e) {
				String[] regex_result = multiplicity_other_end.split("\\.\\.");
				int mult = Integer.parseInt(regex_result[regex_result.length-1]);
				return verify_number_assocs(instance, mult, assoc);
			}
		}
	}
	

	private boolean verify_number_assocs(ClassInstance instance, int mult, AssociationMeta assoc) {
		int ocurrences = 0;
		for(int i = 0; i < instance.assocs.size(); ++i) {
			if(instance.assocs.get(i).assoc_type.equals(assoc)) {
				++ocurrences;
				if(ocurrences >= mult)
					return false;
			}
				
		}
			
		return true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public WorkAreaListener(WorkAreaPanel j, ButtonGroup buttons) {
		panel = j;
		tool_buttons = buttons;
		panel.add(error);
	}
	
	public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

}
