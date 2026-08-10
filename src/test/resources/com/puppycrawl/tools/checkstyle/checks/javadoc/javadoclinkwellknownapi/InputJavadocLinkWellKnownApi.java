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

  // ok, member reference is not checked
  /**
   * Uses a {@link String#length()}.
   */
  void validMember() { }

  // violation 2 lines below 'java.lang.Class' should not be linked
  /**
   * Uses a {@link java.lang.Class<String>}.
   */
  void invalidGenerics() { }

  // ok, member reference is not checked
  /**
   * Uses a {@link java.lang.Class<String>#getName()}.
   */
  void validGenericsAndMember() { }

  // ok, reference to a class from a subpackage is not checked
  /**
   * Uses a {@link java.lang.ref.WeakReference}.
   */
  void validSubpackage() { }

  // ok, reference to a class from a subpackage is not checked
  /**
   * Uses a {@link java.lang.reflect.ParameterizedType}.
   */
  void validSubpackageReflect() { }

  // ok, reference to a nested class is not checked
  /**
   * Uses a {@link java.lang.System.Logger}.
   */
  void validNestedClass() { }

  // ok, reference to a nested class is not checked
  /**
   * Uses a {@link java.lang.Thread.State}.
   */
  void validNestedClassThread() { }

  // ok, reference to a package is not checked
  /**
   * Uses a {@link java.lang.ref}.
   */
  void validPackageOnly() { }

  // ok, reference to a package is not checked
  /**
   * Uses a {@link java.lang.classfile}.
   */
  void validPackageOnlyClassfile() { }

  // ok, no class name follows the package prefix
  /**
   * Uses a {@link java.lang.}.
   */
  void validTrailingDot() { }

  // ok, package name is not followed by a class name
  /**
   * Uses a {@link java.langXYZ}.
   */
  void validNoClass() { }
}
