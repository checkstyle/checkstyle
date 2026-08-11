/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParamOrder"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

// xdoc section - start
public class Example1 {

  // violation 5 lines below '@param tag for 'first' should be in declaration order.'
  /**
   * The first tag should follow the first declared parameter.
   *
   * @param second second value
   * @param first first value
   */
  void method(String first, String second) {
  }

  record Person(String name, int age) {

    // violation 5 lines below '@param tag for 'name' should be in declaration order.'
    /**
     * Compact constructor parameter tags follow record component order.
     *
     * @param age age
     * @param name name
     */
    Person {
    }
  }

  /**
   * A missing parameter tag does not affect relative order validation.
   *
   * @param first first value
   * @param third third value
   */
  void missingTag(String first, String second, String third) {
  }

  /**
   * Duplicate parameter tags are allowed when they do not move backwards.
   *
   * @param first first value
   * @param second second value
   * @param second repeated second value
   */
  void duplicateTag(String first, String second) {
  }
}
// xdoc section - end
