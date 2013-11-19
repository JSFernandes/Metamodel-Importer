package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WorkAreaListener implements MouseListener {
	
	JPanel panel;
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
		String selected_button_name = getSelectedButtonText(tool_buttons);
		JLabel lab = new JLabel(selected_button_name);
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
	
	public WorkAreaListener(JPanel j, ButtonGroup buttons) {
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

}
