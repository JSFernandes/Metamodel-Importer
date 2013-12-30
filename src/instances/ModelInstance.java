package instances;

import java.util.Hashtable;

public class ModelInstance {
	public Hashtable<Integer, ClassInstance> instanced_classes = new Hashtable<Integer, ClassInstance>();
	public Hashtable<Integer, AssociationInstance>  instanced_assocs = new Hashtable<Integer, AssociationInstance>();
	
	static ModelInstance instance;
	
	public static ModelInstance getInstance() {
		if(instance == null)
			instance = new ModelInstance();
		
		return instance;
	}
}
