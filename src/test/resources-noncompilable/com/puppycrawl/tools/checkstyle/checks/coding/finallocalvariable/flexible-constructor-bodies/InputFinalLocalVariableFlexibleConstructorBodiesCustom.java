/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable">
      <property name="validateEnhancedForLoopVariable" value="true"/>
      <property name="validateUnnamedVariables" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

class InputFinalLocalVariableFlexibleConstructorBodiesCustom {

    static class Before {
        Before(String[] items) {
            int local = 1; // violation 'Variable 'local' should be declared final.'
            for (String item : items) { // violation 'Variable 'item' should be declared final.'
                System.out.println(item);
            }
            super();
            if (local > 0) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop) {
            super();
            int local = 1; // violation 'Variable 'local' should be declared final.'
            if (stop) {
                return;
            }
        }
    }
}
