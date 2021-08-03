/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisCaseGroup { // ok
    private String aVariable;

    public String method1(int val) {
        switch (val) {
            case 0:
                String aVariable = "";

                if (this.aVariable != null) {
                    aVariable = this.aVariable;
                }

                return aVariable;
            default:
                return null;
        }
    }

    public String method2(int val) {
        switch (val) {
            case 0:
                String aVariable = "";

                if (this.aVariable != null) {
                    aVariable = this.aVariable;
                }

                return aVariable;
        }
        return null;
    }

    public String method3(int val) {
        switch (val) {
            case 0:
                String other = "";

                if (aVariable != null) {
                    other = aVariable;
                }

                return other;
        }
        return null;
    }
}
