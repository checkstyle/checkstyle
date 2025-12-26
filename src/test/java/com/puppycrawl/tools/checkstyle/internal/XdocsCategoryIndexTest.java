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

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;

/**
 * JUnit test suite for validating the integrity and consistency of Checkstyle's XDoc
 * category {@code index.xml} files.
 * This test verifies that:
 * <ul>
 *   <li>All checks are accurately listed in their respective category index files.</li>
 *   <li>Hyperlinks correctly point to the corresponding XDoc page and section for each check.</li>
 *   <li>Index descriptions accurately reflect the main XDoc descriptions.</li>
 * </ul>
 *
 * <p>
 * Prerequisites for execution:
 * <ul>
 *   <li>{@code mvn clean compile}</li>
 *   <li>{@code mvn plexus-component-metadata:generate-metadata}
 *       (for custom macro/parser discovery)</li>
 * </ul>
 */
public class XdocsCategoryIndexTest extends AbstractModuleTestSupport {

    private static final Path XDOC_CHECKS_DIR = Path.of("src", "site", "xdoc", "checks");

    @Override
    public String getPackageLocation() {
        return "com.puppycrawl.tools.checkstyle.internal";
    }

    @Test
    void allChecksListedInCategoryIndexAndDescriptionMatches() throws Exception {
        final List<Path> checkXdocFiles = getCheckXdocFiles();

        for (final Path checkXdocFile : checkXdocFiles) {
            final String mainSectionName = getMainSectionName(checkXdocFile);
            final Path categoryDir = checkXdocFile.getParent();
            final Path categoryIndexFile = categoryDir.resolve("index.xml");

            assertWithMessage("Category index file should exist for check: %s", checkXdocFile)
                    .that(Files.exists(categoryIndexFile)).isTrue();

            final Map<String, CheckIndexInfo> indexedChecks = parseCategoryIndex(categoryIndexFile);
            final Set<String> foundKeys = indexedChecks.keySet();

            final String checkNotFoundMsg = String.format(Locale.ROOT,
                    "Check '%s' from %s not in %s. Found Checks: %s",
                    mainSectionName, checkXdocFile.getFileName(), categoryIndexFile, foundKeys);
            assertWithMessage(checkNotFoundMsg)
                    .that(indexedChecks.containsKey(mainSectionName)).isTrue();

            final CheckIndexInfo checkInfoFromIndex = indexedChecks.get(mainSectionName);
            final String internalErrorMsg = String.format(Locale.ROOT,
                "CheckInfo for '%s' null (key present). Test error.", mainSectionName);
            assertWithMessage(internalErrorMsg)
                    .that(checkInfoFromIndex)
                    .isNotNull();

            // Validate Href
            final String expectedHrefFileName = checkXdocFile.getFileName().toString()
                    .replace(".xml", ".html");
            final String expectedHref = expectedHrefFileName.toLowerCase(Locale.ROOT)
                    + "#" + mainSectionName;
            final String actualHref = checkInfoFromIndex.href();

            final String hrefMismatchMsg = String.format(Locale.ROOT,
                    "Href mismatch for '%s' in %s." + "Expected: '%s', Found: '%s'",
                    mainSectionName, categoryIndexFile, expectedHref, actualHref);
            assertWithMessage(hrefMismatchMsg)
                    .that(actualHref).isEqualTo(expectedHref);

            // Validate Description
            final String descriptionFromXdoc = getCheckDescriptionFromXdoc(checkXdocFile);
            final String descriptionFromIndex = checkInfoFromIndex.description();
            final String normalizedIndexDesc = normalizeText(descriptionFromIndex);
            final String normalizedXdocDesc = normalizeText(descriptionFromXdoc);

            final String descMismatchMsg = String.format(Locale.ROOT,
                    "Check '%s' in index '%s': "
                            + "index description is not a prefix of XDoc description.",
                    mainSectionName, categoryIndexFile);
            assertWithMessage(descMismatchMsg)
                    .that(normalizedXdocDesc)
                    .startsWith(normalizedIndexDesc);
        }
    }

