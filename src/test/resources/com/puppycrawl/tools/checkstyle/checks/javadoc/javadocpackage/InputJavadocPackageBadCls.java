package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;
// violation above 'Missing package-info.java file'

/* Config:
 * allowLegacy = "false"
 * fileExtensions = "java"
 */
class InputJavadocPackageBadCls {
    class X extends Exception {}
    void m() throws X {}
}
