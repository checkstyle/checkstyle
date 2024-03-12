/*
OverloadMethodsDeclarationOrder


*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

public class InputOverloadMethodsDeclarationOrderPrivateAndStaticMethods {
    public void testing() {
    }

    private void testing(int a) {
    }

    public void testing(int a, int b) {
    }

    public static void testing(String a) {
    }

    public void testing(String a, String b) {
    }
}
