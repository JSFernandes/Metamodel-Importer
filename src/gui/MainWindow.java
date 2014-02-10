package gui;

import instances.ModelInstance;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import parser.ParserBody;
import restrictions.FeatureModelRestrictions;
import restrictions.RestrictionException;
import restrictions.Restrictions;
import data.AssociationMeta;
import data.ClassMeta;

public class MainWindow {

	ButtonGroup tool_buttons = new ButtonGroup();
	ImageIcon class_icon;
	ImageIcon assoc_icon;
	
	private JFrame frame;
	WorkAreaPanel work_panel;
	WorkAreaListener work_listener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ParserBody p = new ParserBody("project3.xml");
		p.generateData();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		
		WorkAreaPanel working_area = new WorkAreaPanel();
		work_panel = working_area;
		working_area.setLayout(null);
		frame.getContentPane().add(working_area, BorderLayout.CENTER);
		work_listener = new WorkAreaListener(working_area, tool_buttons);
		working_area.addMouseListener(work_listener);
		
		working_area.setBackground(Color.WHITE);
	}
	
	private void setTools(JPanel toolbox) {
		
		ArrayList<ClassMeta> classes = ClassMeta.getCreatableClasses();
		ArrayList<AssociationMeta> assocs = AssociationMeta.getAllCreatableAssocs();
		
		for(int i = 0; i < classes.size(); ++i) {
			UMLButton b = new UMLButton(classes.get(i).name, class_icon, classes.get(i).id, "Class");
			tool_buttons.add(b);
			toolbox.add(b);
		}
		
		for(int i = 0; i < assocs.size(); ++i) {
			UMLButton b = new UMLButton(assocs.get(i).name, assoc_icon, assocs.get(i).id, "Association");
			tool_buttons.add(b);
			toolbox.add(b);
		}
		UMLButton deleter = new UMLButton("Delete", new ImageIcon("delete_icon.png"), null, null);
		tool_buttons.add(deleter);
		toolbox.add(deleter);
		UMLButton no_choice = new UMLButton("No action", new ImageIcon("empty_icon.png"), null, null);
		tool_buttons.add(no_choice);
		toolbox.add(no_choice);
		
		Button b = new Button("Generate instances");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setUpForInstantiation();
		}});
		toolbox.add(b);
	}

	protected void setUpForInstantiation() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModelInstance i = ModelInstance.getInstance();
					Restrictions rest = new FeatureModelRestrictions();
					rest.checkInstantiationRestriction(i);
					SubInstancesWindow window = new SubInstancesWindow();
					window.frame.setVisible(true);
				}
				catch(RestrictionException e) {
					work_listener.error.setText(e.getMessage());
					work_listener.error.setLocation(10, 10);
					work_listener.error.setSize(work_listener.error.getPreferredSize());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
