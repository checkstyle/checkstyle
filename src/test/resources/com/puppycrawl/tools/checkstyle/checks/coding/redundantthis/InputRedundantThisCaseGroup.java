package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config = default
*/
public class InputRedundantThisCaseGroup {
    private String aVariable;

    public String method1(int val) {
        switch (val) {
            case 0:
                String aVariable = "";

                if (this.aVariable != null) { // no violation
                    aVariable = this.aVariable; // no violation
                }

                return aVariable;
            default:
                return null;
        }
    }

    public String method2(int val) {
        switch (val) {
            case 0:
                String other = "";

                if (this.aVariable != null) { // violation
                    other = this.aVariable; // violation
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

    public String method4(int val) {
         switch (val) {
            case 0:
                return this.method3(val); // violation
        }
        return null;
    }

    public String method5(int val) {
        switch (val) {
            case 0:
                return method3(val);
        }
        return null;
    }
}
