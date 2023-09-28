package com.josedavid.testsonatafy;

import java.util.ArrayList;

/**
 *
 * @author josedavidochoaortiz
 */

public class CustomizedList extends ArrayList<Object>{

    @AuditKey
    private String id;

    public CustomizedList() {
    }

    public CustomizedList(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals (Object o) {
        return super.equals (o);
    }

    @Override
     public int hashCode () {
         return super.hashCode ();
     }



    
}
