/*
UnnecessaryNullCheckWithInstanceOf


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarynullcheckwithinstanceof;

interface InputXpathUnnecessaryNullCheckWithInstanceOfInterface {

    default boolean validateString(Object obj) {
        return obj != null && obj instanceof String;
        // violation above 'Unnecessary nullity check with instanceof operator'
    }

    boolean validate(Object obj);
}
