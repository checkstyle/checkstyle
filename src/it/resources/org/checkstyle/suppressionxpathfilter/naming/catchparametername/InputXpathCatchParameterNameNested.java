/*
CatchParameterName
format = (default)^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$


*/

package org.checkstyle.suppressionxpathfilter.naming.catchparametername;

public class InputXpathCatchParameterNameNested {
    public static class NestedClass {
        void method() {
            if (true) {
                try {
                    try {
                    } catch (Exception i) { // violation 'Name 'i' must match pattern'
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
