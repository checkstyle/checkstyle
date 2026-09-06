/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocSeeTagOrder"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocseetagorder;

// xdoc section - start
class Example1 {
  private String field;

  Example1() {}

  private void method() {}

  private void method(String value) {}

  // violation 9 lines below """@see tag '#method()' should be placed before
  //  '#method(java.lang.String)'."""
  // violation 8 lines below """@see tag '#Example1()' should be placed before
  //  '#method()'."""
  /**
   * Incorrect order: local method first, then constructor, and overloaded
   * methods not in telescoping order.
   *
   * @see #method(java.lang.String)
   * @see #method()
   * @see #Example1()
   */
  void wrongSeeTags() {}

  // ok, local member before simple class before qualified class
  /**
   * Correct order of {@code @see} tags.
   *
   * @see #field
   * @see #method()
   * @see OtherClass
   * @see java.util.List
   */
  void validSeeTags() {}
}
// xdoc section - end
