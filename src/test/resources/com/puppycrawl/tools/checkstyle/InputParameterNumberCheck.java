package com.puppycrawl.tools.checkstyle;

class InputParameterNumberCheckBase
{
    // method with many parameters
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) {

    }

    // method with many parameters
    void myMethod2(int a, int b, int c, int d, int e, int f, int g, int h) {

    }
}

public class InputParameterNumberCheck extends InputParameterNumberCheckBase
{
    @Override
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) {

    }

    @java.lang.Override
    void myMethod2(int a, int b, int c, int d, int e, int f, int g, int h) {

    }
}
