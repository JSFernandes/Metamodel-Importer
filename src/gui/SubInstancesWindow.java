package gui;

import instances.ClassInstance;
import instances.ModelInstance;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import subinstance.ModelSubInstance;

public class SubInstancesWindow {
	ButtonGroup tool_buttons = new ButtonGroup();
	ImageIcon class_icon;
	ImageIcon assoc_icon;
	
	public JFrame frame;
	InstancesWorkAreaListener listener;
	
	public SubInstancesWindow() {
		initialize();
	}
	
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		class_icon = new ImageIcon("class_icon.jpg", "Class");
		assoc_icon = new ImageIcon("assoc_icon.jpg", "Association");
		
		JPanel vertical = new JPanel(new GridLayout(30, 1));
		frame.getContentPane().add(vertical, BorderLayout.EAST);
		
		JScrollPane scrollVertical = new JScrollPane();
		vertical.add(scrollVertical);
		
		setTools(vertical);
		
		ModelSubInstance model = new ModelSubInstance();
		InstancesWorkAreaPanel working_area = new InstancesWorkAreaPanel(model);
		working_area.setLayout(null);
		frame.getContentPane().add(working_area, BorderLayout.CENTER);
		listener = new InstancesWorkAreaListener(working_area, tool_buttons);
		working_area.addMouseListener(listener);
		
		working_area.setBackground(Color.WHITE);
	}

	private void setTools(JPanel toolbox) {
		for(ClassInstance c: ModelInstance.getInstance().instanced_classes.values()) {
			UMLButton b = new UMLButton(c.meta.name + ":" + c.instance_name, class_icon, String.valueOf(c.id), "Class");
			tool_buttons.add(b);
			toolbox.add(b);
		}
		UMLButton b = new UMLButton("Connector", assoc_icon, null, "Association");
		tool_buttons.add(b);
		toolbox.add(b);
		UMLButton deleter = new UMLButton("Delete", new ImageIcon("delete_icon.png"), null, null);
		tool_buttons.add(deleter);
		toolbox.add(deleter);
		UMLButton no_choice = new UMLButton("No action", new ImageIcon("empty_icon.png"), null, null);
		tool_buttons.add(no_choice);
		toolbox.add(no_choice);
		
		Button b2 = new Button("Verify valid");
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.verifyValidity();
		}});
		toolbox.add(b2);
	}
}
