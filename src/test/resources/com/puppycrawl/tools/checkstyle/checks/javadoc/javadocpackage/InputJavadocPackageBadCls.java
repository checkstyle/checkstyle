package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;
/*
allowLegacy = "false"
fileExtensions = "java"


*/
// violation 7 lines above 'Missing package-info.java file'

class InputJavadocPackageBadCls {
    class X extends Exception {}
    void m() throws X {}
}
