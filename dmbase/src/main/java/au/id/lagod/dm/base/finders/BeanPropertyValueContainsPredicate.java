package au.id.lagod.dm.base.finders;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;

public class BeanPropertyValueContainsPredicate extends BeanPropertyValueEqualsPredicate {

	public BeanPropertyValueContainsPredicate(String propertyName, Object propertyValue) {
		super(propertyName, propertyValue);
	}
	
	@Override
	protected boolean evaluateValue(Object expected, Object actual) {
		if (!((expected instanceof String) && (actual instanceof String))) {
			throw new IllegalArgumentException("Contains predicate requires string arguments");
		}
		return ((expected != null) && ((String) actual).contains((CharSequence) expected));
    }
	

}
