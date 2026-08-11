/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnField"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsonfield;

// xdoc section - start
class Example1 {
  /**
   * Valid Javadoc on field with appropriate tags.
   *
   * @since 1.0
   * @deprecated for testing
   * @see Object
   * @serial field description
   */
  private String field1;

  /**
   * Inappropriate tags on class field.
   *
   * @return Invalid tag for field.
   * // violation above 'Invalid 'return' tag for 'field2'.'
   * @param BAD Invalid tag for field.
   * // violation above 'Invalid 'param' tag for 'field2'.'
   * @throws Exception Invalid tag for field.
   * // violation above 'Invalid 'throws' tag for 'field2'.'
   * @exception Exception Invalid tag for field.
   * // violation above 'Invalid 'exception' tag for 'field2'.'
   */
  private String field2;

  interface MyInterface {
    /**
     * Inappropriate tags on interface field.
     *
     * @return Invalid tag for field.
     * // violation above 'Invalid 'return' tag for 'INT_FIELD'.'
     * @param BAD Invalid tag for field.
     * // violation above 'Invalid 'param' tag for 'INT_FIELD'.'
     */
    int INT_FIELD = 0;
  }
}
// xdoc section - end
