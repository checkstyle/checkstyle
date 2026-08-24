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
   *
   * @param BAD Invalid tag for field.
   *
   * @throws Exception Invalid tag for field.
   *
   * @exception Exception Invalid tag for field.
   */
  private String field2;
  // 4 violations above:
  // 'Invalid '@return' tag for 'field2'.'
  // 'Invalid '@param' tag for 'field2'.'
  // 'Invalid '@throws' tag for 'field2'.'
  // 'Invalid '@exception' tag for 'field2'.'

  interface MyInterface {
    /**
     * Inappropriate tags on interface field.
     *
     * @return Invalid tag for field.
     *
     * @param BAD Invalid tag for field.
     */
    int INT_FIELD = 0;
    // 2 violations above:
    // 'Invalid '@return' tag for 'INT_FIELD'.'
    // 'Invalid '@param' tag for 'INT_FIELD'.'
  }
}
// xdoc section - end
