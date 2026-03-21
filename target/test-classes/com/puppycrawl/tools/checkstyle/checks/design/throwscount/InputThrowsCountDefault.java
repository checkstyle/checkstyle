/*
ThrowsCount
max = (default)4
ignorePrivateMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.design.throwscount;

import java.awt.AWTException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class InputThrowsCountDefault {
    void method1() throws Exception
    {
    }

    void method2() throws java.awt.AWTException
    {
    }

    // violation below 'Throws count is 5 (max allowed is 4)'
    void method3() throws Exception, AWTException, SQLException,
            FileNotFoundException, EOFException
    {
    }

    // violation below 'Throws count is 5 (max allowed is 4)'
    void method4() throws Exception, java.awt.AWTException, java.sql.SQLException,
            java.io.FileNotFoundException, java.io.EOFException
    {
    }

    // violation below 'Throws count is 6 (max allowed is 4)'
    void method5() throws Exception, AWTException, Throwable, SQLException,
            FileNotFoundException, EOFException
    {
    }

    void method6() {
    }

    private void method7() throws Exception, AWTException, SQLException,
            FileNotFoundException, EOFException {
    }
}

class SubClass extends InputThrowsCountDefault {
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
    final void method2(Object ...objects) throws Exception,
            // violation above 'Throws count is 5 (max allowed is 4)'
            AWTException, SQLException, FileNotFoundException, EOFException{
    }

    @java.lang.Override
    void method3() throws Exception {
    }
}
