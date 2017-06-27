package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 2                                                        //indent:1 exp:1
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
class InputIndentationArrays { //indent:0 exp:0
  /**                             //indent:2 exp:2
   * Look-up table for factories. //indent:3 exp:3
   */                             //indent:3 exp:3
  static final int[] factories = { 666666, 666666, //indent:2 exp:2
    666666, 666666, 666666, //indent:4 exp:4
    666666, 666666, 666666, //indent:4 exp:4
  }; //indent:2 exp:2

  static final int[][] factories1 = { //indent:2 exp:2
    { 666666, 666666, 666666, 666666 }, //indent:4 exp:4
    {}, // no support for SOFT keys //indent:4 exp:4
    { 666666, 666666, 666666, 666666 } //indent:4 exp:4
  }; //indent:2 exp:2

  // binomial(biggestBinomials[k], k) fits in an int, but not //indent:2 exp:2
  // binomial(biggestBinomials[k]+1,k).                       //indent:2 exp:2
  static int[] biggestBinomials = { //indent:2 exp:2
    Integer.MAX_VALUE, //indent:4 exp:4
    Integer.MAX_VALUE, //indent:4 exp:4
    65536, //indent:4 exp:4
    2345, //indent:4 exp:4
    477, //indent:4 exp:4
    193, //indent:4 exp:4
    110, //indent:4 exp:4
    75, //indent:4 exp:4
    58, //indent:4 exp:4
    49, //indent:4 exp:4
    43, //indent:4 exp:4
    39, //indent:4 exp:4
    37, //indent:4 exp:4
    35, //indent:4 exp:4
    34, //indent:4 exp:4
    34, //indent:4 exp:4
    33 //indent:4 exp:4
  }; //indent:2 exp:2

  @VisibleForTesting static final int[] halfPowersOf10 = //indent:2 exp:2
      {3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE}; //indent:6 exp:6

  public byte[] asBytes() { //indent:2 exp:2
    byte hash = 0; //indent:4 exp:4
    return new byte[] { //indent:4 exp:4
        (byte) hash, //indent:8 exp:8
        (byte) (hash >> 8), //indent:8 exp:8
        (byte) (hash >> 16), //indent:8 exp:8
        (byte) (hash >> 24), //indent:8 exp:8
        (byte) (hash >> 32), //indent:8 exp:8
        (byte) (hash >> 40), //indent:8 exp:8
        (byte) (hash >> 48), //indent:8 exp:8
        (byte) (hash >> 56)}; //indent:8 exp:8
  } //indent:2 exp:2
} //indent:0 exp:0

@interface VisibleForTesting {} //indent:0 exp:0
