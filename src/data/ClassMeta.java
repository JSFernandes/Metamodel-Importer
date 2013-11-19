package data;

import java.util.ArrayList;

public class ClassMeta extends EntityMeta {
	public boolean is_abstract;
	public ClassMeta parent;
	public ArrayList<AttributeMeta> attributes = new ArrayList<AttributeMeta>();
	public String name;
	public ClassMeta instance_of;
	public boolean is_attribute = false;
	
	boolean setAsAttribute() {
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
			return parent.setAsAttribute();
		}
	}
	
	public void setAttribute() {
		is_attribute = setAsAttribute();
	}
	
	public boolean isCreatable() {
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
			if(c.isCreatable())
				instantiable.add(c);
		}
		
		return instantiable;
	}
	
	public boolean isOrInheritsFrom(String parent_id) {
		if(id.equals(parent_id))
			return true;
		else if(parent != null)
			return parent.isOrInheritsFrom(parent_id);
		else
			return false;
	}
}
