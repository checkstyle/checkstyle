////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SuppressionFilterTest extends AbstractModuleTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionfilter";
    }

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier
                .forClass(SuppressionFilter.class)
                .usingGetClass()
                .withIgnoredFields("file", "optional", "configuration")
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void testAccept() throws Exception {
        final String fileName = getPath("InputSuppressionFilterNone.xml");
        final boolean optional = false;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "ATest.java", null);

        assertTrue("Audit event should be excepted when there are no suppressions",
            filter.accept(ev));
    }

    @Test
    public void testAcceptOnNullFile() throws CheckstyleException {
        final String fileName = null;
        final boolean optional = false;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyJava.java", null);
        assertTrue("Audit event on null file should be excepted, but was not", filter.accept(ev));
    }

    @Test
    public void testNonExistingSuppressionFileWithFalseOptional() {
        final String fileName = "non_existing_suppression_file.xml";
        try {
            final boolean optional = false;
            createSuppressionFilter(fileName, optional);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid error message",
                "Unable to find: " + fileName, ex.getMessage());
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
            assertEquals("Invalid error message",
                "Unable to parse " + fileName + " - invalid files or checks format",
                ex.getMessage());
        }
    }

    @Test
    public void testExistingSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = getPath("InputSuppressionFilterNone.xml");
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertTrue("Suppression file with true optional was not accepted",
            filter.accept(ev));
    }

    @Test
    public void testNonExistingSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = "non_existing_suppression_file.xml";
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertTrue("Should except event when suppression file does not exist",
            filter.accept(ev));
    }

    @Test
    public void testNonExistingSuppressionUrlWithTrueOptional() throws Exception {
        final String fileName =
                "http://checkstyle.sourceforge.net/non_existing_suppression.xml";
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertTrue("Should except event when suppression file url does not exist",
            filter.accept(ev));
    }

    @Test
    public void testLocalFileExternalResourceContentDoesNotChange() throws Exception {
        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionFilter.class);
        filterConfig.addAttribute("file", getPath("InputSuppressionFilterNone.xml"));

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addChild(filterConfig);
        final String cacheFile = temporaryFolder.newFile().getPath();
        checkerConfig.addAttribute("cacheFile", cacheFile);

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addListener(getBriefUtLogger());
        checker.configure(checkerConfig);

        final String filePath = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, filePath, expected);
        // One more time to use cache.
        verify(checker, filePath, expected);
    }

    @Test
    public void testRemoteFileExternalResourceContentDoesNotChange() throws Exception {
        final String[] urlCandidates = {
            "http://checkstyle.sourceforge.net/files/suppressions_none.xml",
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
            firstFilterConfig.addAttribute("file", urlForTest);

            final DefaultConfiguration firstCheckerConfig =
                new DefaultConfiguration("checkstyle_checks");
            firstCheckerConfig.addChild(firstFilterConfig);
            final String cacheFile = temporaryFolder.newFile().getPath();
            firstCheckerConfig.addAttribute("cacheFile", cacheFile);

            final Checker checker = new Checker();
            checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
            checker.configure(firstCheckerConfig);
            checker.addListener(getBriefUtLogger());

            final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checker, pathToEmptyFile, expected);

            // One more time to use cache.
            final DefaultConfiguration secondFilterConfig =
                createModuleConfig(SuppressionFilter.class);
            secondFilterConfig.addAttribute("file", urlForTest);

            final DefaultConfiguration secondCheckerConfig =
                new DefaultConfiguration("checkstyle_checks");
            secondCheckerConfig.addAttribute("cacheFile", cacheFile);
            secondCheckerConfig.addChild(secondFilterConfig);

            checker.configure(secondCheckerConfig);

            verify(checker, pathToEmptyFile, expected);
        }
    }

    private static boolean isConnectionAvailableAndStable(String url) throws Exception {
        boolean available = false;

        if (isUrlReachable(url)) {
            final int attemptLimit = 5;
            int attemptCount = 0;

            while (attemptCount <= attemptLimit) {
                InputStream stream = null;
                try {
                    final URL address = new URL(url);
                    stream = address.openStream();
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
                        Closeables.closeQuietly(stream);
                        throw ex;
                    }
                }
                finally {
                    Closeables.closeQuietly(stream);
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
