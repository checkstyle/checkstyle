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
  // violation above, 'Non-constant field name 'Foo' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  int f;
  // violation above, 'Non-constant field name 'f' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  int gradle_9_5_1;
  // violation above, 'Non-constant field name 'gradle_9_5_1' has invalid underscore usage, underscores are only allowed between adjacent digits.'

  int foo_bar;
  // violation above, 'Non-constant field name 'foo_bar' has invalid underscore usage, underscores are only allowed between adjacent digits.'

  int foo$bar;
  // violation above, 'Non-constant field name 'foo\$bar' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  int fO;
  // violation above, 'Non-constant field name 'fO' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'
}
// xdoc section -- end
