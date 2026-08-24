/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnField" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsonfield;

public class InputInappropriateJavadocBlockTagsOnFieldDefault2 {

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
            public int localClassField;
            // violation 3 lines above 'Invalid '@return' tag for 'localClassField'.'
        }

        return localVariable;
    }

    /**
     * Constructor should not be checked.
     *
     * @param x parameter
     * @throws Exception error
     */
    public InputInappropriateJavadocBlockTagsOnFieldDefault2(int x) throws Exception {
        int ctorVar = x;
    }

    public InputInappropriateJavadocBlockTagsOnFieldDefault2() {
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
        int INTERFACE_CONST = 42;
        // violation 4 lines above 'Invalid '@return' tag for 'INTERFACE_CONST'.'
        // violation 4 lines above 'Invalid '@param' tag for 'INTERFACE_CONST'.'

        /**
         * Valid interface field.
         *
         * @since 1.0
         */
        String VALID_CONST = "valid";
    }

}
