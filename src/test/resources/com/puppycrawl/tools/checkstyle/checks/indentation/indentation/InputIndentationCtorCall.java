package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.function.Function; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 */                                                                           //indent:1 exp:1
class InputIndentationCtorCall { //indent:0 exp:0

  class Base { //indent:2 exp:2

    public Base(long arg) { //indent:4 exp:4
    } //indent:4 exp:4

    public Base(Function arg) { //indent:4 exp:4
    } //indent:4 exp:4

  } //indent:2 exp:2

  class Invalid extends Base { //indent:2 exp:2

    public Invalid(long arg) { //indent:4 exp:4
    super( //indent:4 exp:6 warn
    arg //indent:4 exp:6 warn
    + 1L); //indent:4 exp:6 warn
    } //indent:4 exp:4

    public Invalid() { //indent:4 exp:4
    this( //indent:4 exp:6 warn
    0L); //indent:4 exp:6 warn
    } //indent:4 exp:4

    public Invalid(int arg) { //indent:4 exp:4
    new InputIndentationCtorCall().super( //indent:4 exp:6 warn
    arg //indent:4 exp:8 warn
    + 1L); //indent:4 exp:8 warn
    } //indent:4 exp:4

    public Invalid(InputIndentationCtorCall obj, long arg) { //indent:4 exp:4
    obj.super( //indent:4 exp:6 warn
    arg); //indent:4 exp:8 warn
    } //indent:4 exp:4

    public Invalid(InputIndentationCtorCall arg) { //indent:4 exp:4
    arg.super //indent:4 exp:6 warn
    ( //indent:4 exp:8 warn
    x -> x); //indent:4 exp:8 warn
    } //indent:4 exp:4

    public Invalid(char arg) { //indent:4 exp:4
    this //indent:4 exp:6 warn
    (); //indent:4 exp:6 warn
    } //indent:4 exp:4

    public Invalid(InputIndentationCtorCall obj, int arg) { //indent:4 exp:4
      obj //indent:6 exp:6
    .super( //indent:4 exp:10 warn
    arg); //indent:4 exp:8 warn
    } //indent:4 exp:4

    public Invalid(InputIndentationCtorCall obj, char arg) { //indent:4 exp:4
      obj. //indent:6 exp:6
    super( //indent:4 exp:10 warn
    arg); //indent:4 exp:8 warn
    } //indent:4 exp:4

    public Invalid(InputIndentationCtorCall obj, double arg) { //indent:4 exp:4
      obj. //indent:6 exp:6
          super( //indent:10 exp:10
          x -> arg); //indent:10 exp:12,14 warn
    } //indent:4 exp:4

  } //indent:2 exp:2

} //indent:0 exp:0
