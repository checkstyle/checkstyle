/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExceptionWrappingShouldUseContext"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.exceptionwrappingshouldusecontext;

import java.io.IOException;

// xdoc section - start
class Example1 {
  void method1(String someParameter) {
    try {
      riskyOperation();
    }
    catch (IOException ex) {
      // violation below 'Exception wrapping should always use context variables.'
      throw new RuntimeException("unable to process ", ex);
    }
  }

  void method2(String someParameter) {
    try {
      riskyOperation();
    }
    catch (IOException ex) {
      // ok, uses 'someParameter' in the exception constructor
      throw new RuntimeException("unable to process " + someParameter, ex);
    }
  }

  private void riskyOperation() throws IOException {
  }
}
// xdoc section - end
