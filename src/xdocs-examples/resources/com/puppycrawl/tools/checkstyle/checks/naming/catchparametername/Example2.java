/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CatchParameterName">
      <property name="format" value="^[a-z][a-zA-Z0-9]+$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.catchparametername;


import java.io.IOException;

// xdoc section -- start
public class Example2 {
  public void myTest() {
    try {
      throw new IOException();
      // ...
    } catch (ArithmeticException ex) { // OK
      // ...
    } catch (ArrayIndexOutOfBoundsException ex2) { // OK
      // ...
    } catch (IOException thirdException) { // OK
      // ...
    } catch (Exception FourthException) { // violation, 'Name 'FourthException' must match pattern'
      // ...
    }
  }
}
// xdoc section -- end
