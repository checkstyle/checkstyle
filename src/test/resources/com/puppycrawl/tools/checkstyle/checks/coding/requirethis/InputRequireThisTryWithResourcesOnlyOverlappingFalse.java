/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.*;
import java.util.Scanner;

public class InputRequireThisTryWithResourcesOnlyOverlappingFalse implements AutoCloseable{
    private BufferedReader bufferedReader;
    private InputStreamReader streamReader;
    private Scanner scanner;
    private String fieldCharset = "utf-8";

    void oneResource() {
        try (BufferedReader bufferedReader
                     = new BufferedReader(new InputStreamReader(null, "utf-8"))) { } // ok
        catch (IOException e) { }
    }

    void twoResourcesReferencingEachOther() {
        try (InputStreamReader streamReader = new InputStreamReader(null, "utf-8"); // ok
             BufferedReader bufferedReader = new BufferedReader(streamReader)) { } // ok
        catch (IOException e) { }
    }

    void threeResourcesReferencingEachOther() {
        try (InputStreamReader streamReader = new InputStreamReader(null, "utf-8"); // ok
             BufferedReader bufferedReader = new BufferedReader(streamReader); // ok
             Scanner scanner
                     = new Scanner(streamReader.toString() + bufferedReader.toString())) { } // ok
        catch (IOException e) { }
    }

    void failToHandleParameter() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                null, fieldCharset))) { } // violation '.*variable 'fieldCharset'.*'
        catch (IOException e) { }
    }

    void handleParameter() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(null, this.fieldCharset))) { } // ok
        catch (IOException e) { }
    }

    void noResources() {
        try {
            int a = 5;
            fieldCharset += a; // violation '.*variable 'fieldCharset'.*'
        }
        catch (Exception ex) { }
    }

    String methodToInvoke() {
        return "string";
    }

    void methodInvoke() {
        try (BufferedReader bufferedReader = new BufferedReader(
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
        try (InputStreamReader streamReader = new InputStreamReader(null, "utf-8")) { // ok
            try (BufferedReader bufferedReader = new BufferedReader(streamReader)) { // ok
                try (Scanner scanner = new Scanner(
                        bufferedReader.toString() + streamReader.toString())) { } // ok
            }
            // violation below '.*variable 'bufferedReader' needs "this."'
            try (Scanner scanner = new Scanner(bufferedReader.toString()
                    + streamReader.toString())) { }
            // 2 violations below
            String a = streamReader.toString() + bufferedReader.toString() + scanner.toString();
        }
        catch (IOException e) {
             // 3 violations below
            String a = streamReader.toString() + bufferedReader.toString() + scanner.toString();
        }
    }

    @Override
    public void close() throws Exception { }
}
