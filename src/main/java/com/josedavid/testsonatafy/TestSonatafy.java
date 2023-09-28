package com.josedavid.testsonatafy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josedavidochoaortiz
 */
public class TestSonatafy {

    public static void main(String[] args) {
        // Create previous and current objects
        Map<String, Object> previous = new HashMap<>();
        previous.put("firstName", "James");
        previous.put("subscription.status", "ACTIVE");
        Map<String, Object> inner = new HashMap<>();
        inner.put("name", "cinemark");
        inner.put("status", "ACTIVE");
        previous.put("subscriptionG", inner);
        CustomizedList toTest= new CustomizedList();
            toTest.add("Oil Change");
            toTest.add( "Tire Rotation");
            toTest.add("test");
        previous.put("services", toTest);

        Map<String, Object> current = new HashMap<>();
        current.put("firstName", "Jim");
        current.put("subscription.status", "EXPIRED");
        CustomizedList toTestCurrent= new CustomizedList();
            toTestCurrent.setId("V_1");
            toTestCurrent.add("Tire Rotation");
            toTestCurrent.add( "Brake Service");
        current.put("services", toTestCurrent);
            Map<String, Object> innerc = new HashMap<>();
            innerc.put("status", "expired");
            current.put("subscriptionG", innerc);       
        
        DiffTool objt = new DiffTool();
        List<ChangeType> changes = objt.diff(previous, current);
        
        Logger logger = Logger.getLogger(TestSonatafy.class.getName());
        String output ;
            // Print the changes
            for (ChangeType change : changes) {
                
            switch (change) {
                case PropertyUpdate propertyUpdate -> {
                    
                    output = "Property: " + propertyUpdate.getProperty() + " Previous: " + propertyUpdate.getPrevious() + " Current: " + propertyUpdate.getCurrent();
                    logger.log(Level.INFO, output);
                }
                case ListUpdate listUpdate -> {
                    
                    output = "Property:  " + listUpdate.getProperty() + " Removed: " + listUpdate.getRemoved() +" Added: " + listUpdate.getAdded();
                    logger.log(Level.INFO, output);
                }
                case default -> {
                }
            }
            }
        } 
            
       

    
}
