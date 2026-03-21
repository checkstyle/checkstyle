/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/


package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.OutputStream;

/**
 * Config treatTryResourcesAsStatement = true
 */
public class InputOneStatementPerLineTryWithResources {

    void method() throws IOException {
        OutputStream s1 = new PipedOutputStream();
        OutputStream s2 = new PipedOutputStream();
        try (s1; s2; OutputStream s3 = new PipedOutputStream();) {
        }
        try (s1; OutputStream s4 = new PipedOutputStream(); s2;) {
        }
        try (s1; s2; OutputStream s5 = new PipedOutputStream()) {
        }
        try (s1; OutputStream s6 = new PipedOutputStream(); s2) {
        }
        try (
                // violation below 'Only one statement per line allowed.'
OutputStream s7 = new PipedOutputStream();OutputStream s8 = new PipedOutputStream();
           s2;
        ) {}
        try (
                // violation below 'Only one statement per line allowed.'
OutputStream s9=new PipedOutputStream();s2;OutputStream s10 = new PipedOutputStream())
        {}
        try (s1; OutputStream s11 = new PipedOutputStream();
             s2;) {
        }
        try (OutputStream
                // violation below 'Only one statement per line allowed.'
             s12 = new PipedOutputStream();s1;OutputStream s3 = new PipedOutputStream()
             ;s2;) {
        }
        try (OutputStream
                // violation below 'Only one statement per line allowed.'
             s12 = new PipedOutputStream();s1;OutputStream s3
                = new PipedOutputStream()) {}
        try (s1; s2; OutputStream stream3 =
             new PipedOutputStream()) {}
        try (OutputStream s10 = new PipedOutputStream();
             OutputStream s11 = new PipedOutputStream(); s2;) {
        }
    }

    void testNestedInLambda() {
        Runnable r = () -> {
            try (OutputStream s1 = new PipedOutputStream();
                 OutputStream s2 = new PipedOutputStream();) {
            }
            catch (IOException e) {
            }
        };
    }
}
