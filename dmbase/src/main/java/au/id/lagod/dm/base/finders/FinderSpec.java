package au.id.lagod.dm.base.finders;

public class FinderSpec {
	
	private String fieldName;
	private FinderOperator op;
	private Object value;
	
	public FinderSpec() {}

	public FinderSpec(String fieldName, FinderOperator op, Object value) {
		super();
		this.fieldName = fieldName;
		this.op = op;
		this.value = value;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public FinderOperator getOp() {
		return op;
	}

	public void setOp(FinderOperator op) {
		this.op = op;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	

}
