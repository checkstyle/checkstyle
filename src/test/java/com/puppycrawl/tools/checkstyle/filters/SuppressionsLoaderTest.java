///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

/**
 * Tests SuppressionsLoader.
 */
class SuppressionsLoaderTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionsloader";
    }

    @Test
    void noSuppressions() throws Exception {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(getPath("InputSuppressionsLoaderNone.xml"));
        final FilterSet fc2 = new FilterSet();
        assertWithMessage("No suppressions should be loaded, but found: " + fc.getFilters().size())
            .that(fc.getFilters())
            .isEqualTo(fc2.getFilters());
    }

    @Test
    void loadFromUrl() throws Exception {
        final String[] urlCandidates = {
            "https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/site/resources/"
                + "files/suppressions_none.xml",
            "https://checkstyle.org/files/suppressions_none.xml",
        };
        FilterSet actualFilterSet = null;

        for (String url : urlCandidates) {
            actualFilterSet = loadFilterSet(url);

            if (actualFilterSet != null) {
                break;
            }
        }

        assumeTrue(actualFilterSet != null, "No Internet connection.");
        final FilterSet expectedFilterSet = new FilterSet();
        assertWithMessage("Failed to load from url")
            .that(actualFilterSet.getFilters())
            .isEqualTo(expectedFilterSet.getFilters());
    }

    @Test
    void loadFromMalformedUrl() {
        try {
            SuppressionsLoader.loadSuppressions("http");
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid error message")
                .that(exc.getMessage())
                .isEqualTo("Unable to find: http");
        }
    }

    @Test
    void loadFromNonExistentUrl() {
        try {
            SuppressionsLoader.loadSuppressions("http://^%$^* %&% %^&");
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid error message")
                .that(exc.getMessage())
                .isEqualTo("Unable to find: http://^%$^* %&% %^&");
        }
    }

    @Test
    void multipleSuppression() throws Exception {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(getPath("InputSuppressionsLoaderMultiple.xml"));
        final FilterSet fc2 = new FilterSet();

        final SuppressFilterElement se0 =
                new SuppressFilterElement("file0", "check0", null, null, null, null);
        fc2.addFilter(se0);
        final SuppressFilterElement se1 =
                new SuppressFilterElement("file1", "check1", null, null, "1,2-3", null);
        fc2.addFilter(se1);
        final SuppressFilterElement se2 =
                new SuppressFilterElement("file2", "check2", null, null, null, "1,2-3");
        fc2.addFilter(se2);
        final SuppressFilterElement se3 =
                new SuppressFilterElement("file3", "check3", null, null, "1,2-3", "1,2-3");
        fc2.addFilter(se3);
        final SuppressFilterElement se4 =
                new SuppressFilterElement(null, null, "message0", null, null, null);
        fc2.addFilter(se4);
        assertWithMessage("Multiple suppressions were loaded incorrectly")
            .that(fc.getFilters())
            .isEqualTo(fc2.getFilters());
    }

    @Test
    void noFile() throws IOException {
        final String fn = getPath("InputSuppressionsLoaderNoFile.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException exc) {
            final String messageStart = "Unable to parse " + fn;
            assertWithMessage("Exception message should start with: " + messageStart)
                    .that(exc.getMessage())
                    .startsWith("Unable to parse " + fn);
            assertWithMessage("Exception message should contain \"files\"")
                    .that(exc.getMessage())
                    .contains("\"files\"");
            assertWithMessage("Exception message should contain \"suppress\"")
                    .that(exc.getMessage())
                    .contains("\"suppress\"");
        }
    }

    @Test
    void noCheck() throws IOException {
        final String fn = getPath("InputSuppressionsLoaderNoCheck.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException exc) {
            final String messageStart = "Unable to parse " + fn;
            assertWithMessage("Exception message should start with: " + messageStart)
                    .that(exc.getMessage())
                    .startsWith(messageStart);
            assertWithMessage("Exception message should contain \"checks\"")
                    .that(exc.getMessage())
                    .contains("\"checks\"");
            assertWithMessage("Exception message should contain \"suppress\"")
                    .that(exc.getMessage())
                    .contains("\"suppress\"");
        }
    }

    @Test
    void badInt() throws IOException {
        final String fn = getPath("InputSuppressionsLoaderBadInt.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage(exc.getMessage())
                .that(exc.getMessage())
                .startsWith("Number format exception " + fn + " - For input string: \"a\"");
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
                catch (CheckstyleException exc) {
                    // for some reason Travis CI failed sometimes (unstable) on reading this file
                    if (attemptCount < attemptLimit && exc.getMessage()
                            .contains("Unable to read")) {
                        attemptCount++;
                        // wait for bad/disconnection time to pass
                        Thread.sleep(1000);
                    }
                    else {
                        throw exc;
                    }
                }
            }
        }
        return filterSet;
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

    @Test
    void unableToFindSuppressions() {
        final String sourceName = "InputSuppressionsLoaderNone.xml";

        try {
            TestUtil.invokeStaticMethod(SuppressionsLoader.class, "loadSuppressions",
                    new InputSource(sourceName), sourceName);
            assertWithMessage("InvocationTargetException is expected").fail();
        }
        catch (ReflectiveOperationException exc) {
            assertWithMessage("Invalid exception cause message")
                .that(exc)
                    .hasCauseThat()
                        .hasMessageThat()
                        .isEqualTo("Unable to find: " + sourceName);
        }
    }

    @Test
    void unableToReadSuppressions() {
        final String sourceName = "InputSuppressionsLoaderNone.xml";

        try {
            TestUtil.invokeStaticMethod(SuppressionsLoader.class, "loadSuppressions",
                    new InputSource(), sourceName);
            assertWithMessage("InvocationTargetException is expected").fail();
        }
        catch (ReflectiveOperationException exc) {
            assertWithMessage("Invalid exception cause message")
                .that(exc)
                    .hasCauseThat()
                        .hasMessageThat()
                        .isEqualTo("Unable to read " + sourceName);
        }
    }

    @Test
    void noCheckNoId() throws IOException {
        final String fn = getPath("InputSuppressionsLoaderNoCheckAndId.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid error message")
                .that(exc.getMessage())
                .isEqualTo("Unable to parse " + fn
                        + " - missing checks or id or message attribute");
        }
    }

    @Test
    void noCheckYesId() throws Exception {
        final String fn = getPath("InputSuppressionsLoaderId.xml");
        final FilterSet set = SuppressionsLoader.loadSuppressions(fn);

        assertWithMessage("Invalid number of filters")
            .that(set.getFilters())
            .hasSize(1);
    }

    @Test
    void invalidFileFormat() throws IOException {
        final String fn = getPath("InputSuppressionsLoaderInvalidFile.xml");
        try {
            SuppressionsLoader.loadSuppressions(fn);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid error message")
                .that(exc.getMessage())
                .isEqualTo("Unable to parse " + fn
                        + " - invalid files or checks or message format");
        }
    }

    @Test
    void loadFromClasspath() throws Exception {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(getPath("InputSuppressionsLoaderNone.xml"));
        final FilterSet fc2 = new FilterSet();
        assertWithMessage("Suppressions were not loaded")
            .that(fc.getFilters())
            .isEqualTo(fc2.getFilters());
    }

    @Test
    void settingModuleId() throws Exception {
        final FilterSet fc =
                SuppressionsLoader.loadSuppressions(getPath("InputSuppressionsLoaderWithId.xml"));
        final SuppressFilterElement suppressElement = (SuppressFilterElement) fc.getFilters()
                .toArray()[0];

        final String id = TestUtil.getInternalState(suppressElement, "moduleId");
        assertWithMessage("Id has to be defined")
            .that(id)
            .isEqualTo("someId");
    }

    @Test
    void xpathSuppressions() throws Exception {
        final String fn = getPath("InputSuppressionsLoaderXpathCorrect.xml");
        final Set<TreeWalkerFilter> filterSet = SuppressionsLoader.loadXpathSuppressions(fn);

        final Set<TreeWalkerFilter> expectedFilterSet = new HashSet<>();
        final XpathFilterElement xf0 =
                new XpathFilterElement("file1", "test", null, "id1", "//CLASS_DEF");
        expectedFilterSet.add(xf0);
        final XpathFilterElement xf1 =
                new XpathFilterElement(null, null, "message1", null, "//CLASS_DEF");
        expectedFilterSet.add(xf1);
        assertWithMessage("Multiple xpath suppressions were loaded incorrectly")
            .that(filterSet)
            .isEqualTo(expectedFilterSet);
    }

    @Test
    void xpathInvalidFileFormat() throws IOException {
        final String fn = getPath("InputSuppressionsLoaderXpathInvalidFile.xml");
        try {
            SuppressionsLoader.loadXpathSuppressions(fn);
            assertWithMessage("Exception should be thrown").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid error message")
                .that(exc.getMessage())
                .isEqualTo("Unable to parse " + fn
                        + " - invalid files or checks or message format for suppress-xpath");
        }
    }

    @Test
    void xpathNoCheckNoId() throws IOException {
        final String fn =
                getPath("InputSuppressionsLoaderXpathNoCheckAndId.xml");
        try {
            SuppressionsLoader.loadXpathSuppressions(fn);
            assertWithMessage("Exception should be thrown").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid error message")
                .that(exc.getMessage())
                .isEqualTo("Unable to parse " + fn
                        + " - missing checks or id or message attribute for suppress-xpath");
        }
    }

    @Test
    void xpathNoCheckYesId() throws Exception {
        final String fn = getPath("InputSuppressionsLoaderXpathId.xml");
        final Set<TreeWalkerFilter> filterSet = SuppressionsLoader.loadXpathSuppressions(fn);

        assertWithMessage("Invalid number of filters")
            .that(filterSet)
            .hasSize(1);
    }

}
