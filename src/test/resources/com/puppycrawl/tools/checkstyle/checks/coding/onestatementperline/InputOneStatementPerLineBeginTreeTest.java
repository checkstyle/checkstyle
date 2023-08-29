/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.OutputStream;

public class InputOneStatementPerLineBeginTreeTest {

    void testNestedInLambda() {
        Runnable r = () -> {
            try (OutputStream s1 = new PipedOutputStream();
                 OutputStream s2 = new PipedOutputStream();) {
            }
            catch (IOException e) {
            }
        };
    }

    public void doLegalForLoop() {
        for (int i = 0; i < 20; i++) {
        }
     }

    private void foo4() {
        for(int n = 0,
            k = 1
            ; n<5
            ; ) { int a = 5,
        b = 2;}
    }

}
