/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExecutableStatementCount">
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

class InputExecutableStatementCountFlexibleConstructorBodiesCustom {

    static class Before {
        // violation below 'Executable statement count is 4 (max allowed is 3).'
        Before(boolean stop) {
            int value = 0;
            value++;
            value++;
            super();
        }
    }

    static class After {
        After(boolean stop) {
            super();
            int value = 0;
            value++;
        }
    }
}
