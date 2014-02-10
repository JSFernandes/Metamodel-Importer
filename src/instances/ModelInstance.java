package instances;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class ModelInstance {
	public Hashtable<Integer, ClassInstance> instanced_classes = new Hashtable<Integer, ClassInstance>();
	public Hashtable<Integer, AssociationInstance>  instanced_assocs = new Hashtable<Integer, AssociationInstance>();
	
	static ModelInstance instance;
	
	public static ModelInstance getInstance() {
		if(instance == null)
			instance = new ModelInstance();
		
		return instance;
	}
	
	public ArrayList<AssociationInstance> getAllAssocsRelatedTo(int id) {
		ArrayList<AssociationInstance> found_instances = new ArrayList<AssociationInstance>();
		
		Iterator<Map.Entry<Integer, AssociationInstance>> it = instanced_assocs.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<Integer, AssociationInstance> entry = it.next();
			if(entry.getValue().source_id == id || entry.getValue().target_id == id)
				found_instances.add(entry.getValue());
		}
		
		return found_instances;
	}
}
