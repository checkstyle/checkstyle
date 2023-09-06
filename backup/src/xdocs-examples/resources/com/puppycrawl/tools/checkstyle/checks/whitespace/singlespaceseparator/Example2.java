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
  // violation below 'Use a single space'
  void fun1() {}  // 2 whitespaces before the comment starts
  // violation below 'Use a single space'
  void fun2() { return; }  /* 2 whitespaces before the comment starts */
  // violation below 'Use a single space'
  /* 2 whitespaces after the comment ends */  int a;

  String s; /* OK, 1 whitespace */

  /**
   * This is a Javadoc comment
   */  int b; // 2 whitespaces after the javadoc comment ends
  // violation above 'Use a single space'
  float f1;

  /**
   * OK, 1 white space after the doc comment ends
   */ float f2;
}
// xdoc section -- end
