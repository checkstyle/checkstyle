// java21
package org.checkstyle.suppressionxpathfilter.naming.patternvariablename;

public class InputXpathPatternVariableNameThree {
    void MyClass(Object o1) {
        if (o1 instanceof String STR) { // warning
        }
    }
}
