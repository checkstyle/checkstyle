/*
CatchParameterName
format = (default)^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$


*/

package org.checkstyle.suppressionxpathfilter.naming.catchparametername;

public class InputXpathCatchParameterNameSimple {
    void method() {
        try {
        // violation below 'Name 'e1' must match pattern '^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$'.'
        } catch (Exception e1) {
        }
    }
}
