package org.checkstyle.suppressionxpathfilter;

public class InputXpathPatternVariableNameThree {
    void MyClass(Object o1){
        if (o1 instanceof String STR) { // warning
        } 
    }
}
