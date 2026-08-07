/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLinkWellKnownApi">
      <property name="wellKnownQualifiedPackages" value="java.lang, java.util"/>
      <property name="wellKnownSimpleNames" value="String, Integer, Object"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkwellknownapi;

// xdoc section - start
class Example2 {
  // violation 2 lines below 'String' should not be linked
  /**
   * Uses a {@link String}.
   */
  public String valid() { return ""; }
  // violation 2 lines below 'Integer' should not be linked
  /**
   * Uses a {@link Integer}.
   */
  public Integer validInteger() { return 0; }
  // violation 2 lines below 'java.util.List' should not be linked
  /**
   * Uses a {@link java.util.List}.
   */
  public java.util.List validList() { return null; }
  // violation 2 lines below 'java.lang.String' should not be linked
  /**
   * Uses a {@link java.lang.String}.
   */
  public String validLangString() { return ""; }
  // violation 2 lines below 'String' should not be linked
  /**
   * Uses a {@linkplain String}.
   */
  public String validLinkplain() { return ""; }
  // violation 2 lines below 'Object' should not be linked
  /**
   * Uses a {@linkplain Object}.
   */
  public Object validLinkplainObject() { return null; }
}
// xdoc section - end
