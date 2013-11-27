package gui;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

public class UMLButton extends JRadioButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7181758211606346428L;
	public String id;
	public String class_or_assoc = new String();
	
	public UMLButton(String name, ImageIcon assoc_icon, String ident, String c_or_a) {
		// TODO Auto-generated constructor stub
		super(name, assoc_icon);
		id = ident;
		class_or_assoc = c_or_a;
	}
}
