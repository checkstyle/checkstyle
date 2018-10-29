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
    public void method() throws AWTException, SQLException,
    FileNotFoundException, EOFException, FileAlreadyExistsException {}
}
