package data;

import java.util.ArrayList;

public class ClassMeta extends EntityMeta {
	public boolean is_abstract;
	public ClassMeta parent;
	public ArrayList<AttributeMeta> attributes = new ArrayList<AttributeMeta>();
	public String name;
	public ClassMeta instance_of;
	public boolean is_attribute = false;
	
	boolean set_as_attribute() {
		if(parent == null) {
			if(name.equals("Attribute"))
				return true;
			
			ClassMeta c = EntityMeta.getDependencyTo(id);
			if(c == null || !c.name.equals("Attribute"))
				return false;
			else
				return true;
		}
		else {
			return parent.set_as_attribute();
		}
	}
	
	public void set_attribute() {
		is_attribute = set_as_attribute();
	}
	
	public boolean is_creatable() {
		return (!is_abstract && !is_attribute && !name.equals("Class") && !name.equals("Attribute"));
	}

	public void implementFather() {
		
		if(parent == null)
			return;
		else {
			if(parent.parent != null)
				parent.implementFather();
			
			attributes.addAll(parent.attributes);
		}
	}
	
	public static ArrayList<ClassMeta> getCreatableClasses() {
		ArrayList<ClassMeta> instantiable = new ArrayList<ClassMeta>();
		
		for(ClassMeta c : EntityMeta.all_classes.values()) {
			if(c.is_creatable())
				instantiable.add(c);
		}
		
		return instantiable;
	}
}