    /**
     * Scans the XDOC_CHECKS_DIR for all individual check XDoc files.
     * It filters out common files like 'index.xml' and 'property_types.xml'.
     *
     * @return A list of paths to check XDoc files.
     * @throws IOException if an I/O error occurs when walking the path.
     */
    private static List<Path> getCheckXdocFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(XDOC_CHECKS_DIR)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".xml"))
                    .filter(path -> !"index.xml".equals(path.getFileName().toString()))
                    .filter(path -> !"property_types.xml".equals(path.getFileName().toString()))
                    .toList();
        }
    }

    /**
     * Extracts the main section name from a check's XDoc file.
     * This is typically the value of the 'name' attribute of the first &lt;section&gt; tag.
     *
     * @param checkXdocFile Path to the check's XDoc file.
     * @return The main section name.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws IOException if an I/O error occurs reading the file.
     * @throws AssertionError if no &lt;section name=...&gt; is found.
     */
    private static String getMainSectionName(Path checkXdocFile)
            throws ParserConfigurationException, IOException {
        final String content = Files.readString(checkXdocFile);
        final Document document = XmlUtil.getRawXml(checkXdocFile.toString(), content, content);
        final NodeList sections = document.getElementsByTagName("section");

        for (int sectionIndex = 0; sectionIndex < sections.getLength(); sectionIndex++) {
            final Node sectionNode = sections.item(sectionIndex);
            if (sectionNode instanceof Element sectionElement
                  && sectionElement.hasAttribute("name")) {
                return sectionElement.getAttribute("name");
            }
        }

        final String errorMsg = String.format(Locale.ROOT,
                "No <section name=...> found in %s", checkXdocFile);
        throw new AssertionError(errorMsg);
    }

    /**
     * Extracts the description of a check from its XDoc file.
     * It looks for a &lt;subsection name="Description"&gt; and then tries to find the content
     * within a &lt;div&gt; or &lt;p&gt; tag.
     * If not found, it aggregates direct text nodes of the subsection.
     * As a last resort, it uses the full text content of the subsection.
     *
     * @param checkXdocFile Path to the check's XDoc file.
     * @return The check's description text.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws IOException if an I/O error occurs reading the file.
     * @throws AssertionError if no suitable description subsection is found.
     */
    private static String getCheckDescriptionFromXdoc(Path checkXdocFile)
            throws ParserConfigurationException, IOException {
        final String content = Files.readString(checkXdocFile);
        final Document document = XmlUtil.getRawXml(checkXdocFile.toString(), content, content);
        final NodeList subsections = document.getElementsByTagName("subsection");

        for (int subsectionIdx = 0; subsectionIdx < subsections.getLength(); subsectionIdx++) {
            final Node subsectionNode = subsections.item(subsectionIdx);
            if (subsectionNode instanceof Element subsectionElement
                && "Description".equals(subsectionElement.getAttribute("name"))) {
                final Optional<String> description =
                            getDescriptionFromSubsection(subsectionElement);
                if (description.isPresent()) {
                    return description.get();
                }
            }
        }
        final String errorMsg = String.format(Locale.ROOT,
                "No <subsection name=\"Description\"> with suitable content in %s",
                checkXdocFile);
        throw new AssertionError(errorMsg);
    }

    /**
     * Extracts the description text from a given "Description" subsection element.
     * It tries multiple strategies in order of preference:
     * <ol>
     *   <li>Text content of the first direct child {@code <div> }.</li>
     *   <li>Text content of the first direct child {@code <p> }.</li>
     *   <li>Aggregated direct text nodes of the subsection.</li>
     *   <li>Full text content of the subsection.</li>
     * </ol>
     *
     * @param subsectionElement The "Description" {@code <subsection> } DOM element.
     * @return An {@link Optional} with the extracted description if found and non-blank,
     *         otherwise {@link Optional#empty()}.
     */
    private static Optional<String> getDescriptionFromSubsection(Element subsectionElement) {
        Optional<String> description = Optional.empty();
        final Optional<String> textFromDiv = findTextInChildElements(subsectionElement, "div");
        if (textFromDiv.isPresent()) {
            description = textFromDiv;
        }

        if (description.isEmpty()) {
            final Optional<String> textFromP = findTextInChildElements(subsectionElement, "p");
            if (textFromP.isPresent()) {
                description = textFromP;
            }
        }

        if (description.isEmpty()) {
            final Optional<String> aggregatedText = getAggregatedDirectText(subsectionElement);
            if (aggregatedText.isPresent()) {
                description = aggregatedText;
            }
        }

        if (description.isEmpty()) {
            final String fullSubsectionText = subsectionElement.getTextContent();
            if (fullSubsectionText != null && !fullSubsectionText.isBlank()) {
                description = Optional.of(fullSubsectionText);
            }
        }
        return description;
    }

    /**
     * Finds the text content of the first non-blank direct child element with the given tag name.
     *
     * @param parent The parent DOM element.
     * @param tagName The tag name to search for.
     * @return An Optional containing the text if found, otherwise Optional.empty().
     */
    private static Optional<String> findTextInChildElements(Element parent, String tagName) {
        Optional<String> foundText = Optional.empty();
        for (final Element childElement : getChildrenElementsByTagName(parent, tagName)) {
            final String text = childElement.getTextContent();
            if (text != null && !text.isBlank()) {
                foundText = Optional.of(text);
                break;
            }
        }
        return foundText;
    }

    /**
     * Aggregates text from all direct TEXT_NODE children of a parent element.
     *
     * @param parent The parent DOM element.
     * @return An Optional containing the aggregated non-blank text, otherwise Optional.empty().
     */
    private static Optional<String> getAggregatedDirectText(Element parent) {
        final StringBuilder directTextContent = new StringBuilder(32);
        final NodeList directChildren = parent.getChildNodes();
        for (int childIdx = 0; childIdx < directChildren.getLength(); childIdx++) {
            final Node directChild = directChildren.item(childIdx);
            if (directChild.getNodeType() == Node.TEXT_NODE) {
                directTextContent.append(directChild.getNodeValue());
            }
        }
        final String aggregatedText = directTextContent.toString();
        Optional<String> result = Optional.empty();
        if (!aggregatedText.isBlank()) {
            result = Optional.of(aggregatedText);
        }
        return result;
    }

    /**
     * Parses a category index.xml file to extract information about the checks listed.
     * It iterates through all tables and their rows to find check names, hrefs, and descriptions.
     *
     * @param categoryIndexFile Path to the category's index.xml file.
     * @return A map with check names (from &lt;a&gt; tag text) as keys
     *         and {@link CheckIndexInfo} objects as values.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws IOException if an I/O error occurs reading the file.
     * @throws AssertionError if no &lt;table&gt; is found in the index file.
     */
    private static Map<String, CheckIndexInfo> parseCategoryIndex(Path categoryIndexFile)
            throws ParserConfigurationException, IOException {
        final Map<String, CheckIndexInfo> indexedChecks = new HashMap<>();
        final String content = Files.readString(categoryIndexFile);
        final Document document = XmlUtil.getRawXml(categoryIndexFile.toString(), content, content);
        final NodeList tableNodes = document.getElementsByTagName("table");

        if (tableNodes.getLength() == 0) {
            final String errorMsg = String.format(Locale.ROOT,
                "No <table> found in %s", categoryIndexFile);
            throw new AssertionError(errorMsg);
        }

        for (int tableIdx = 0; tableIdx < tableNodes.getLength(); tableIdx++) {
            final Node tableNode = tableNodes.item(tableIdx);
            if (tableNode instanceof Element element) {
                processTableElement(element, indexedChecks);
            }
        }
        return indexedChecks;
    }

    /**
     * Processes a single &lt;table&gt; element from a category index file.
     * Iterates over its rows, skipping a potential header row, and processes data rows.
     *
     * @param tableElement The &lt;table&gt; DOM element.
     * @param indexedChecks The map to populate with check information.
     */
    private static void processTableElement(Element tableElement,
                                            Map<String, CheckIndexInfo> indexedChecks) {
        final List<Element> rowElements = getChildrenElementsByTagName(tableElement, "tr");
        boolean isFirstRowInTable = true;

        for (final Element rowElement : rowElements) {
            if (isFirstRowInTable) {
                isFirstRowInTable = false;
                if (isHeaderRow(rowElement)) {
                    continue;
                }
            }
            processDataRow(rowElement, indexedChecks);
        }
    }

    /**
     * Checks if a given table row element is a header row (i.e., contains &lt;th&gt; elements).
     *
     * @param rowElement The &lt;tr&gt; DOM element.
     * @return True if it's a header row, false otherwise.
     */
    private static boolean isHeaderRow(Element rowElement) {
        return !getChildrenElementsByTagName(rowElement, "th").isEmpty();
    }

    /**
     * Processes a data row (&lt;tr&gt; with &lt;td&gt; children) from a category index table.
     * Extracts the check name, href, and description.
     *
     * @param rowElement The &lt;tr&gt; DOM element representing a data row.
     * @param indexedChecks The map to populate with check information.
     */
    private static void processDataRow(Element rowElement,
                                       Map<String, CheckIndexInfo> indexedChecks) {
        final List<Element> cellElements = getChildrenElementsByTagName(rowElement, "td");
        if (cellElements.size() >= 2) {
            final Element nameCell = cellElements.get(0);
            final Element descCell = cellElements.get(1);

            getFirstChildElementByTagName(nameCell, "a").ifPresent(anchorElement -> {
                if (anchorElement.hasAttribute("href")) {
                    final String checkNameInIndex = anchorElement.getTextContent().trim();
                    final String href = anchorElement.getAttribute("href");
                    final String description = descCell.getTextContent();
                    indexedChecks.put(checkNameInIndex,
                            new CheckIndexInfo(href, description));
                }
            });
        }
    }

    /**
     * Retrieves all child elements of a given parent node that match the specified tag name.
     *
     * @param parent The parent DOM node.
     * @param tagName The tag name to filter child elements by.
     * @return A list of matching child elements. Empty if parent is null or no matches.
     */
    private static List<Element> getChildrenElementsByTagName(Node parent, String tagName) {
        final List<Element> elements = new ArrayList<>();
        if (parent != null) {
            final NodeList children = parent.getChildNodes();
            for (int childIdx = 0; childIdx < children.getLength(); childIdx++) {
                final Node child = children.item(childIdx);
                if (child instanceof Element element && tagName.equals(child.getNodeName())) {
                    elements.add(element);
                }
            }
        }
        return elements;
    }

    /**
     * Retrieves the first child element of a given parent node that matches the specified tag name.
     *
     * @param parent The parent DOM node.
     * @param tagName The tag name to filter child elements by.
     * @return An {@link Optional} with the first matching child element,
     *         or empty {@link Optional} if none found or parent is null.
     */
    private static Optional<Element> getFirstChildElementByTagName(Node parent, String tagName) {
        Optional<Element> result = Optional.empty();
        if (parent != null) {
            final NodeList children = parent.getChildNodes();
            for (int childIdx = 0; childIdx < children.getLength(); childIdx++) {
                final Node child = children.item(childIdx);
                if (child instanceof Element element && tagName.equals(child.getNodeName())) {
                    result = Optional.of(element);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Normalizes a string by trimming whitespace, replacing non-breaking spaces,
     * and collapsing multiple internal whitespace characters into a single space.
     *
     * @param text The text to normalize.
     * @return The normalized text, or an empty string if the input is null.
     */
    private static String normalizeText(String text) {
        String normalized = "";
        if (text != null) {
            normalized = text.replace("\u00a0", " ").trim().replaceAll("\\s+", " ");
        }
        return normalized;
    }

    /**
     * Stores information about a check as parsed from an index.xml file.
     * It holds the hyperlink reference (href) and the description text.
     */
    private static final class CheckIndexInfo {
        private final String hrefValue;
        private final String descriptionText;

        /**
         * Constructs a new CheckIndexInfo instance.
         *
         * @param href The href attribute for the check's link.
         * @param description The description text for the check.
         */
        /* package */ CheckIndexInfo(String href, String description) {
            hrefValue = href;
            descriptionText = description;
        }

        /**
         * Gets the href attribute for the check's link.
         *
         * @return The href string.
         */
        private String href() {
            return hrefValue;
        }

        /**
         * Gets the description text for the check.
         *
         * @return The description string.
         */
        private String description() {
            return descriptionText;
        }
    }
}
