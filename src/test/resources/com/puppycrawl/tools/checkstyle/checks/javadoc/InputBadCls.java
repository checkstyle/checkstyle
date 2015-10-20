package com.puppycrawl.tools.checkstyle.checks.javadoc;

class InputBadCls {
    class X extends Exception {}
    void m() throws X {}
}
