/*
JavadocPackage
allowLegacy = (default)false
fileExtensions = (default).java


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;
// violation first line 'Missing package-info.java file'

class InputJavadocPackageBadCls {
    class X extends Exception {}
    void m() throws X {}
}
