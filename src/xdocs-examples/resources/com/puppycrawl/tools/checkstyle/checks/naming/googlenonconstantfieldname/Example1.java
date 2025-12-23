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

  static final int STATIC_FINAL = 0;
  private static final int Invalid_Name = 1;

  static int staticField;
  private static int Static_Bad;

  final int Bad = 0;
  // violation above, 'Non-constant field name 'Bad' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  public final int mValue = 0;
  // violation above, 'Non-constant field name 'mValue' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  private final int f = 0;
  // violation above, 'Non-constant field name 'f' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  protected final int foo_bar = 0;
  // violation above, 'Non-constant field name 'foo_bar' has invalid underscore usage, underscores are only allowed between adjacent digits.'

  int fA;
  // violation above, 'Non-constant field name 'fA' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  public int mField;
  // violation above, 'Non-constant field name 'mField' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  private int foo$bar;
  // violation above, 'Non-constant field name 'foo\$bar' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  protected int a;
  // violation above, 'Non-constant field name 'a' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  int gradle_9_5_1;
  // violation above, 'Non-constant field name 'gradle_9_5_1' has invalid underscore usage, underscores are only allowed between adjacent digits.'

  int foo1_bar;
  // violation above, 'Non-constant field name 'foo1_bar' has invalid underscore usage, underscores are only allowed between adjacent digits.'

  int _foo;
  // violation above, 'Non-constant field name '_foo' has invalid underscore usage, underscores are only allowed between adjacent digits.'
}
// xdoc section -- end
