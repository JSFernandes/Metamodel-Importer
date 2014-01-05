package instances;

import java.util.ArrayList;

import data.AttributeMeta;
import data.ClassMeta;
import data.EnumMeta;

public class ClassInstance extends Instance {
	public ClassMeta meta;
	public String instance_name = "name";
	public ArrayList<AssociationInstance> assocs = new ArrayList<AssociationInstance>();
	public ArrayList<AttributeInstance> attributes = new ArrayList<AttributeInstance>();
	public ArrayList<EnumInstance> enums = new ArrayList<EnumInstance>();
	
	public ClassInstance(ClassMeta m) {
		meta = m;
		populateAttributes();
		populateEnums();
	}
	
	public void populateAttributes() {
		ArrayList<AttributeMeta> metas = meta.attributes;
		
		for(int i = 0; i < metas.size(); ++i) {
			AttributeInstance attr_instance = new AttributeInstance();
			attr_instance.meta = metas.get(i);
			attr_instance.value = "null";
			attributes.add(attr_instance);
		}
	}
	
	public void populateEnums() {
		ArrayList<EnumMeta> enumerations = meta.enumerations;
		
		for(int i = 0; i < enumerations.size(); ++i) {
			EnumInstance e = new EnumInstance();
			e.meta = enumerations.get(i);
			enums.add(e);
		}
	}
}
