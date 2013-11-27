package gui;

import instances.Instance;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WorkAreaListener implements MouseListener {
	
	WorkAreaPanel panel;
	ButtonGroup tool_buttons;
	
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
		//String selected_button_name = getSelectedButtonText(tool_buttons);
		//JLabel lab = new JLabel(selected_button_name);
		//panel.add(lab);
		//lab.setLocation(arg0.getPoint());
		//lab.setSize(lab.getPreferredSize());
		
		//panel.revalidate();
		//panel.repaint();
		
		UMLButton selected_button = getSelectedButton(tool_buttons);
		
		if(selected_button != null && selected_button.class_or_assoc.equals("Class"))
			handleClassPlacement(arg0, selected_button);
		else if(selected_button != null && selected_button.class_or_assoc.equals("Association"))
			handleAssociationPlacement(arg0, selected_button);
	}

	private void handleClassPlacement(MouseEvent arg0, UMLButton selected_button) {
		// TODO Auto-generated method stub
		JLabel lab = new JLabel(selected_button.getText());
		//g.drawRect(arg0.getPoint().x - 5, arg0.getPoint().y - 5, 
		//		150, 20);
		
		Instance i = new Instance();
		panel.rectangles.put(i.id, arg0.getPoint());
		
		panel.add(lab);
		lab.setLocation(arg0.getPoint());
		lab.setSize(lab.getPreferredSize());
		
		panel.revalidate();
		panel.repaint();
	}

	private void handleAssociationPlacement(MouseEvent arg0,
			UMLButton selected_button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public WorkAreaListener(WorkAreaPanel j, ButtonGroup buttons) {
		panel = j;
		tool_buttons = buttons;
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
	
	public UMLButton getSelectedButton(ButtonGroup button_group) {
		for (Enumeration<AbstractButton> buttons = button_group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return (UMLButton) button;
            }
        }
		return null;
	}

}
