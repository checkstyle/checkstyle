/*
AnnotationUseStyle
elementStyle = ignore
closingParens = ignore
trailingArrayComma = ALWAYS


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

@SuppressWarnings({}) // violation 'Annotation array values must contain trailing comma'
public class InputAnnotationUseStyleNoTrailingComma
{
  @SuppressWarnings({"common"}) // violation 'Annotation array values must contain trailing comma'
  public void foo() {

      /** Suppress warnings */
      // violation below 'Annotation array values must contain trailing comma'
      @SuppressWarnings({"common","foo"})
      Object o = new Object() {

          // violation below 'Annotation array values must contain trailing comma'
          @SuppressWarnings(value ={"common"})
          public String toString() {

              // violation below 'Annotation array values must contain trailing comma'
              @SuppressWarnings( value={"leo","herbie"})
              final String pooches = "leo.herbie";

              return pooches;
          }
      };
  }
  // 2 violations 3 lines below:
  // 'Annotation array values must contain trailing comma.'
  // 'Annotation array values must contain trailing comma.'
  @Test2(value={"foo"}, more={"bar"})
  // 2 violations 3 lines below:
  // 'Annotation array values must contain trailing comma.'
  // 'Annotation array values must contain trailing comma.'
  @Pooches2(tokens={},other={})
  enum P {
      // 2 violations 3 lines below:
      // 'Annotation array values must contain trailing comma.'
      // 'Annotation array values must contain trailing comma.'
      @Pooches2(tokens={Pooches2.class},other={1})
      L, // annotation in enum
      // 2 violations 3 lines below:
      // 'Annotation array values must contain trailing comma.'
      // 'Annotation array values must contain trailing comma.'
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
