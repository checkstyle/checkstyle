///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.site;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Verifies that {@link SearchIndexGenerator}'s check category display names
 * stay in sync with the labels actually shown in the site's sidebar
 * navigation ({@code src/site/site.xml}).
 *
 * <p>A mismatch here means search results would show a different label for
 * a category than the sidebar navigation does for the same category, which
 * is confusing to users and easy to miss in review - this was flagged as a
 * gap on the {@code SearchIndexGenerator} PR (issue #16214).</p>
 */
public class SearchIndexGeneratorTest {

    /**
     * Path to the Maven site navigation descriptor, relative to the project
     * root. Adjust if the descriptor lives elsewhere in this checkout.
     */
    private static final String SITE_XML_PATH = "src/site/site.xml";

    /**
     * Matches a sidebar {@code <item>}'s {@code href} of the form
     * {@code checks/<category>/index.html}, capturing the lowercase category
     * directory name in group 1. Per-check pages (e.g.
     * {@code checks/blocks/emptyblock.html}) intentionally do not match.
     */
    private static final Pattern CHECKS_CATEGORY_HREF =
            Pattern.compile("^checks/([a-z]+)/index\\.html$");

    /** Name of the field under test on {@link SearchIndexGenerator}. */
    private static final String CATEGORY_FIELD_NAME = "CHECKS_CATEGORY_DISPLAY_NAMES";

    @Test
    public void checksCategoryDisplayNamesMatchSidebarNavigation() throws Exception {
        final Map<String, String> navCategoryNames = readNavCategoryNames();
        final Map<String, String> generatorCategoryNames = readGeneratorCategoryNames();

        assertWithMessage("No 'checks/<category>/index.html' entries found in " + SITE_XML_PATH
                        + " - has the nav structure or href pattern changed?")
                .that(navCategoryNames.isEmpty())
                .isFalse();

        assertWithMessage("SearchIndexGenerator." + CATEGORY_FIELD_NAME + " must match the check"
                        + " category labels shown in the sidebar navigation ("
                        + SITE_XML_PATH + "). Update whichever one is out of date.")
                .that(generatorCategoryNames)
                .isEqualTo(navCategoryNames);
    }

    /**
     * Reads the directory-name-to-display-name map directly from
     * {@link SearchIndexGenerator}'s private {@code CHECKS_CATEGORY_DISPLAY_NAMES}
     * field via reflection, so the test can catch drift without needing to
     * widen that field's visibility just for testing.
     *
     * @return the generator's category display name map
     * @throws ReflectiveOperationException if the field cannot be accessed
     */
    @SuppressWarnings("unchecked")
    private static Map<String, String> readGeneratorCategoryNames()
            throws ReflectiveOperationException {
        final Field field = SearchIndexGenerator.class.getDeclaredField(CATEGORY_FIELD_NAME);
        field.setAccessible(true);
        return (Map<String, String>) field.get(null);
    }

    /**
     * Parses {@code site.xml} and extracts the check category display name
     * for every {@code checks/<category>/index.html} entry found anywhere in
     * the navigation, keyed by lowercase directory name.
     *
     * @return map of directory name to sidebar display label
     * @throws Exception if site.xml cannot be found or parsed
     */
    private static Map<String, String> readNavCategoryNames() throws Exception {
        final Map<String, String> result = new LinkedHashMap<>();
        final File siteXml = new File(SITE_XML_PATH);

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document doc = builder.parse(siteXml);

        final NodeList items = doc.getElementsByTagName("item");
        for (int index = 0; index < items.getLength(); index++) {
            final Element item = (Element) items.item(index);
            final String href = item.getAttribute("href");
            final Matcher matcher = CHECKS_CATEGORY_HREF.matcher(href);
            if (matcher.matches()) {
                final String dirName = matcher.group(1);
                final String displayName = item.getAttribute("name");
                result.put(dirName, displayName);
            }
        }
        return result;
    }

}
