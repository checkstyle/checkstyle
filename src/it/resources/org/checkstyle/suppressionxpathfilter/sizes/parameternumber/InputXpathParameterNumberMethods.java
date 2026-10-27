/*
ParameterNumber
max = 10
ignoreAnnotatedBy = (default)
ignoreOverriddenMethods = (default)false
tokens = METHOD_DEF


*/

package org.checkstyle.suppressionxpathfilter.sizes.parameternumber;

public class InputXpathParameterNumberMethods
        extends InputXpathParameterNumberDefault {

    @Override
    // violation below 'More than 10 parameters'
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h,
                    int k, int l, int m) {
    }
    public InputXpathParameterNumberMethods(int a, int b, int c,
                    int d, int e, int f, int g, int h, int k, int l, int m)
    {
    }
    void myMethod3(int a, int b, int c, int d, int e, int f, int g, int h) {
    }
}
