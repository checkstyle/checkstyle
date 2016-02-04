////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterSet;

/**
 * Tests SuppressionsLoader.
 * @author Rick Giles
 * @author <a href="mailto:andreyselkin@gmail.com">Andrei Selkin</a>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SuppressionsLoader.class, SuppressionsLoaderTest.class })
public class SuppressionsLoaderTest extends BaseCheckTestSupport {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Override
    protected String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/filters/" + filename;
    }

    @Test
    public void testNoSuppressions()
        throws CheckstyleException {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(getPath("suppressions_none.xml"));
        final FilterSet fc2 = new FilterSet();
        assertEquals(fc, fc2);
    }

    @Test
    public void testLoadFromUrl() throws Exception {
        final String[] urlCandidates = {
            "http://checkstyle.sourceforge.net/files/suppressions_none.xml",
            "https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/site/resources/"
                + "files/suppressions_none.xml",
        };
        FilterSet actualFilterSet = null;

        for (String url : urlCandidates) {
            actualFilterSet = loadFilterSet(url);

            if (actualFilterSet != null) {
                break;
            }
        }
        // Use Assume.assumeNotNull(actualFilterSet) instead of the if-condition
        // when https://github.com/jayway/powermock/issues/428 will be fixed
        if (actualFilterSet != null) {
            final FilterSet expectedFilterSet = new FilterSet();
            assertEquals(expectedFilterSet, actualFilterSet);
        }
    }

    @Test(expected = CheckstyleException.class)
    public void testLoadFromMalformedUrl() throws CheckstyleException {
        SuppressionsLoader.loadSuppressions("http");
    }

    @Test(expected = CheckstyleException.class)
    public void testLoadFromNonExistingUrl() throws CheckstyleException {
        SuppressionsLoader.loadSuppressions("http://^%$^* %&% %^&");
    }

    @Test
    public void testMultipleSuppression()
        throws CheckstyleException {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(getPath("suppressions_multiple.xml"));
        final FilterSet fc2 = new FilterSet();
        final SuppressElement se0 = new SuppressElement("file0");
        se0.setChecks("check0");
        fc2.addFilter(se0);
        final SuppressElement se1 = new SuppressElement("file1");
        se1.setChecks("check1");
        se1.setLines("1,2-3");
        fc2.addFilter(se1);
        final SuppressElement se2 = new SuppressElement("file2");
        se2.setChecks("check2");
        se2.setColumns("1,2-3");
        fc2.addFilter(se2);
        final SuppressElement se3 = new SuppressElement("file3");
        se3.setChecks("check3");
        se3.setLines("1,2-3");
        se3.setColumns("1,2-3");
        fc2.addFilter(se3);
        assertEquals(fc, fc2);
    }

    @Test
    public void testNoFile() {
        final String fn = getPath("suppressions_no_file.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith("Unable to parse " + fn));
            assertTrue(ex.getMessage().contains("\"files\""));
            assertTrue(ex.getMessage().contains("\"suppress\""));
        }
    }

    @Test
    public void testNoCheck() {
        final String fn = getPath("suppressions_no_check.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith("Unable to parse " + fn));
            assertTrue(ex.getMessage().contains("\"checks\""));
            assertTrue(ex.getMessage().contains("\"suppress\""));
        }
    }

    @Test
    public void testBadInt() {
        final String fn = getPath("suppressions_bad_int.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertTrue(
                ex.getMessage(),
                ex.getMessage().startsWith("Number format exception " + fn + " - "));
        }
    }

    private static FilterSet loadFilterSet(String url) throws Exception {
        FilterSet filterSet = null;

        if (isUrlReachable(url)) {
            int attemptCount = 0;
            final int attemptLimit = 5;

            while (attemptCount <= attemptLimit) {
                try {
                    filterSet = SuppressionsLoader.loadSuppressions(url);
                    break;
                }
                catch (CheckstyleException ex) {
                    // for some reason Travis CI failed some times(unstable) on reading this file
                    if (attemptCount < attemptLimit && ex.getMessage().contains("Unable to read")) {
                        attemptCount++;
                        // wait for bad/disconnection time to pass
                        Thread.sleep(1000);
                    }
                    else {
                        throw ex;
                    }
                }
            }
        }
        return filterSet;
    }

    private static boolean isUrlReachable(String url) {
        try {
            final URL verifiableUrl = new URL(url);
            final HttpURLConnection urlConnect = (HttpURLConnection) verifiableUrl.openConnection();
            urlConnect.getContent();
        }
        catch (IOException ex) {
            return false;
        }
        return true;
    }

    @Test
    public void testUnableToFindSuppressions() throws Exception {
        final Class<SuppressionsLoader> loaderClass = SuppressionsLoader.class;
        final Method loadSuppressions =
            loaderClass.getDeclaredMethod("loadSuppressions", InputSource.class, String.class);
        loadSuppressions.setAccessible(true);

        final String sourceName = "suppressions_none.xml";
        final InputSource inputSource = new InputSource(sourceName);

        thrown.expect(CheckstyleException.class);
        thrown.expectMessage("Unable to find: " + sourceName);

        loadSuppressions.invoke(loaderClass, inputSource, sourceName);
    }

    @Test
    public void testUnableToReadSuppressions() throws Exception {
        final Class<SuppressionsLoader> loaderClass = SuppressionsLoader.class;
        final Method loadSuppressions =
            loaderClass.getDeclaredMethod("loadSuppressions", InputSource.class, String.class);
        loadSuppressions.setAccessible(true);

        final InputSource inputSource = new InputSource();

        thrown.expect(CheckstyleException.class);
        final String sourceName = "suppressions_none.xml";
        thrown.expectMessage("Unable to read " + sourceName);

        loadSuppressions.invoke(loaderClass, inputSource, sourceName);
    }

    @Test
    public void testNoCheckNoId() {
        final String fn = getPath("suppressions_no_check_and_id.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "Unable to parse " + fn + " - missing checks and id attribute",
                ex.getMessage());
        }
    }

    @Test
    public void testNoCheckYesId() throws Exception {
        final String fn = getPath("suppressions_id.xml");
        SuppressionsLoader.loadSuppressions(fn);
    }

    @Test
    public void testInvalidFileFormat() {
        final String fn = getPath("suppressions_invalid_file.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "Unable to parse " + fn + " - invalid files or checks format",
                ex.getMessage());
        }
    }

    @Test
    public void testLoadFromClasspath()
        throws CheckstyleException {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(getPath("suppressions_none.xml"));
        final FilterSet fc2 = new FilterSet();
        assertEquals(fc, fc2);
    }
}
