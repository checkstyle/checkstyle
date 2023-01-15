/*
ThrowsCount
max = 0
ignorePrivateMethods = false


*/

package com.puppycrawl.tools.checkstyle.checks.design.throwscount;

import java.awt.AWTException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class InputThrowsCount4 {
    void method1() throws Exception // violation
    {
    }

    void method2() throws java.awt.AWTException // violation
    {
    }

    void method3() throws Exception, AWTException, SQLException, // violation
            FileNotFoundException, EOFException
    {
    }

    void method4() throws Exception, java.awt.AWTException, java.sql.SQLException, // violation
            java.io.FileNotFoundException, java.io.EOFException
    {
    }

    void method5() throws Exception, AWTException, Throwable, SQLException, // violation
            FileNotFoundException, EOFException
    {
    }

    void method6() {
    }

    private void method7() throws Exception, AWTException, SQLException, // violation
            FileNotFoundException, EOFException {
    }
}

class SubClass4 extends InputThrowsCount {
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
    final void method2(Object ...objects) throws Exception, // violation
            AWTException, SQLException, FileNotFoundException, EOFException{
    }

    @java.lang.Override
    void method3() throws Exception {
    }
}
