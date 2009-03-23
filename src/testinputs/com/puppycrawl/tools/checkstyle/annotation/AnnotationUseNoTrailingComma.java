package com.puppycrawl.tools.checkstyle.annotation;

@SuppressWarnings({})
public class AnnotationUseNoTrailingComma
{
  @SuppressWarnings({"common"})
  public void foo() {
      
      
      @SuppressWarnings({"common","foo"})
      Object o = new Object() {
        
          @SuppressWarnings(value={"common"})
          public String toString() {
              
              @SuppressWarnings(value={"leo","herbie"})
              final String pooches = "leo.herbie";
              
              return pooches;
          }
      };
  }
  
  @Test2(value={(false) ? "" : "foo"}, more={(true) ? "" : "bar"})

  @Pooches2(tokens={},other={})
  enum P {
      
      @Pooches2(tokens={Pooches2.class},other={1})
      L,
      
      @Test2(value={}, more={(false) ? "" : "unchecked"})
      Y;
  }
  
}

@interface Test2 {
  String[] value();
  String[] more() default {};
}

@interface Pooches2 {
  
  Class<?>[] tokens();
  int[] other();
}
