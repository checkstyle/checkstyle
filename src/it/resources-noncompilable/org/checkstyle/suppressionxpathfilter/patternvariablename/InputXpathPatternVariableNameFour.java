// non-compiled with javac: compilable with java21
package com.puppycrawl.tools.checkstyle.checks.naming;

public class InputXpathPatternVariableNameFour {
    MyClass(Object o1){
        if (o1 instanceof String st) { // warning
        }
    }
}
