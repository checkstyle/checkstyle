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
  // violation 9 lines below """@see tag '#method()' should be placed before
  //  '#method(java.lang.String)'."""
  private String field;

  /**
   * Incorrect order: local method first, then constructor, and overloaded
   * methods not in telescoping order.
   *
   * @see #method(java.lang.String)
   * @see #method()
   * @see #Test()
   */
  Example1() {
  }

  private void method() {
  }

  private void method(String value) {
  }

  // ok, local member before simple class before qualified class
  /**
   * Correct order of {@code @see} tags.
   *
   * @see #field
   * @see #method()
   * @see OtherClass
   * @see java.util.List
   */
  void validSeeTags() {
  }
}
// xdoc section - end
