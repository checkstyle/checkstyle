/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnField" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsonfield;

public class InputInappropriateJavadocBlockTagsOnFieldDefault {

    /**
     * Valid field javadoc with allowed tags.
     *
     * @since 1.0
     * @see String
     * @deprecated replaced
     * @serial serial description
     * @serialField next String next description
     */
    public int validField;

    /**
     * Inappropriate return tag on field.
     *
     * @return something
     */
    // violation 2 lines above 'Invalid 'return' tag for 'invalidReturn'.'
    private String invalidReturn;

    /**
     * Inappropriate param tag on field.
     *
     * @param badParam bad param description
     */
    // violation 2 lines above 'Invalid 'param' tag for 'invalidParam'.'
    protected Object invalidParam;

    /**
     * Inappropriate throws tag on field.
     *
     * @throws Exception when error occurs
     */
    // violation 2 lines above 'Invalid 'throws' tag for 'invalidThrows'.'
    public boolean invalidThrows;

    /**
     * Inappropriate exception tag on field.
     *
     * @exception Exception when error occurs
     */
    // violation 2 lines above 'Invalid 'exception' tag for 'invalidException'.'
    public boolean invalidException;

    /**
     * Multiple inappropriate tags on field.
     *
     * @return result
     * @param p param
     * @throws RuntimeException on error
     * @exception IllegalStateException on state error
     */
    // violation 5 lines above 'Invalid 'return' tag for 'multipleInvalid'.'
    // violation 5 lines above 'Invalid 'param' tag for 'multipleInvalid'.'
    // violation 5 lines above 'Invalid 'throws' tag for 'multipleInvalid'.'
    // violation 5 lines above 'Invalid 'exception' tag for 'multipleInvalid'.'
    public double multipleInvalid;

    /**
     * Mixed valid and invalid tags on field.
     *
     * @since 2.0
     * @return test
     * @see Object
     * @throws Exception test
     * @deprecated deprecated
     */
    // violation 5 lines above 'Invalid 'return' tag for 'mixedTags'.'
    // violation 4 lines above 'Invalid 'throws' tag for 'mixedTags'.'
    public float mixedTags;

    // Field without javadoc
    public int noJavadocField = 0;

    /** */
    public int emptyJavadocField = 1;

    /**
     * Method Javadoc should not be checked by this check.
     *
     * @param a param
     * @return value
     * @throws Exception ex
     */
    public int method(int a) throws Exception {
        /**
         * Local variable javadoc should not be checked.
         *
         * @return local
         */
        int localVariable = a;

        class LocalClass {
            /**
             * Field in local class with invalid tag.
             *
             * @return local class field
             */
            // violation 2 lines above 'Invalid 'return' tag for 'localClassField'.'
            public int localClassField;
        }

        return localVariable;
    }

    /**
     * Constructor should not be checked.
     *
     * @param x parameter
     * @throws Exception error
     */
    public InputInappropriateJavadocBlockTagsOnFieldDefault(int x) throws Exception {
        int ctorVar = x;
    }

    public InputInappropriateJavadocBlockTagsOnFieldDefault() {
    }

    static {
        /**
         * Block variable javadoc should not be checked.
         *
         * @return test
         */
        int staticBlockVar = 0;
    }

    {
        /**
         * Instance init variable javadoc should not be checked.
         *
         * @return test
         */
        int initBlockVar = 0;
    }

    interface InnerInterface {
        /**
         * Inappropriate tags on interface field.
         *
         * @return constant
         * @param invalid tag
         */
        // violation 3 lines above 'Invalid 'return' tag for 'INTERFACE_CONST'.'
        // violation 3 lines above 'Invalid 'param' tag for 'INTERFACE_CONST'.'
        int INTERFACE_CONST = 42;

        /**
         * Valid interface field.
         *
         * @since 1.0
         */
        String VALID_CONST = "valid";
    }

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
