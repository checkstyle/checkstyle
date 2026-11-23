/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExecutableStatementCount"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

class InputExecutableStatementCountFlexibleConstructorBodies {

    static class Before {
        // violation below 'Executable statement count is 31 (max allowed is 30).'
        Before(boolean stop) {
            int value = 0;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            super();
        }
    }

    static class After {
        // violation below 'Executable statement count is 31 (max allowed is 30).'
        After(boolean stop) {
            super();
            int value = 0;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
            value++;
        }
    }
}
