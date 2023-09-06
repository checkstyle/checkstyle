//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming;

public class SuppressionXpathRegressionPatternVariableName3 {
    MyClass(Object o1){ 
        if (o1 instanceof String STR) { // warning
        } 
    }
}
