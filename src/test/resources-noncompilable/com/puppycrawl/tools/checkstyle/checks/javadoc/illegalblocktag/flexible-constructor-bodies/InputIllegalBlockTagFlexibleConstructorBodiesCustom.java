/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalBlockTag">
      <property name="tag" value="@since"/>
      <property name="tagTextPattern" value="^[1-9.]+$"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

class InputIllegalBlockTagFlexibleConstructorBodiesCustom {

    static class Before {
        // violation 5 lines below 'Block tag 'since' is matched illegal pattern'
        /**
         * Creates a Before instance.
         *
         * @param stop whether to stop
         * @since 1.1-beta
         */
        Before(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        /**
         * Creates an After instance.
         *
         * @param stop whether to stop
         * @since 1.2
         */
        After(boolean stop) {
            super();
            if (stop) {
                return;
            }
        }
    }
}
