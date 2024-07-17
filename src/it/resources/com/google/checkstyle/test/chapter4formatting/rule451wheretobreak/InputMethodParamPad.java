package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.HashMap;
import java.util.Map;

class InputMethodParamPad {
  class Inner {
    void testGenerics1
      () // violation ''(' should be on the previous line.'
        {
      Comparable<String> c = new String();
      Map<String, String> map = new HashMap<String, String>();
      boolean flag = false;

      int init = 9;
    }
  }

  Inner anon = new Inner
          () { // violation ''(' should be on the previous line.'
        void testGenerics1
          () // violation ''(' should be on the previous line.'
            {
          Comparable<String> c = new String();
          Map<String, String> map = new HashMap<String, String>();
          boolean flag = false;
          int init = 9;
        }
      };
}

class AsInput1 {
  int abc = 0;
  String string = "string";
  double PI = 3.1415;
}

class Ternary2 {
  void foo() {
    boolean flag = true;
    int i2 = flag == true ? 1 : 2;
    int i3 = flag == true ? 1 : 2;
  }
}

class AssignClass3 {
  void foo() {
    int i = 0;
    int j = 0;
    i += 1;
    j += 2;
    i -= 1;
    j -= 2;
    i /= 1;
    j /= 2;
    i *= 1;
    j *= 2;
    i %= 1;
    j %= 2;
    i ^= 1;
    j ^= 2;
    i |= 1;
    j |= 2;
    i &= 1;
    j &= 2;
    i >>= 1;
    j >>= 2;
    i >>>= 1;
    j >>>= 2;
    i <<= 1;
    j <<= 2;
  }

  class InnerClass {
    void foo() {
      int i = 0;
      int j = 0;
      i += 1;
      j += 2;
      i -= 1;
      j -= 2;
      i /= 1;
      j /= 2;
      i *= 1;
      j *= 2;
      i %= 1;
      j %= 2;
      i ^= 1;
      j ^= 2;
      i |= 1;
      j |= 2;
      i &= 1;
      j &= 2;
      i >>= 1;
      j >>= 2;
      i >>>= 1;
      j >>>= 2;
      i <<= 1;
      j <<= 2;
    }
  }

  InnerClass anon =
      new InnerClass() {
        void foo() {
          int i = 0;
          int j = 0;
          i += 1;
          j += 2;
          i -= 1;
          j -= 2;
          i /= 1;
          j /= 2;
          i *= 1;
          j *= 2;
          i %= 1;
          j %= 2;
          i ^= 1;
          j ^= 2;
          i |= 1;
          j |= 2;
          i &= 1;
          j &= 2;
          i >>= 1;
          j >>= 2;
          i >>>= 1;
          j >>>= 2;
          i <<= 1;
          j <<= 2;
        }
      };

  enum TestEnum {
    // violation below ''(' is preceded with whitespace.'
    FIRST () {},

    SECOND
    () // violation ''(' should be on the previous line.'
    {}
  }
}
