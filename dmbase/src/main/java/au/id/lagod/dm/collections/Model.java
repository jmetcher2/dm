package au.id.lagod.dm.collections;

import au.id.lagod.dm.di.ModelInjector;

/**
 * 
 * @author owner
 * 
 * Model is the root object for the whole domain model i.e. the "aggregate root" in Domain Driven Design terms.
 * 
 * Model is a singleton, in a stronger sense than Spring DI singletons.  In Spring DI, singletons are unique to an
 * applicationContext hierarchy.  Model is unique to the classloader.  This is enforced by storing the current Model in
 * the singleton ModelInjector aspect.  ModelFactory provides a convenience method getModel() for obtaining a reference to the current 
 * model.  There is guard logic to prevent you from overwriting the current Model, or from instantiating a second Model.
 * 
 * Model will accumulate state.  Essentially, the whole domain model lives within Model.  For testing purposes, it is possible to reset 
 * the Model state by calling ModelFactory.resetModel().  This method should NEVER be called in production, as it will clear all
 * domain model data.
 * 
 * Any domain object may need a reference to Model in order to find and reference other parts of the domain model.  For example, "lookup"
 * data like experience types sits directly under Model, but is needed by the ExperienceManager in order to create new Experience objects.
 * Rather than passing Model references through the constructor chain ad nauseam, domain objects can implement the ModelClient interface. 
 * The ModelInjector aspect will then inject a reference to Model at the time the domain object is constructed.
 * Alternatively: the domain object can just call ModelFactory.getModel() directly.  TODO: undecided about which approach is best.
 * 
 * A note about Spring vs ModelFactory
 * We chose to create a static factory class rather than use Spring DI.  The reasoning goes like this:
 * - It's desirable to allow any domain object get a reference to Model
 * - It's prohibitively verbose to pass a reference to Model through every constructor
 * - Even if we did so, we'd need custom logic to handle deserialization scenarios (such as Hibernate or XStream), where
 *   constructors may not be called as we would expect
 * - Thus the injector aspect ModelInjector is a clean way to make Model globally available.
 * - ModelInjector needs a reference to a Model instance
 * - ModelInjector, as a singleton aspect, is global to the classloader
 * - Thus any mechanism that configures ModelInjector must also be global to the classloader, otherwise we risk
 *   crosstalk between possible duplicate configuration mechanisms. 
 * 
 * A static factory method fulfils this criterion.  Spring contexts do not.  It is quite possible to have two Spring contexts 
 * overwriting each other's configuration of the aspect.  It's even possible, via Spring's test framework context caching mechanisms,
 * to create this scenario by accident, as Spring will create a second context if the context configuration string changes from one test to the next.
 *
 */
public class Model {
	
	public Model() {
		ModelInjector injector = ModelInjector.aspectOf();
		if (injector.hasModel()) {
			throw new Error("There is already a configured ModelInjector in this classloader");
		}
	}

}
