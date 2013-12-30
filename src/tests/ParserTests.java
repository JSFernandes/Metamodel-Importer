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
	
	static String GROUP_KIND_ID        = "6GCWIEKFS_j4ogZ3";
	static String CLASS_ID             = "qaM6IEKFS_j4ogkf";
	static String ATTRIBUTE_ID         = "jyK6IEKFS_j4ogkw";
	static String FEATURE_ID           = "fQQGIEKFS_j4ogl_";
	static String CONCEPT_ID           = "gDzWIEKFS_j4oga8";
	static String SUB_FEATURE_GROUP_ID = "hcoGIEKFS_j4ogmK";
	
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
		
		assertEquals("GroupKind", EntityMeta.all_classes.get(GROUP_KIND_ID).name);
		assertEquals("Class", EntityMeta.all_classes.get(CLASS_ID).name);
		assertEquals("Attribute", EntityMeta.all_classes.get(ATTRIBUTE_ID).name);
		assertEquals("Feature", EntityMeta.all_classes.get(FEATURE_ID).name);
		assertEquals("Concept", EntityMeta.all_classes.get(CONCEPT_ID).name);
		assertEquals("SubFeatureGroup", EntityMeta.all_classes.get(SUB_FEATURE_GROUP_ID).name);
		
	}
	
	@Test public void testDataConstruction() {
		ParserBody p = new ParserBody("project.xml");
		p.generateData();
		
		assertEquals(EntityMeta.all_classes.get(CONCEPT_ID).parents.get(0), EntityMeta.all_classes.get(FEATURE_ID));
		
		assertEquals(true, EntityMeta.all_classes.get(GROUP_KIND_ID).is_attribute);
		assertEquals(false, EntityMeta.all_classes.get(GROUP_KIND_ID).isCreatable());
		
		assertEquals(false, EntityMeta.all_classes.get(SUB_FEATURE_GROUP_ID).is_attribute);
		assertEquals(true, EntityMeta.all_classes.get(SUB_FEATURE_GROUP_ID).isCreatable());
		
		assertEquals(false, EntityMeta.all_classes.get(CONCEPT_ID).is_attribute);
		assertEquals(true, EntityMeta.all_classes.get(CONCEPT_ID).isCreatable());
		
		assertEquals(false, EntityMeta.all_classes.get(CLASS_ID).isCreatable());
		assertEquals(false, EntityMeta.all_classes.get(ATTRIBUTE_ID).isCreatable());
		
		assertEquals(1, EntityMeta.all_classes.get(CONCEPT_ID).attributes.size());
		assertEquals(1, EntityMeta.all_classes.get(FEATURE_ID).attributes.size());
		
		assertEquals(1, EntityMeta.all_classes.get(SUB_FEATURE_GROUP_ID).attributes.size());
		
		assertEquals(2, EntityMeta.all_classes.get(ATTRIBUTE_ID).attributes.size());
		assertEquals(1, EntityMeta.all_classes.get(FEATURE_ID).attributes.size());
		
		assertEquals("1", EntityMeta.all_classes.get(SUB_FEATURE_GROUP_ID).attributes.get(0).multiplicity);
	}

	
	@Test public void testInstantiation() {
		ParserBody p = new ParserBody("project.xml");
		p.generateData();
		
		ArrayList<ClassMeta>       creatable_classes = ClassMeta.getCreatableClasses();
		ArrayList<AssociationMeta> creatable_assocs  = AssociationMeta.getAllCreatableAssocs();
		
		assertEquals(3, creatable_classes.size());
		assertEquals(2, creatable_assocs.size());
		assertEquals("f2sfg", creatable_assocs.get(0).name);
		assertEquals("sfg2f", creatable_assocs.get(1).name);
	}
}
