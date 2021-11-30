package au.id.lagod.dm.base.finders;

public interface IFinderCriterion {

		/* this interface exists so that its two implementors can form a hacky kind of union class */
	
	public boolean isConjunction();
}
