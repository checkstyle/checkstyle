package com.puppycrawl.tools.checkstyle.checks.javadoc;

class BadCls {
    class X extends Exception {}
    void m() throws X {}
}
