package subinstance;

import java.util.ArrayList;

import instances.ClassInstance;

public class ClassSubInstance extends SubInstance {
	public ClassInstance meta;
	public ArrayList<AssociationSubInstance> assocs = new ArrayList<AssociationSubInstance>();
	
	public ClassSubInstance(ClassInstance m) {
		meta = m;
	}
}
