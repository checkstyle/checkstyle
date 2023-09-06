/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;

public final class InputRequireThisStatic { // ok
    public static String staticField1 = "";

    public static String staticField2 = new String(staticField1);

    public String instanceField1;
    public BufferedReader instFld2;

    static {
        try (BufferedReader instFld2 = new BufferedReader(new InputStreamReader(null, "utf-8"))) {
            instFld2.readLine();
        }
        catch (FileNotFoundException e) {
        }
        catch (IOException e) {
        }
    }

    public String get() {
        return staticField1;
    }

    void foo50(String staticField1) {
        staticField1 = staticField1;
    }

    void foo52(String staticField1) {
        staticField1 += staticField1;
    }

    static void test(String instanceField1) {
        if (instanceField1 == null) {
            instanceField1 = staticField1;
        }
    }

    static void test2() {
        try (BufferedReader instFld2 = new BufferedReader(new InputStreamReader(null, "utf-8"))) {
            instFld2.readLine();
        }
        catch (IOException e) {
        }
    }
}
