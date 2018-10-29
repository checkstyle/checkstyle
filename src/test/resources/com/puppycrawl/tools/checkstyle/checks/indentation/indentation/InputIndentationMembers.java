package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0
import java.util.HashMap; //indent:0 exp:0
import java.util.Map; //indent:0 exp:0

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
class InputIndentationMembers { //indent:0 exp:0

  boolean flag //indent:2 exp:2
     = conditionFirst("Loooooooooooooooooong", new  //indent:5 exp:6 warn
      SecondFieldClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:6 exp:6
      getInteger(new InputIndentationMembers(), "Log"), new InnerClassFoo()); //indent:6 exp:6


  String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionFirst(String longString, int //indent:2 exp:2
      integer, InnerClassFoo someInstance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionSecond(double longLongLongDoubleValue, //indent:2 exp:2
      String longLongLongString, String secondLongLongString) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  class InnerClassFoo { //indent:2 exp:2

    boolean flag //indent:4 exp:4
        = conditionFirst("Loooooooooooooooooong", new //indent:8 exp:8
        SecondFieldClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). //indent:8 exp:8
            getInteger(new InputIndentationMembers(), "Loooooooooog"), //indent:12 exp:>=8
             new InnerClassFoo()); //indent:13 exp:>=8

    InputIndentationMembers anonymousClass = //indent:4 exp:4
        new InputIndentationMembers() { //indent:8 exp:8
          boolean secondFlag = conditionSecond(10000000000.0, new //indent:10 exp:10
              SecondFieldClassWithVeryVeryVeryLongName("Looooooooooooo" //indent:14 exp:14
                + "oooooooooooong").getString(new InputIndentationMembers(), //indent:16 exp:>=14
                   new SecondFieldClassWithVeryVeryVeryLongName("looooooong"). //indent:19 exp:>=14
                 getInteger(new InputIndentationMembers(), "lg")), "loooong"); //indent:17 exp:>=14
        }; //indent:8 exp:8
   } //indent:3 exp:2 warn
} //indent:0 exp:0

class SecondFieldClassWithVeryVeryVeryLongName { //indent:0 exp:0

  public SecondFieldClassWithVeryVeryVeryLongName(String string) { //indent:2 exp:2

  } //indent:2 exp:2

  String getString(InputIndentationMembers instance, int integer) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  int getInteger(InputIndentationMembers instance, String string) { //indent:2 exp:2
    return -1;   //indent:4 exp:4
  } //indent:2 exp:2

  boolean getBoolean(InputIndentationMembers instance, boolean flag) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  SecondFieldClassWithVeryVeryVeryLongName getInstance() { //indent:2 exp:2
    return new SecondFieldClassWithVeryVeryVeryLongName("VeryLoooooooooo" //indent:4 exp:4
        + "oongString"); //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0

abstract class WithAnnotations { //indent:0 exp:0
  @GwtIncompatible("Non-UTF-8" //indent:2 exp:2
       + "Charset") //indent:7 exp:7
  public static final int FOO_CONSTANT = 111; //indent:2 exp:2

  private void foo34() {} //indent:2 exp:2
  final Map<String, String> //indent:2 exp:2
      comeMapWithLongName = new HashMap //indent:6 exp:6
      <String, String>(); //indent:6 exp:6

  @MyAnnotation //indent:2 exp:2
  byte[] getBytesInternal() { //indent:2 exp:2
    return new byte[] {}; //indent:4 exp:4
  } //indent:2 exp:2

  @MyAnnotation public abstract <K, V> Map<K, V> rowMap(); //indent:2 exp:2
} //indent:0 exp:0

@interface GwtIncompatible { //indent:0 exp:0
  String value(); //indent:2 exp:2
} //indent:0 exp:0

@interface MyAnnotation {} //indent:0 exp:0

@MyAnnotation //indent:0 exp:0
enum MyEnum{ //indent:0 exp:0

} //indent:0 exp:0
