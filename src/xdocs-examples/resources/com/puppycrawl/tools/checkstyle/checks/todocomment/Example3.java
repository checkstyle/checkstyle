/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TodoComment">
      <property name="id" value="BoxComments"/>
      <property name="format" value="^\s*([*=#-])\1{8,}\s*$"/>
      <message key="todo.match"
               value="Comment uses box-like repetitive character pattern."/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.todocomment;

// xdoc section -- start
/** // violation 'Comment uses box-like repetitive character pattern'
 =========================================
 Box comment with equals
 =========================================
 */

public class Example3 {
  // violation below 'Comment uses box-like repetitive character pattern'
  // ---------
  void method1() {
    int x = 1;
  }

  // violation below 'Comment uses box-like repetitive character pattern'
  // =========
  void method2() {
    int y = 2;
  }

  // violation below 'Comment uses box-like repetitive character pattern'
  // *********
  void method3() {
    int z = 3;
  }

  // violation below 'Comment uses box-like repetitive character pattern'
  // #########
  void method4() {
    int a = 4;
  }

  // normal comment
  void method5() {
    int b = 5;
  }

  // ###### (only 6 chars - below 9-char threshold)
  void method6() {
    int c = 6;
  }
}
// xdoc section -- end
