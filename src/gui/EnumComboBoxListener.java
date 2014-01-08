package gui;

import instances.EnumInstance;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class EnumComboBoxListener implements ActionListener {
	
	public EnumInstance enum_i;

	public void actionPerformed(ActionEvent e) {
		 JComboBox<String> cb = (JComboBox<String>)e.getSource();
	     String enum_val = (String) cb.getSelectedItem();
	     enum_i.chosen_value = enum_val;
	 }
	
	public EnumComboBoxListener(EnumInstance instance) {
		enum_i = instance;
	}

}
