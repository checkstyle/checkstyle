//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.membername;

public class InputMemberNameJava14EnhancedInstanceof {
    Object obj = "object"; //violation
    {
        if (obj instanceof String str) { // str is local variable, no violation
            System.out.println(str);
        }
    }
}
