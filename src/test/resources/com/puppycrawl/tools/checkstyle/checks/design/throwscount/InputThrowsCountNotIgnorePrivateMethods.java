/*
ThrowsCount
max = (default)4
ignorePrivateMethods = false


*/

package com.puppycrawl.tools.checkstyle.checks.design.throwscount;

import java.awt.AWTException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class InputThrowsCountNotIgnorePrivateMethods {
    void method1() throws Exception
    {
    }

    void method2() throws AWTException
    {
    }

    void method3() throws Exception, AWTException, SQLException, // violation
            FileNotFoundException, EOFException
    {
    }

    void method4() throws Exception, AWTException, SQLException, // violation
            FileNotFoundException, EOFException
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

class SubClass3 extends InputThrowsCountNotIgnorePrivateMethods {
    @Override
    void method1() {
    }

    @Deprecated
    @Override
    void method4() throws Exception, AWTException {
    }

    @Override
    void method5() throws Exception, AWTException, Throwable {
    }

    @SuppressWarnings("deprecation")
    final void method2(Object ...objects) throws Exception, // violation
            AWTException, SQLException, FileNotFoundException, EOFException{
    }

    @Override
    void method3() throws Exception {
    }
}
