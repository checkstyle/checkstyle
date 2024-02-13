/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.*;


public class InputOneStatementPerLineBeginTreeLastVariableResourcesStatementEnd1 {
    public void resourceListExists() throws IOException {
        try (FileInputStream f1 = new FileInputStream("1"); FileInputStream f2 =
                new FileInputStream("2")) { // violation above 'Only one .* per line allowed'
        }
    }
}
