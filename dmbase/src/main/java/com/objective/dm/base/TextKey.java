package com.objective.dm.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Marker annotation to indicate a field that is a natural key,
 * i.e. a unique identifier for the class.
 *
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface TextKey  {
}
