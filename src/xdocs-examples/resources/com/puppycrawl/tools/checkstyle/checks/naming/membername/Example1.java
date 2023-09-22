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
  public int num1; // OK
  protected int num2; // OK
  final int num3 = 3; // OK
  private int num4; // OK

  static int num5; // ignored: not an instance variable
  public static final int CONSTANT = 1; // ignored: not an instance variable

  public int NUM1; // violation, name 'NUM1'
  // must watch pattern '^[a-z][a-zA-Z0-9]*$'
  protected int NUM2; // violation, name 'NUM2'
  // must watch pattern '^[a-z][a-zA-Z0-9]*$'
  int NUM3; // violation, name 'NUM3'
  // must watch pattern '^[a-z][a-zA-Z0-9]*$'
  private int NUM4; // violation, name 'NUM4'
  // must watch pattern '^[a-z][a-zA-Z0-9]*$'
}
// xdoc section -- end
