package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputFinalLocalVariableAssignedMultipleTimes {

    void foo1() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
        }
        i = 2;
    }

    void foo2() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
        }
        else {

        }
        i = 2;
    }

    void foo3() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
            if (i >= 1) {

            }
            else {

            }
        }
        i = 2;
    }


    void foo4() {
        final boolean some_condition = true;
        int i; // violation
        if (some_condition) {
            if (true) {
            }
            else {
            }
            i = 1;
        }
        else {
            i = 2;
        }
        if (true) {

        }
        else {
        }

    }

    void foo5() {
        final boolean some_condition = true;
        int i;

        {
            i = 2;
        }

        if (some_condition) {
            i = 1;
        }
    }

    void foo6() {
        final boolean some_condition = true;
        int i;

        {
            i = 2;
        }

        if (some_condition) {
            i = 1;
        }
        else {
            i = 6;
        }
    }

    void foo7() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
        }
        else {
            i = 1;
        }
        i = 2;
    }

    void foo8() {
        final boolean some_condition = true;
        final int i;
        if (some_condition) {
            i = 1;
        }
        else {

        }
    }
}
