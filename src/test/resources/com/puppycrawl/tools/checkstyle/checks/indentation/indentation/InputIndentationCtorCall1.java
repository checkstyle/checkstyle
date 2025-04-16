package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationCtorCall1 extends InputIndentationCtorCall { //indent:0 exp:0

  class Valid extends Base { //indent:2 exp:2

    public Valid(int arg) { //indent:4 exp:4
      super( //indent:6 exp:6
          arg //indent:10 exp:10
            + 1L); //indent:12 exp:12
    } //indent:4 exp:4

    public Valid(long arg) { //indent:4 exp:4
      new InputIndentationCtorCall().super( //indent:6 exp:6
          arg //indent:10 exp:10
            + 1L); //indent:12 exp:12
    } //indent:4 exp:4

    public Valid() { //indent:4 exp:4
      this( //indent:6 exp:6
          0L); //indent:10 exp:10
    } //indent:4 exp:4

    public Valid(InputIndentationCtorCall obj, long arg) { //indent:4 exp:4
      obj.super( //indent:6 exp:6
          arg); //indent:10 exp:10
    } //indent:4 exp:4

    public Valid(InputIndentationCtorCall obj, char arg) { //indent:4 exp:4
      obj.super( //indent:6 exp:6
          x -> arg); //indent:10 exp:10
    } //indent:4 exp:4

    public Valid(InputIndentationCtorCall obj, int arg) { //indent:4 exp:4
      obj //indent:6 exp:6
          .super( //indent:10 exp:10
            arg); //indent:12 exp:12
    } //indent:4 exp:4

    public Valid(InputIndentationCtorCall obj, float arg) { //indent:4 exp:4
      obj. //indent:6 exp:6
          super( //indent:10 exp:10
              x -> arg); //indent:14 exp:14
    } //indent:4 exp:4

    public Valid(InputIndentationCtorCall obj, double arg) { //indent:4 exp:4
      obj. //indent:6 exp:6
          super( //indent:10 exp:10
            x -> arg); //indent:12 exp:12
    } //indent:4 exp:4

  } //indent:2 exp:2

} //indent:0 exp:0
