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
    private String fieldReturn;
    // violation 3 lines above 'Invalid '@return' tag for 'fieldReturn'.'

    /**
     * Inappropriate param tag on field.
     *
     * @param badParam bad param description
     */
    protected Object fieldParam;
    // violation 3 lines above 'Invalid '@param' tag for 'fieldParam'.'

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
