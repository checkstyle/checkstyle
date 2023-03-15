/*
ThrowsCount
max = 5
ignorePrivateMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.design.throwscount;

import java.awt.AWTException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class InputThrowsCount2 {
    void method1() throws Exception
    {
    }

    void method2() throws AWTException
    {
    }

    void method3() throws Exception, AWTException, SQLException,
            FileNotFoundException, EOFException
    {
    }

    void method4() throws Exception, AWTException, SQLException,
            FileNotFoundException, EOFException
    {
    }

    void method5() throws Exception, AWTException, Throwable, SQLException, // violation
            FileNotFoundException, EOFException
    {
    }

    void method6() {
    }

    private void method7() throws Exception, AWTException, SQLException,
            FileNotFoundException, EOFException {
    }
}

class SubClass2 extends InputThrowsCount2 {
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
    final void method2(Object ...objects) throws Exception, AWTException, SQLException,
            FileNotFoundException, EOFException{
    }

    @Override
    void method3() throws Exception {
    }
}
