/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DeclarationOrder">
      <property name="ignoreConstructors" value="true"/>
      <property name="ignoreModifiers" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

class InputDeclarationOrderFlexibleConstructorBodiesCustom {

    static class Before {
        private static int first;

        public static int second;

        void method() {
        }

        Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop) {
            super();
            int value = 1;
            if (stop) {
                return;
            }
        }

        // violation below 'Instance variable definition in wrong order.'
        private int value;
    }
}
