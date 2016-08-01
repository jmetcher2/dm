package au.id.lagod.dm.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marker annotation used to denote public methods that should not be used outside 
 * the domain model code.  See CheckRestricted
 * 
 * @author Jaime Metcher
 *
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Restricted {
	//public String packageSpec();
}
