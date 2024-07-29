/*
ParameterAlignment
tokens = (default)METHOD_DEF, CTOR_DEF, RECORD_DEF

*/

//non-compiled with javac: Compilable with Java17

package com.puppycrawl.tools.checkstyle.checks.coding.parameteralignment;

class InputParameterAlignmentRecord
{
    record Attribute1(int a, long b) { }

    record Attribute2(
            int a, double b) { }

    record Attribute2(float a,
                      int b,
                      int c) { }

    record Attribute3(
            int a,
            int b, int c,
            int d, int e, int f, int g, int h) {
    }

    record Attribute4(char a,
        // violation below 'Parameters must be aligned.'
        float b) {
    }

    record Attribute5(char a, int b, int c, int d, int e, int f, int g,
        // violation below 'Parameters must be aligned.'
        int h) {
    }

    record Attribute6(byte a,
        // violation below 'Parameters must be aligned.'
        int b, int c, int d, int e, int f, int g,
            // violation below 'Parameters must be aligned.'
            int h) {
    }

}

