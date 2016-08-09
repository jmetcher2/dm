package au.id.lagod.dmexample.collections;

import au.id.lagod.dmexample.entities.Company;

public class Model extends au.id.lagod.dm.collections.Model {
	
	/*
	 * SINGLETON STUFF
	 */
	private static Model model;
	
	public static Model getModel() {
		if (model == null) {
			// TODO: make this thread-safe
			model = new Model();
		}
		return model;
	}
	
	public static void resetModel() {
		// TODO: make this thread-safe
		model = new Model();
	}
	
	/*
	 * Base collections
	 */
	
	private CompanyManager companies = new CompanyManager();
	
	public CompanyManager getCompanies() {
		return companies;
	}
	
	public Company companies(String code) {
		return getCompanies().get(code);
	}
}
