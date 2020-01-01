////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal.powermock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ParseMode.class)
public class MainFrameModelPowerTest extends AbstractModuleTestSupport {

    private static final String FILE_NAME_TEST_DATA = "InputMainFrameModel.java";
    private static final String FILE_NAME_NON_JAVA = "NotJavaFile.notjava";
    private static final String FILE_NAME_NON_EXISTENT = "non-existent.file";

    private MainFrameModel model;
    private File testData;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/mainframemodel";
    }

    @Before
    public void prepareTestData() throws IOException {
        model = new MainFrameModel();
        testData = new File(getPath(FILE_NAME_TEST_DATA));
    }

    @Test
    public void testShouldAcceptFile() throws IOException {
        final File directory = PowerMockito.mock(File.class);
        PowerMockito.when(directory.isDirectory()).thenReturn(true);
        assertTrue("MainFrame should accept directory",
                MainFrameModel.shouldAcceptFile(directory));

        final File javaFile = new File(getPath(FILE_NAME_TEST_DATA));
        assertTrue("MainFrame should accept java file",
                MainFrameModel.shouldAcceptFile(javaFile));

        final File nonJavaFile = PowerMockito.mock(File.class);
        PowerMockito.when(nonJavaFile.isDirectory()).thenReturn(false);
        PowerMockito.when(nonJavaFile.getName()).thenReturn(FILE_NAME_NON_JAVA);
        assertFalse("MainFrame should not accept nonJava file",
                MainFrameModel.shouldAcceptFile(nonJavaFile));

        final File nonExistentFile = new File(getPath(FILE_NAME_NON_EXISTENT));
        assertFalse("MainFrame should not accept nonexistent file",
                MainFrameModel.shouldAcceptFile(nonExistentFile));
    }

    @Test
    public void testOpenFileWithUnknownParseMode() throws CheckstyleException {
        final ParseMode unknownParseMode = PowerMockito.mock(ParseMode.class);
        Whitebox.setInternalState(unknownParseMode, "ordinal", 3);

        PowerMockito.when(unknownParseMode.toString()).thenReturn("Unknown parse mode");
        PowerMockito.mockStatic(ParseMode.class);
        PowerMockito.when(ParseMode.values()).thenReturn(
                new ParseMode[] {
                    ParseMode.PLAIN_JAVA, ParseMode.JAVA_WITH_COMMENTS,
                    ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS, unknownParseMode, });

        try {
            model.setParseMode(unknownParseMode);
            model.openFile(testData);

            fail("Expected IllegalArgumentException is not thrown.");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "Unknown mode: Unknown parse mode", ex.getMessage());
        }
    }

}
