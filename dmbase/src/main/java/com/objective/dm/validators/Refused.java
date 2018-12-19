package com.objective.dm.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marker annotation used to denote public methods that should not be called.  See CheckRefused
 * 
 * @author Jaime Metcher
 *
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Refused {
	//public String packageSpec();
}
