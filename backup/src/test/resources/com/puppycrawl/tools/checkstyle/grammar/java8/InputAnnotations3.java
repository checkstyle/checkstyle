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




public class InputAnnotations3 {

    public static void methodName(Object str) {
        String myString = (@NonNull String) str;
    }

    @Target(ElementType.TYPE_USE)
    @interface NonNull {
    }

}
