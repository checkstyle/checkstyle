/*
SetterSinceTag


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.settersincetag;

public class InputSetterSinceTagCheckNestedClassCheck {

    static class ExampleCheck {

        private Long id1;

        private Long id2;

        /**
         * Set id of the user
         *
         * @param id1 id of the user
         * @since 13.4.0
         */
        public void setId1(Long id1) {
          this.id1 = id1;
        }

        /**
         * Set id of the user
         *
         * @param id2 id of the user
         */
        public void setId2(Long id2) { // violation, '@since' tag is missing
          this.id2 = id2;
        }

        /***/
        public String nullCheck() {
            return "SetterCheck";
        }

        public String noJavaDoc() {
            return "SetterCheck";
        }

        String noModifier() {
            return "SetterCheck";
        }
    }
}
