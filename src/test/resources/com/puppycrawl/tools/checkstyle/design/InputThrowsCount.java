package com.puppycrawl.tools.checkstyle.design;

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

class SubClass extends InputThrowsCount {
    @Override
    void method1() {        
    }

    @Deprecated
    @Override
    void method4() throws Exception, java.awt.AWTException {
    }

    @Override
    void method5() throws Exception, AWTException, Throwable {        
    }
    
    @SuppressWarnings("deprecation")
    final void method2(Object ...objects) throws Exception, AWTException{        
    }
    
    @java.lang.Override
    void method3() throws Exception {
    }
}