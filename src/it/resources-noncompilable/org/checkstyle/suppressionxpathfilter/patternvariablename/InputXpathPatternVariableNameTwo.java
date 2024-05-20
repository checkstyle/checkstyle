//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming;

public class InputXpathPatternVariableNameTwo {
    MyClass(Object o1){
        if (o1 instanceof String s) { // warning
        }
    }
}
