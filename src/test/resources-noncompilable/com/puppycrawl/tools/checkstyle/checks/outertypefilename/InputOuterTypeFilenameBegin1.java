//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

// ok
public class InputOuterTypeFilenameBegin1 {
   void MyClass(Object o1){
       if (o1 instanceof String STRING1) {
       }
   }
}
