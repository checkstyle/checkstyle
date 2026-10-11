package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputFormattedLeftCurlyMethod {
  InputFormattedLeftCurlyMethod() {}

  InputFormattedLeftCurlyMethod(String one) {}

  InputFormattedLeftCurlyMethod(int one) {}

  void method1() {}

  void method2() {}

  void method3() {}

  void method4() {}

  void method5(String one, String two) {}

  void method6(String one, String two) {}

  enum InputLeftCurlyMethodEnum {
    CONSTANT1("hello") {
      void method1() {}

      void method2() {}

      void method3() {}

      void method4() {}

      void method5(String one, String two) {}

      void method6(String one, String two) {}
    },

    CONSTANT2("hello") {},

    CONSTANT3("hellohellohellohellohellohellohellohellohellohellohellohellohellohello") {};

    private InputLeftCurlyMethodEnum(String value) {}

    void method1() {}

    void method2() {}

    void method3() {}

    void method4() {}

    void method5(String one, String two) {}

    void method6(String one, String two) {}
  }
}
