package com.sasip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.exception.FeatureNotFoundException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = {AnnotationConfigContextLoader.class})
public class InventorWebAppApplicationTests 
{

    @Autowired
    FF4j ff4j;
    
    @Test
    //@Ignore
    public void contextLoads() {
    }
    
    
    @Test
    //@Ignore
    public void my_first_test() {
        // Given: file exist
        System.out.println(getClass().getClassLoader().getResource(".").toString());
        //assertNotNull(getClass().getClassLoader().getResourceAsStream("ff4j.xml"));
        // When: init
        //FF4j ff4j = new FF4j("ff4j.xml");
        for(Map.Entry<String, Feature> entry : ff4j.getFeatures().entrySet())
        {
            System.out.println(entry.getKey() + " --- " + entry.getValue().getUid());
            //System.out.println();
        }
        // Then
        assertEquals(10, ff4j.getFeatures().size());
        assertTrue(ff4j.exist("AwesomeFeature"));
        assertTrue(ff4j.check("AwesomeFeature"));
        
        System.out.println(ff4j.exist("sasi-f1"));
        System.out.println(ff4j.check("sasi-f1"));
        
        // Usage
        if (ff4j.check("AwesomeFeature")) {
            // hello is toggle on
            System.out.println("AwesomeFeature");
        }
        
        // When: Toggle Off
        ff4j.disable("AwesomeFeature");
        // Then: expected to be off
        assertFalse(ff4j.check("AwesomeFeature"));
    }
    
    @Test
    @Ignore
    public void how_does_AutoCreate_Works() {
        // Given: feature not exist
        //FF4j ff4j = new FF4j("ff4j.xml");
        FF4j ff4j = new FF4j();
        assertFalse(ff4j.exist("does_not_exist"));
        
        // When: use it
        try {
           if (ff4j.check("does_not_exist")) fail();
        } catch (FeatureNotFoundException fnfe) {
            // Then: exception
            System.out.println("By default, feature not found throw exception");
        }
        
        // When: using autocreate
        ff4j.setAutocreate(true);
        // Then: no more exceptions
        if (!ff4j.check("does_not_exist")) {
            System.out.println("Auto created and set as false");
        }
    }
    
    @Test
    @Ignore
    public void how_groups_works() {
        // Given: 2 features 'off' within existing group
        FF4j ff4j = new FF4j("ff4j.xml");
        assertTrue(ff4j.exist("userStory3_1"));
        assertTrue(ff4j.exist("userStory3_2"));
        assertTrue(ff4j.getFeatureStore().readAllGroups().contains("sprint_3"));
        assertEquals("sprint_3", ff4j.getFeature("userStory3_1").getGroup());
        assertEquals("sprint_3", ff4j.getFeature("userStory3_2").getGroup());
        assertFalse(ff4j.check("userStory3_1"));
        assertFalse(ff4j.check("userStory3_2"));
  
        // When: toggle group on
        ff4j.getFeatureStore().enableGroup("sprint_3");
        
        // Then: all features on
        assertTrue(ff4j.check("userStory3_1"));
        assertTrue(ff4j.check("userStory3_2"));
    }
}

