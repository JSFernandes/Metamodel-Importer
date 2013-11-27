package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import parser.ParserBody;
import data.AssociationMeta;
import data.ClassMeta;

public class MainWindow {

	ButtonGroup tool_buttons = new ButtonGroup();
	ImageIcon class_icon;
	ImageIcon assoc_icon;
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ParserBody p = new ParserBody("project.xml");
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
		working_area.setLayout(null);
		frame.getContentPane().add(working_area, BorderLayout.CENTER);
		working_area.addMouseListener(new WorkAreaListener(working_area, tool_buttons));
		
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
	}
}
