/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceLogic {

    public boolean isCheckBoxEnabled(int path) {
        String model = "";
        if (true) {
            for (int index = 0; index < path; ++index) {
                int nodeIndex = model.codePointAt(path);
                if (model.contains("")) {
                    return false;
                }
            }
        }
        else {
            int nodeIndex = model.codePointAt(path);
            if (model.contains("")) {
                return false;
            }
        }
        return true;
    }

    public Object readObject(String in) throws Exception {
        String startDay = new String("");
        String endDay = new String("");
        return new String(startDay + endDay);
    }

    public int[] getSelectedIndices() {
        int[] sel = new int[5];
        String model = "";
        int a = 0;
        a++;
        for (int index = 0; index < 5; ++index) {
            sel[index] = Integer.parseInt(model.valueOf(a));
        }
        return sel;
    }

    public void testMethod15() {
        String confDebug = "";
        if (!confDebug.equals("") && !confDebug.equals("null")) {
            LogLog.warn("The \"" + "\" attribute is deprecated.");
            LogLog.warn("Use the \"" + "\" attribute instead.");
            LogLog.setInternalDebugging(confDebug, true);
        }

        int i = 0;
        int k = 7;
        boolean b = false;
        for (; i < k; i++) {
            b = true;
            k++;
        }

        int sw;
        switch (i) {
            case 0:
                k++;
                sw = 0;
                break;
            case 1:
                b = false;
                break;
            default:
                b = true;
        }

        int wh = 0;
        b = true;
        do {
            k--;
            i++;
        } while (wh > 0);

        if (wh > 0) {
            k++;
        }
        else if (!b) {
            i++;
        }
        else {
            i--;
        }
    }

    public void testMethod16() {
        int wh = 1, i = 4, k = 0;
        if (i > 0) {
            k++;
        }
        else if (wh > 0) {
            i++;
        }
        else {
            i--;
        }
    }

    static class LogLog {
        public static void warn(String string) {
        }

        public static void setInternalDebugging(String confDebug, boolean b) {
        }
    }
}
