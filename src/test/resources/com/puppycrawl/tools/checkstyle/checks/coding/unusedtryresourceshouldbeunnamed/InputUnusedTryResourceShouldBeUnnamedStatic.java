/*
UnusedTryResourceShouldBeUnnamed

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

import java.io.*;

public class InputUnusedTryResourceShouldBeUnnamedStatic implements AutoCloseable {

    final static InputUnusedTryResourceShouldBeUnnamedStatic r1 =
      new InputUnusedTryResourceShouldBeUnnamedStatic();
    final InputUnusedTryResourceShouldBeUnnamedStatic r2 =
      new InputUnusedTryResourceShouldBeUnnamedStatic();

    void staticVariables() {
      try (r1) { }
      catch (Exception e) { }

      try (r1.r2) { }
      catch (Exception e) { }
    }

    @Override
    public void close() throws Exception { }
}

