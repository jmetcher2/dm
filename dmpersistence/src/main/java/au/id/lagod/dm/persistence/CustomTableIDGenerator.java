package au.id.lagod.dm.persistence;

import java.io.Serializable;
import java.util.Properties;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class CustomTableIDGenerator implements IdentifierGenerator, Configurable {
	private static final String SP_NAME_PARAM = "sp_name";
	
	private String sp_name; 

	public Serializable generate(SharedSessionContractImplementor session, Object object)
	        throws HibernateException {

    	StoredProcedureQuery query = session.createStoredProcedureCall( sp_name );
    	query.registerStoredProcedureParameter( "sequence_id", Long.class, ParameterMode.OUT);

    	query.execute();
    	Long nextId = (Long) query.getOutputParameterValue("sequence_id");
	    return nextId;
	  }
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		sp_name =  params.getProperty(SP_NAME_PARAM);
	}
}
