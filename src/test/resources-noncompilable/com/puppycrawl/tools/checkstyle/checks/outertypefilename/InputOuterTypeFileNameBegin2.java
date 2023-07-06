//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

public class InputOuterTypeFileNameBegin1 {
     // violation above 'The name of the outer type and the file do not match'
    void MyClass(Object o1){
        if (o1 instanceof String st) {
        }
    }
}
