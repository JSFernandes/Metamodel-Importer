package data;

import java.util.ArrayList;

public class ClassMeta extends EntityMeta {
	public boolean is_abstract;
	public ArrayList<ClassMeta> parents = new ArrayList<ClassMeta>();
	public ArrayList<AttributeMeta> attributes = new ArrayList<AttributeMeta>();
	public String name;
	public ClassMeta instance_of;
	public boolean is_attribute = false;
	
	boolean setAsAttribute() {
		if(parents.size() == 0) {
			if(name.equals("Attribute"))
				return true;
			
			ClassMeta c = EntityMeta.getDependencyTo(id);
			if(c == null || !c.name.equals("Attribute"))
				return false;
			else
				return true;
		}
		else {
			for(int i = 0; i < parents.size(); ++i) {
				if(parents.get(i).setAsAttribute())
					return true;
			}
			return false;
		}
	}
	
	public void setAttribute() {
		is_attribute = setAsAttribute();
	}
	
	public boolean isCreatable() {
		return (!is_abstract && !is_attribute && !name.equals("Class") && !name.equals("Attribute"));
	}

	public void implementFather() {
		
		if(parents.size() == 0)
			return;
		else {
			for(int i = 0; i < parents.size(); ++i) {
				if(parents.get(i) != null)
					parents.get(i).implementFather();
			}
			
			for(int i = 0; i < parents.size(); ++i)
				attributes.addAll(parents.get(i).attributes);
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
		else if(parents.size() != 0) {
			for(int i = 0; i < parents.size(); ++i) {
				if(parents.get(i).isOrInheritsFrom(parent_id))
					return true;
			}
			return false;
		}
		else
			return false;
	}
}
