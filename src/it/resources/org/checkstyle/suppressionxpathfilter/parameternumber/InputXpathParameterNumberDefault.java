package org.checkstyle.suppressionxpathfilter.parameternumber;

public class InputXpathParameterNumberDefault {

    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h, // warn
                  int i, int j, int k) {
    }

    public InputXpathParameterNumberDefault() { // ok
    }

    void myMethod2(int a, int b, int c, int d) { // ok
    }

}
