/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

class InputFinalLocalVariableFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop) {
            int local = 1; // violation 'Variable 'local' should be declared final.'
            int other = 2;
            other = 3;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(String[] items) {
            super();
            int local = 1; // violation 'Variable 'local' should be declared final.'
            for (String item : items) {
                System.out.println(item);
            }
            if (local > 0) {
                return;
            }
        }
    }
}
