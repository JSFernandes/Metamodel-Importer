package subinstance;

import java.util.Hashtable;

public class ModelSubInstance {
	public Hashtable<Integer, ClassSubInstance>       classes = new Hashtable<Integer, ClassSubInstance>();
	public Hashtable<Integer, AssociationSubInstance> assocs  = new Hashtable<Integer, AssociationSubInstance>();
}
