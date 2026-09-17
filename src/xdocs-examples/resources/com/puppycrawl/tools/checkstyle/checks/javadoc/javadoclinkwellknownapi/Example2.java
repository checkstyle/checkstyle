/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLinkWellKnownApi">
      <property name="wellKnownQualifiedPackages" value="java.lang, java.util"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkwellknownapi;

// xdoc section - start
class Example2 {
  // violation 3 lines below """'String' should not be linked because it is
  //  configured as a well-known API."""
  /**
   * Uses a {@link String}.
   */
  public String valid() { return ""; }

  // ok, Integer is not in the default wellKnownSimpleNames
  /**
   * Uses a {@link Integer}.
   */
  public Integer validInteger() { return 0; }
  // violation 3 lines below """'java.util.List' should not be linked because
  //  it belongs to a well-known package."""
  /**
   * Uses a {@link java.util.List}.
   */
  public java.util.List validList() { return null; }
  // violation 3 lines below """'java.lang.String' should not be linked because
  //  it belongs to a well-known package."""
  /**
   * Uses a {@link java.lang.String}.
   */
  public String validLangString() { return ""; }
  // violation 3 lines below """'String' should not be linked because it is
  //  configured as a well-known API."""
  /**
   * Uses a {@linkplain String}.
   */
  public String validLinkplain() { return ""; }

  // ok, Object is not in the default wellKnownSimpleNames
  /**
   * Uses a {@linkplain Object}.
   */
  public Object validLinkplainObject() { return null; }

  // ok, member references are not checked
  /**
   * Uses a {@link String#length()}.
   */
  public int validMember() { return 0; }

  // ok, member references are not checked
  /**
   * Uses a {@link java.lang.Class<String>#getName()}.
   */
  public String validMemberQualified() { return ""; }

  // ok, subpackage references are not checked
  /**
   * Uses a {@link java.lang.ref.WeakReference}.
   */
  public java.lang.ref.WeakReference validSubpackage() { return null; }

  // ok, nested class references are not checked
  /**
   * Uses a {@link java.lang.System.Logger}.
   */
  public java.lang.System.Logger validNestedClass() { return null; }

  // ok, package references are not checked
  /**
   * Uses a {@link java.lang.ref}.
   */
  public Class<?> validPackageReference() { return null; }
}
// xdoc section - end
