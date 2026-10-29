/*
PatternVariableName
format = ^[a-z][_a-zA-Z0-9]{2,}$


*/

package org.checkstyle.suppressionxpathfilter.naming.patternvariablename;

public class InputXpathPatternVariableNameFour {
    void MyClass(Object o1){
        // violation below "Name 'st' must match pattern '^[a-z][_a-zA-Z0-9]{2,}$'."
        if (o1 instanceof String st) {
        }
    }
}
