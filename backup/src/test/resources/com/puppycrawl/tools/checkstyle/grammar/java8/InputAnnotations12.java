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


public class InputAnnotations12 {
    public void m1(@MyAnnotation1 String @MyAnnotation1 ... vararg) {}
    public void m2(@MyAnnotation1 int @MyAnnotation1 ... vararg) {}
    public void m3(@MyAnnotation1 String @MyAnnotation1 [] @MyAnnotation1 [] ... vararg) {}
    public void m4(@MyAnnotation1 int[] @MyAnnotation1 ... vararg) {}
    public void m5(@MyAnnotation1 String[][] @MyAnnotation1 [] @MyAnnotation1 [] ... vararg) {}

    @Target(ElementType.TYPE_USE)
    @interface MyAnnotation1 {}
}
