package restrictions;

import instances.ClassInstance;
import instances.ModelInstance;
import subinstance.ClassSubInstance;
import subinstance.ModelSubInstance;

public abstract class Restrictions {
	public abstract void checkAllRestrictions(ModelSubInstance m) throws RestrictionException;
	public abstract void checkClassAddRestriction(ClassSubInstance c1, ModelSubInstance m) throws RestrictionException;
	public abstract void checkAssocAddRestriction(ClassSubInstance c1, ClassSubInstance c2, ModelSubInstance m) throws RestrictionException;
	
	public abstract void checkInstantiationRestriction(ModelInstance m) throws RestrictionException;
	public abstract void checkAssocAddRestrictionMeta(ClassInstance c1, ClassInstance c2, ModelInstance m) throws RestrictionException;
	public abstract void checkClassAddRestrictionMeta(ClassInstance c1, ModelInstance m) throws RestrictionException;
}
