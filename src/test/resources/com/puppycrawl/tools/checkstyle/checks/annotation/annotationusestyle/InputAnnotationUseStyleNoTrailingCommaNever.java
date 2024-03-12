/*
AnnotationUseStyle
elementStyle = ignore
closingParens = ignore
trailingArrayComma = NEVER


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

@SuppressWarnings({})
public class InputAnnotationUseStyleNoTrailingCommaNever
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

  @Test5(value={"foo"}, more={"bar"})

  @Pooches5(tokens={},other={})
  enum P {

      @Pooches5(tokens={Pooches5.class},other={1})
      L, // annotation in enum

      @Test5(value={}, more={"unchecked"})
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
