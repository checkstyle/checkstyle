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
// ok below, since we are only checking for '^unchecked$|^unused$'
@SuppressWarnings("")
class Example2 {
  // ok below as VARIABLE_DEF is not configured in tokens to check
  @SuppressWarnings("")
  final int num1 = 1;
  @SuppressWarnings("all")
  final int num2 = 2;
  @SuppressWarnings("unused")
  final int num3 = 3;

  // ok below as PARAMETER_DEF is not configured in tokens to check
  void foo1(@SuppressWarnings("unused") int param) {}

  // ok below, since we are only checking for '^unchecked$|^unused$'
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
