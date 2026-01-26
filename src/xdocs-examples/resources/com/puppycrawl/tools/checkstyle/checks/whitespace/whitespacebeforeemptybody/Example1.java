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
  Example1(){} // violation 'Whitespace is not present before the empty body'

  void method(){} // violation 'Whitespace is not present before the empty body'

  class Inner{} // violation 'Whitespace is not present before the empty body'

  interface InnerInterface{}
  // violation above, 'Whitespace is not present before the empty body'

  enum InnerEnum{} // violation 'Whitespace is not present before the empty body'

  static{} // violation 'Whitespace is not present before the empty body of 'static''

  Runnable lambda = () ->{};
  // violation above, 'Whitespace is not present before the empty body'

  void testLoops() {
    while (true){} // violation 'Whitespace is not present before the empty body'
  }

  void testTryCatchFinally() {
    try{} // violation 'Whitespace is not present before the empty body of 'try''
    catch (Exception e){}
    // violation above, 'Whitespace is not present before the empty body of 'catch''
    finally{} // violation 'Whitespace is not present before the empty body'
  }

  void testSynchronized() {
    synchronized (this){}
    // violation above, 'Whitespace is not present before the empty body'
  }

  void testSwitch(int x) {
    switch (x){}
    // violation above, 'Whitespace is not present before the empty body of 'switch''
  }

  Object anon = new Object(){};
  // violation above, 'Whitespace is not present before the empty body'

  void method1() {}
  class Inner1 {}
  interface InnerInterface1 {}
  enum InnerEnum1 {}

}
// xdoc section -- end
