package au.id.lagod.dm.persistence.xml;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import au.id.lagod.dm.xml.XmlMap;

public class XmlMapBasicType 
    extends AbstractSingleColumnStandardBasicType<XmlMap> 
implements DiscriminatorType<XmlMap> {
	protected static final long serialVersionUID = 1L;
	
	public XmlMapBasicType(XmlMapBasicTypeDescriptor descriptor) {
	    super( VarcharTypeDescriptor.INSTANCE, descriptor );
	}
	
	@Override
	public XmlMap stringToObject(String xml) throws Exception {
	    return fromString( xml );
	}
	
	@Override
	public String objectToSQLString(XmlMap value, Dialect dialect) throws Exception {
	    return toString( value );
	}
	
	@Override
	public String getName() {
	    return "xmlmap";
	}
	
	
}
