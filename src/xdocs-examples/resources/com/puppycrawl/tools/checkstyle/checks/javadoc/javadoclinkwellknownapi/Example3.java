/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLinkWellKnownApi">
      <property name="wellKnownQualifiedPackages" value="java.lang"/>
      <property name="wellKnownSimpleNames" value="String, Integer"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkwellknownapi;

// xdoc section - start
class Example3 {
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

  /**
   * Uses a {@linkplain Object}.
   */
  public Object validLinkplainObject() { return null; }
}
// xdoc section - end
