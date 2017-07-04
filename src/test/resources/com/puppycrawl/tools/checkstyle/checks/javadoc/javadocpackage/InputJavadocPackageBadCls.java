package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocpackage;

class InputJavadocPackageBadCls {
    class X extends Exception {}
    void m() throws X {}
}
