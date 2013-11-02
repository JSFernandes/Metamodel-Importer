package data;

import java.util.Hashtable;

public abstract class EntityMeta {
	
	public static Hashtable<String, EntityMeta>         all_entities         = new Hashtable<String, EntityMeta>();
	public static Hashtable<String, ClassMeta>          all_classes          = new Hashtable<String, ClassMeta>();
	public static Hashtable<String, AssociationMeta>    all_associations     = new Hashtable<String, AssociationMeta>();
	public static Hashtable<String, AssociationEndMeta> all_association_ends = new Hashtable<String, AssociationEndMeta>();
	public static Hashtable<String, GeneralizationMeta> all_generalizations  = new Hashtable<String, GeneralizationMeta>();
	public static Hashtable<String, ConstraintMeta>     all_constraints      = new Hashtable<String, ConstraintMeta>();
	public static Hashtable<String, DependencyMeta>     all_dependencies     = new Hashtable<String, DependencyMeta>();
	
	
    public static ClassMeta getDependencyTo(String from_id) {
    	for(DependencyMeta d : all_dependencies.values()) {
    		if(d.from.equals(from_id))
    			return all_classes.get(d.to);
    	}
    	return null;
    }
	
	
	public String id;
}