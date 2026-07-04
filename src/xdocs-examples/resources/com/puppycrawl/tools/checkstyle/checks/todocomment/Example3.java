/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TodoComment">
      <property name="id" value="BoxComments"/>
      <property name="format" value="^\s*([*=#])\1{8,}\s*$"/>
      <message key="todo.match"
               value="Comment uses box-like repetitive character pattern."/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.todocomment;

// xdoc section -- start
// violation below 'Comment uses box-like repetitive character pattern'
/**
 =========================================
 Box comment with equals
 =========================================
 */
public class Example3 {
  int i;
  int x;

  // =========
  // violation above 'Comment uses box-like repetitive character pattern'
  public void test() {
    // TODO: do differently in future

    i++;

    // todo: do differently in future

    i++;

    // FIXME: handle x = 0 case

    i = i / x;
  }
  // *********
  // violation above 'Comment uses box-like repetitive character pattern'
  public void method1() {
    int y = 2;
  }
  // #########
  // violation above 'Comment uses box-like repetitive character pattern'
  public void method2() {
    int z = 3;
  }
  // ###### (only 6 chars)
  public void method4() {
    int b = 5;
  }
}
// xdoc section -- end
