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

  interface ExampleInterface {
    int CONSTANT = 1;
    String Invalid_Name = "test";
  }

  @interface ExampleAnnotation {
    int DEFAULT = 0;
    String Bad_Name = "test";
  }

  final int Bad = 0;
  // violation above, 'Non-constant field name 'Bad' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  public final int mValue = 0;
  // violation above, 'Non-constant field name 'mValue' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  private final int f = 0;
  // violation above, 'Non-constant field name 'f' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  protected final int foo_bar = 0;
  // violation above, 'Non-constant field name 'foo_bar' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  int fA;
  // violation above, 'Non-constant field name 'fA' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  public int mField;
  // violation above, 'Non-constant field name 'mField' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  private int foo$bar;
  // violation above, 'Non-constant field name 'foo\$bar' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  protected int a;
  // violation above, 'Non-constant field name 'a' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  int gradle_9_5_1;
  // violation above, 'Non-constant field name 'gradle_9_5_1' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'

  int _foo;
  // violation above, 'Non-constant field name '_foo' must start lowercase, be at least 2 chars, avoid single lowercase letter followed by uppercase, contain only letters, digits or underscores, with underscores allowed only between adjacent digits.'
}
// xdoc section -- end
