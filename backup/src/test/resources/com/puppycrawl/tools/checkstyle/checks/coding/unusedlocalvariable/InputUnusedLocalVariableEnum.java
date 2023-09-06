/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public enum InputUnusedLocalVariableEnum {

    CONST_1,
    CONST_2(new a() {
    }),
    CONST_3(new a() {
    });

    InputUnusedLocalVariableEnum(Object obj) {
        int a = 12; // violation
        a = (int) obj;
    }

    InputUnusedLocalVariableEnum() {
    }

    public static class nestedClass {
        int d = 12;
    }

    public class innerClass {
        int k = 1;
    }

    public void testTryWithResources(int a) {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("someFile.txt"))) { // ok
            br.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int a = 12;

    public void testIncrementAndDecrementKinds() {
        int a = 0; // violation
        a = ++this.a;
        a++;
        a--;
        int index = 1;
        int[] arr = {1};
        arr[--index] = 3;
        int ind = 0;
        testTryWithResources(--ind);
        int k = 1;
        if (++k > 12) {
        }
        int p = 2;
        if ((++p - --p) > 21) {
        }
        int m = 2;
        int h = ++m;
        h += 1;
    }

    public void testBooleanExpressions() {
        boolean b = false; // ok
        if (!b) {
        }
        boolean b1 = true; // ok
        if (b1) {
        }
        boolean a; // violation
        if ((a = true) != false) {
        }
        boolean j; // violation
        if (j = true) {
        }
        boolean k = true, l = false;
        if (k && !l) {
        }
    }
}

class a {

    void method() {
        int d = 12; // violation
        InputUnusedLocalVariableEnum.nestedClass obj =
                new InputUnusedLocalVariableEnum.nestedClass() {
                    void method() {
                        d += 12;
                    }
                };
        obj.getClass();
    }
}
