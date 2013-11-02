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
}
