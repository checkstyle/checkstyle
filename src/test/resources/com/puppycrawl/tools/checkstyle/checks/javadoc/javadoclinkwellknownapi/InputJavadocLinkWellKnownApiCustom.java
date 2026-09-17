/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLinkWellKnownApi">
      <property name="wellKnownSimpleNames" value="Integer, Object"/>
      <property name="wellKnownQualifiedPackages" value="java.util, java.io"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkwellknownapi;

class InputJavadocLinkWellKnownApiCustom {

  /**
   * Uses a {@link String}.
   * Uses a {@link java.lang.String}.
   * Uses a {@link java.util.concurrent.Executor}.
   */
  void valid() { }

  // violation 2 lines below 'Integer' should not be linked
  /**
   * Uses a {@link Integer}.
   */
  void invalidInteger() { }

  // violation 2 lines below 'Object' should not be linked
  /**
   * Uses a {@link Object}.
   */
  void invalidObject() { }

  // violation 2 lines below 'java.util.List' should not be linked
  /**
   * Uses a {@link java.util.List}.
   */
  void invalidQualified() { }

  // violation 2 lines below 'java.io.File' should not be linked
  /**
   * Uses a {@link java.io.File}.
   */
  void invalidQualifiedSecondPackage() { }
}
