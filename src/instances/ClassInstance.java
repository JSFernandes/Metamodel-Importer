package instances;

import java.util.ArrayList;

import data.AttributeMeta;
import data.ClassMeta;

public class ClassInstance extends Instance {
	public ClassMeta meta;
	public String instance_name = "name";
	public ArrayList<AssociationInstance> assocs = new ArrayList<AssociationInstance>();
	public ArrayList<AttributeInstance> attributes = new ArrayList<AttributeInstance>();
	
	public ClassInstance(ClassMeta m) {
		meta = m;
		populateAttributes();
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
}
