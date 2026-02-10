/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

; // violation 'Empty statement'

public class InputEmptyStatementBeforeClass {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
