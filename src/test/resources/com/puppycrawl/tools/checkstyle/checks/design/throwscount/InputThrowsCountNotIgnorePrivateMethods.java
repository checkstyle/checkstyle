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

    // violation below 'Throws count is 5 (max allowed is 4)'
    void method3() throws Exception, AWTException, SQLException,
            FileNotFoundException, EOFException
    {
    }

    // violation below 'Throws count is 5 (max allowed is 4)'
    void method4() throws Exception, AWTException, SQLException,
            FileNotFoundException, EOFException
    {
    }

    // violation below 'Throws count is 6 (max allowed is 4)'
    void method5() throws Exception, AWTException, Throwable, SQLException,
            FileNotFoundException, EOFException
    {
    }

    void method6() {
    }

    // violation below 'Throws count is 5 (max allowed is 4)'
    private void method7() throws Exception, AWTException, SQLException,
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
    final void method2(Object ...objects) throws Exception,
            // violation above 'Throws count is 5 (max allowed is 4)'
            AWTException, SQLException, FileNotFoundException, EOFException{
    }

    @Override
    void method3() throws Exception {
    }
}
