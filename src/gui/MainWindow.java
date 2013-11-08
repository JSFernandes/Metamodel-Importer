package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import parser.ParserBody;
import data.ClassMeta;

public class MainWindow {

	ButtonGroup tool_buttons;
	
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
		
		
		JPanel vertical = new JPanel();
		frame.getContentPane().add(vertical, BorderLayout.EAST);
		
		JScrollPane scrollVertical = new JScrollPane();
		vertical.add(scrollVertical);
		
		setTools(vertical);
		
		JPanel workingArea = new JPanel();
		frame.getContentPane().add(workingArea, BorderLayout.CENTER);
		
		workingArea.setBackground(Color.WHITE);
	}

	
	private void setTools(JPanel toolbox) {
		tool_buttons = new ButtonGroup();
		
		ArrayList<ClassMeta> classes = ClassMeta.getCreatableClasses();
		
		for(int i = 0; i < classes.size(); ++i) {
			JRadioButton b = new JRadioButton(classes.get(i).name);
			tool_buttons.add(b);
			toolbox.add(b);
		}
	}
}
