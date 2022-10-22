// violation below 'Missing package-info.java file'
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;

/* Config:
 * allowLegacy = "true"
 * fileExtensions = "java"
 */
class InputJavadocPackageBadCls2 {
    class X extends Exception {}
    void m() throws X {}
}
