/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLocalVariable"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

class InputUnusedLocalVariableFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop) {
            int unused = 1; // violation 'Unused named local variable 'unused'.'
            int used = 2;
            super();
            if (stop) {
                return;
            }
            System.out.println(used);
        }
    }

    static class After {
        After(boolean stop) {
            super();
            int unused = 1; // violation 'Unused named local variable 'unused'.'
            if (stop) {
                return;
            }
        }
    }
}
