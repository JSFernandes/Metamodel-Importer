package restrictions;

import java.util.Iterator;
import java.util.Map;

import instances.ClassInstance;
import instances.ModelInstance;
import subinstance.AssociationSubInstance;
import subinstance.ClassSubInstance;
import subinstance.ModelSubInstance;

public class FeatureModelRestrictions extends Restrictions {

	@Override
	public void checkAllRestrictions(ModelSubInstance m)
			throws RestrictionException {
		ModelInstance meta = ModelInstance.getInstance();
		
		Iterator<Map.Entry<Integer, ClassInstance>> it = meta.instanced_classes.entrySet().iterator();
		while(it.hasNext()) {
			ClassInstance c = it.next().getValue();
			if(c.meta.name.equals("SubFeatureGroup")) {
				for(int i = 0; i < c.attributes.size(); ++i) {
					if(c.attributes.get(i).meta.name.equals("GroupNecessity") && c.attributes.get(i).value.equals("Required")) {
						if(!searchModelSubForClass(c, m))
							throw new RestrictionException("Subfeagutre group " + c.instance_name + " is required!");
					}
				}
			}
		}
		
		Iterator<Map.Entry<Integer, ClassSubInstance>> it2 = m.classes.entrySet().iterator();
		while(it.hasNext()) {
			ClassSubInstance c = it2.next().getValue();
			if(c.meta.meta.name.equals("SubFeatureGroup")) {
				searchForAssocs(c, m);
			}
		}
	}

	private void searchForAssocs(ClassSubInstance c, ModelSubInstance m) throws RestrictionException{
		Iterator<Map.Entry<Integer, AssociationSubInstance>> it = m.assocs.entrySet().iterator();
		boolean found_source = false;
		boolean found_target = false;
		while(it.hasNext()) {
			AssociationSubInstance a = it.next().getValue();
			if(a.source.equals(c))
				found_source = true;
			if(a.target.equals(c))
				found_target = true;
			
			if(found_source && found_target)
				return;
		}
		if(!found_source)
			throw new RestrictionException("SubFeature group " + c.meta.instance_name + " needs to be a subfeature of a feature!");
		if(!found_target)
			throw new RestrictionException("SubFeature group " + c.meta.instance_name + " needs to have features belong to it!");
	}

	private boolean searchModelSubForClass(ClassInstance c, ModelSubInstance m) {
		Iterator<Map.Entry<Integer, ClassSubInstance>> it = m.classes.entrySet().iterator();
		
		while(it.hasNext()) {
			if(it.next().getValue().meta.instance_name.equals(c))
				return true;
		}
		return false;
	}

	@Override
	public void checkClassAddRestriction(ClassSubInstance c1, ModelSubInstance m)
			throws RestrictionException {
		Iterator<Map.Entry<Integer, ClassSubInstance>> it = m.classes.entrySet().iterator();
		while(it.hasNext()) {
			ClassSubInstance c = it.next().getValue();
			if(c1.meta.instance_name.equals(c.meta.instance_name))
				throw new RestrictionException("Cannot repeat classes!");
		}
	}

	@Override
	public void checkAssocAddRestriction(ClassSubInstance source,
			ClassSubInstance target, ModelSubInstance m)
			throws RestrictionException {
		Iterator<Map.Entry<Integer, AssociationSubInstance>> it = m.assocs.entrySet().iterator();
		while(it.hasNext()) {
		AssociationSubInstance a = it.next().getValue();
		if(a.target.equals(target) && a.source.equals(source))
			throw new RestrictionException("Can not repeat associations!");
		}
		String type = "";
		for(int i = 0; i < source.meta.enums.size(); ++i)
			if(source.meta.enums.get(i).meta.name.equals("GroupKind"))
				type = source.meta.enums.get(i).chosen_value;
			
		if(type.equals("Alternative")) {
			handleAlternative(m, source);
		}
	}

	private void handleAlternative(ModelSubInstance m, ClassSubInstance sfg) throws RestrictionException {
		Iterator<Map.Entry<Integer, AssociationSubInstance>> it = m.assocs.entrySet().iterator();
		while(it.hasNext()) {
			AssociationSubInstance a = it.next().getValue();
			if(a.source.equals(sfg))
				throw new RestrictionException("Alternative SFG means it can only have 1 descending feature!");
		}
	}

	@Override
	public void checkInstantiationRestriction(ModelInstance m)
			throws RestrictionException {
		Iterator<Map.Entry<Integer, ClassInstance>> it = m.instanced_classes.entrySet().iterator();
		
		while(it.hasNext()) {
			ClassInstance c = it.next().getValue();
			if(c.meta.name.equals("Feature") || c.meta.name.equals("Concept")) {
				for(int i = 0; i < c.attributes.size(); ++i) {
					if(c.attributes.get(i).meta.name.equals("open") && 
							(!c.attributes.get(i).value.equalsIgnoreCase("True") && !c.attributes.get(i).value.equalsIgnoreCase("False")))
						throw new RestrictionException("Feature " + c.instance_name + " must have open true or false!");
				}
			}
		}
	}

	@Override
	public void checkAssocAddRestrictionMeta(ClassInstance c1,
			ClassInstance c2, ModelInstance m) throws RestrictionException {
		if(c2.meta.name.equals("Concept"))
			throw new RestrictionException("Concepts cannot belong to a subfeature group!");
	}

	@Override
	public void checkClassAddRestrictionMeta(ClassInstance c1, ModelInstance m)
			throws RestrictionException {
		// TODO Auto-generated method stub
		
	}

}
