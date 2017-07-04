package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
@MyAnnotation1 //indent:0 exp:0
public class //indent:0 exp:0
    InputAnonymousClasses { //indent:4 exp:4
  public InputAnonymousClasses(String longString, String secondLongString) { //indent:2 exp:2

  } //indent:2 exp:2
  public boolean foo() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  void foo2(StrangeInstance instance) {} //indent:2 exp:2
} //indent:0 exp:0

class WithAnonnymousClass { //indent:0 exp:0
  public static final InputAnonymousClasses anon = new InputAnonymousClasses("Looooooooooooooooong", //indent:2 exp:2
      "SecondLoooooooooooong") { //indent:6 exp:6
    @Override public boolean foo() { //indent:4 exp:4
      return false; //indent:6 exp:6
    } //indent:4 exp:4
  }; //indent:2 exp:2

  InputAnonymousClasses foo() { //indent:2 exp:2
    return new InputAnonymousClasses( //indent:4 exp:4
        "Loooooooooooooooong", "SecondLoooooooooong") { //indent:8 exp:>=8
          @Override public boolean foo() { //indent:10 exp:10
            InputAnonymousClasses InputAnonymousClasses = new InputAnonymousClasses("", ""); //indent:12 exp:12
            InputAnonymousClasses.equals(new StrangeInstance(new InputAnonymousClasses("", "")) { //indent:12 exp:12
              @Override void foo (String loongString, String secondLongString) {} //indent:14 exp:14
            }); //indent:12 exp:12
            return false; //indent:12 exp:12
          } //indent:10 exp:10
        }; //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0

class StrangeInstance { //indent:0 exp:0
  public StrangeInstance(InputAnonymousClasses InputAnonymousClasses) {} //indent:2 exp:2
  void foo (String loongString, String secondLongString) {} //indent:2 exp:2
} //indent:0 exp:0

@interface MyAnnotation1 {} //indent:0 exp:0
