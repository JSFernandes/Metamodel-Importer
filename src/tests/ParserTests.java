package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;

import data.AssociationEndMeta;
import data.AssociationMeta;
import data.ClassMeta;
import data.ConstraintMeta;
import data.DependencyMeta;
import data.EntityMeta;
import data.GeneralizationMeta;
import parser.ParserBody;

public class ParserTests{
	
	@Before public void cleanEntities() {
		EntityMeta.all_entities         = new Hashtable<String, EntityMeta>();
		EntityMeta.all_classes          = new Hashtable<String, ClassMeta>();
		EntityMeta.all_associations     = new Hashtable<String, AssociationMeta>();
		EntityMeta.all_association_ends = new Hashtable<String, AssociationEndMeta>();
		EntityMeta.all_generalizations  = new Hashtable<String, GeneralizationMeta>();
		EntityMeta.all_constraints      = new Hashtable<String, ConstraintMeta>();
		EntityMeta.all_dependencies     = new Hashtable<String, DependencyMeta>();
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
		
		assertEquals("GroupKind", EntityMeta.all_classes.get("6GCWIEKFS_j4ogZ3").name);
		assertEquals("Class", EntityMeta.all_classes.get("qaM6IEKFS_j4ogkf").name);
		assertEquals("Attribute", EntityMeta.all_classes.get("jyK6IEKFS_j4ogkw").name);
		assertEquals("Feature", EntityMeta.all_classes.get("fQQGIEKFS_j4ogl_").name);
		assertEquals("Concept", EntityMeta.all_classes.get("gDzWIEKFS_j4oga8").name);
		assertEquals("SubFeatureGroup", EntityMeta.all_classes.get("hcoGIEKFS_j4ogmK").name);
		
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
		
		assertEquals(1, EntityMeta.all_classes.get("gDzWIEKFS_j4oga8").attributes.size());
		assertEquals(1, EntityMeta.all_classes.get("fQQGIEKFS_j4ogl_").attributes.size());
		
		assertEquals(1, EntityMeta.all_classes.get("hcoGIEKFS_j4ogmK").attributes.size());
		
		assertEquals(2, EntityMeta.all_classes.get("jyK6IEKFS_j4ogkw").attributes.size());
		assertEquals(1, EntityMeta.all_classes.get("fQQGIEKFS_j4ogl_").attributes.size());
		
		assertEquals("1", EntityMeta.all_classes.get("hcoGIEKFS_j4ogmK").attributes.get(0).multiplicity);
	}

	
	@Test public void testInstantiation() {
		ParserBody p = new ParserBody("project.xml");
		p.generateData();
		
		ArrayList<ClassMeta> creatable_classes = ClassMeta.getCreatableClasses();
		ArrayList<AssociationMeta> creatable_assocs = AssociationMeta.getAllCreatableAssocs();
		
		assertEquals(3, creatable_classes.size());
		assertEquals(2, creatable_assocs.size());
	}
}
