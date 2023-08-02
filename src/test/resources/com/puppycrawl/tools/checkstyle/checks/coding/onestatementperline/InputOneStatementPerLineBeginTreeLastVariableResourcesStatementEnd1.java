/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.*;

// ok
public class InputOneStatementPerLineBeginTreeLastVariableResourcesStatementEnd1 {
    public void resourceListExists() throws IOException {
        try (FileInputStream f1 = new FileInputStream("1"); FileInputStream f2 = // violation 'Only one statement per line allowed'
                new FileInputStream("2")) {
        }
    }
}
