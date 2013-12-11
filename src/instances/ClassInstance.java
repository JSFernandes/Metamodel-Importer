package instances;

import java.util.ArrayList;

import data.ClassMeta;

public class ClassInstance extends Instance {
	public ClassMeta meta;
	public String instance_name;
	public ArrayList<AssociationInstance> assocs = new ArrayList<AssociationInstance>();
}
