package org.checkstyle.suppressionxpathfilter.parameternumber;

public class InputXpathParameterNumberMethods
        extends InputXpathParameterNumberDefault {

    @Override
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h, // warn
                    int k, int l, int m) {
    }
    public InputXpathParameterNumberMethods(int a, int b, int c,  // ok
                    int d, int e, int f, int g, int h, int k, int l, int m)
    {
    }
    void myMethod3(int a, int b, int c, int d, int e, int f, int g, int h) { // ok
    }
}
