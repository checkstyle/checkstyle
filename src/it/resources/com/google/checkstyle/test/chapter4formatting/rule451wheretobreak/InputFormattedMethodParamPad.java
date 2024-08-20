package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.HashMap;
import java.util.Map;

class InputFormattedMethodParamPad {
  class Inner {
    void testGenerics1() {
      Comparable<String> c = new String();
      Map<String, String> map = new HashMap<String, String>();
      boolean flag = false;

      int init = 9;
    }
  }

  Inner anon =
      new Inner() {
        void testGenerics1() {
          Comparable<String> c = new String();
          Map<String, String> map = new HashMap<String, String>();
          boolean flag = false;
          int init = 9;
        }
      };
}

// violation below 'Top-level class ExtraAsInput1 has to reside in its own source file.'
class ExtraAsInput1 {
  int abc = 0;
  String string = "string";
  double pi = 3.1415;
}

// violation below 'Top-level class ExtraTernary2 has to reside in its own source file.'
class ExtraTernary2 {
  void foo() {
    boolean flag = true;
    int i2 = flag == true ? 1 : 2;
    int i3 = flag == true ? 1 : 2;
  }
}

// violation below 'Top-level class ExtraAssignClass3 has to reside in its own source file.'
class ExtraAssignClass3 {
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
    FIRST() {},

    SECOND() {}
  }
}
