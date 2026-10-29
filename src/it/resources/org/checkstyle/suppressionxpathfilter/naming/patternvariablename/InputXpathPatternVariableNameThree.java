/*
PatternVariableName
format = ^[a-z](_?[a-zA-Z0-9]+)*$


*/

package org.checkstyle.suppressionxpathfilter.naming.patternvariablename;

public class InputXpathPatternVariableNameThree {
    void MyClass(Object o1) {
        // violation below "Name 'STR' must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'."
        if (o1 instanceof String STR) {
        }
    }
}
