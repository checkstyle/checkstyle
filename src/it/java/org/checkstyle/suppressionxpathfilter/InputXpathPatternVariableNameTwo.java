package org.checkstyle.suppressionxpathfilter;

public class InputXpathPatternVariableNameTwo {
    void MyClass(Object o1){
        if (o1 instanceof String s) { // warning
        }
    }
}
