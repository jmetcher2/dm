package au.id.lagod.dmexample.model.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;

@Entity
@Table(name="customers")
public class Customer extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")			protected Long id;
	
	@Column(name="name", length = 255)
	@NotBlank @Size(max=255)											protected String name;
	
	@TextKey
	@Column(name="code", length = 20)
	@NotBlank @Size(max=20)												protected String code;

	protected Customer() {}
	
	protected Customer(String code) {
		this.name = code;
		this.code = code;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public String getName() { return name; }
	public String getCode() { return code; }

}
