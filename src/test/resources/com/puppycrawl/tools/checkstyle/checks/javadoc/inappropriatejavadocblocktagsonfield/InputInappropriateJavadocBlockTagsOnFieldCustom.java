/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnField">
      <property name="javadocTokens" value="RETURN_BLOCK_TAG, PARAM_BLOCK_TAG" />
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsonfield;

public class InputInappropriateJavadocBlockTagsOnFieldCustom {

    /**
     * Inappropriate return tag on field.
     *
     * @return something
     */
    // violation 2 lines above 'Invalid '@return' tag for 'fieldReturn'.'
    private String fieldReturn;

    /**
     * Inappropriate param tag on field.
     *
     * @param badParam bad param description
     */
    // violation 2 lines above 'Invalid '@param' tag for 'fieldParam'.'
    protected Object fieldParam;

    /**
     * Throws tag is ignored because it's not in javadocTokens.
     *
     * @throws Exception when error occurs
     */
    public boolean fieldThrows;

    /**
     * Exception tag is ignored because it's not in javadocTokens.
     *
     * @exception Exception when error occurs
     */
    public boolean fieldException;
}
