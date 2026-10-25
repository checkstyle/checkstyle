/*
UnnecessaryNullCheckWithInstanceOf


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarynullcheckwithinstanceof;

public class InputXpathUnnecessaryNullCheckWithInstanceOf {
    public void methodWithUnnecessaryNullCheck1(Object obj) {
        if (obj != null && obj instanceof String) {
        // violation above 'Unnecessary nullity check with instanceof operator'
            String str = (String) obj;
        }
    }
}
