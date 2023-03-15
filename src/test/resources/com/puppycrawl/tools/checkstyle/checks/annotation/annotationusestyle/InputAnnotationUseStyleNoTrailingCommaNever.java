/*
AnnotationUseStyle
elementStyle = ignore
closingParens = ignore
trailingArrayComma = NEVER


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

@SuppressWarnings({}) // ok
public class InputAnnotationUseStyleNoTrailingCommaNever
{
  @SuppressWarnings({"common"}) // ok
  public void foo() {

      /** Suppress warnings */
      @SuppressWarnings({"common","foo"}) // ok
      Object o = new Object() {

          @SuppressWarnings(value={"common"}) // ok
          public String toString() {

              @SuppressWarnings(value={"leo","herbie"}) // ok
              final String pooches = "leo.herbie";

              return pooches;
          }
      };
  }

  @Test5(value={"foo"}, more={"bar"}) // ok

  @Pooches5(tokens={},other={}) // ok
  enum P {

      @Pooches5(tokens={Pooches5.class},other={1}) // ok
      L, // annotation in enum

      @Test5(value={}, more={"unchecked"}) // ok
      Y;
  }

}

@interface Test5 {
  String[] value();
  String[] more() default {};
}

@interface Pooches5 {

  Class<?>[] tokens();
  int[] other();
}
