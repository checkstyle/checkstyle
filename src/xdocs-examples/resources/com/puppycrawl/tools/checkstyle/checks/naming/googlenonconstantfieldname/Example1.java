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
  private static final int PRIVATE_STATIC_FINAL = 1;

  static int staticField;
  private static int privateStatic;

  interface ExampleInterface {
    int CONSTANT = 1;
    String Invalid_Name = "test";
  }

  @interface ExampleAnnotation {
    int DEFAULT = 0;
    String Bad_Name = "test";
  }

  final int bad = 0;
  final int Bad = 0;
  // violation above, 'Non-constant field name 'Bad' must start lowercase'

  public final int httpClient = 0;
  public final int mValue = 0;
  // violation above, 'avoid single lowercase letter followed by uppercase'

  private final int foo = 0;
  private final int f = 0; // violation, 'be at least 2 chars'

  protected final int fooBar1_1 = 0;
  protected final int foo_bar = 0;
  // violation above, 'underscores allowed only between adjacent digits'

  int loginFails;
  int fA; // violation, 'avoid single lowercase letter followed by uppercase'

  int fooBar2;
  int foo$bar; // violation, 'contain only letters, digits or underscores'

  int version1_2_3;
  int gradle_9_5_1; // violation, 'underscores allowed only between adjacent digits'

  int count99;
  int _foo; // violation, 'underscores allowed only between adjacent digits'
}
// xdoc section -- end
