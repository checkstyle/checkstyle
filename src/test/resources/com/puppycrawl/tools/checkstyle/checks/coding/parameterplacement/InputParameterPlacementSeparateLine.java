/*
ParameterPlacement
option = SEPARATE_LINE
tokens = (default)METHOD_DEF, CTOR_DEF, RECORD_DEF, METHOD_CALL, CTOR_CALL, \
         SUPER_CTOR_CALL, LITERAL_NEW, ANNOTATION


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterplacement;

class InputParameterPlacementSeparateLine
{
    InputParameterPlacementSeparateLine() {

    }

    InputParameterPlacementSeparateLine(int a) {

    }

    InputParameterPlacementSeparateLine(
            char a) {

    }

    // violation below 'Parameters must be placed on separate lines.'
    InputParameterPlacementSeparateLine(int a, int b) {

    }

    InputParameterPlacementSeparateLine(char a,
            int b) {

    }

    InputParameterPlacementSeparateLine(
            byte a,
            // violation below 'Parameters must be placed on separate lines.'
            int b, int c,
            int d) {

    }

    InputParameterPlacementSeparateLine(
            float a,
            int b,
            int c) {

    }

    void noParamMethod() {

    }

    void singleParamMethodInline(int a) {

    }

    void singleParamMethodSeparateLine(
            int a) {

    }

    // violation below 'Parameters must be placed on separate lines.'
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) {

    }

    // violation below 'Parameters must be placed on separate lines.'
    void myMethod2(int a, int b, int c, int d, int e, int f, int g,
            int h) {

    }

    void myMethod2(byte a,
            // violation below 'Parameters must be placed on separate lines.'
            int b, int c, int d, int e, int f, int g,
            int h) {

    }

    void myMethod2(
            float a,
            int b,
            int c,
            int d,
            int e,
            int f,
            int g,
            int h) {

    }

}

