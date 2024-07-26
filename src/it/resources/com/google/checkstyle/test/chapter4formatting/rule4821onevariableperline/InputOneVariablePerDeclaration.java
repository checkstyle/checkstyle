package com.google.checkstyle.test.chapter4formatting.rule4821onevariableperline;

/** Some javadoc. */
public class InputOneVariablePerDeclaration {
  int mnp, efg; // violation 'Each variable declaration must be in its own statement.'
  int i1; int j1;
  // 2 violations above:
  //  'Only one variable definition per line allowed.'
  //  'Only one statement per line allowed.'

  void method1() {
    String str, str1; // violation 'Each variable declaration must be in its own statement.'
    java.lang.Object obj; Object obj1;
    // 2 violations above:
    //  'Only one variable definition per line allowed.'
    //  'Only one statement per line allowed.'
  }

  // second definition is wrapped
  // line of VARIABLE_DEF is not the same as first line of the definition
  // violation below 'Only one variable definition per line allowed.'
  java.lang.String string; java.lang.String[]
      strings;
  // both definitions are wrapped
  // violation below 'Only one variable definition per line allowed.'
  java.lang
              .String string1; java.lang.String[]
                  strings1;

  void method2() {
    for (int i = 0, j = 0; i < 10; i++, j--) { // ok
    }

    for (int i = 0; i < 4; i++) {}
  }

  class Inner {
    int xyz, qwe; // violation 'Each variable declaration must be in its own statement.'
    int i1; int j1;
    // 2 violations above:
    //  'Only one variable definition per line allowed.'
    //  'Only one statement per line allowed.'

    void method1() {
      String str, str1;
      // violation above 'Each variable declaration must be in its own statement.'
      java.lang.Object obj; Object obj1;
      // 2 violations above:
      //  'Only one variable definition per line allowed.'
      //  'Only one statement per line allowed.'
    }

    // second definition is wrapped
    // line of VARIABLE_DEF is not the same as first line of the definition
    // violation below 'Only one variable definition per line allowed.'
    java.lang.String string; java.lang.String[]
        strings;
    // both definitions are wrapped
    // violation below 'Only one variable definition per line allowed.'
    java.lang
                .String string1; java.lang.String[]
                    strings1;

    void method2() {
      for (int i = 0, j = 0; i < 10; i++, j--) { // ok
      }

      for (int i = 0; i < 4; i++) {}
    }

    Inner anon =
            new Inner() {
              int abc, pqr; // violation 'Each variable declaration must be in its own statement.'
              int i1; int j1;
              // 2 violations above:
              //  'Only one variable definition per line allowed.'
              //  'Only one statement per line allowed.'

              void method1() {
                String str, str1;
                // violation above 'Each variable declaration must be in its own statement.'
                java.lang.Object obj; Object obj1;
                // 2 violations above:
                //  'Only one variable definition per line allowed.'
                //  'Only one statement per line allowed.'
              }

              // second definition is wrapped
              // line of VARIABLE_DEF is not the same as first line of the definition
              // violation below 'Only one variable definition per line allowed.'
              java.lang.String string; java.lang.String[]
                  strings;
              // both definitions are wrapped
              // violation below 'Only one variable definition per line allowed.'
              java.lang
                  .String string1; java.lang.String[]
                      strings1;
              // violation 2 lines above '.* incorrect indentation .*, expected .*: 10, 12, 14.'

              void method2() {
                for (int i = 0, j = 0; i < 10; i++, j--) { // ok
                }

                for (int i = 0; i < 4; i++) {}
              }
            };
  }

  class Suppress {
    @SuppressWarnings("unused")
    // violation above 'Each variable declaration must be in its own statement.'
    long q1, q2, q3;

    @SuppressWarnings("unused") long q4, q5, q6;
    // violation above 'Each variable declaration must be in its own statement.'
  }
}
