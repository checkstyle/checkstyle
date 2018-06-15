package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;
//this file compiles in jdk but NOT with eclipse 
//eclipse error "The value for annotation attribute must be a constant expression"
public class InputAnnotationUseStyleNoTrailingComma
{
  @Test2(value={(false) ? "" : "foo"}, more={(true) ? "" : "bar"})

  enum P {
      L,
      Y;
  }
  
}

@interface Test2 {
  String[] value();
  String[] more() default {};
}
