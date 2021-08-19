/*
AnnotationUseStyle
elementStyle = ignore
closingParens = ignore
trailingArrayComma = ALWAYS


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

@SuppressWarnings({}) // violation
public class InputAnnotationUseStyleNoTrailingComma
{
  @SuppressWarnings({"common"}) // violation
  public void foo() {

      /** Suppress warnings */
      @SuppressWarnings({"common","foo"}) // violation
      Object o = new Object() {

          @SuppressWarnings(value={"common"}) // violation
          public String toString() {

              @SuppressWarnings(value={"leo","herbie"}) // violation
              final String pooches = "leo.herbie";

              return pooches;
          }
      };
  }

  @Test2(value={"foo"}, more={"bar"}) // 2 violations

  @Pooches2(tokens={},other={}) // 2 violations
  enum P {

      @Pooches2(tokens={Pooches2.class},other={1}) // 2 violations
      L, // annotation in enum

      @Test2(value={}, more={"unchecked"}) // 2 violations
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
