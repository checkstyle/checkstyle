package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;
// suppress
@SuppressWarnings({})
public class InputAnnotationUseStyleNoTrailingComma
{
  @SuppressWarnings({"common"})
  public void foo() {
       
      /** Suppress warnings */
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
  
  @Test2(value={"foo"}, more={"bar"})

  @Pooches2(tokens={},other={})
  enum P {
      
      @Pooches2(tokens={Pooches2.class},other={1})
      L, // annotation in enum
      
      @Test2(value={}, more={"unchecked"})
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
