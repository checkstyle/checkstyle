/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GoogleNonConstantFieldName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

// xdoc section -- start
class Example1 {

  int fooBar;
  int guava33_4_5;
  int httpClient;
  int version1_2;

  int Foo;
  // violation above, ''Foo' must start with a lowercase letter'

  int f;
  // violation above, ''f' must start with a lowercase letter, min 2 chars'

  int gradle_9_5_1;
  // violation above, ''gradle_9_5_1' has invalid underscore usage'

  int foo_bar;
  // violation above, ''foo_bar' has invalid underscore usage'
}
// xdoc section -- end
