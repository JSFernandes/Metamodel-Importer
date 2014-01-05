package data;

import java.util.ArrayList;

public class ClassMeta extends EntityMeta {
	public boolean is_abstract;
	public ArrayList<ClassMeta> parents = new ArrayList<ClassMeta>();
	public ArrayList<AttributeMeta> attributes = new ArrayList<AttributeMeta>();
	public ArrayList<EnumMeta> enumerations = new ArrayList<EnumMeta>();
	public String name;
	public ClassMeta instance_of;
	public boolean is_attribute = false;
	public boolean is_enum = false;
	public boolean is_enum_value = false;
	
	boolean setAs(String to_search) {
		if(parents.size() == 0) {
			if(name.equals(to_search))
				return true;
			
			ClassMeta c = EntityMeta.getDependencyTo(id);
			if(c == null || !c.name.equals(to_search))
				return false;
			else {
				return true;
			}
		}
		else {
			for(int i = 0; i < parents.size(); ++i) {
				if(parents.get(i).setAs(to_search))
					return true;
			}
			return false;
		}
	}
	
	public void setAttribute() {
		is_attribute = setAs("Attribute");
		is_enum = setAs("EnumerationType");
		is_enum_value = setAs("EnumerationLiteral");
	}
	
	public boolean isCreatable() {
		return (!is_abstract && !is_attribute && !name.equals("Class") && !name.equals("Attribute")
				&& !is_enum && !is_enum_value && !name.equals("EnumerationType") 
				&& !name.equals("EnumerationLiteral"));
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
