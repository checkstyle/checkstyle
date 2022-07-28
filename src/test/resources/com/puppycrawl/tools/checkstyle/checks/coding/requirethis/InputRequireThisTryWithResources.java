/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.*;
import java.util.Scanner;

public class InputRequireThisTryWithResources implements AutoCloseable{
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
                new InputStreamReader(null, charset))) { } // ok
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
            charset += a; // ok
        }
        catch (Exception ex) { }
    }

    String methodToInvoke() {
        return "string";
    }

    void methodInvoke() {
        try (BufferedReader br = new BufferedReader(
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
            try (Scanner sc = new Scanner(br.toString() + isr.toString())) { } // ok
            String a = isr.toString() + br.toString() + sc.toString(); // ok
        }
        catch (IOException e) {
            String a = isr.toString() + br.toString() + sc.toString(); // ok
        }
    }

    @Override
    public void close() throws Exception { }
}
