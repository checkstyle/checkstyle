/*
ParameterPlacement
option = SEPARATE_LINE
tokens = (default)METHOD_DEF, CTOR_DEF, RECORD_DEF

*/

//non-compiled with javac: Compilable with Java17

package com.puppycrawl.tools.checkstyle.checks.coding.parameterplacement;

class InputParameterPlacementRecordSeparateLine
{
    // violation below 'Parameters must be placed on separate lines.'
    record Attribute1(int a, long b) { }

    record Attribute2(
            // violation below 'Parameters must be placed on separate lines.'
            int a, double b) { }

    record Attribute3(float a,
                      int b,
                      int c) { }

    record Attribute4(
            int a,
            // violation below 'Parameters must be placed on separate lines.'
            int b, int c,
            // violation below 'Parameters must be placed on separate lines.'
            int d, int e, int f, int g, int h) {
    }

    record Attribute5(char a,
        float b) {
    }

    // violation below 'Parameters must be placed on separate lines.'
    record Attribute6(char a, int b, int c, int d, int e, int f, int g,
        int h) {
    }

    record Attribute7(byte a,
        // violation below 'Parameters must be placed on separate lines.'
        int b, int c, int d, int e, int f, int g,
            int h) {
    }

    record Attribute8(
            int a,
            long b) { }

    record Attribute9(
            int a) { }

}
