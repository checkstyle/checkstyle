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

  final int bad = 0;
  final int Bad = 0;
  // violation above, 'Non-constant field name 'Bad' must start lowercase'

  public final int myValue = 0;
  public final int mValue = 0;
  // violation above, 'avoid single lowercase letter followed by uppercase'

  private final int foo = 0;
  private final int f = 0; // violation, 'be at least 2 chars'

  protected final int fooBar = 0;
  protected final int foo_bar = 0;
  // violation above, 'underscores allowed only between adjacent digits'

  int fieldA;
  int fA; // violation, 'avoid single lowercase letter followed by uppercase'

  int xmlParser;
  int xml$parser; // violation, 'contain only letters, digits or underscores'

  int gradle9_5_1;
  int gradle_9_5_1; // violation, 'underscores allowed only between adjacent digits'

  int foo2;
  int _foo2; // violation, 'underscores allowed only between adjacent digits'
}
// xdoc section -- end
