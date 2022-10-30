package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;
/*
allowLegacy = "true"
fileExtensions = "java"


*/
// violation 7 lines above 'Missing package-info.java file'

class InputJavadocPackageBadCls2 {
    class X extends Exception {}
    void m() throws X {}
}
