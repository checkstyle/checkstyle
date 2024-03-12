/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MemberName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.membername;

// xdoc section -- start
class Example1 {
  public int num1;
  protected int num2;
  final int num3 = 3;
  private int num4;

  static int num5;
  public static final int CONSTANT = 1;

  public int NUM1; // violation

  protected int NUM2; // violation

  int NUM3; // violation

  private int NUM4; // violation

}
// xdoc section -- end
