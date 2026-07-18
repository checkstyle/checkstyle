package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations.declarationrightbeforefirstusage;

// violation first line 'Header mismatch*'

/** Some javadoc. */
public class InputDeclarationUsageDistanceAtMostThree4 {

    /** Some javadoc. */
    public int[] getSelectedIndices() {
        int[] sel = new int[5];
        String model = "";
        int a = 0;
        a++;
        for (int index = 0; index < 5; ++index) {
            sel[index] = Integer.parseInt(model.valueOf(a)); // DECLARATION OF VARIABLE 'sel'
            // SHOULD BE HERE (distance = 2)
            // DECLARATION OF VARIABLE 'model'
            // SHOULD BE HERE (distance = 2)
        }
        return sel;
    }

    /** Some javadoc. */
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
            case 0: {
                k++;
                sw = 0; // DECLARATION OF VARIABLE 'sw' SHOULD BE HERE (distance = 2)
                break;
            }
            case 1: {
                b = false;
                break;
            }
            default: {
                b = true;
            }
        }

        int wh = 0;
        b = true;
        do {
            k--;
            i++;
        } while (wh > 0); // DECLARATION OF VARIABLE 'wh' SHOULD BE HERE (distance = 2)

        if (wh > 0) {
            k++;
        } else if (!b) {
            i++;
        } else {
            i--;
        }
    }

    /** Some javadoc. */
    public void testMethod16() {
        int wh = 1;
        int i = 0;
        int k = 7;
        if (i > 0) {
            k++;
        } else if (wh > 0) {
            i++;
        } else {
            i--;
        }
    }

    static class LogLog {

        public static void warn(String string) {}

        public static void setInternalDebugging(String confDebug, boolean b) {}
    }
}
