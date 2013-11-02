package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import data.EntityMeta;
import parser.ParserBody;

public class ParserTests{
	@Test public void testDataMethods() {
		
	}
	
	@Test public void testParser() {
		
		ParserBody p = new ParserBody("project.xml");
		p.generateData();
		
		assertEquals(6, EntityMeta.all_classes.size());
		assertEquals(4, EntityMeta.all_associations.size());
		assertEquals(8, EntityMeta.all_association_ends.size());
		assertEquals(1, EntityMeta.all_generalizations.size());
		assertEquals(1, EntityMeta.all_constraints.size());
		assertEquals(3, EntityMeta.all_dependencies.size());
	}
	
	@Test public void testInstantiation() {
		
	}
}
