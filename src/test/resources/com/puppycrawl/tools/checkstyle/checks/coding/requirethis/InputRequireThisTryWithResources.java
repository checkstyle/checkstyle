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
                     = new BufferedReader(new InputStreamReader(null, "utf-8"))) { }
        catch (IOException e) { }
    }

    void twoResourcesReferencingEachOther() {
        try (InputStreamReader fldStreamReader = new InputStreamReader(null, "utf-8");
             BufferedReader fldBufferedReader = new BufferedReader(fldStreamReader)) { }
        catch (IOException e) { }
    }

    void threeResourcesReferencingEachOther() {
        try (InputStreamReader fldStreamReader = new InputStreamReader(null, "utf-8");
             BufferedReader fldBufferedReader = new BufferedReader(fldStreamReader);
             Scanner fldScanner = new Scanner(
                     fldStreamReader.toString() + fldBufferedReader.toString())) { }
        catch (IOException e) { }
    }

    void failToHandleParameter() {
        try (BufferedReader fldBufferedReader = new BufferedReader(
                new InputStreamReader(null, fldCharset))) { }
        catch (IOException e) { }
    }

    void handleParameter() {
        try (BufferedReader fldBufferedReader = new BufferedReader(
                new InputStreamReader(null, this.fldCharset))) { }
        catch (IOException e) { }
    }

    void noResources() {
        try {
            int a = 5;
            fldCharset += a;
        }
        catch (Exception ex) { }
    }

    String methodToInvoke() {
        return "string";
    }

    void methodInvoke() {
        try (BufferedReader fldBufferedReader = new BufferedReader(
                new InputStreamReader(null, methodToInvoke()))) { }
        catch (Exception ex) { }
    }

    void methodIdentCopy() {
        try (BufferedReader methodToInvoke = new BufferedReader(
                new InputStreamReader(null, this.methodToInvoke()))) {
            String a = methodToInvoke() + methodToInvoke.toString();
        }
        catch (Exception ex) { }
    }

    final static InputRequireThisTryWithResources r1 = new InputRequireThisTryWithResources();
    final InputRequireThisTryWithResources r2 = new InputRequireThisTryWithResources();
    static InputRequireThisTryWithResources r3 = new InputRequireThisTryWithResources();
    InputRequireThisTryWithResources r4 = new InputRequireThisTryWithResources();

    void staticVariables() {
        try (r1) { }
        catch (Exception e) { }

        try (r1.r2) { }
        catch (Exception e) { }

        try (r1.r2.r4.r2.r4.r2) { }
        catch (Exception e) { }
    }

    void nestedTryWithResources() {
        try (InputStreamReader fldStreamReader = new InputStreamReader(null, "utf-8")) {
            try (BufferedReader fldBufferedReader = new BufferedReader(fldStreamReader)) {
                try (Scanner fldScanner = new Scanner(
                        fldBufferedReader.toString() + fldStreamReader.toString())) { }
            }
            try (Scanner fldScanner = new Scanner(
                    fldBufferedReader.toString() + fldStreamReader.toString())) { }
            String a = fldStreamReader.toString()
                    + fldBufferedReader.toString() + fldScanner.toString();
        }
        catch (IOException e) {
            String a = fldStreamReader.toString()
                    + fldBufferedReader.toString() + fldScanner.toString();
        }
    }

    @Override
    public void close() throws Exception { }
}
