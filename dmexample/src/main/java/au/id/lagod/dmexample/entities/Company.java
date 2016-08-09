package au.id.lagod.dmexample.entities;

import javax.validation.constraints.Size;
import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;
import org.hibernate.validator.constraints.NotBlank;

public class Company extends BaseDomainObject {

	@TextKey
	@NotBlank @Size(max=50)	private String code;
	@Size(max=255)			private String description;

	public Company(String name) {
		this.code = name;
		this.description = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}
	
	

}
