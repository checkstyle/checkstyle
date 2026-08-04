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
   * @return Invalid tag for field. // violation 'Invalid 'return' tag for 'field2'.'
   * @param BAD Invalid tag for field. // violation 'Invalid 'param' tag for 'field2'.'
   * @throws Exception Invalid tag for field. // violation 'Invalid 'throws' tag for 'field2'.'
   * @exception Exception Invalid tag for field. // violation 'Invalid 'exception' tag for 'field2'.'
   */
  private String field2;

  interface MyInterface {
    /**
     * Inappropriate tags on interface field.
     *
     * @return Invalid tag for field. // violation 'Invalid 'return' tag for 'INT_FIELD'.'
     * @param BAD Invalid tag for field. // violation 'Invalid 'param' tag for 'INT_FIELD'.'
     */
    int INT_FIELD = 0;
  }

  enum MyEnum {
    VALUE;

    /**
     * Inappropriate tag on enum field.
     *
     * @throws RuntimeException Invalid tag. // violation 'Invalid 'throws' tag for 'enumField'.'
     */
    private int enumField;
  }

  record MyRecord() {
    /**
     * Inappropriate tag on record static field.
     *
     * @param x Invalid tag for field. // violation 'Invalid 'param' tag for 'RECORD_FIELD'.'
     */
    public static final String RECORD_FIELD = "value";
  }
}
// xdoc section - end
