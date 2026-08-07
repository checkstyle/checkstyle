/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLinkWellKnownApi"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoclinkwellknownapi;

class InputJavadocLinkWellKnownApi {

  /**
   * Uses a {@link Integer}.
   * Uses a {@link java.util.List}.
   * Uses a {@code String}.
   * Uses a similar package {@link java.language.String}.
   */
  void valid() { }

  // violation 2 lines below 'String' should not be linked
  /**
   * Uses a {@link String}.
   */
  void invalidSimpleName() { }

  // violation 2 lines below 'java.lang.String' should not be linked
  /**
   * Uses a {@link java.lang.String}.
   */
  void invalidQualified() { }

  // violation 2 lines below 'String' should not be linked
  /**
   * Uses a {@linkplain String}.
   */
  void invalidLinkplain() { }

  // violation 2 lines below 'String' should not be linked
  /**
   * Uses a {@link String#length()}.
   */
  void invalidMember() { }

  // violation 2 lines below 'java.lang.Class' should not be linked
  /**
   * Uses a {@link java.lang.Class<String>}.
   */
  void invalidGenerics() { }

  // violation 2 lines below 'java.lang.Class' should not be linked
  /**
   * Uses a {@link java.lang.Class<String>#getName()}.
   */
  void invalidGenericsAndMember() { }
}
