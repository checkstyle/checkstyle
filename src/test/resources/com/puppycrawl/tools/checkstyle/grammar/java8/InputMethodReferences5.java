/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface InputMethodReferences5 { // ok
    JoinFormula formula() default @JoinFormula(value = "", referencedColumnName = "");

    JoinColumn column() default @JoinColumn();
}

@interface JoinFormula {
    String value();

    String referencedColumnName();
}

@interface JoinColumn {

}
