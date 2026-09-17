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
    // violation above 'Invalid '@return' tag for 'invalidReturn'.'

    /**
     * Inappropriate param tag on field.
     *
     * @param badParam bad param description
     */
    protected int invalidParam;
    // violation above 'Invalid '@param' tag for 'invalidParam'.'

    /**
     * Inappropriate throws tag on field.
     *
     * @throws Exception when error occurs
     */
    public boolean invalidThrows;
    // violation above 'Invalid '@throws' tag for 'invalidThrows'.'

    /**
     * Inappropriate exception tag on field.
     *
     * @exception Exception when error occurs
     */
    public boolean invalidException;
    // violation above 'Invalid '@exception' tag for 'invalidException'.'

    /**
     * Inappropriate author tag on field.
     *
     * @author John Doe
     */
    public int invalidAuthor;
    // violation above 'Invalid '@author' tag for 'invalidAuthor'.'

    /**
     * Inappropriate version tag on field.
     *
     * @version 1.0
     */
    public String invalidVersion;
    // violation above 'Invalid '@version' tag for 'invalidVersion'.'

    /**
     * Inappropriate uses tag on field.
     *
     * @uses SomeService service description
     */
    public boolean invalidUses;
    // violation above 'Invalid '@uses' tag for 'invalidUses'.'

    /**
     * Inappropriate provides tag on field.
     *
     * @provides SomeService with SomeServiceImpl
     */
    public int invalidProvides;
    // violation above 'Invalid '@provides' tag for 'invalidProvides'.'

    // Field without javadoc
    public int noJavadocField = 0;

    /** */
    public int emptyJavadocField = 1;

    public void method() {
        /**
         * @return something
         */
        final int localVariable = 0;
    }

}
