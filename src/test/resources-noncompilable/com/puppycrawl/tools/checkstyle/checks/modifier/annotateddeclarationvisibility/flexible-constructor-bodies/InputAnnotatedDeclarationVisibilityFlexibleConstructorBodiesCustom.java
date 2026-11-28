/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotatedDeclarationVisibility">
      <property name="annotations" value="java.lang.Deprecated"/>
      <property name="visibility" value="public"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

// VisibleForTesting is defined at the end of this file, so this input compiles on its own.

class InputAnnotatedDeclarationVisibilityFlexibleConstructorBodiesCustom {

    static class Before {
        @Deprecated
        public Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }

        // violation below 'Annotated element has disallowed visibility 'protected'.'
        @Deprecated
        protected Before(int input) {
            String text = "unused";
            super();
        }
    }

    static class After {
        // violation below 'Annotated element has disallowed visibility 'private'.'
        @Deprecated
        private After(boolean stop) {
            super();
            int value = 1;
            if (stop) {
                return;
            }
        }

        @VisibleForTesting
        public After(int input) {
            int value = 1;
            super();
        }
    }
}

@interface VisibleForTesting {}
