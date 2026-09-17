/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnField" />
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsonfield;

public class InputInappropriateJavadocBlockTagsOnFieldDefault2 {

    public int method(int a) throws Exception {
        int firstVar = 0;
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
            // violation above 'Invalid '@return' tag for 'localClassField'.'
        }

        return localVariable;
    }

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
        // 2 violations above:
        // 'Invalid '@param' tag for 'INTERFACE_CONST'.'
        // 'Invalid '@return' tag for 'INTERFACE_CONST'.'

        /**
         * Valid interface field.
         *
         * @since 1.0
         */
        String VALID_CONST = "valid";
    }

}
