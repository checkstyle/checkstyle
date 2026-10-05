/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExceptionThrowShouldUseContext"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.exceptionthrowshouldusecontext;

import java.io.IOException;

// xdoc section - start
class Example1 {
  void method1(String someParameter) {
    try {
      riskyOperation();
    }
    catch (IOException ex1) {
      // violation below 'Exception throw should always use context variables.'
      throw new RuntimeException("unable to process ", ex1);
    }
    catch (Exception ex2) {
      // ok, uses 'someParameter' in the exception constructor
      throw new RuntimeException("unable to process " + someParameter, ex2);
    }
  }

  private void riskyOperation() throws IOException {
  }
}
// xdoc section - end
