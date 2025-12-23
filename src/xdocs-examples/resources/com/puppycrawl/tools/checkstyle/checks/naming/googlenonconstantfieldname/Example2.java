/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GoogleNonConstantFieldName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

// xdoc section -- start
class Example2 {

  static final int STATIC_FINAL = 0;
  private static final int Invalid_Name = 1;

  static int staticField;
  private static int Static_Bad;

  final int foo = 0;
  public final int httpClient = 0;
  private final int version123 = 0;
  protected final int guava33_4_5 = 0;

  int bar;
  public int xmlParser;
  private int count99;
  protected int release22_1_0;

  int id;
  int myVariableName;
  int version1_2;
}
// xdoc section -- end
