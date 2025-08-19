// java21
package org.checkstyle.suppressionxpathfilter.naming.patternvariablename;

public class InputXpathPatternVariableNameTwo {
    void MyClass(Object o1){
        if (o1 instanceof String s) { // warning
        }
    }
}
