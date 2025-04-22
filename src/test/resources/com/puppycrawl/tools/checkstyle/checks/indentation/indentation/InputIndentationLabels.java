package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

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
class InputIndentationLabels { //indent:0 exp:0
  void foo() { //indent:2 exp:2
//    OUT: //indent:0 exp:0
    while (true) { //indent:4 exp:4
    } //indent:4 exp:4
  } //indent:2 exp:2

  void foo2() { //indent:2 exp:2
    positions: while (true) { //indent:4 exp:4
    } //indent:4 exp:4
  } //indent:2 exp:2

  void foo3() { //indent:2 exp:2
    OUT1: //indent:4 exp:4
    for (;;) { //indent:4 exp:4
      if (true){ //indent:6 exp:6
        break OUT1; //indent:8 exp:8
      } //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2

  void foo4() { //indent:2 exp:2
    OUT1: for (;;) { //indent:4 exp:4
      if (true){ //indent:6 exp:6
        break OUT1; //indent:8 exp:8
      } //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2

  void fooo() { //indent:2 exp:2
    IN: if (true) { //indent:4 exp:4
    } //indent:4 exp:4
  } //indent:2 exp:2

  void fooo1() { //indent:2 exp:2
    IN: //indent:4 exp:4
    if (true) { //indent:4 exp:4
    } //indent:4 exp:4
  } //indent:2 exp:2

  void foooo() { //indent:2 exp:2
    IN: do {} while (true); //indent:4 exp:4
  } //indent:2 exp:2

  void foooo1() { //indent:2 exp:2
    IN: //indent:4 exp:4
    do {} while (true); //indent:4 exp:4
  } //indent:2 exp:2

  class Inner { //indent:2 exp:2
    void foo() { //indent:4 exp:4
      OUT: while (true) { //indent:6 exp:6
      } //indent:6 exp:6
    } //indent:4 exp:4

    void foo2() { //indent:4 exp:4
      positions: //indent:6 exp:6
      while (true) { //indent:6 exp:6
      } //indent:6 exp:6
    } //indent:4 exp:4

    void foo5() { //indent:4 exp:4
      OUT1: //indent:6 exp:6
      for (;;) { //indent:6 exp:6
        if (true){ //indent:8 exp:8
          break OUT1; //indent:10 exp:10
        } //indent:8 exp:8
      } //indent:6 exp:6
    } //indent:4 exp:4

    void foo6() { //indent:4 exp:4
      OUT1: for (;;) { //indent:6 exp:6
        if (true){ //indent:8 exp:8
          break OUT1; //indent:10 exp:10
        } //indent:8 exp:8
      } //indent:6 exp:6
    } //indent:4 exp:4

    void fooo11() { //indent:4 exp:4
      IN: if (true) { //indent:6 exp:6
      } //indent:6 exp:6
    } //indent:4 exp:4

    void fooo12() { //indent:4 exp:4
      IN: //indent:6 exp:6
      if (true) { //indent:6 exp:6
      } //indent:6 exp:6
    } //indent:4 exp:4

    void foooo3() { //indent:4 exp:4
      IN: do {} while (true); //indent:6 exp:6
    } //indent:4 exp:4

    void foooo4() { //indent:4 exp:4
      IN: //indent:6 exp:6
      do {} while (true); //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
