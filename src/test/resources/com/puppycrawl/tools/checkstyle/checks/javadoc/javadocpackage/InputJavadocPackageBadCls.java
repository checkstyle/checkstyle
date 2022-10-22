// violation below 'Missing package-info.java file'
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;

/* Config:
 * allowLegacy = "false"
 * fileExtensions = "java"
 */
class InputJavadocPackageBadCls {
    class X extends Exception {}
    void m() throws X {}
}
