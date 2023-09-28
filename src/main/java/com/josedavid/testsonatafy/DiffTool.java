package com.josedavid.testsonatafy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author josedavidochoaortiz
 */
public class DiffTool {
    
    
    //list of changes
    private List<ChangeType> changes;
   

    //in this method i added the change to the list once the objets were analized
    public  List<ChangeType> diff(Object previous, Object current)  {
        changes = new ArrayList<>();
        recDiff("", current, previous);
        return changes;
    }

    // In this method, I'll evaluate the objects. I'm refactoring to make it easier to read and understand and reduce the cognitive Complexity
        private void recDiff(String prop, Object currentObject , Object previousObject){
         
        //i validate if the previous object is not the equal to the current object
        if (!Objects.equals(previousObject, currentObject) && !prop.equals("") &&  currentObject!=null && previousObject!= null) {
            changes.add(new PropertyUpdate(prop, previousObject, currentObject));
        }

        //In this section I'll validate if the objects are list or else I'll analize the objects as map
        if (previousObject instanceof List && currentObject instanceof List) {
           processList( prop, currentObject ,  previousObject);
           
        } else if (previousObject instanceof Map && currentObject instanceof Map) {
           processMap(prop,  currentObject,  previousObject);
           
        }
    }
    

    // In this method, I'll evaluate the list of objects.
    private void processList(String prop, Object currentObject , Object previousObject) 
    {
        try{
            List<Object> prevList = (List<Object>) previousObject;
            List<Object> currList = (List<Object>) currentObject;
            List<Object> remList;
            List<Object> addList;
            //I'm validating directly if the object has an 'id' field or an 'auditkey' annotation.
            if ( !validateObject(previousObject)  ||  !validateObject(currentObject) ) {
                     throw new UnsupportedOperationException("The audit system lacks the information it needs to determine what has changed.");
            }
            for (int i = 0; i < Math.max(prevList.size(), currList.size()); i++) {

                Object prevItem = i < prevList.size() ? prevList.get(i) : null;
                Object currItem = i < currList.size() ? currList.get(i) : null;
                String itemPath = prop + "[" + currentObject.getClass().getMethod("getId", null).invoke(currentObject, null) + "]";
                recDiff(itemPath, currItem, prevItem);
            }

            remList = prevList.stream().filter(item -> !currList.contains(item)).collect(Collectors.toList());
            addList = currList.stream().filter(item ->  !prevList.contains(item)).collect(Collectors.toList());

            // I'm adding items to the list of removed and added services. 
            changes.add(new ListUpdate(prop, remList, addList));
        }
        catch(Exception e )
        {
            e.printStackTrace();
        }
    }
    // I've added a method to evaluate the map object.
    private  void processMap(String prop, Object currentObject , Object previousObject)
    {
        try
        {
            Map<String, Object> previous = (Map<String, Object>) previousObject;
            Map<String, Object> current = (Map<String, Object>) currentObject;
            for (Entry<String, Object> entry : previous.entrySet()) {
                String nestedPath = prop.isEmpty() ? entry.getKey() : prop + "." + entry.getKey();
                Object prevValue = previous.get(entry.getKey());
                Object currValue = current.get(entry.getKey());
                recDiff(nestedPath,  currValue,prevValue);
            }

            //analize in recursive mode for adding dot notation to nested properties
            for (Entry<String, Object> entry : current.entrySet()) {
                if (!previous.containsKey(entry.getKey())) {
                    String nestedPath = prop.isEmpty() ? entry.getKey() : prop + "." + entry.getKey();
                    changes.add(new ListUpdate(nestedPath, null, entry.getValue()));
                }
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
           
    }



    //in this method I'll validate if the id of a list item a field annotated with @AuditKey or have the name 'id', using java reflection
    private static boolean validateObject(Object obj) {
        Class<?> classforreflection = obj.getClass();
        Field[] fields = classforreflection.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(AuditKey.class)) {
                return true;
            }

            if (field.getName().equals("id")) {
                return true;
            }
        }

        return false;
    }
    
    
    

    
    
}


