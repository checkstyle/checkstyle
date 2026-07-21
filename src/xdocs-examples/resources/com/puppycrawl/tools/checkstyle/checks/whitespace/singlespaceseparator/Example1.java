/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SingleSpaceSeparator"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

// xdoc section -- start
class Example1 {
  int foo()   { // violation 'Use a single space'
    return  1;  // violation 'Use a single space'
  }

  void fun1() {}


  // violation below 'Use a single space'
  void  fun2() { return; }  /* 2 whitespaces before the comment starts */

  /* 2 whitespaces after the comment ends */  int a;

  String s; /* OK, 1 whitespace */

  /**
   * This is a Javadoc comment
   */  int b;

}
// xdoc section -- end
