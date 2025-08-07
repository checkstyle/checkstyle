package org.checkstyle.suppressionxpathfilter.coding.unnecessarynullcheckwithinstanceof;

interface InputXpathUnnecessaryNullCheckWithInstanceOfInterface {

    default boolean validateString(Object obj) {
        return obj != null && obj instanceof String; // warn
    }

    boolean validate(Object obj);
}
