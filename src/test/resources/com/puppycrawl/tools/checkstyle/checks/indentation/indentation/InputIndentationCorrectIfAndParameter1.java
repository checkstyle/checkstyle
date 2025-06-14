package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.co; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionFifth; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionFirst; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conditionFourth; //indent:0 exp:0
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation.InputIndentationCorrectIfAndParameter.conNoArg; //indent:0 exp:0
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
            getInteger(new InputIndentationCorrectIfAndParameter(), "Log"), //indent:12 exp:12
                new InputIndentationCorrectIfAndParameter.InnerClassFoo())) {} //indent:16 exp:16

    if (conditionSecond(10000000000.0, new //indent:4 exp:4
        SecondClassLongNam7("Looooooooooooo" //indent:8 exp:8
            + "ooog").getString(new InputIndentationCorrectIfAndParameter(), //indent:12 exp:12
            new SecondClassLongNam7("loooooooooong"). //indent:12 exp:14,16 warn
                    getInteger(new InputIndentationCorrectIfAndParameter(), "long")), "long") //indent:20 exp:20
                        || conditionThird(2048) || conditionFourth(new //indent:24 exp:24
                            SecondClassLongNam7("Looooooo" //indent:28 exp:28
                                + "oo").gB(new InputIndentationCorrectIfAndParameter(), false)) || //indent:32 exp:32
                                    conditionFifth(true, new SecondClassLongNam7(getString(2048, "Looo" //indent:36 exp:36
                                        + "oooong")).gB( //indent:40 exp:40
                                        new InputIndentationCorrectIfAndParameter(), true)) //indent:40 exp:42,44 warn
                                            || co(false, new //indent:44 exp:44
                                                SecondClassLongNam7(getString(100000, "Long" //indent:48 exp:48
                                                    + "Foo><"))) || conNoArg() //indent:52 exp:52
                                                        || conNoArg() || //indent:56 exp:56
                                                            conNoArg() || conNoArg()) {} //indent:60 exp:60
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
