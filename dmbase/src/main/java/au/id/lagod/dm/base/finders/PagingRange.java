package au.id.lagod.dm.base.finders;

public class PagingRange {

	public final boolean specifiedByPage;
	public final int pageNumber;
	public final int pageSize;
	public final int start;
	public final int end;

	public PagingRange(int start, int end) {
		this.specifiedByPage = false;
		this.start = start;
		this.end = end;
		this.pageSize = end - start;
		this.pageNumber = start / pageSize;
	}
	
	public PagingRange(boolean specifiedByPage, int pageSizeOrItemStart, int pageNumberOrItemEnd) {
		this.specifiedByPage = specifiedByPage;
		if (this.specifiedByPage) {
			this.pageSize = pageSizeOrItemStart;
			this.pageNumber = (pageNumberOrItemEnd != 0 ? pageNumberOrItemEnd : 1);
			this.start = (this.pageSize * (this.pageNumber - 1));
			this.end = (this.start + this.pageSize) - 1; 
		} else {
			this.start = pageSizeOrItemStart;
			this.end = pageNumberOrItemEnd;
			this.pageSize = (end - start) + 1;
			this.pageNumber = (start / pageSize) + 1;
		}
	}
	
	public String offsetFetchSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("offset ");
		sb.append(start);
		sb.append(" rows\n");
		sb.append(" fetch next ");
		sb.append(pageSize);
		sb.append(" rows only option(recompile)");
		return sb.toString();
	}

}
