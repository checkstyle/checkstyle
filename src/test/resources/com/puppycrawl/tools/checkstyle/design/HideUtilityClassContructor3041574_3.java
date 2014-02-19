package com.puppycrawl.tools.checkstyle.design;

import java.io.Serializable;

public class HideUtilityClassContructor3041574_3 implements Serializable {
    private static final long serialVersionUID = 1L;

    public HideUtilityClassContructor3041574_3(int i) {
        // no code
    }

    public String getValue() {
        return "";
    }
    
    // It is NOT Utility Inner class
    @SuppressWarnings("unused")
    public static class Event {
        // Top level class have access to fields - no need in public getters
        private String ind;
        private String ind1;
        
        public Event(String value){
            // do a lot of calculations
        }
        
        // static because this method is utility
        public static String getEmptyString() {
            return "";
        }
    }
    
    // It is Utility Inner class
    @SuppressWarnings("unused")
    public static class Event1 {
        private String ind;
        private String ind1;
        
        private Event1(){
            // do a lot of calculations
        }
        
        // static because this method is utility
        public static String getEmptyString() {
            return "";
        }
    }
}