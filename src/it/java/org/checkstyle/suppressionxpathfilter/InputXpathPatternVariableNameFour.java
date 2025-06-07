package org.checkstyle.suppressionxpathfilter;

public class InputXpathPatternVariableNameFour {
    void MyClass(Object o1){
        if (o1 instanceof String st) { // warning
        }
    }
}
