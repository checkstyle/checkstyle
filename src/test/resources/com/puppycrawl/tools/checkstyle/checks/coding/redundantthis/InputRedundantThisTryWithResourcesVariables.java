package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.Scanner;

/*
* Config = default
*/
public class InputRedundantThisTryWithResourcesVariables {
    private Throwable ex;
    private Scanner scanner;

    public InputRedundantThisTryWithResourcesVariables(Throwable ex) {
        this.ex = ex; // no violation
    }

    public void run() {
        this.ex = null; // violation
        this.scanner = null; // violation

        try (Scanner scanner = new Scanner(System.in)) {
            this.scanner = scanner; // no violation
        } catch (Exception ex) {
            this.ex = ex; // no violation
        }
    }
}
