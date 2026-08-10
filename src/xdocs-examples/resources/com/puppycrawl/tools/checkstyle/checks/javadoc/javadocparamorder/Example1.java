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

  // violation 5 lines below '@param tag for 'name' should be in declaration order.'
  /**
   * Parameter tags should follow the declaration order.
   *
   * @param role user role
   * @param name user name
   */
  void method(String name, String role) {
  }

  record Person(String id, int age) {

    // violation 5 lines below '@param tag for 'id' should be in declaration order.'
    /**
     * Compact constructor parameter tags follow record component order.
     *
     * @param age age
     * @param id identifier
     */
    Person {
    }
  }

  /**
   * A missing parameter tag does not affect relative order validation.
   *
   * @param name user name
   * @param locale preferred locale
   */
  void missingTag(String name, String email, String locale) {
  }

  /**
   * Duplicate parameter tags are allowed when they do not move backwards.
   *
   * @param name user name
   * @param email email address
   * @param email repeated email address
   */
  void duplicateTag(String name, String email) {
  }
}
// xdoc section - end
