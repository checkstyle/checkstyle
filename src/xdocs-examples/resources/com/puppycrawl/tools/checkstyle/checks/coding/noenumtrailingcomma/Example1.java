/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoEnumTrailingComma"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

// xdoc section -- start
class Example1 {
  enum Foo1 {
    FOO,
    BAR;
  }
  enum Foo2 {
    FOO,
    BAR
  }
  enum Foo3 {
    FOO,
    BAR, // violation, 'Enum should not contain trailing comma'
  }
  enum Foo4 {
    FOO,
    BAR,; // violation, 'Enum should not contain trailing comma'
  }
  enum Foo5 {
    FOO,
    BAR,; // violation, 'Enum should not contain trailing comma'
  }
  enum Foo6 { FOO, BAR,; } // violation, 'Enum should not contain trailing comma'
  enum Foo7 { FOO, BAR, } // violation, 'Enum should not contain trailing comma'
  enum Foo8 {
    FOO,
    BAR;
  }
  enum Foo9 { FOO, BAR; }
  enum Foo10 { FOO, BAR }
}
// xdoc section -- end
