package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class InputOperatorWrap {
  void test() {
    int x = 1 + // violation ''\+' should be on a new line.'
            2 - // violation ''-' should be on a new line.'
            3
            -
            4;
    x = x + 2;
    boolean y = true
        &&
        false;
    y = true && // violation ''&&' should be on a new line.'
        false;
    y = false
        && true;
    /* Note: The three tests below will be used when issue #3381 is closed */
    Arrays.sort(null, String
            // violation below ''::' should be on a new line.'
            ::
            compareToIgnoreCase);
    Arrays.sort(null, String::   // violation ''::' should be on a new line.'
            compareToIgnoreCase);
    Arrays.sort(null, String
            ::compareToIgnoreCase);
  }

  void testAssignment() {
    int x
        = 0;
    int y =
        0;
  }

  <T extends Comparable & // violation ''&' should be on a new line.'
      java.io.Serializable>
      void testGenerics1() {
    Comparable
        <
        String
        >
        c = new String();
    Map<String, String> map = new HashMap<String, String>();

    boolean flag = false;

    int init = 9;

    for (Map.Entry<String, String> entry :
        map.entrySet()) {
      int i = flag == true ? // violation ''?' should be on a new line.'
              1 : 2;
    }

    if (init != // violation ''!=' should be on a new line.'
        9) { /* ignore */ }

    while (init == // violation ''==' should be on a new line.'
        10) { }

    if (init > // violation ''>' should be on a new line.'
        10) { /* ignore */ }

    while (init < 10 || // violation ''\|\|' should be on a new line.'
        !flag) { }
  }

  class Inner {
    void testGenerics1() {
      Comparable
          <
          String
          >
          c = new String();
      Map<String, String> map = new HashMap<String, String>();
      boolean flag = false;

      int init = 9;

      for (Map.Entry<String, String> entry :
          map.entrySet()) {
        int i = flag == true ? // violation ''?' should be on a new line.'
                1 : 2;
      }

      if (init != // violation ''!=' should be on a new line.'
          9) { /* ignore */ }

      while (init == // violation ''==' should be on a new line.'
          10) { }

      if (init > // violation ''>' should be on a new line.'
          10) { /* ignore */ }

      while (init < 10 || // violation ''\|\|' should be on a new line.'
          !flag) {}
    }
  }

  Inner anon =
      new Inner() {
        void testGenerics1() {
          Comparable
              <
              String
              >
              c = new String();
          Map<String, String> map = new HashMap<String, String>();
          boolean flag = false;
          int init = 9;

          for (Map.Entry<String, String> entry :
              map.entrySet()) {
              int i = flag == true ? // violation ''?' should be on a new line.'
                      1 : 2;
          }

          if (init != // violation ''!=' should be on a new line.'
              9) { /* ignore */ }

          while (init == // violation ''==' should be on a new line.'
              10) { }

          if (init > // violation ''>' should be on a new line.'
              10) { /* ignore */ }

          while (init < 10 || // violation ''\|\|' should be on a new line.'
              !flag) {}
        }
      };

  class AsInput {
    int abc = 0;
    String string
            = "string";
    double pi =
            3.1415;
  }

  class Ternary {
    void foo() {
      boolean flag = true;
      int i = flag == true ? // violation ''?' should be on a new line.'
              1 :
              2;
      int i2 = flag == true
              ?
              1
              :
              2;
      int i3 = flag == true
              ? 1
              : 2;
    }
  }

  class AssignClass {
    void foo() {
      int i = 0;
      int j = 0;
      i +=
              1;
      j
              += 2;
      i -=
              1;
      j
              -= 2;
      i /=
              1;
      j
              /= 2;
      i *=
              1;
      j
              *= 2;
      i %=
              1;
      j
              %= 2;
      i ^=
              1;
      j
              ^= 2;
      i |=
              1;
      j
              |= 2;
      i &=
              1;
      j
              &= 2;
      i >>=
              1;
      j
              >>= 2;
      i >>>=
              1;
      j
              >>>= 2;
      i <<=
              1;
      j
              <<= 2;
    }

    class InnerClass {
      void foo() {
        int i = 0;
        int j = 0;
        i +=
                1;
        j
                += 2;
        i -=
                1;
        j
                -= 2;
        i /=
                1;
        j
                /= 2;
        i *=
                1;
        j
                *= 2;
        i %=
                1;
        j
                %= 2;
        i ^=
                1;
        j
                ^= 2;
        i |=
                1;
        j
                |= 2;
        i &=
                1;
        j
                &= 2;
        i >>=
                1;
        j
                >>= 2;
        i >>>=
                1;
        j
                >>>= 2;
        i <<=
                1;
        j
                <<= 2;
      }
    }

    InnerClass anon =
            new InnerClass() {
              void foo() {
                int i = 0;
                int j = 0;
                i +=
                        1;
                j
                        += 2;
                i -=
                        1;
                j
                        -= 2;
                i /=
                        1;
                j
                        /= 2;
                i *=
                        1;
                j
                        *= 2;
                i %=
                        1;
                j
                        %= 2;
                i ^=
                        1;
                j
                        ^= 2;
                i |=
                        1;
                j
                        |= 2;
                i &=
                        1;
                j
                        &= 2;
                i >>=
                        1;
                j
                        >>= 2;
                i >>>=
                        1;
                j
                        >>>= 2;
                i <<=
                        1;
                j
                        <<= 2;
              }
            };

    <T extends Comparable
            & java.io.Serializable>
        void testWrapBeforeOperator() {}
  }
}
