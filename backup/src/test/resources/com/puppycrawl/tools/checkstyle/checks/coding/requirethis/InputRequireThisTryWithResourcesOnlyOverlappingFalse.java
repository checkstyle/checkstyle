/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.*;
import java.util.Scanner;

public class InputRequireThisTryWithResourcesOnlyOverlappingFalse implements AutoCloseable {
    private BufferedReader fldBufferedReader;
    private InputStreamReader fldStreamReader;
    private Scanner fldScanner;
    private String fldCharset = "utf-8";

    void oneResource() {
        try (BufferedReader fldBufferedReader
                     = new BufferedReader(new InputStreamReader(null, "utf-8"))) { } // ok
        catch (IOException e) { }
    }

    void twoResourcesReferencingEachOther() {
        try (InputStreamReader fldStreamReader = new InputStreamReader(null, "utf-8"); // ok
             BufferedReader fldBufferedReader = new BufferedReader(fldStreamReader)) { } // ok
        catch (IOException e) { }
    }

    void threeResourcesReferencingEachOther() {
        try (InputStreamReader fldStreamReader = new InputStreamReader(null, "utf-8"); // ok
             BufferedReader fldBufferedReader = new BufferedReader(fldStreamReader); // ok
             Scanner fldScanner
                     = new Scanner(fldStreamReader.toString()
                     + fldBufferedReader.toString())) { } // ok
        catch (IOException e) { }
    }

    void failToHandleParameter() {
        try (BufferedReader fldBufferedReader = new BufferedReader(new InputStreamReader(
                null, fldCharset))) { } // violation '.*variable 'fldCharset'.*'
        catch (IOException e) { }
    }

    void handleParameter() {
        try (BufferedReader fldBufferedReader = new BufferedReader(
                new InputStreamReader(null, this.fldCharset))) { } // ok
        catch (IOException e) { }
    }

    void noResources() {
        try {
            int a = 5;
            fldCharset += a; // violation '.*variable 'fldCharset'.*'
        }
        catch (Exception ex) { }
    }

    String methodToInvoke() {
        return "string";
    }

    void methodInvoke() {
        try (BufferedReader fldBufferedReader = new BufferedReader(
                // violation below 'Method .* 'methodToInvoke' needs "this."'
                new InputStreamReader(null, methodToInvoke()))) { }
        catch (Exception ex) { }
    }

    void methodIdentCopy() {
        try (BufferedReader methodToInvoke = new BufferedReader(
                new InputStreamReader(null, this.methodToInvoke()))) { // ok
            // violation below 'Method .* 'methodToInvoke' needs "this."'
            String a = methodToInvoke() + methodToInvoke.toString();
        }
        catch (Exception ex) { }
    }

    final static InputRequireThisTryWithResources r1 = new InputRequireThisTryWithResources();
    final InputRequireThisTryWithResources r2 = new InputRequireThisTryWithResources();
    static InputRequireThisTryWithResources r3 = new InputRequireThisTryWithResources();
    InputRequireThisTryWithResources r4 = new InputRequireThisTryWithResources();

    void staticVariables() {
        try (r1) { } // ok
        catch (Exception e) { }
        try (r1.r2) { } // ok
        catch (Exception e) { }
        try (r1.r2.r4.r2.r4.r2) { } // ok
        catch (Exception e) { }
    }

    void nestedTryWithResources() {
        try (InputStreamReader fldStreamReader = new InputStreamReader(null, "utf-8")) { // ok
            try (BufferedReader fldBufferedReader = new BufferedReader(fldStreamReader)) { // ok
                try (Scanner fldScanner = new Scanner(
                        fldBufferedReader.toString() + fldStreamReader.toString())) { } // ok
            }
            // violation below '.*variable 'fldBufferedReader' needs "this."'
            try (Scanner fldScanner = new Scanner(fldBufferedReader.toString()
                    + fldStreamReader.toString())) { }

            String a = fldStreamReader.toString()
                    + fldBufferedReader.toString() + fldScanner.toString(); // 2 violations
        }
        catch (IOException e) {
            String a = fldStreamReader.toString() // violation '.*variable 'fldStreamReader'.*'
                    + fldBufferedReader.toString() + fldScanner.toString(); // 2 violations
        }
    }

    @Override
    public void close() throws Exception { }
}
