package instances;

import java.util.Hashtable;

public class Instance {
	
	public static int next_id = 0;
	
	public int id;
	
	public Instance() {
		id = next_id;
		++next_id;
	}
	
	public static Hashtable<Integer, ClassInstance> instanced_classes = new Hashtable<Integer, ClassInstance>();
}
