package au.id.lagod.dm.config;

public class AuditUserHolder {
	
	protected static ThreadLocal<String> auditUserID = new ThreadLocal<String>() {
		@Override protected synchronized String initialValue() {
            return "system";
        }

	};
	
	public static String getAuditUserID() {
		return auditUserID.get();
	}
	
	public static void setAuditUserID(String userID) {
		auditUserID.set(userID);
	}


}
