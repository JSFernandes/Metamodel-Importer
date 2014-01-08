package gui;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

public class ButtonFinder {
	
	public static UMLButton getSelectedButton(ButtonGroup button_group) {
		for (Enumeration<AbstractButton> buttons = button_group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return (UMLButton) button;
            }
        }
		return null;
	}
}
