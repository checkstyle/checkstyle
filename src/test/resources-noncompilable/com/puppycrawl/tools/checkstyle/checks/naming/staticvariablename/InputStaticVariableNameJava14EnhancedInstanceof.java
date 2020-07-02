//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.staticvariablename;

public class InputStaticVariableNameJava14EnhancedInstanceof {
    Object obj = "string";
    {
        if (obj instanceof String str) {
            System.out.println(str);
        }
    }
}
