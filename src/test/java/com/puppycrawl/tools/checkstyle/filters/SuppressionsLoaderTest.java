////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.PatternSyntaxException;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterSet;

/**
 * Tests SuppressionsLoader.
 * @author Rick Giles
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SuppressionsLoader.class, SuppressionsLoaderTest.class })
public class SuppressionsLoaderTest {
    @Test
    public void testNoSuppressions()
        throws CheckstyleException {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(
                    "src/test/resources/com/puppycrawl/tools/checkstyle/suppressions_none.xml");
        final FilterSet fc2 = new FilterSet();
        assertEquals(fc, fc2);
    }

    @Test
    public void testLoadFromURL()
        throws CheckstyleException, InterruptedException {
        boolean online = isInternetReachable();

        Assume.assumeTrue(online);

        FilterSet fc = null;

        int attemptCount = 0;
        final int attemptLimit = 5;
        while (attemptCount <= attemptLimit) {
            try {

                fc = SuppressionsLoader
                        .loadSuppressions("http://checkstyle.sourceforge.net/files/suppressions_none.xml");
                break;

            }
            catch (CheckstyleException ex) {
                // for some reason Travis CI failed some times(unstable) on reading this file
                if (attemptCount < attemptLimit
                        && ex.getMessage().contains("unable to read")) {
                    attemptCount++;
                    // wait for bad/disconnection time to pass
                    Thread.sleep(1000);
                }
                else {
                    throw ex;
                }
            }
        }

        final FilterSet fc2 = new FilterSet();
        assertEquals(fc, fc2);
    }

    @Test(expected = CheckstyleException.class)
    public void testLoadFromMalformedURL() throws CheckstyleException {
        SuppressionsLoader.loadSuppressions("http");
    }

    @Test(expected = CheckstyleException.class)
    public void testLoadFromNonExistingURL() throws CheckstyleException {
        SuppressionsLoader.loadSuppressions("http://^%$^* %&% %^&");
    }

    @Test
    public void testMultipleSuppression()
        throws CheckstyleException, PatternSyntaxException {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(
                    "src/test/resources/com/puppycrawl/tools/checkstyle/suppressions_multiple.xml");
        final FilterSet fc2 = new FilterSet();
        SuppressElement se0 = new SuppressElement("file0");
        se0.setChecks("check0");
        fc2.addFilter(se0);
        SuppressElement se1 = new SuppressElement("file1");
        se1.setChecks("check1");
        se1.setLines("1,2-3");
        fc2.addFilter(se1);
        SuppressElement se2 = new SuppressElement("file2");
        se2.setChecks("check2");
        se2.setColumns("1,2-3");
        fc2.addFilter(se2);
        SuppressElement se3 = new SuppressElement("file3");
        se3.setChecks("check3");
        se3.setLines("1,2-3");
        se3.setColumns("1,2-3");
        fc2.addFilter(se3);
        assertEquals(fc, fc2);
    }

    @Test
    public void testNoFile() {
        final String fn = "src/test/resources/com/puppycrawl/tools/checkstyle/suppressions_no_file.xml";
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "unable to parse " + fn + " - Attribute \"files\" is required and must be specified for element type \"suppress\".",
                ex.getMessage());
        }
    }

    @Test
    public void testNoCheck() {
        final String fn = "src/test/resources/com/puppycrawl/tools/checkstyle/suppressions_no_check.xml";
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "unable to parse " + fn + " - Attribute \"checks\" is required and must be specified for element type \"suppress\".",
                ex.getMessage());
        }
    }

    @Test
    public void testBadInt() {
        final String fn = "src/test/resources/com/puppycrawl/tools/checkstyle/suppressions_bad_int.xml";
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertTrue(
                ex.getMessage(),
                ex.getMessage().startsWith("number format exception " + fn + " - "));
        }
    }

    private static boolean isInternetReachable() {
        try {
            URL url = new URL("http://checkstyle.sourceforge.net/");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            @SuppressWarnings("unused")
            Object objData = urlConnect.getContent();
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUnableToFindSuppressions() throws Exception {
        mockStatic(SuppressionsLoader.class);

        String fileName = "suppressions_none.xml";
        InputSource source = mock(InputSource.class);

        when(source.getByteStream()).thenThrow(FileNotFoundException.class);
        when(SuppressionsLoader.class, "loadSuppressions", source, fileName).thenCallRealMethod();

        try {
            Whitebox.invokeMethod(SuppressionsLoader.class, "loadSuppressions", source, fileName);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getCause() instanceof  FileNotFoundException);
            assertEquals("unable to find " + fileName, ex.getMessage());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUnableToReadSuppressions() throws Exception {
        mockStatic(SuppressionsLoader.class);

        String fileName = "suppressions_none.xml";
        InputSource source = mock(InputSource.class);

        when(source.getByteStream()).thenThrow(IOException.class);
        when(SuppressionsLoader.class, "loadSuppressions", source, fileName).thenCallRealMethod();

        try {
            Whitebox.invokeMethod(SuppressionsLoader.class, "loadSuppressions", source, fileName);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getCause() instanceof  IOException);
            assertEquals("unable to read " + fileName, ex.getMessage());
        }
    }

    @Test
    public void testNoCheckNoId() {
        final String fn = "src/test/resources/com/puppycrawl/tools/checkstyle/suppressions_no_check_and_id.xml";
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "unable to parse " + fn + " - missing checks and id attribute",
                ex.getMessage());
        }
    }

    @Test
    public void testNoCheckYesId() throws Exception {
        final String fn = "src/test/resources/com/puppycrawl/tools/checkstyle/suppressions_id.xml";
        SuppressionsLoader.loadSuppressions(fn);
    }

    @Test
    public void testInvalidFileFormat() {
        final String fn = "src/test/resources/com/puppycrawl/tools/checkstyle/suppressions_invalid_file.xml";
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "unable to parse " + fn + " - invalid files or checks format",
                ex.getMessage());
        }
    }

    @Test
    public void testLoadFromClasspath()
        throws CheckstyleException {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(
                    "/com/puppycrawl/tools/checkstyle/suppressions_none.xml");
        final FilterSet fc2 = new FilterSet();
        assertEquals(fc, fc2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testloadSuppressionsURISyntaxException() throws Exception {
        mockStatic(SuppressionsLoader.class);

        URL configUrl = mock(URL.class);
        String fileName = "suppressions_none.xml";

        when(SuppressionsLoader.class.getResource(fileName)).thenReturn(configUrl);
        when(configUrl.toURI()).thenThrow(URISyntaxException.class);
        when(SuppressionsLoader.loadSuppressions(fileName))
                .thenCallRealMethod();

        try {
            SuppressionsLoader.loadSuppressions(fileName);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getCause() instanceof  URISyntaxException);
            assertEquals("unable to find " + fileName, ex.getMessage());
        }
    }
}
