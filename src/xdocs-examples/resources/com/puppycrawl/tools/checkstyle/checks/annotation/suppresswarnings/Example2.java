/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWarnings">
      <property name="format"
          value="^unchecked$|^unused$"/>
      <property name="tokens"
        value="
        CLASS_DEF,INTERFACE_DEF,ENUM_DEF,
        ANNOTATION_DEF,ANNOTATION_FIELD_DEF,
        ENUM_CONSTANT_DEF,METHOD_DEF,CTOR_DEF
        "/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

// xdoc section -- start
// ok below as applied to a CLASS_DEF, allowed by the tokens property
@SuppressWarnings("")
class Example2 {
  // ok below as not applied to a restricted token
  @SuppressWarnings("")
  final int num1 = 1;
  @SuppressWarnings("all") // ok
  final int num2 = 2;
  @SuppressWarnings("unused") // ok
  final int num3 = 3;

  // ok as it's applied to a parameter, not a restricted token
  void foo1(@SuppressWarnings("unused") int param) {}

  // ok as it's applied to a METHOD_DEF, allowed by the tokens property.
  @SuppressWarnings("all")
  void foo2(int param) {}

  // violation below, 'The warning 'unused' cannot be suppressed at this location'
  @SuppressWarnings("unused")
  void foo3(int param) {}

  // violation below, 'The warning 'unused' cannot be suppressed at this location'
  @SuppressWarnings(true?"all":"unused")
  void foo4(int param) {}
}

// violation below, 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings("unchecked")
class Test2 {}
// xdoc section -- end
