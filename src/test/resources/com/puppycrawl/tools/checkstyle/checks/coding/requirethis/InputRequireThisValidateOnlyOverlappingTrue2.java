/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputRequireThisValidateOnlyOverlappingTrue2 {
    private final InputRequireThisValidateOnlyOverlappingTrue2 analyzer;
    private final int positionOffsetGap;

    public InputRequireThisValidateOnlyOverlappingTrue2(
            InputRequireThisValidateOnlyOverlappingTrue2 analyzer) {
        this(analyzer.analyzer(), 0); // ok
    }

    public InputRequireThisValidateOnlyOverlappingTrue2(
            InputRequireThisValidateOnlyOverlappingTrue2 analyzer, int positionOffsetGap) {
        this.analyzer = analyzer;
        this.positionOffsetGap = positionOffsetGap;
    }

    public InputRequireThisValidateOnlyOverlappingTrue2 analyzer() {
        return null;
    }
}
class Issue11821a {
    private BufferedReader br;
    private InputStreamReader isr;
    private Scanner sc;

    void oneResource() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(null, "utf-8"))) { // ok
        }
        catch (IOException e) {
        }
    }

    void twoResourcesReferencingEachOther() {
        try (InputStreamReader isr = new InputStreamReader(null, "utf-8"); // ok
             BufferedReader br = new BufferedReader(isr)) { // ok
        }
        catch (IOException e) {
        }
    }

    void threeResourcesReferencingEachOther() {
        try (InputStreamReader isr = new InputStreamReader(null, "utf-8"); // ok
             BufferedReader br = new BufferedReader(isr); // ok
             Scanner sc = new Scanner(isr.toString() + br.toString())) { // ok
        }
        catch (IOException e) {
        }
    }

    private String charset = "utf-8";
    void failToHandleParameter() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(null, charset))) { // ok
        } catch (IOException e) {
        }
    }

    void handleParameter() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(null, this.charset))) { // ok
        }
        catch (IOException e) {
        }
    }

    void noResources() {
        try {
            int a = 5;
            charset += a; // ok
        } catch (Exception ex) {
        }
    }

    String methodToInvoke() {
        return "string";
    }

    void methodInvoke() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(null, methodToInvoke()) // ok
        )) {
        } catch (Exception ex) {
        }
    }

    void methodIdentCopy() {
        try (BufferedReader methodToInvoke = new BufferedReader(
                new InputStreamReader(null, this.methodToInvoke()) // ok
        )) {
        } catch (Exception ex) {
        }
    }
}
