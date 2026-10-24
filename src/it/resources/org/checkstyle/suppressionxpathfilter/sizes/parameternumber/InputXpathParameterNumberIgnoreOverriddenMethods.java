/*
ParameterNumber
max = (default)7
ignoreAnnotatedBy = (default)
ignoreOverriddenMethods = true
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.sizes.parameternumber;

public class InputXpathParameterNumberIgnoreOverriddenMethods
        extends InputXpathParameterNumberDefault {

     // violation below 'More than 7 parameters'
     public InputXpathParameterNumberIgnoreOverriddenMethods(int a,
                    int b, int c, int d, int e, int f, int g, int h)
    {
    }
    @Override
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h,
                    int k, int l, int m) {
    }
}
