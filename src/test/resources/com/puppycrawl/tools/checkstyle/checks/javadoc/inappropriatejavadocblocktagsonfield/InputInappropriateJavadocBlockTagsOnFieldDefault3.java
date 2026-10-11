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
        private int enumField;
        // violation above 'Invalid '@throws' tag for 'enumField'.'

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
        public static final String RECORD_STATIC_FIELD = "rec";
        // violation above 'Invalid '@return' tag for 'RECORD_STATIC_FIELD'.'

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
        String ANNOTATION_FIELD = "ann";
        // violation above 'Invalid '@exception' tag for 'ANNOTATION_FIELD'.'
    }

    static class StaticNestedClass {
        /**
         * Inappropriate tag on static nested class field.
         *
         * @param badParam tag
         */
        private long nestedField;
        // violation above 'Invalid '@param' tag for 'nestedField'.'
    }

    class InnerClass {
        /**
         * Inappropriate tag on inner class field.
         *
         * @return value
         */
        private short innerField;
        // violation above 'Invalid '@return' tag for 'innerField'.'
    }

    public void anonymousClassMethod() {
        Object anonymous = new Object() {
            /**
             * Inappropriate tag on anonymous class field.
             *
             * @return anon field
             */
            private int anonField;
            // violation above 'Invalid '@return' tag for 'anonField'.'
        };
    }

}
