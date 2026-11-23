/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavaNCSS">
      <property name="methodMaximum" value="5"/>
      <property name="classMaximum" value="1000"/>
      <property name="fileMaximum" value="1000"/>
      <property name="recordMaximum" value="100"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;

class InputJavaNCSSFlexibleConstructorBodiesCustom {

    static class Before {
        // violation below 'NCSS for this method is 8 (max allowed is 5).'
        Before(boolean stop) {
            int value = 0;
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
        }
    }
}
