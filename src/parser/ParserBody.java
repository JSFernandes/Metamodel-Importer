package parser;

import java.io.FileInputStream;

import data.AssociationEndMeta;
import data.AssociationMeta;
import data.AttributeMeta;
import data.ClassMeta;
import data.ConstraintMeta;
import data.DependencyMeta;
import data.EntityMeta;
import data.EnumMeta;
import data.GeneralizationMeta;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;


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
		
		firstPassingNode(root);
		constructInheritance();
		setClassesAsAttributesOrEnums();
		implementInheritance();
		
		
		System.out.println("NumAssocs: " + EntityMeta.all_associations.size());
		System.out.println("NumCLasses: " + EntityMeta.all_classes.size());
		System.out.println("NumDependencies: " + EntityMeta.all_dependencies.size());
		// Go back to root, find association container
		// Associate everything
		// Rework the data depending on wether the classes are really classes or attributes
	}
	
	public void handleClass(Element e) {
		
		String id = e.getAttributeValue("Id");
		
		if(id == null || EntityMeta.all_entities.get(id) != null)
			return;
		
		ClassMeta c = new ClassMeta();
		
		c.id          = id;
		c.name        = e.getAttributeValue("Name");
		c.is_abstract = Boolean.parseBoolean(e.getAttributeValue("Abstract"));
		
		EntityMeta.all_entities.put(c.id, c);
		EntityMeta.all_classes.put(c.id, c);
		
		Element tmp = e.getFirstChildElement("ModelChildren");
		if(tmp != null) {
			Elements attributes = tmp.getChildElements("Attribute");
		
			for(int i = 0; i < attributes.size(); ++i)
				handleAttribute(attributes.get(i), c);
		}
	}
	
	public AssociationEndMeta handleAssociationEnd(Element e, AssociationMeta a, String from_or_to) {
		
		Element tmp = e.getFirstChildElement(from_or_to);
		
		AssociationEndMeta result = new AssociationEndMeta();
		tmp = tmp.getFirstChildElement("AssociationEnd");
		
		result.id           = tmp.getAttributeValue("Id");
		result.name         = tmp.getAttributeValue("Name");
		result.multiplicity = tmp.getAttributeValue("Multiplicity");
		result.assoc_id     = a.id;
		
		tmp = tmp.getFirstChildElement("Type");
		tmp = tmp.getFirstChildElement("Class");
		
		result.target_id = tmp.getAttributeValue("Idref");
		
		
		return result;
	}
	
	public void handleAssociation(Element e) {
		AssociationMeta a = new AssociationMeta();
		a.id = e.getAttributeValue("Id");
		a.name = e.getAttributeValue("Name");
		
		if(a.id == null)
			return;
		
		AssociationEndMeta src  = handleAssociationEnd(e, a, StringDefinitions.FROM_END_STRING);
		AssociationEndMeta trgt = handleAssociationEnd(e, a, StringDefinitions.TO_END_STRING);
		
		a.source = src;
		a.target = trgt;
		
		EntityMeta.all_entities.put(a.id, a);
		EntityMeta.all_entities.put(src.id, src);
		EntityMeta.all_entities.put(trgt.id, trgt);
		
		EntityMeta.all_associations.put(a.id, a);
		
		EntityMeta.all_association_ends.put(src.id, src);
		EntityMeta.all_association_ends.put(trgt.id, trgt);
	}
	
	public void handleDependency(Element e) {
		if(e.getAttributeValue("Id") == null)
			return;
		
		DependencyMeta d = new DependencyMeta();
		
		d.id   = e.getAttributeValue("Id");
		d.name = e.getAttributeValue("Name");
		d.from = e.getAttributeValue("From");
		d.to   = e.getAttributeValue("To");
		
		EntityMeta.all_entities.put(d.id, d);
		EntityMeta.all_dependencies.put(d.id, d);
	}
	
	public void handleConstraint(Element e) {
		
		ConstraintMeta c = new ConstraintMeta();
		
		c.id         = e.getAttributeValue("Id");
		c.name       = e.getAttributeValue("Name");
		c.target_id  = e.getAttributeValue("From");
		c.expression = e.getAttributeValue("Expression");
		
		EntityMeta.all_entities.put(c.id, c);
		EntityMeta.all_constraints.put(c.id, c);
	}
	
	public void handleAttribute(Element e, ClassMeta c) {
		AttributeMeta attr = new AttributeMeta();
		
		attr.id = e.getAttributeValue("Id");
		attr.name = e.getAttributeValue("Name");
		
		Element tmp = e.getFirstChildElement("Type");
		attr.type = tmp.getFirstChildElement("DataType").getAttributeValue("Name");
		
		attr.default_value = e.getAttributeValue("InitialValue");
		
		EntityMeta.all_entities.put(attr.id, attr);
		
		c.attributes.add(attr);
	}
	
	public void handleGeneralization(Element e) {
		GeneralizationMeta g = new GeneralizationMeta();
		
		if(e.getAttributeValue("Id") == null)
			return;
		
		g.id   = e.getAttributeValue("Id");
		g.from = e.getAttributeValue("From");
		g.to   = e.getAttributeValue("To");
		
		EntityMeta.all_entities.put(g.id, g);
		EntityMeta.all_generalizations.put(g.id, g);
	}
	
	public void firstPassingNode(Element e) {
		
		if(e.getLocalName().equals(StringDefinitions.DIAGRAMS_STRING)) {
			return;
		}
		else if(e.getLocalName().equals(StringDefinitions.CLASS_STRING)) {
			handleClass(e);
		}
		else if (e.getLocalName().equals(StringDefinitions.ASSOCIATION_STRING)) {
			handleAssociation(e);
		}
		else if (e.getLocalName().equals(StringDefinitions.DEPENDENCY_STRING)) {
			handleDependency(e);
		}
		else if (e.getLocalName().equals(StringDefinitions.CONSTRAINT_STRING) && 
				e.getAttribute("Expression") != null) {
			handleConstraint(e);
		}
		else if (e.getLocalName().equals(StringDefinitions.GENERALIZATION_STRING)) {
			handleGeneralization(e);
		}
		
		
		Elements children = e.getChildElements();
		for(int i = 0; i < children.size(); ++i)
			firstPassingNode(children.get(i));
	}
	
	public void constructInheritance() {
		
		for(GeneralizationMeta g : EntityMeta.all_generalizations.values()) {
			EntityMeta.all_classes.get(g.to).parents.add(EntityMeta.all_classes.get(g.from));
		}
	}

	public void implementInheritance() {
		for (ClassMeta c : EntityMeta.all_classes.values()) {
			c.implementFather();
		}
	}
	
	public void setClassesAsAttributesOrEnums() {
		for(ClassMeta c : EntityMeta.all_classes.values()) {
			c.setAttribute();
			System.out.println("Id: " + c.id + "Name: " + c.name + " Attr: " + c.is_attribute + " Enum: " + c.is_enum);
			if(c.is_attribute)
				handleClassThatIsAttribute(c);
			if(c.is_enum)
				handleClassThatIsEnum(c);
		}
		
		for(ClassMeta c : EntityMeta.all_classes.values()) {
			if(c.is_enum)
				addValuesToEnum(c);
		}
	}
	
	private void addValuesToEnum(ClassMeta c) {
		EnumMeta e = EntityMeta.all_enumerations.get(c.id);
		
		for(AssociationEndMeta ae : EntityMeta.all_association_ends.values()) {
			if(ae.target_id.equals(c.id)) {
				AssociationMeta assoc = EntityMeta.all_associations.get(ae.assoc_id);
				
				if(ae.equals(assoc.source)) {
					AssociationEndMeta other_end = assoc.target;
					
					ClassMeta real_class = EntityMeta.all_classes.get(other_end.target_id);
					e.possible_values.add(real_class.name);
				}
				assoc.is_creatable = false;
			}
		}
	}

	private void handleClassThatIsEnum(ClassMeta c) {
		for(AssociationEndMeta ae : EntityMeta.all_association_ends.values()) {
			if(ae.target_id.equals(c.id)) {
				AssociationMeta assoc = EntityMeta.all_associations.get(ae.assoc_id);
				
				if(ae.equals(assoc.target)) {
					AssociationEndMeta other_end = assoc.source;
					
					ClassMeta real_class = EntityMeta.all_classes.get(other_end.target_id);
					
					EnumMeta e = new EnumMeta();
					e.name = c.name;
					
					real_class.enumerations.add(e);
					
					EntityMeta.all_enumerations.put(c.id, e);
				}
				assoc.is_creatable = false;
			}
		}
	}

	public void handleClassThatIsAttribute(ClassMeta c) {
		
		System.out.println("Handling: " + c.name);
		for(AssociationEndMeta ae : EntityMeta.all_association_ends.values()) {
			if(ae.target_id.equals(c.id)) {
				System.out.println("found an ae");
				AssociationMeta assoc = EntityMeta.all_associations.get(ae.assoc_id);
				
				AssociationEndMeta other_end;
				if(ae.equals(assoc.target)) {
					other_end = assoc.source;
				}
				else
					other_end = assoc.target;
					
				ClassMeta real_class = EntityMeta.all_classes.get(other_end.target_id);
					
				AttributeMeta a = new AttributeMeta();
				a.id = c.id;
				a.name = c.name;
				a.owner_class = real_class.id;
				a.multiplicity = ae.multiplicity;
				real_class.attributes.add(a);
				System.out.println("attr added to: " + real_class.name);
				
				for(int i = 0; i < c.attributes.size(); ++i) {
					if(c.attributes.get(i).name.equals("type"))
						a.type = c.attributes.get(i).default_value;
				}
					
				assoc.is_creatable = false;
			}
		}
	}
	
	public static void main(String[] args) {
		//ParserBody pb = new ParserBody("project.xml");
	}
}
