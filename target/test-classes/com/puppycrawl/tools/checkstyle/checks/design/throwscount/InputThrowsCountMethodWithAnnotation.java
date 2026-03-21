/*
ThrowsCount
max = (default)4
ignorePrivateMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.design.throwscount;

import java.awt.AWTException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;

public class InputThrowsCountMethodWithAnnotation extends ParentClass {
    @Override
    public void method() throws AWTException, SQLException, FileNotFoundException,
            EOFException, FileAlreadyExistsException {
        super.method();
    }
}

class ParentClass {
    // violation below 'Throws count is 5 (max allowed is 4)'
    public void method() throws AWTException, SQLException,
    FileNotFoundException, EOFException, FileAlreadyExistsException {}
}
