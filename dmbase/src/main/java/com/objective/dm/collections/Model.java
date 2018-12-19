package com.objective.dm.collections;

/*
 * Marker interface to indicate the object is the top level entry point for the domain model.
 * This has two consequences:
 * 1. Aspects will guard against being initialized with two different models
 * 2. The object will then be type-compatible with Bootstrapper etc.  Implementations will still 
 * to cast to a specific model type to do anything useful, so this interface just acts as a 
 * declaration of design intention.
 */
public class Model {
	
	private Boolean bootstrapped = false;
	
	public Boolean isBootstrapped() {
		return bootstrapped;
	}
	
	public void setBootstrapped() {
		bootstrapped = true;
	}
	
}
