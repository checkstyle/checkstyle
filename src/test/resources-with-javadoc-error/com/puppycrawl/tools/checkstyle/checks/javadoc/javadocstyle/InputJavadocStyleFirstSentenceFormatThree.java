/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = ([.][ \t\n\r\f<])|([.]$)
checkEmptyJavadoc = (default)false
checkHtml = false
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleFirstSentenceFormatThree
{
    /**
     * A test class.
     * @param <T1> this is NOT an unclosed T1 tag
     * @param <KEY_T> for bug 1649020.
     * @author <a href="mailto:foo@nomail.com">Foo Bar</a>
     */
    public class TestClass<T1, KEY_T>
    {
        /**
         * Retrieves X.
         * @return a value
         */
        public T1 getX()
        {
            return null;
        }

        /**
         * Retrieves Y.
         * @param <V> this is not an unclosed V tag
         * @return a value
         */
        public <V> V getY()
        {
            return null;
        }

        /**
         * Retrieves Z.
         *
         * @param <KEY_T1> this is not an unclosed KEY_T tag
         * @return a value
         */
        public <KEY_T1> KEY_T getZ_1649020_1()
        {
            return null;
        }

        /**
         * Retrieves something.
         *
         * @param <KEY_T_$_1_t> strange type
         * @return a value
         */
        public <KEY_T_$_1_t> KEY_T_$_1_t getEh_1649020_2() {
            return null;
        }

        /**
         * Retrieves more something.
         *
         * @param <$_12_xY_z> strange type
         * @return a value
         */
        public <$_12_xY_z> $_12_xY_z getUmmm_1649020_3() {
            return null;
        }
    }

    /**
     * Checks if the specified IClass needs to be
     * annotated with the @Type annotation.
     */
    public void foo_1291847_1() {}

    /**
     * Returns the string containing the properties of
     * <code>@Type</code> annotation.
     */
    public void foo_1291847_2() {}

    /**
     * Checks generics javadoc.
     *
     * @param strings this is a List<String>
     * @param test Map<String, List<String>> a map indexed on String of Lists of Strings.
     */
    public void method20() {}

    /**
     * Checks HTML tags in javadoc.
     *
     * HTML no good tag
     * <string>Tests</string>
     *
     */
    public void method21() {}

    /**
     * First sentence.
     * <
     * /a>
     */
    void tagClosedInNextLine() {}
}
