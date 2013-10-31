package data;

import java.util.ArrayList;

public class ClassMeta extends EntityMeta {
	public boolean is_abstract;
	public ClassMeta parent;
	public ArrayList<AttributeMeta[]> attributes;
	public String name;
	public ClassMeta instance_of;
	
	public boolean is_attribute() {
		return (instance_of.name == "Attribute");
	}
}
