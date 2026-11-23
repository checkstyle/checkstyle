/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedDeclarationVisibility"><property name="annotations" value="com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility.VisibleForTesting"/></module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

// VisibleForTesting is defined at the end of this file, so this input compiles on its own.

class InputAnnotatedDeclarationVisibilityFlexibleConstructorBodies {

    static class Before {
        // violation below 'Annotated element has disallowed visibility 'public'.'
        @VisibleForTesting
        public Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }

        // violation below 'Annotated element has disallowed visibility 'private'.'
        @VisibleForTesting
        private Before(int input) {
            String text = "unused";
            super();
        }

        @VisibleForTesting
        protected Before(String text) {
            int value = 1;
            super();
        }
    }

    static class After {
        // violation below 'Annotated element has disallowed visibility 'public'.'
        @VisibleForTesting
        public After(boolean stop) {
            super();
            int value = 1;
            if (stop) {
                return;
            }
        }
    }
}

@interface VisibleForTesting {}
