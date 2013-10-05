package com.puppycrawl.tools.checkstyle.annotation;
//this file compiles in eclipse 3.4 but not with Sun's JDK 1.6.0.11

/** FIXME: CheckStyle's ANTL grammar cannot handle this syntax
@SuppressWarnings({,})
*/
public class AnnotationUseWithTrailingComma
{
    @SuppressWarnings({"common",})
    public void foo() {
        
        
        @SuppressWarnings({"common","foo",})
        Object o = new Object() {
          
            @SuppressWarnings(value={"common",})
            public String toString() {
                
                @SuppressWarnings(value={"leo","herbie",})
                final String pooches = "leo.herbie";
                
                return pooches;
            }
        };
    }
    
    @Test(value={(false) ? "" : "foo",}, more={(true) ? "" : "bar",})
    /** FIXME: CheckStyle's ANTL grammar cannot handle this syntax
    @Pooches(tokens={,},other={,})
    */
    enum P {
        
        @Pooches(tokens={Pooches.class,},other={1,})
        L,
        
        /** FIXME: CheckStyle's ANTL grammar cannot handle this syntax
        @Test(value={,}, more={(false) ? "" : "unchecked",})
        */
        Y;
    }
    
}

@interface Test {
    String[] value();
    String[] more() default {};
    /** FIXME: CheckStyle's ANTL grammar cannot handle this syntax
    String[] moreAr() default {,};
    */
}

@interface Pooches {
    
    Class<?>[] tokens();
    int[] other();
}

