package com.puppycrawl.tools.checkstyle.annotation;

import java.lang.annotation.Documented;

@SuppressWarnings("unchecked")
public class SuppressWarningsSingle
{   
    @SuppressWarnings("   ")
    class Empty {
        
        @SuppressWarnings("")
        public Empty() {
            
        }
    }
    
    @SuppressWarnings("unused")
    enum Duh {
        
        @SuppressWarnings("unforgiven")
        D;
        
        public static void foo() {
            
            @SuppressWarnings("unused")
            Object o = new SuppressWarningsSingle() {
                
                @Override
                @SuppressWarnings("unchecked")
                public String toString() {
                    return "";
                }
            };
        }
    }
    
    @SuppressWarnings("abcun")
    @Documented
    @interface Sweet {
        int cool();
    }
    
    @Documented
    @SuppressWarnings("abcun")
    @interface MoreSweetness {
        
        @SuppressWarnings("unused")
        int cool();
    }
    
    public class Junk {
        
        @SuppressWarnings("")
        int a = 1;
        
        @SuppressWarnings("unchecked")
        @Deprecated
        int b = 1;
        void doFoo(String s, @SuppressWarnings("unchecked")String y) {
            
        }
    }
    
    @SuppressWarnings((false) ? "unchecked" : "")
    class Cond {
        
        @SuppressWarnings((false) ? "" : "unchecked")
        public Cond() {
            
        }
        
        @SuppressWarnings((false) ? (true) ? "   " : "unused" : "unchecked")
        public void aCond1() {
            
        }
        
        @SuppressWarnings((false) ? "unchecked" : (true) ? "   " : "unused")
        public void aCond2() {
            
        }
        
        @java.lang.SuppressWarnings((false) ? "unchecked" : ("" == "") ? (false) ? (true) ? "" : "foo" : "    " : "unused")
        public void seriously() {
            
        }
    }
}
