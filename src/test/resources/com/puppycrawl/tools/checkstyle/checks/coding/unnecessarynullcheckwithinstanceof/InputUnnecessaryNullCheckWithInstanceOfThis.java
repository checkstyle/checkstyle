/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

public class InputUnnecessaryNullCheckWithInstanceOfThis {

    private String str;
    private Object other;

    public void testThisField() {
        // violation below 'Unnecessary nullity check with instanceof operator'
        if (this.str != null && this.str instanceof String) {
            System.out.println("ok");
        }

        // violation below 'Unnecessary nullity check with instanceof operator'
        if (null != this.str && this.str instanceof String) {
            System.out.println("ok");
        }

        // violation below 'Unnecessary nullity check with instanceof operator'
        if (this.str instanceof String && this.str != null) {
            System.out.println("ok");
        }

        // violation below 'Unnecessary nullity check with instanceof operator'
        if (this.str instanceof String && null != this.str) {
            System.out.println("ok");
        }

        // violation below 'Unnecessary nullity check with instanceof operator'
        if (this.str != null && this.str instanceof String s) {
            System.out.println(s);
        }

        if (this.str instanceof String) {
            System.out.println("ok");
        }
    }

    public void testThisFieldDereferenced() {
        if (this.str != null && this.str.isEmpty() && this.str instanceof String) {
            System.out.println("dereferenced");
        }
    }

    public void testParameterShadowing(String str) {
        if (str != null && this.str instanceof String) {
            System.out.println("different variables");
        }
        if (this.str != null && str instanceof String) {
            System.out.println("different variables");
        }
    }

    public void testDifferentFields() {
        if (this.str != null && this.other instanceof String) {
            System.out.println("different fields");
        }
    }

}
