package data;

import java.util.ArrayList;

public class AssociationMeta extends EntityMeta {
	public AssociationEndMeta source;
	public AssociationEndMeta target;
	public String name;
	public boolean is_creatable = true;
	
	public static ArrayList<AssociationMeta> getAllCreatableAssocs() {
		ArrayList<AssociationMeta> assocs = new ArrayList<AssociationMeta>();
		
		for(AssociationMeta assoc : EntityMeta.all_associations.values()) {
			if(assoc.is_creatable)
				assocs.add(assoc);
		}
		
		return assocs;
	}
}
