/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavaNCSS"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;

class InputJavaNCSSFlexibleConstructorBodies {

    static class Before {
        // violation below 'NCSS for this method is 58 (max allowed is 50).'
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
        After(boolean stop) {
            super();
            int value = 0;
            value++;
            if (stop) {
                return;
            }
        }
    }
}
