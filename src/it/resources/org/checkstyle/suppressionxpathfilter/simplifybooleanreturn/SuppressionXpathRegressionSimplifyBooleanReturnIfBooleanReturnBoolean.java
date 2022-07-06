package org.checkstyle.suppressionxpathfilter.simplifybooleanreturn;

import java.util.ArrayList;
import java.util.Arrays;

public class SuppressionXpathRegressionSimplifyBooleanReturnIfBooleanReturnBoolean {
    public static void toTest() {
        ArrayList<Boolean> boolList
            = new ArrayList<Boolean>(Arrays.asList(false, true, false, false));
        boolList.stream().filter(statement -> {
            if (!statement) { // warn
                return true;
            }
            else {
                return false;
            }
        });
    }
}
