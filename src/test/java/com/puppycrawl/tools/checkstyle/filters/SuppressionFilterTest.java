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

package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressionFilterTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionfilter";
    }

    @Test
    public void testAccept() throws Exception {
        final String fileName = getPath("InputSuppressionFilterNone.xml");
        final boolean optional = false;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "ATest.java", null);

        assertTrue(filter.accept(ev),
                "Audit event should be excepted when there are no suppressions");
    }

    @Test
    public void testAcceptFalse() throws Exception {
        final String fileName = getPath("InputSuppressionFilterSuppress.xml");
        final boolean optional = false;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final LocalizedMessage message = new LocalizedMessage(1, 1, null, "msg", null,
                SeverityLevel.ERROR, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);

        assertFalse(filter.accept(ev),
                "Audit event should be rejected when there is a matching suppression");
    }

    @Test
    public void testAcceptOnNullFile() throws CheckstyleException {
        final String fileName = null;
        final boolean optional = false;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyJava.java", null);
        assertTrue(filter.accept(ev), "Audit event on null file should be excepted, but was not");
    }

    @Test
    public void testNonExistentSuppressionFileWithFalseOptional() {
        final String fileName = "non_existent_suppression_file.xml";
        try {
            final boolean optional = false;
            createSuppressionFilter(fileName, optional);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Unable to find: " + fileName, ex.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testExistingInvalidSuppressionFileWithTrueOptional() throws IOException {
        final String fileName = getPath("InputSuppressionFilterInvalidFile.xml");
        try {
            final boolean optional = true;
            createSuppressionFilter(fileName, optional);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals(
                    "Unable to parse " + fileName + " - invalid files or checks or message format",
                ex.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testExistingSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = getPath("InputSuppressionFilterNone.xml");
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertTrue(filter.accept(ev), "Suppression file with true optional was not accepted");
    }

    @Test
    public void testNonExistentSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = "non_existent_suppression_file.xml";
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertTrue(filter.accept(ev), "Should except event when suppression file does not exist");
    }

    @Test
    public void testNonExistentSuppressionUrlWithTrueOptional() throws Exception {
        final String fileName =
                "https://checkstyle.org/non_existent_suppression.xml";
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertTrue(filter.accept(ev),
                "Should except event when suppression file url does not exist");
    }

    @Test
    public void testLocalFileExternalResourceContentDoesNotChange() throws Exception {
        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionFilter.class);
        filterConfig.addAttribute("file", getPath("InputSuppressionFilterNone.xml"));

        final DefaultConfiguration checkerConfig = createRootConfig(filterConfig);
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final String filePath = File.createTempFile("file", ".java", temporaryFolder).getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, filePath, expected);
        // One more time to use cache.
        verify(checkerConfig, filePath, expected);
    }

    @Test
    public void testRemoteFileExternalResourceContentDoesNotChange() throws Exception {
        final String[] urlCandidates = {
            "https://checkstyle.org/files/suppressions_none.xml",
            "https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/site/resources/"
                + "files/suppressions_none.xml",
        };

        String urlForTest = null;
        for (String url : urlCandidates) {
            if (isConnectionAvailableAndStable(url)) {
                urlForTest = url;
                break;
            }
        }

        // Run the test only if connection is available and url is reachable.
        // We must use an if statement over junit's rule or assume because
        // powermock disrupts the assume exception and turns into a failure
        // instead of a skip when it doesn't pass
        if (urlForTest != null) {
            final DefaultConfiguration firstFilterConfig =
                createModuleConfig(SuppressionFilter.class);
            // -@cs[CheckstyleTestMakeup] need to test dynamic property
            firstFilterConfig.addAttribute("file", urlForTest);

            final DefaultConfiguration firstCheckerConfig = createRootConfig(firstFilterConfig);
            final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
            firstCheckerConfig.addAttribute("cacheFile", cacheFile.getPath());

            final String pathToEmptyFile =
                    File.createTempFile("file", ".java", temporaryFolder).getPath();
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(firstCheckerConfig, pathToEmptyFile, expected);

            // One more time to use cache.
            final DefaultConfiguration secondFilterConfig =
                createModuleConfig(SuppressionFilter.class);
            // -@cs[CheckstyleTestMakeup] need to test dynamic property
            secondFilterConfig.addAttribute("file", urlForTest);

            final DefaultConfiguration secondCheckerConfig = createRootConfig(secondFilterConfig);
            secondCheckerConfig.addAttribute("cacheFile", cacheFile.getPath());

            verify(secondCheckerConfig, pathToEmptyFile, expected);
        }
    }

    private static boolean isConnectionAvailableAndStable(String url) throws Exception {
        boolean available = false;

        if (isUrlReachable(url)) {
            final int attemptLimit = 5;
            int attemptCount = 0;

            while (attemptCount <= attemptLimit) {
                try (InputStream stream = new URL(url).openStream()) {
                    // Attempt to read a byte in order to check whether file content is available
                    available = stream.read() != -1;
                    break;
                }
                catch (IOException ex) {
                    // for some reason Travis CI failed some times (unstable) on reading the file
                    if (attemptCount < attemptLimit && ex.getMessage().contains("Unable to read")) {
                        attemptCount++;
                        available = false;
                        // wait for bad / disconnection time to pass
                        Thread.sleep(1000);
                    }
                    else {
                        throw ex;
                    }
                }
            }
        }
        return available;
    }

    private static boolean isUrlReachable(String url) {
        boolean result = true;
        try {
            final URL verifiableUrl = new URL(url);
            final HttpURLConnection urlConnect = (HttpURLConnection) verifiableUrl.openConnection();
            urlConnect.getContent();
        }
        catch (IOException ignored) {
            result = false;
        }
        return result;
    }

    private static SuppressionFilter createSuppressionFilter(String fileName, boolean optional)
            throws CheckstyleException {
        final SuppressionFilter suppressionFilter = new SuppressionFilter();
        suppressionFilter.setFile(fileName);
        suppressionFilter.setOptional(optional);
        suppressionFilter.finishLocalSetup();
        return suppressionFilter;
    }

}
