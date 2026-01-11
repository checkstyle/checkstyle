/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/


package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineAnnotation {

    @Deprecated
    String str = "hello"; ; // violation 'Only one statement per line allowed.'

    @Deprecated
    public void method() {
        int a = 1; } ; // violation 'Only one statement per line allowed.'
}
