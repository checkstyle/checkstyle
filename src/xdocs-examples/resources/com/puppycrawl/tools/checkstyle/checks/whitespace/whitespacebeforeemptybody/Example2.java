/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceBeforeEmptyBody">
      <property name="tokens" value="METHOD_DEF, CLASS_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

// xdoc section -- start
class Example2 {
  Example2(){}

  void methodWarn(){} // violation 'Whitespace is not present before the empty body'
  void methodGood() {}

  class InnerWarn{} // violation 'Whitespace is not present before the empty body'
  class InnerGood {}

  static{}
  static {}

  Runnable lambdaWarn = () ->{};
  Runnable lambdaGood = () -> {};

  void testLoops() {
    while (true){}
  }

  void testTryCatch() {
    try{}
    catch (Exception e){}

    try {}
    catch (Exception e) {}
  }

  Object anonWarn = new Object(){};
  Object anonGood = new Object() {};
}
// xdoc section -- end
