package au.id.lagod.dm.base;


public class BaseDomainObject {

	public BaseDomainObject() {
		super();
	}

	public int compareTo(BaseDomainObject o) {
		return 0;
	}
	
	// Note: if this reflection-based approach proves to be too slow, we can
	// a) cache the value after the first call, or
	// b) override these two methods in subclasses 
	public boolean hasTextKey() {
		return Utility.getTextKeyField(getClass()) != null;
	}
	
	public String getTextKey() {
		return Utility.getTextIDValue(this,Utility.getTextKeyField(getClass()));
	}
	
	/**
	 * Remove associated objects
	 * 
	 * We call this before removing an object from its aggregate, which is effectively deletion.
	 * This method must disconnect the object from any associations with objects outside the aggregate.
	 * Note that composed child objects must also be disconnected
	 * 
	 * Default is to do nothing.  Override this method if the object or its children have associates.
	 */
	public void removeAssociates() {
	}

}