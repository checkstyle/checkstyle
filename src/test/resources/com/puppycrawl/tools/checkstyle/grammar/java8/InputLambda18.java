/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.util.function.Predicate;

public class InputLambda18 {

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? null
                : object -> targetRef.equals(object);
    }
}
