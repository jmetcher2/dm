package au.id.lagod.dm.base.finders;

public class OrderBy {
	
	private boolean ascending;
	private String fieldName;
	
	public OrderBy(String fieldName, boolean ascending) {
		this.fieldName = fieldName;
		this.ascending = ascending;
	}

	public boolean isAscending() {
		return ascending;
	}

	public String getFieldName() {
		return fieldName;
	}
	
	
}
