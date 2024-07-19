package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputLeftCurlyMethod
{ // violation ''{' at column 1 should be on the previous line.'
  InputLeftCurlyMethod() {}

  InputLeftCurlyMethod(String aOne) {}

  InputLeftCurlyMethod(int aOne)
  { // violation ''{' at column 3 should be on the previous line.'
  }

  void method1() {}

  void method2() {}

  void method3()
  { // violation ''{' at column 3 should be on the previous line.'
  }

  void method4()
  { // violation ''{' at column 3 should be on the previous line.'
  }

  void method5(String aOne, String aTwo)
  { // violation ''{' at column 3 should be on the previous line.'
  }

  void method6(String aOne, String aTwo) {}
}

enum InputLeftCurlyMethodEnum
{ // violation ''{' at column 1 should be on the previous line.'
  CONSTANT1("hello")
  { // violation ''{' at column 3 should be on the previous line.'
    void method1() {}

    void method2() {}

    void method3()
    { // violation ''{' at column 5 should be on the previous line.'
    }

    void method4()
    { // violation ''{' at column 5 should be on the previous line.'
    }

    void method5(String aOne, String aTwo)
    { // violation ''{' at column 5 should be on the previous line.'
    }

    void method6(String aOne, String aTwo) {}
  },

  CONSTANT2("hello") {},

  CONSTANT3("hellohellohellohellohellohellohellohellohellohellohellohellohellohello")
  { // violation ''{' at column 3 should be on the previous line.'
  };

  private InputLeftCurlyMethodEnum(String value)
  { // violation ''{' at column 3 should be on the previous line.'
  }

  void method1() {}

  void method2() {}

  void method3()
  { // violation ''{' at column 3 should be on the previous line.'
  }

  void method4()
  { // violation ''{' at column 3 should be on the previous line.'
  }

  void method5(String aOne, String aTwo)
  { // violation ''{' at column 3 should be on the previous line.'
  }

  void method6(String aOne, String aTwo) {}
}
