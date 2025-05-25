package org.checkstyle.suppressionxpathfilter.unnecessarynullcheckwithinstanceof;

interface InputXpathUnnecessaryNullCheckWithInstanceOfInterface {

    default boolean validateString(Object obj) {
        return obj != null && obj instanceof String; // warn
    }

    boolean validate(Object obj);
}
