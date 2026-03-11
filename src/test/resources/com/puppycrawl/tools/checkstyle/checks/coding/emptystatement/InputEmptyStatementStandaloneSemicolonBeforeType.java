/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

; // violation 'Empty statement'
public class InputEmptyStatementStandaloneSemicolonBeforeType {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
