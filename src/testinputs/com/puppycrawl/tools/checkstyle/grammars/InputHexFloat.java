package com.puppycrawl.tools.checkstyle.grammars;

/**
 * Input for hex float and double test.
 */
public class InputHexFloat
{
    double f1 = 0x.0P10;
    double f2 = 0x1.P-1;
    double f3 = 0Xab1P0;
    double f4 = 0Xab1ap+20;
    double f5 = 0Xab1ap+20D;
    double f6 = 0Xab1ap+20d;
    double f7 = 0Xab1ap+20f;
    double f8 = 0Xab1ap+20F;
}
