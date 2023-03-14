/*
JavadocPackage
allowLegacy = true
fileExtensions = java


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;
// violation 9 lines above 'Missing package-info.java file'

class InputJavadocPackageBadCls2 {
    class X extends Exception {}
    void m() throws X {}
}
