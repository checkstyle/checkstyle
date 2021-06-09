/*
JavadocPackage
allowLegacy = (default)false
fileExtensions = (default)java


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;      // violation

class InputJavadocPackageBadCls {
    class X extends Exception {}
    void m() throws X {}
}
