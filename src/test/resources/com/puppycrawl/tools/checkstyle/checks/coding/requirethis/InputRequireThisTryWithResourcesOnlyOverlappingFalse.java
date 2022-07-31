/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                new InputStreamReader(null, charset))) { } // violation
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
            charset += a; // violation
        }
        catch (Exception ex) { }
    }

    String methodToInvoke() {
        return "string";
    }

    void methodInvoke() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(null, methodToInvoke()))) { } // violation
        catch (Exception ex) { }
    }

    void methodIdentCopy() {
        try (BufferedReader methodToInvoke = new BufferedReader(
                new InputStreamReader(null, this.methodToInvoke()))) { } // ok
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

    @Override
    public void close() throws Exception { }
}
