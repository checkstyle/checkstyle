/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnField" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsonfield;

public class InputInappropriateJavadocBlockTagsOnFieldDefault3 {

    enum InnerEnum {
        ONE, TWO;

        /**
         * Inappropriate tag on enum field.
         *
         * @throws Exception err
         */
        // violation 2 lines above 'Invalid 'throws' tag for 'enumField'.'
        private int enumField;

        /**
         * Valid enum field.
         *
         * @see String
         */
        private String validEnumField;
    }

    record InnerRecord(int x) {
        /**
         * Inappropriate tag on record static field.
         *
         * @return val
         */
        // violation 2 lines above 'Invalid 'return' tag for 'RECORD_STATIC_FIELD'.'
        public static final String RECORD_STATIC_FIELD = "rec";

        /**
         * Valid record static field.
         *
         * @since 1.0
         */
        public static final int VALID_REC_FIELD = 10;
    }

    @interface InnerAnnotation {
        /**
         * Inappropriate tag on annotation field.
         *
         * @exception Exception tag
         */
        // violation 2 lines above 'Invalid 'exception' tag for 'ANNOTATION_FIELD'.'
        String ANNOTATION_FIELD = "ann";
    }

    static class StaticNestedClass {
        /**
         * Inappropriate tag on static nested class field.
         *
         * @param badParam tag
         */
        // violation 2 lines above 'Invalid 'param' tag for 'nestedField'.'
        private long nestedField;
    }

    class InnerClass {
        /**
         * Inappropriate tag on inner class field.
         *
         * @return value
         */
        // violation 2 lines above 'Invalid 'return' tag for 'innerField'.'
        private short innerField;
    }

    public void anonymousClassMethod() {
        Object anonymous = new Object() {
            /**
             * Inappropriate tag on anonymous class field.
             *
             * @return anon field
             */
            // violation 2 lines above 'Invalid 'return' tag for 'anonField'.'
            private int anonField;
        };
    }

}
