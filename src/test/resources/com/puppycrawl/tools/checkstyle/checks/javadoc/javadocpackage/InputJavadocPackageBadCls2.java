package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;      // violation

/* Config:
 * allowLegacy = "true"
 * fileExtensions = "java"
 */
class InputJavadocPackageBadCls2 {
    class X extends Exception {}
    void m() throws X {}
}
