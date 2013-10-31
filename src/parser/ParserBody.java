package parser;

import java.io.FileInputStream;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;

public class ParserBody {
	
	FileInputStream file;
	Document doc;
	Element root;
	
	public ParserBody(String filename) {
		try {
			file = new FileInputStream(filename);
			Builder builder = new Builder();
			doc = builder.build(file);
			root = doc.getRootElement();
		}
		catch(Exception e) {
			System.out.println("Something went wrong");
		}
	}
	
	public void generateData() {
		// Find the child called models
		// For each child called model
			// Find classes and store them
		
		// Go back to root, find association container
		// Associate everything
		// Rework the data depending on wether the classes are really classes or attributes
	}
}
