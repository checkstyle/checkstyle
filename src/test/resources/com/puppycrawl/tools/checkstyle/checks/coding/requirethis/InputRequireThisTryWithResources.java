/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.*;
import java.util.Scanner;

public class InputRequireThisTryWithResources implements AutoCloseable {
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
             Scanner fldScanner = new Scanner(
                     fldStreamReader.toString() + fldBufferedReader.toString())) { } // ok
        catch (IOException e) { }
    }

    void failToHandleParameter() {
        try (BufferedReader fldBufferedReader = new BufferedReader(
                new InputStreamReader(null, fldCharset))) { } // ok
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
            fldCharset += a; // ok
        }
        catch (Exception ex) { }
    }

    String methodToInvoke() {
        return "string";
    }

    void methodInvoke() {
        try (BufferedReader fldBufferedReader = new BufferedReader(
                new InputStreamReader(null, methodToInvoke()))) { } // ok
        catch (Exception ex) { }
    }

    void methodIdentCopy() {
        try (BufferedReader methodToInvoke = new BufferedReader(
                new InputStreamReader(null, this.methodToInvoke()))) { // ok
            String a = methodToInvoke() + methodToInvoke.toString(); // ok
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
            try (Scanner fldScanner = new Scanner(
                    fldBufferedReader.toString() + fldStreamReader.toString())) { } // ok
            String a = fldStreamReader.toString() // ok
                    + fldBufferedReader.toString() + fldScanner.toString(); // ok
        }
        catch (IOException e) {
            String a = fldStreamReader.toString() // ok
                    + fldBufferedReader.toString() + fldScanner.toString(); // ok
        }
    }

    @Override
    public void close() throws Exception { }
}
