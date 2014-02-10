package subinstance;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class ModelSubInstance {
	public Hashtable<Integer, ClassSubInstance>       classes = new Hashtable<Integer, ClassSubInstance>();
	public Hashtable<Integer, AssociationSubInstance> assocs  = new Hashtable<Integer, AssociationSubInstance>();
	
	
	public ArrayList<AssociationSubInstance> getAllAssocsRelatedTo(int id) {
		ArrayList<AssociationSubInstance> found_instances = new ArrayList<AssociationSubInstance>();
		
		Iterator<Map.Entry<Integer, AssociationSubInstance>> it = assocs.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<Integer, AssociationSubInstance> entry = it.next();
			if(entry.getValue().source.id == id || entry.getValue().target.id == id)
				found_instances.add(entry.getValue());
		}
		
		return found_instances;
	}
}
