/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParenPad">
      <property name="option" value="space"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.io.IOException;

// xdoc section -- start
class Example3 {
  int x;
  public Example3( int n) {} // violation '')' is not preceded with whitespace'

  public void fun() {
    try {
      throw new IOException();
    } catch( IOException e) {} // violation 'not preceded with whitespace'
    catch(Exception e ) {} // violation 'not followed by whitespace'
    for ( int i = 0; i < x; i++ ) {



    }
  }

  public void fun2() {
    switch( x) { // violation 'not preceded with whitespace'
      case 2:
        break;
      default:
        break;
    }
  }

  class Example4 extends Example3 {
    public Example4() {
      super(1 ); // violation 'not followed by whitespace'
    }
    public Example4( int k) { // violation '')' is not preceded with whitespace'
      super( k );


      // violation below 'not preceded with whitespace'
      for ( int i = 0; i < k; i++) {
      }
    }
  }
}
// xdoc section -- end
