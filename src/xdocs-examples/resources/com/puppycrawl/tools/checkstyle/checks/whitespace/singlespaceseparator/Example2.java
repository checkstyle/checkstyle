/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SingleSpaceSeparator">
      <property name="validateComments" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

// xdoc section -- start
class Example2 {
  int foo()   { // violation 'Use a single space'
    return  1; // violation 'Use a single space'
  }

  void fun1() {}
  // 2 violations 3 lines below:
  // 'Use a single space to separate non-whitespace characters.'
  // 'Use a single space to separate non-whitespace characters.'
  void  fun2() { return; }  /* 2 whitespaces before the comment starts */
  // violation below 'Use a single space'
  /* 2 whitespaces after the comment ends */  int a;

  String s; /* OK, 1 whitespace */

  /**
   * This is a Javadoc comment
   */  int b;
  // violation above 'Use a single space'
}
// xdoc section -- end
