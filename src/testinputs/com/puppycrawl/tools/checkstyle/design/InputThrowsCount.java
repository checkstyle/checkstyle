package com.puppycrawl.tools.checkstyle.checks.design;

import java.awt.AWTException;

public class InputThrowsCount {
    void method1() throws Exception
    {
    }

    void methdo2() throws java.awt.AWTException
    {
    }

    void method3() throws Exception, AWTException
    {
    }

    void method4() throws Exception, java.awt.AWTException
    {
    }

    void method5() throws Exception, AWTException, Throwable
    {
    }

    void method6() {
    }
}
