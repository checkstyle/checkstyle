/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.*;

// ok
public class InputOneStatementPerLineBeginTreeLastVariableResourcesStatementEnd2 {
    void m(OutputStream out) throws IOException {
        try (out; InputStream in = new FileInputStream("filename")) {

        }
  }
}
