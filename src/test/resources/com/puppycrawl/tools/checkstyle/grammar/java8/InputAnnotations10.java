/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class InputAnnotations10 {
    public static Object methodName(Object str) {
        try {
            return null;

        } catch (@MyAnnotation1(name = "ABC", version = 1) Exception ex) {
            return "";
        }
    }

    @Target(ElementType.TYPE_USE)
    @interface MyAnnotation1 {

    String name();
    int version();
    }
}
