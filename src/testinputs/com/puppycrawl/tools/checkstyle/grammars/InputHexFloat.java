package com.puppycrawl.tools.checkstyle.grammars;

/**
 * Input for hex float and double test.
 */
public class InputHexFloat
{
    float f1 = 0x.0P10;
    float f2 = 0x1.P-1;
    float f3 = 0Xab1P0;
    float f4 = 0Xab1ap+20;
    float f5 = 0Xab1ap+20D;
    float f6 = 0Xab1ap+20d;
    float f5 = 0Xab1ap+20f;
    float f6 = 0Xab1ap+20F;
}
