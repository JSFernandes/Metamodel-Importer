package gui;

import instances.AttributeInstance;
import instances.ClassInstance;
import instances.Instance;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class JTextFieldListener implements FocusListener{
	
	JTextField text_field;
	Instance inst;

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		update();
	}
	
	public void update() {
		if(inst.getClass().equals(ClassInstance.class)) {
			ClassInstance c = (ClassInstance) inst;
			c.instance_name = text_field.getText();
		}
		else if (inst.getClass().equals(AttributeInstance.class)){
			AttributeInstance a = (AttributeInstance) inst;
			a.value = text_field.getText();
			System.out.println(a.value);
		}
	}
	
	public JTextFieldListener(JTextField field, Instance i) {
		text_field = field;
		inst = i;
	}

}
