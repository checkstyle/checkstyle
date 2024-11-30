/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnonInnerLength">
      <property name="max" value="7"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.anoninnerlength;

// xdoc section -- start
class Example2 {
  void testMethod() {
    Runnable shortAnonClass = new Runnable() {
      @Override
      public void run() {
        System.out.println("Short anonymous class.");
      }
    };
    shortAnonClass.run();
    // violation below, 'Anonymous inner class length is 9 lines (max allowed is 7)'
    Runnable longAnonClass = new Runnable() {
      @Override
      public void run() {
        System.out.println("This is a lengthy anonymous class.");
        System.out.println("It has too many lines of code.");
        System.out.println("Exceeding the length limit.");
        System.out.println("This would trigger the AnonInnerLength rule.");
      }
    };
    longAnonClass.run();
  }
}
// xdoc section -- end
