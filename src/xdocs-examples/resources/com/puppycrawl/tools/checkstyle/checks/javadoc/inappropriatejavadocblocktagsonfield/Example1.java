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
   * @author Author Name
   * @version 1.0
   * @param BAD Invalid tag for field.
   * @return Invalid tag for field.
   * @throws Exception Invalid tag for field.
   * @exception Exception Invalid tag for field.
   * @uses Service Invalid tag for field.
   * @provides Service Invalid tag for field.
   */
  private String field2;
  // 8 violations above:
  // 'Invalid '@author' tag for 'field2'.'
  // 'Invalid '@exception' tag for 'field2'.'
  // 'Invalid '@param' tag for 'field2'.'
  // 'Invalid '@provides' tag for 'field2'.'
  // 'Invalid '@return' tag for 'field2'.'
  // 'Invalid '@throws' tag for 'field2'.'
  // 'Invalid '@uses' tag for 'field2'.'
  // 'Invalid '@version' tag for 'field2'.'

  void method() {
    /**
     * Local variables are not fields, so no violations are reported.
     *
     * @return local
     */
    int localVariable = 0; // ok
  }

  interface MyInterface {
    /**
     * Inappropriate tags on interface field.
     *
     * @param BAD Invalid tag for field.
     * @return Invalid tag for field.
     */
    int INT_FIELD = 0;
    // 2 violations above:
    // 'Invalid '@param' tag for 'INT_FIELD'.'
    // 'Invalid '@return' tag for 'INT_FIELD'.'
  }
}
// xdoc section - end
