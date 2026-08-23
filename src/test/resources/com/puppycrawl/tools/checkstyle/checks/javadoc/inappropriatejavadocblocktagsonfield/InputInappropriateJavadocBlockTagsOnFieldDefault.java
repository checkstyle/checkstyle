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
    // violation 2 lines above 'Invalid '@return' tag for 'invalidReturn'.'
    private String invalidReturn;

    /**
     * Inappropriate param tag on field.
     *
     * @param badParam bad param description
     */
    // violation 2 lines above 'Invalid '@param' tag for 'invalidParam'.'
    protected Object invalidParam;

    /**
     * Inappropriate throws tag on field.
     *
     * @throws Exception when error occurs
     */
    // violation 2 lines above 'Invalid '@throws' tag for 'invalidThrows'.'
    public boolean invalidThrows;

    /**
     * Inappropriate exception tag on field.
     *
     * @exception Exception when error occurs
     */
    // violation 2 lines above 'Invalid '@exception' tag for 'invalidException'.'
    public boolean invalidException;

    /**
     * Multiple inappropriate tags on field.
     *
     * @return result
     * @param p param
     * @throws RuntimeException on error
     * @exception IllegalStateException on state error
     */
    // violation 5 lines above 'Invalid '@return' tag for 'multipleInvalid'.'
    // violation 5 lines above 'Invalid '@param' tag for 'multipleInvalid'.'
    // violation 5 lines above 'Invalid '@throws' tag for 'multipleInvalid'.'
    // violation 5 lines above 'Invalid '@exception' tag for 'multipleInvalid'.'
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
    // violation 5 lines above 'Invalid '@return' tag for 'mixedTags'.'
    // violation 4 lines above 'Invalid '@throws' tag for 'mixedTags'.'
    public float mixedTags;

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
