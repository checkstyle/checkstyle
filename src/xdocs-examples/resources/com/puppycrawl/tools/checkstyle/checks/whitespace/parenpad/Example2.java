/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParenPad">
      <property name="tokens"
                value="LITERAL_FOR, LITERAL_CATCH, SUPER_CTOR_CALL"/>
      <property name="option" value="space"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.io.IOException;

// xdoc section -- start
class Example2 {
  int x;

  public Example2(int n) {
  }

  public void fun() {
    try {
      throw new IOException();
    } catch( IOException e) { // violation 'not preceded with whitespace'
    } catch( Exception e ) {
    }

    for ( int i = 0; i < x; i++ ) {
    }
  }

  class Bar extends Example2 {
    public Bar() {
      super(1 ); // violation 'not followed by whitespace'
    }

    public Bar(int k) {
      super( k );

      for ( int i = 0; i < k; i++) { // violation 'not preceded with whitespace'
      }
    }
  }
}
// xdoc section -- end
