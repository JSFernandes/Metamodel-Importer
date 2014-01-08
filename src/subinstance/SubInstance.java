package subinstance;

public abstract class SubInstance {
public static int next_id = 0;
	
	public int id;
	
	public SubInstance() {
		id = next_id;
		++next_id;
	}
}
