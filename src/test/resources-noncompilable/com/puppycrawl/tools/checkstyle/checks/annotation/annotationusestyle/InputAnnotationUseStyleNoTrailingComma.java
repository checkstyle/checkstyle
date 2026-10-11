/*
AnnotationUseStyle
elementStyle = ignore
closingParens = ignore
trailingArrayComma = ALWAYS


*/
// non-compiled with javac: Compilable with Java21 individually
// non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

public class InputAnnotationUseStyleNoTrailingComma
{
  @Test2(value={(false) ? "" : "foo"}, more={(true) ? "" : "bar"})
  // 2 violations above:
  // 'Annotation array values must contain trailing comma.'
  // 'Annotation array values must contain trailing comma.'
  enum P {
      L,
      Y;
  }

}

@interface Test2 {
  String[] value();
  String[] more() default {};
}
