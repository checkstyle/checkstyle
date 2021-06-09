/*
JavadocPackage
allowLegacy = true
fileExtensions = (default)java


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;      // violation

class InputJavadocPackageBadCls2 {
    class X extends Exception {}
    void m() throws X {}
}
