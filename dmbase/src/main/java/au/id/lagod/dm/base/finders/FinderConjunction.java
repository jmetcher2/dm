package au.id.lagod.dm.base.finders;

public class FinderConjunction implements IFinderCriterion {
	
	private IFinderCriterion lhs;
	private ConjunctionOperator op;
	private IFinderCriterion rhs;
	
	public FinderConjunction() {}

	public FinderConjunction(IFinderCriterion lhs, ConjunctionOperator op, IFinderCriterion rhs) {
		super();
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	public boolean isConjunction() {
		return true;
	}
	
	public IFinderCriterion getLhs() {
		return lhs;
	}

	public void setLhs(IFinderCriterion lhs) {
		this.lhs = lhs;
	}

	public ConjunctionOperator getOp() {
		return op;
	}

	public void setOp(ConjunctionOperator op) {
		this.op = op;
	}

	public IFinderCriterion getRhs() {
		return rhs;
	}

	public void setRhs(IFinderCriterion rhs) {
		this.rhs = rhs;
	}
	
	

}
