///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
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

        assertWithMessage("Audit event should be excepted when there are no suppressions")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testAcceptFalse() throws Exception {
        final String fileName = getPath("InputSuppressionFilterSuppress.xml");
        final boolean optional = false;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final Violation message = new Violation(1, 1, null, "msg", null,
                SeverityLevel.ERROR, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);

        assertWithMessage("Audit event should be rejected when there is a matching suppression")
                .that(filter.accept(ev))
                .isFalse();
    }

    @Test
    public void testAcceptOnNullFile() throws CheckstyleException {
        final String fileName = null;
        final boolean optional = false;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyJava.java", null);
        assertWithMessage("Audit event on null file should be excepted, but was not")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testNonExistentSuppressionFileWithFalseOptional() {
        final String fileName = "non_existent_suppression_file.xml";
        try {
            final boolean optional = false;
            createSuppressionFilter(fileName, optional);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Unable to find: " + fileName);
        }
    }

    @Test
    public void testExistingInvalidSuppressionFileWithTrueOptional() throws IOException {
        final String fileName = getPath("InputSuppressionFilterInvalidFile.xml");
        try {
            final boolean optional = true;
            createSuppressionFilter(fileName, optional);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Unable to parse " + fileName
                        + " - invalid files or checks or message format");
        }
    }

    @Test
    public void testExistingSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = getPath("InputSuppressionFilterNone.xml");
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertWithMessage("Suppression file with true optional was not accepted")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testNonExistentSuppressionFileWithTrueOptional() throws Exception {
        final String fileName = "non_existent_suppression_file.xml";
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertWithMessage("Should except event when suppression file does not exist")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testNonExistentSuppressionUrlWithTrueOptional() throws Exception {
        final String fileName =
                "https://checkstyle.org/non_existent_suppression.xml";
        final boolean optional = true;
        final SuppressionFilter filter = createSuppressionFilter(fileName, optional);

        final AuditEvent ev = new AuditEvent(this, "AnyFile.java", null);

        assertWithMessage("Should except event when suppression file url does not exist")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testUseCacheLocalFileExternalResourceContentDoesNotChange() throws Exception {
        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionFilter.class);
        filterConfig.addProperty("file", getPath("InputSuppressionFilterNone.xml"));

        final DefaultConfiguration checkerConfig = createRootConfig(filterConfig);
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File cacheFile = new File(temporaryFolder, uniqueFileName);
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final File filePath = new File(temporaryFolder, uniqueFileName);

        execute(checkerConfig, filePath.toString());
        // One more time to use cache.
        execute(checkerConfig, filePath.toString());
    }

    @Test
    public void testUseCacheRemoteFileExternalResourceContentDoesNotChange() throws Exception {
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

        assumeTrue(urlForTest != null, "No Internet connection.");
        final DefaultConfiguration firstFilterConfig = createModuleConfig(SuppressionFilter.class);
        // -@cs[CheckstyleTestMakeup] need to test dynamic property
        firstFilterConfig.addProperty("file", urlForTest);

        final DefaultConfiguration firstCheckerConfig = createRootConfig(firstFilterConfig);
        final String uniqueFileName1 = "junit_" + UUID.randomUUID() + ".java";
        final File cacheFile = new File(temporaryFolder, uniqueFileName1);
        firstCheckerConfig.addProperty("cacheFile", cacheFile.getPath());

        final String uniqueFileName2 = "file_" + UUID.randomUUID() + ".java";
        final File pathToEmptyFile = new File(temporaryFolder, uniqueFileName2);

        execute(firstCheckerConfig, pathToEmptyFile.toString());

        // One more time to use cache.
        final DefaultConfiguration secondFilterConfig =
            createModuleConfig(SuppressionFilter.class);
        // -@cs[CheckstyleTestMakeup] need to test dynamic property
        secondFilterConfig.addProperty("file", urlForTest);

        final DefaultConfiguration secondCheckerConfig = createRootConfig(secondFilterConfig);
        secondCheckerConfig.addProperty("cacheFile", cacheFile.getPath());

        execute(secondCheckerConfig, pathToEmptyFile.toString());
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
                    // for some reason Travis CI failed sometimes (unstable) on reading the file
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

    @Test
    public void testXpathSuppression() throws Exception {
        for (int test = 1; test <= 6; test++) {
            final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";
            final String[] expected = {
                "19:29: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                        "different_name_than_suppression", pattern),
            };
            final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
            final String path = "InputSuppressionFilter" + test + ".java";
            verifyFilterWithInlineConfigParser(getPath(path),
                    expected, suppressed);
        }
    }

    @Test
    public void testSuppression2() throws Exception {
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";
        final String[] expected = {
            "19:29: " + getCheckMessage(ConstantNameCheck.class,
                                        MSG_INVALID_PATTERN, "bad_name", pattern),
        };
        final String[] suppressed = {
            "19:29: " + getCheckMessage(ConstantNameCheck.class,
                                        MSG_INVALID_PATTERN, "bad_name", pattern),

        };
        verifyFilterWithInlineConfigParser(getPath("InputSuppressionFilter7.java"),
                                           expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testSuppression3() throws Exception {
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";
        final String[] expected = {
            "19:29: " + getCheckMessage(ConstantNameCheck.class,
                                        MSG_INVALID_PATTERN, "bad_name", pattern),
        };
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifyFilterWithInlineConfigParser(getPath("InputSuppressionFilter8.java"),
                                           expected, removeSuppressed(expected, suppressed));
    }
}
