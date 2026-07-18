/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParenPad">
      <property name="tokens"
                value="LITERAL_FOR, LITERAL_CATCH, SUPER_CTOR_CALL"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.io.IOException;

// xdoc section -- start
class Example2 {
  int x;
  public Example2(int n) {}

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
    switch( x) {
      case 2:
        break;
      default:
        break;
    }
  }

  class Example3 extends Example2 {
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
