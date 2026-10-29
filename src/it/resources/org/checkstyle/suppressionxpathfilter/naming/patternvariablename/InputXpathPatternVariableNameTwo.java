/*
PatternVariableName
format = ^_[a-zA-Z0-9]*$


*/

package org.checkstyle.suppressionxpathfilter.naming.patternvariablename;

public class InputXpathPatternVariableNameTwo {
    void MyClass(Object o1){
        // violation below "Name 's' must match pattern '^_[a-zA-Z0-9]*$'."
        if (o1 instanceof String s) {
        }
    }
}
