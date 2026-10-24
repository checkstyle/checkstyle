/*
ParameterNumber
max = (default)7
ignoreAnnotatedBy = (default)
ignoreOverriddenMethods = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.sizes.parameternumber;

public class InputXpathParameterNumberDefault {

    // violation below 'More than 7 parameters'
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h,
                  int i, int j, int k) {
    }

    public InputXpathParameterNumberDefault() {
    }

    void myMethod2(int a, int b, int c, int d) {
    }

}
