/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLinkFirstOccurrence"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkfirstoccurrence;

// xdoc section - start
class Example1 {

  /**
   * Uses a {@link String}.
   * The String is returned.
   */
  public String valid1() { return ""; }

  // violation 3 lines below 'String' should be linked only on its first occurrence
  /**
   * Uses a {@link String}.
   * The {@link String} is returned.
   */
  public String invalid1() { return ""; }

  /**
   * Uses a {@link String} and an {@link Object}.
   * The String is converted to an Object.
   */
  public Object valid2(String s) { return s; }

  // 2 violations 5 lines below:
  // ''String' should be linked only on its first occurrence'
  // ''Object' should be linked only on its first occurrence'
  /**
   * Uses a {@link String} and an {@link Object}.
   * The {@link String} is converted to an {@link Object}.
   */
  public Object invalid2(String s) { return s; }

  // violation 3 lines below 'String' should be linked only on its first occurrence
  /**
   * Uses a {@linkplain String}.
   * The {@linkplain String} is returned.
   */
  public String invalid3() { return ""; }

  /**
   * Uses {@link #method()} and {@link String}.
   */
  public void valid3() { }
}
// xdoc section - end
