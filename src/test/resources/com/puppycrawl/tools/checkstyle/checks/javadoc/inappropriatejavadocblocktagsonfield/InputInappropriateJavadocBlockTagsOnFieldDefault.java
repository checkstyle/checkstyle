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
    private String invalidReturn;
    // violation 3 lines above 'Invalid '@return' tag for 'invalidReturn'.'

    /**
     * Inappropriate param tag on field.
     *
     * @param badParam bad param description
     */
    protected Object invalidParam;
    // violation 3 lines above 'Invalid '@param' tag for 'invalidParam'.'

    /**
     * Inappropriate throws tag on field.
     *
     * @throws Exception when error occurs
     */
    public boolean invalidThrows;
    // violation 3 lines above 'Invalid '@throws' tag for 'invalidThrows'.'

    /**
     * Inappropriate exception tag on field.
     *
     * @exception Exception when error occurs
     */
    public boolean invalidException;
    // violation 3 lines above 'Invalid '@exception' tag for 'invalidException'.'

    /**
     * Multiple inappropriate tags on field.
     *
     * @return result
     * @param p param
     * @throws RuntimeException on error
     * @exception IllegalStateException on state error
     */
    public double multipleInvalid;
    // violation 6 lines above 'Invalid '@return' tag for 'multipleInvalid'.'
    // violation 6 lines above 'Invalid '@param' tag for 'multipleInvalid'.'
    // violation 6 lines above 'Invalid '@throws' tag for 'multipleInvalid'.'
    // violation 6 lines above 'Invalid '@exception' tag for 'multipleInvalid'.'

    /**
     * Mixed valid and invalid tags on field.
     *
     * @since 2.0
     * @return test
     * @see Object
     * @throws Exception test
     * @deprecated deprecated
     */
    public float mixedTags;
    // violation 6 lines above 'Invalid '@return' tag for 'mixedTags'.'
    // violation 5 lines above 'Invalid '@throws' tag for 'mixedTags'.'

    // Field without javadoc
    public int noJavadocField = 0;

    /** */
    public int emptyJavadocField = 1;

    public void method() {
        /**
         * @return something
         */
        int localVariable = 0;
    }

}
