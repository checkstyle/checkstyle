/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireThis"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

class InputRequireThisFlexibleConstructorBodies {

    static class Before {
        private int value;

        Before(int value) {
            value = value; // violation 'Reference to instance variable 'value' needs "this.".'
            super();
        }
    }

    static class After {
        private int value;

        After(int value) {
            super();
            value = value; // violation 'Reference to instance variable 'value' needs "this.".'
        }
    }
}
