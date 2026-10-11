package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputLeftCurlyMethod
  { // violation ''{' at column 3 should be on the previous line.'
    InputLeftCurlyMethod() {}

    InputLeftCurlyMethod(String one) {}

    InputLeftCurlyMethod(int one)
    { // violation ''{' at column 5 should be on the previous line.'
    }

    void method1() {}

    void method2() {}

    void method3()
    { // violation ''{' at column 5 should be on the previous line.'
    }

    void method4()
    { // violation ''{' at column 5 should be on the previous line.'
    }

    void method5(String one, String two)
    { // violation ''{' at column 5 should be on the previous line.'
    }

    void method6(String one, String two) {}

    enum InputLeftCurlyMethodEnum
      { // violation ''{' at column 7 should be on the previous line.'
        CONSTANT1("hello")
                { // violation ''{' at column 17 should be on the previous line.'
                  void method1() {}

                  void method2() {}

                  void method3()
                    { // violation ''{' at column 21 should be on the previous line.'
                    }

                  void method4()
                    { // violation ''{' at column 21 should be on the previous line.'
                    }

                  void method5(String one, String two)
                    { // violation ''{' at column 21 should be on the previous line.'
                    }

                  void method6(String one, String two) {}
                },

        CONSTANT2("hello") {},

        CONSTANT3("hellohellohellohellohellohellohellohellohellohellohellohellohellohello")
                { // violation ''{' at column 17 should be on the previous line.'
                };

        private InputLeftCurlyMethodEnum(String value)
        { // violation ''{' at column 9 should be on the previous line.'
        }

        void method1() {}

        void method2() {}

        void method3()
        { // violation ''{' at column 9 should be on the previous line.'
        }

        void method4()
        { // violation ''{' at column 9 should be on the previous line.'
        }

        void method5(String one, String two)
        { // violation ''{' at column 9 should be on the previous line.'
        }

        void method6(String one, String two) {}
      }
  }
