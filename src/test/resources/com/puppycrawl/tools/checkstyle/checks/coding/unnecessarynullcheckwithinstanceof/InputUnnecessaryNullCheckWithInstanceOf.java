/*
UnnecessaryNullCheckWithInstanceOf

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

/**
 * Input file demonstrating unnecessary null checks with instanceof.
 */
public class InputUnnecessaryNullCheckWithInstanceOf {
    public void methodWithUnnecessaryNullCheck1(Object obj) {
        if (obj != null && obj instanceof String) { // violation, 'Unnecessary nullity check'
            String str = (String) obj;
        }
    }

    public void methodWithUnnecessaryNullCheck2(Object obj) {
        if (obj != null && obj instanceof CharSequence) { // violation, 'Unnecessary nullity check'
            CharSequence cs = (CharSequence) obj;
        }
    }

    public void methodWithValidNullCheck(Object obj) {
        if (obj != null) {
            CharSequence cs = (CharSequence) obj;
        }
    }

}