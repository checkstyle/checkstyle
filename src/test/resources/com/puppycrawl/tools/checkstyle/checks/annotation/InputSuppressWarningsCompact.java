package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.lang.annotation.Documented;

@SuppressWarnings({"unchecked", "unused"})
public class InputSuppressWarningsCompact
{   
    @SuppressWarnings({"   "})
    class Empty {
        
        @SuppressWarnings({"unchecked", ""})
        public Empty() {
            
        }
    }
    
    @SuppressWarnings({"unused"})
    enum Duh {
        
        @SuppressWarnings({"unforgiven", "    un"})
        D;
        
        public static void foo() {
            
            @SuppressWarnings({"unused"})
            Object o = new InputSuppressWarningsCompact() {
                
                @Override
                @SuppressWarnings({"unchecked"})
                public String toString() {
                    return "";
                }
            };
        }
    }
    
    @SuppressWarnings({"abcun"})
    @Documented
    @interface Sweet {
        int cool();
    }
    
    @Documented
    @SuppressWarnings({})
    @interface MoreSweetness {
        
        @SuppressWarnings({"unused", "bleh"})
        int cool();
    }
    
    public class Junk {
        
        @SuppressWarnings({})
        int a = 1;
        
        @SuppressWarnings({"unchecked"})
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings({"unchecked"})String y) {
            
        }
    }
    
    @SuppressWarnings({(false) ? "unchecked" : "", (false) ? "unchecked" : ""})
    class Cond {
        
        @SuppressWarnings({(false) ? "" : "unchecked"})
        public Cond() {
            
        }
        
        @SuppressWarnings({(false) ? (true) ? "   " : "unused" : "unchecked", (false) ? (true) ? "   " : "unused" : "unchecked"})
        public void aCond1() {
            
        }
        
        @SuppressWarnings({(false) ? "unchecked" : (true) ? "   " : "unused"})
        public void aCond2() {
            
        }
        
        @java.lang.SuppressWarnings({(false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused", (false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" : "foo" : "   " : "unused"})
        public void seriously() {
            
        }
    }
}
