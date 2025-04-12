package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.co; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionFifth; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionFirst; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionFourth; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionNoArg; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionSecond; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionThird; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.getString; //indent:0 exp:0

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
public class InputIndentationCorrectIfAndParameter1 { //indent:0 exp:0

  void fooMethodWithIf() { //indent:2 exp:2

    if (conditionFirst("Loooooooooooooooooong", new //indent:4 exp:4
        SecondClassLongNam7("Loooooooooooooooooog"). //indent:8 exp:8
        getInteger(new InputIndentationCorrectIfAndParameter(), "Looooog"), //indent:8 exp:8
        new InputIndentationCorrectIfAndParameter.InnerClassFoo())) {} //indent:8 exp:8

    if (conditionSecond(10000000000.0, new //indent:4 exp:4
        SecondClassLongNam7("Looooooooooooo" //indent:8 exp:8
        + "oooooooooooong").getString(new InputIndentationCorrectIfAndParameter(), //indent:8 exp:8
        new SecondClassLongNam7("loooooooooong"). //indent:8 exp:10,12 warn
        getInteger(new InputIndentationCorrectIfAndParameter(), "long")), "loong") //indent:8 exp:8
        || conditionThird(2048) || conditionFourth(new //indent:8 exp:8
        SecondClassLongNam7("Looooooooooooooo" //indent:8 exp:8
        + "oo").gB(new InputIndentationCorrectIfAndParameter(), false)) || //indent:8 exp:8
        conditionFifth(true, new SecondClassLongNam7(getString(2048, "Looo" //indent:8 exp:8
        + "ooooooooooooooooooooooooooooooooooooooooooong")).gB( //indent:8 exp:8
        new InputIndentationCorrectIfAndParameter(), true)) //indent:8 exp:10,12 warn
        || co(false, new //indent:8 exp:8
        SecondClassLongNam7(getString(100000, "Loooooong" //indent:8 exp:8
        + "Fooooooo><"))) || conditionNoArg() //indent:8 exp:8
        || conditionNoArg() || //indent:8 exp:8
        conditionNoArg() || conditionNoArg()) {} //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0

class SecondClassLongNam7 { //indent:0 exp:0

  public SecondClassLongNam7(String string) { //indent:2 exp:2

  } //indent:2 exp:2

  String getString(InputIndentationCorrectIfAndParameter instance, int integer) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  int getInteger(InputIndentationCorrectIfAndParameter instance, String string) { //indent:2 exp:2
    return -1;   //indent:4 exp:4
  } //indent:2 exp:2

  boolean gB(InputIndentationCorrectIfAndParameter instance,boolean flag){ //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  SecondClassLongNam7 getInstance() { //indent:2 exp:2
    return new SecondClassLongNam7("VeryLoooooooooo" //indent:4 exp:4
        + "oongString"); //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0
