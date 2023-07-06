//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

public class InputOuterTypeFileNameBegin1 {
    MyClass(Object o1){ // violation above 'The name of the outer type and the file do not match'
        if (o1 instanceof String st) {
        }
    }
}
