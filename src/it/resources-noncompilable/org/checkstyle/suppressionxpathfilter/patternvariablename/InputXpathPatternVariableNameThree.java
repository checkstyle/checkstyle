//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming;

public class InputXpathPatternVariableNameThree {
    MyClass(Object o1){ 
        if (o1 instanceof String STR) { // warning
        } 
    }
}
