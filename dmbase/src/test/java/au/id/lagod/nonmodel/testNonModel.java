package au.id.lagod.nonmodel;

import org.junit.Test;

/**
 * This class demonstrates the compile error that results from calling a method
 * marked with @Restricted.
 * 
 * Comment out the offending lines if the compile error is causing problems.
 * 
 * @author Jaime Metcher
 *
 */
public class testNonModel  {
	
	@Test
	public void testConstructorAccess() {
		// SHOULD GET COMPILE ERROR HERE: 
//		TestObject c = new TestObject(1, "a", null);
	}
	
	@Test
	public void testAddAccess() {
		// SHOULD GET COMPILE ERROR HERE: 
//		new TestObjectManager().add(null);
	}
	
}
