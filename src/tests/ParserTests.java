package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import data.EntityMeta;
import parser.ParserBody;

public class ParserTests{
	
	@Test public void testParser() {
		
		ParserBody p = new ParserBody("project.xml");
		p.generateData();
		
		assertEquals(6, EntityMeta.all_classes.size());
		assertEquals(4, EntityMeta.all_associations.size());
		assertEquals(8, EntityMeta.all_association_ends.size());
		assertEquals(1, EntityMeta.all_generalizations.size());
		assertEquals(1, EntityMeta.all_constraints.size());
		assertEquals(3, EntityMeta.all_dependencies.size());
		
		assertEquals("GroupKind", EntityMeta.all_classes.get("6GCWIEKFS_j4ogZ3").name);
		assertEquals("Class", EntityMeta.all_classes.get("qaM6IEKFS_j4ogkf").name);
		assertEquals("Attribute", EntityMeta.all_classes.get("jyK6IEKFS_j4ogkw").name);
		assertEquals("Feature", EntityMeta.all_classes.get("fQQGIEKFS_j4ogl_").name);
		assertEquals("Concept", EntityMeta.all_classes.get("gDzWIEKFS_j4oga8").name);
		assertEquals("SubFeatureGroup", EntityMeta.all_classes.get("hcoGIEKFS_j4ogmK").name);
		
		assertEquals(2, EntityMeta.all_classes.get("jyK6IEKFS_j4ogkw").attributes.size());
		assertEquals(1, EntityMeta.all_classes.get("fQQGIEKFS_j4ogl_").attributes.size());
		assertEquals(0, EntityMeta.all_classes.get("hcoGIEKFS_j4ogmK").attributes.size());
	}
	
	@Test public void testDataConstruction() {
		ParserBody p = new ParserBody("project.xml");
		p.generateData();
		
		assertEquals(EntityMeta.all_classes.get("gDzWIEKFS_j4oga8").parent, EntityMeta.all_classes.get("fQQGIEKFS_j4ogl_"));
		
		assertEquals(true, EntityMeta.all_classes.get("6GCWIEKFS_j4ogZ3").is_attribute);
		assertEquals(false, EntityMeta.all_classes.get("6GCWIEKFS_j4ogZ3").is_creatable());
		
		assertEquals(false, EntityMeta.all_classes.get("hcoGIEKFS_j4ogmK").is_attribute);
		assertEquals(true, EntityMeta.all_classes.get("hcoGIEKFS_j4ogmK").is_creatable());
		
		assertEquals(false, EntityMeta.all_classes.get("gDzWIEKFS_j4oga8").is_attribute);
		assertEquals(true, EntityMeta.all_classes.get("gDzWIEKFS_j4oga8").is_creatable());
		
		assertEquals(false, EntityMeta.all_classes.get("qaM6IEKFS_j4ogkf").is_creatable());
		assertEquals(false, EntityMeta.all_classes.get("jyK6IEKFS_j4ogkw").is_creatable());
	}
	
	@Test public void testInstantiation() {
		
	}
}
