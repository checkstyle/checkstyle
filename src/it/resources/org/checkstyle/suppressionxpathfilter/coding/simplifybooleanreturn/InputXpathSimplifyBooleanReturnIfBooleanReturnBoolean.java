/*
SimplifyBooleanReturn
tokens = (default)LITERAL_IF


*/

package org.checkstyle.suppressionxpathfilter.coding.simplifybooleanreturn;

import java.util.ArrayList;
import java.util.Arrays;

public class InputXpathSimplifyBooleanReturnIfBooleanReturnBoolean {
    public static void toTest() {
        ArrayList<Boolean> boolList
            = new ArrayList<Boolean>(Arrays.asList(false, true, false, false));
        boolList.stream().filter(statement -> {
            if (!statement) { // violation 'Conditional logic can be removed.'
                return true;
            }
            else {
                return false;
            }
        });
    }
}
