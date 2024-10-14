/*
ParameterPlacement
option = OWN_LINE_ALLOW_SINGLE_LINE
tokens = (default)METHOD_DEF, CTOR_DEF, RECORD_DEF

*/

//non-compiled with javac: Compilable with Java17

package com.puppycrawl.tools.checkstyle.checks.coding.parameterplacement;

class InputParameterPlacementRecordOwnLineAllowSingleLine
{
    record Attribute1(int a, long b) { }

    record Attribute2(
            int a, double b) { }

    // violation below 'Parameters must be placed on separate lines.'
    record Attribute2(float a,
                      int b,
                      int c) { }

    record Attribute3(
            int a,
            // violation below 'Parameters must be placed on separate lines.'
            int b, int c,
            // violation below 'Parameters must be placed on separate lines.'
            int d, int e, int f, int g, int h) {
    }

    // violation below 'Parameters must be placed on separate lines.'
    record Attribute4(char a,
        float b) {
    }

    // violation below 'Parameters must be placed on separate lines.'
    record Attribute5(char a, int b, int c, int d, int e, int f, int g,
        int h) {
    }

    // violation below 'Parameters must be placed on separate lines.'
    record Attribute6(byte a,
        // violation below 'Parameters must be placed on separate lines.'
        int b, int c, int d, int e, int f, int g,
            int h) {
    }

    record Attribute7(
            int a,
            long b) { }

    record Attribute8(
            int a) { }

}
