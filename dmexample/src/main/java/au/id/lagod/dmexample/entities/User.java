package au.id.lagod.dmexample.entities;

import javax.validation.constraints.Size;
import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;
import au.id.lagod.dmexample.collections.DepartmentManager;

import org.hibernate.validator.constraints.NotBlank;

public class User extends BaseDomainObject {

	@TextKey
	@NotBlank @Size(max=50)	private String username;
	@Size(max=255)			private String description;
	
	public User(String name) {
		this.username = name;
		this.description = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}
	

}
