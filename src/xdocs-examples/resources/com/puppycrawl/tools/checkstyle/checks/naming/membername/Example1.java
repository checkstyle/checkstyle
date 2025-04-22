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

  public int NUM1; // violation 'Name 'NUM1' must match pattern'

  protected int NUM2; // violation 'Name 'NUM2' must match pattern'

  int NUM3; // violation 'Name 'NUM3' must match pattern'

  private int NUM4; // violation 'Name 'NUM4' must match pattern'

}
// xdoc section -- end
