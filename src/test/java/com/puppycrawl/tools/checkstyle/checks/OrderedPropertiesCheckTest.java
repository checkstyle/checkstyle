///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.OrderedPropertiesCheck.MSG_IO_EXCEPTION_KEY;
import static com.puppycrawl.tools.checkstyle.checks.OrderedPropertiesCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Collections;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OrderedPropertiesCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/orderedproperties";
    }

    /**
     * Tests the ordinal work of a check.
     * Test of sub keys, repeating key pairs in wrong order
     */
    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_KEY, "key1", "key2"),
            "11: " + getCheckMessage(MSG_KEY, "B", "key4"),
            "14: " + getCheckMessage(MSG_KEY, "key3", "key5"),
            "17: " + getCheckMessage(MSG_KEY, "key3", "key5"),
        };
        verify(checkConfig, getPath("InputOrderedProperties.properties"), expected);
    }

    @Test
    public void testKeysOnly() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, "key1", "key2"),
        };
        verify(checkConfig, getPath("InputOrderedProperties1OrderKey.properties"), expected);
    }

    @Test
    public void testEmptyKeys() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, "key11", "key2"),
        };
        verify(checkConfig, getPath("InputOrderedProperties2EmptyValue.properties"), expected);
    }

    @Test
    public void testMalformedValue() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final String fileName =
                getPath("InputOrderedProperties3MalformedValue.properties");

        verify(checkConfig, fileName, "1: "
                + getCheckMessage(MSG_IO_EXCEPTION_KEY, fileName, "Malformed \\uxxxx encoding."));
    }

    @Test
    public void testCommentsMultiLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final String[] expected = {
            "5: " + getCheckMessage(MSG_KEY, "aKey", "multi.line"),
        };
        verify(checkConfig, getPath("InputOrderedProperties5CommentsMultiLine.properties"),
                expected);
    }

    @Test
    public void testLineNumberRepeatingPreviousKey() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, "a", "b"),
        };
        verify(checkConfig, getPath("InputOrderedProperties6RepeatingPreviousKey.properties"),
                expected);
    }

    @Test
    public void testShouldNotProcessFilesWithWrongFileExtension() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOrderedProperties.txt"), expected);
    }

    /**
     * Tests IO exception, that can occur during reading of properties file.
     */
    @Test
    public void testIoException() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final OrderedPropertiesCheck check = new OrderedPropertiesCheck();
        check.configure(checkConfig);
        final String fileName =
                getPath("InputOrderedPropertiesCheckNotExisting.properties");
        final File file = new File(fileName);
        final FileText fileText = new FileText(file, Collections.emptyList());
        final SortedSet<Violation> violations =
                check.process(file, fileText);
        assertWithMessage("Wrong violations count")
                .that(violations)
                .hasSize(1);
        final Violation violation = violations.iterator().next();
        final String retrievedMessage = violations.iterator().next().getKey();
        assertWithMessage("violation key is not valid")
                .that(retrievedMessage)
                .isEqualTo("unable.open.cause");
        assertWithMessage("violation is not valid")
                .that(getCheckMessage(MSG_IO_EXCEPTION_KEY, fileName, getFileNotFoundDetail(file)))
                .isEqualTo(violation.getViolation());
    }

    /**
     * This test validates the PIT mutation of getIndex().
     * Here the for statement for
     * (int index = startLineNo; index < fileText.size(); index++)
     * will change to
     * for (int index = startLineNo; true; index++)
     * By creating a FileText having no lines it makes sure that
     * fileText.size() returning zero size.
     * This will keep the for loop intact.
     */
    @Test
    public void testKeepForLoopIntact() throws Exception {

        final DefaultConfiguration checkConfig = createModuleConfig(OrderedPropertiesCheck.class);
        final OrderedPropertiesCheck check = new OrderedPropertiesCheck();
        check.configure(checkConfig);
        final String fileName =
                getPath("InputOrderedProperties2EmptyValue.properties");
        final File file = new File(fileName);
        final FileText fileText = new FileText(file, Collections.emptyList());
        final SortedSet<Violation> violations = check.process(file, fileText);

        assertWithMessage("Wrong violations count")
                .that(violations)
                .hasSize(1);
    }

    @Test
    public void testFileExtension() {

        final OrderedPropertiesCheck check = new OrderedPropertiesCheck();
        assertWithMessage("File extension should be set")
                .that(".properties")
                .isEqualTo(check.getFileExtensions()[0]);
    }

    /**
     * Method generates NoSuchFileException details. It tries to open a file that does not exist.
     *
     * @param file to be opened
     * @return localized detail message of {@link NoSuchFileException}
     */
    private static String getFileNotFoundDetail(File file) {
        // Create exception to know detail message we should wait in LocalisedMessage
        try (InputStream stream = Files.newInputStream(file.toPath())) {
            throw new IllegalStateException("File " + file.getPath() + " should not exist");
        }
        catch (IOException ex) {
            return ex.getLocalizedMessage();
        }
    }
}
