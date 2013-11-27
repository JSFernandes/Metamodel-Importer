package instances;

public class Instance {
	
	public static int next_id = 0;
	
	public int id;
	
	public Instance() {
		id = next_id;
		++next_id;
	}
}
