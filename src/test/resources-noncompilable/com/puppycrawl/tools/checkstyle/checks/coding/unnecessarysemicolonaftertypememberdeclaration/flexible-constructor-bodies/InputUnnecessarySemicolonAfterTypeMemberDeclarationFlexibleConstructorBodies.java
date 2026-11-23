/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessarySemicolonAfterTypeMemberDeclaration"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

class InputUnnecessarySemicolonAfterTypeMemberDeclarationFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }; // violation 'Unnecessary semicolon.'
    }

    static class After {
        After(boolean stop) {
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }

        void method() {
        }; // violation 'Unnecessary semicolon.'
    }
}
