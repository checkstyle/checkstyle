/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLocalVariable">
      <property name="allowUnnamedVariables" value="false"/>
      <property name="jdkVersion" value="21"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

class InputUnusedLocalVariableFlexibleConstructorBodiesCustom {

    static class Before {
        Before(boolean stop) {
            int unused = 1; // violation 'Unused local variable 'unused'.'
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop) {
            super();
            int used = 2;
            if (stop) {
                System.out.println(used);
                return;
            }
        }
    }
}
