/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceBeforeEmptyBody"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

// xdoc section -- start
class Example1 {
  // violation below, 'Whitespace is not present before the empty body of 'Example1''
  Example1(){}

  void methodWarn(){} // violation 'Whitespace is not present before the empty body'
  void methodGood() {}

  class InnerWarn{} // violation 'Whitespace is not present before the empty body'
  class InnerGood {}

  static{} // violation 'Whitespace is not present before the empty body of 'static''
  static {}

  // violation below, 'Whitespace is not present before the empty body of 'lambda''
  Runnable lambdaWarn = () ->{};
  Runnable lambdaGood = () -> {};

  void testLoops() {
    while (true){} // violation 'Whitespace is not present before the empty body'
  }

  void testTryCatch() {
    try{} // violation 'Whitespace is not present before the empty body of 'try''
    // violation below, 'Whitespace is not present before the empty body of 'catch''
    catch (Exception e){}

    try {}
    catch (Exception e) {}
  }

  // violation below, 'Whitespace is not present before the empty body'
  Object anonWarn = new Object(){};
  Object anonGood = new Object() {};
}
// xdoc section -- end
