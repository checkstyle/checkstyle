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
    private BufferedReader br;
    private InputStreamReader isr;
    private Scanner sc;

    void oneResource() {
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(null, "utf-8"))) { } // ok
        catch (IOException e) { }
    }

    void twoResourcesReferencingEachOther() {
        try (InputStreamReader isr = new InputStreamReader(null, "utf-8"); // ok
             BufferedReader br = new BufferedReader(isr)) { } // ok
        catch (IOException e) { }
    }

    void threeResourcesReferencingEachOther() {
        try (InputStreamReader isr = new InputStreamReader(null, "utf-8"); // ok
             BufferedReader br = new BufferedReader(isr); // ok
             Scanner sc = new Scanner(isr.toString() + br.toString())) { } // ok
        catch (IOException e) { }
    }

    private String charset = "utf-8";

    void failToHandleParameter() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(null, charset))) { } // violation '.*variable 'charset'.*'
        catch (IOException e) { }
    }

    void handleParameter() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(null, this.charset))) { } // ok
        catch (IOException e) { }
    }

    void noResources() {
        try {
            int a = 5;
            charset += a; // violation '.*variable 'charset'.*'
        }
        catch (Exception ex) { }
    }

    String methodToInvoke() {
        return "string";
    }

    void methodInvoke() {
        try (BufferedReader br = new BufferedReader(
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

    final static InputRequireThisTryWithResources r1
            = new InputRequireThisTryWithResources();
    final InputRequireThisTryWithResources r2
            = new InputRequireThisTryWithResources();
    static InputRequireThisTryWithResources r3
            = new InputRequireThisTryWithResources();
    InputRequireThisTryWithResources r4
            = new InputRequireThisTryWithResources();

    void staticVariables() {
        try (r1) { } // ok
        catch (Exception e) { }

        try (r1.r2) { } // ok
        catch (Exception e) { }

        try (r1.r2.r4.r2.r4.r2) { } // ok
        catch (Exception e) { }
    }

    void nestedTryWithResources() {
        try (InputStreamReader isr = new InputStreamReader(null, "utf-8")) { // ok
            try (BufferedReader br = new BufferedReader(isr)) { // ok
                try (Scanner sc = new Scanner(br.toString() + isr.toString())) { } // ok
            }
            // violation below '.*variable 'br' needs "this."'
            try (Scanner sc = new Scanner(br.toString() + isr.toString())) { }
            String a = isr.toString() + br.toString() + sc.toString(); // 2 violations
        }
        catch (IOException e) {
            String a = isr.toString() + br.toString() + sc.toString(); // 3 violations
        }
    }

    @Override
    public void close() throws Exception { }
}
