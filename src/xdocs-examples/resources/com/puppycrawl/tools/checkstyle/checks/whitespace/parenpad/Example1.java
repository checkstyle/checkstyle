/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParenPad"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.io.IOException;

// xdoc section -- start
class Example1 {
  int x;
  public Example1(int n) {}

  public void fun() {
    try {
      throw new IOException();
    } catch( IOException e) {} // violation 'is followed by whitespace'
    catch(Exception e ) {}  // violation 'is preceded with whitespace'
    for ( int i = 0; i < x; i++ ) {
      // 2 violations above:
      // ''(' is followed by whitespace'
      // '')' is preceded with whitespace'
    }
  }

  public void fun2() {
    switch( x) { // violation 'is followed by whitespace'
      case 2:
        break;
      default:
        break;
    }
  }

  class Example3 extends Example1 {
    public Example3() {
      super(1 ); // violation '')' is preceded with whitespace'
    }
    public Example3(int k) {
      super( k );
      // 2 violations above:
      // ''(' is followed by whitespace'
      // '')' is preceded with whitespace'
      for ( int i = 0; i < k; i++) { // violation ''(' is followed by whitespace'
      }
    }
  }
}
// xdoc section -- end
