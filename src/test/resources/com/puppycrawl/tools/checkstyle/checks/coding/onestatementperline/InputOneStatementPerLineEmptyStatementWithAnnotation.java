/*
OneStatementPerLine

*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineEmptyStatementWithAnnotation {
    String str1 = "123";
    ; // violation 'Only one statement per line allowed.'
    @MyAnnotation
    String str2 = "123";
    ;  // violation 'Only one statement per line allowed.'

    /** Valid javadoc. */
    @Deprecated int field4;
}

public @interface MyAnnotation {
}
