package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;

public class XdocsCategoryIndexTest extends AbstractModuleTestSupport {

    private static final Path XDOC_CHECKS_DIR = Paths.get("src", "site", "xdoc", "checks");

    /**
     * Stores information about a check as parsed from an index.xml file.
     * It holds the hyperlink reference (href) and the description text.
     */
    public static final class CheckIndexInfo {
        private final String hrefValue;
        private final String descriptionText;

        /**
         * Constructs a new CheckIndexInfo instance.
         * @param href The href attribute for the check's link.
         * @param description The description text for the check.
         */
        /* package */ CheckIndexInfo(String href, String description) {
            this.hrefValue = href;
            this.descriptionText = description;
        }

        /**
         * Gets the href attribute for the check's link.
         * @return The href string.
         */
        public String getHref() {
            return hrefValue;
        }

        /**
         * Gets the description text for the check.
         * @return The description string.
         */
        public String getDescription() {
            return descriptionText;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CheckIndexInfo that = (CheckIndexInfo) obj;
            return Objects.equals(hrefValue, that.hrefValue) &&
                   Objects.equals(descriptionText, that.descriptionText);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hrefValue, descriptionText);
        }
    }

    @Override
    protected String getPackageLocation() {
        return "com.puppycrawl.tools.checkstyle.internal";
    }

    @Test
    /* package */ void testAllChecksListedInCategoryIndexAndDescriptionMatches() throws Exception {
        final List<Path> checkXdocFiles = getCheckXdocFiles();

        for (Path checkXdocFile : checkXdocFiles) {
            final String mainSectionName = getMainSectionName(checkXdocFile);
            final Path categoryDir = checkXdocFile.getParent();
            final Path categoryIndexFile = categoryDir.resolve("index.xml");

            assertWithMessage("Category index file should exist for check: " + checkXdocFile)
                    .that(Files.exists(categoryIndexFile)).isTrue();

            final Map<String, CheckIndexInfo> indexedChecks = parseCategoryIndex(categoryIndexFile);
            final Set<String> foundKeys = indexedChecks.keySet();

            assertWithMessage(
                    "Check '" + mainSectionName + "' from file "
                            + checkXdocFile.getFileName()
                            + " should be listed by its main section name in category index " + categoryIndexFile + ". "
                            + "Ensure the <a> tag text matches. Found text keys in index: " + foundKeys)
                    .that(indexedChecks.containsKey(mainSectionName)).isTrue();

            final CheckIndexInfo checkInfoFromIndex = indexedChecks.get(mainSectionName);
            assertNotNull(checkInfoFromIndex, 
                "Internal test error: CheckInfo was null for '" + mainSectionName + "' despite containsKey being true.");

            // Validate Href
            final String expectedHrefFileName = checkXdocFile.getFileName().toString()
                    .replace(".xml", ".html");
            final String expectedHref = expectedHrefFileName.toLowerCase(Locale.ROOT) + "#" + mainSectionName;
            final String actualHref = checkInfoFromIndex.getHref();

            assertWithMessage(
                    "Href mismatch for check '%s' in %s. Expected link: '%s', but found: '%s'. "
                    + "Ensure the filename (e.g., %s - lowercased .html) and #anchor (e.g., #%s - main section name) are correct.",
                    mainSectionName, categoryIndexFile, expectedHref, actualHref, expectedHrefFileName.toLowerCase(Locale.ROOT), mainSectionName)
                    .that(actualHref).isEqualTo(expectedHref);

            // Validate Description
            final String descriptionFromXdoc = getCheckDescriptionFromXdoc(checkXdocFile);
            final String descriptionFromIndex = checkInfoFromIndex.getDescription();
            final String normalizedIndexDesc = normalizeText(descriptionFromIndex);
            final String normalizedXdocDesc = normalizeText(descriptionFromXdoc);

            assertWithMessage(
                    "Description mismatch for check '%s' in %s. "
                    + "The description from the index file (first <td> after name) should be an accurate starting summary (prefix) of the full description from the check's XDoc page (<subsection name=\"Description\">).\n"
                    + "  Index desc (normalized) : [%s]\n"
                    + "  XDoc desc (normalized)  : [%s]",
                    mainSectionName, categoryIndexFile, normalizedIndexDesc, normalizedXdocDesc)
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
    private List<Path> getCheckXdocFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(XDOC_CHECKS_DIR)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".xml"))
                    .filter(path -> !"index.xml".equals(path.getFileName().toString()))
                    .filter(path -> !"property_types.xml".equals(path.getFileName().toString()))
                    .collect(Collectors.toUnmodifiableList());
        }
    }

    /**
     * Extracts the main section name from a check's XDoc file.
     * This is typically the value of the 'name' attribute of the first <section> tag.
     *
     * @param checkXdocFile Path to the check's XDoc file.
     * @return The main section name.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws SAXException if any SAX errors occur during parsing.
     * @throws IOException if an I/O error occurs reading the file.
     * @throws AssertionError if no <section name=...> is found.
     */
    private String getMainSectionName(Path checkXdocFile)
            throws ParserConfigurationException, SAXException, IOException {
        final String content = Files.readString(checkXdocFile);
        final Document document = XmlUtil.getRawXml(checkXdocFile.toString(), content, content);
        final NodeList sections = document.getElementsByTagName("section");

        for (int i = 0; i < sections.getLength(); i++) {
            Node sectionNode = sections.item(i);
            if (sectionNode instanceof Element) {
                Element sectionElement = (Element) sectionNode;
                if (sectionElement.hasAttribute("name")) {
                    return sectionElement.getAttribute("name");
                }
            }
        }
        throw new AssertionError("No <section name=...> found in " + checkXdocFile);
    }

    /**
     * Extracts the description of a check from its XDoc file.
     * It looks for a <subsection name="Description"> and then tries to find the content
     * within a <div> or <p> tag. If not found, it aggregates direct text nodes of the subsection.
     * As a last resort, it uses the full text content of the subsection.
     *
     * @param checkXdocFile Path to the check's XDoc file.
     * @return The check's description text.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws SAXException if any SAX errors occur during parsing.
     * @throws IOException if an I/O error occurs reading the file.
     * @throws AssertionError if no suitable description subsection is found.
     */
    private String getCheckDescriptionFromXdoc(Path checkXdocFile)
            throws ParserConfigurationException, SAXException, IOException {
        final String content = Files.readString(checkXdocFile);
        final Document document = XmlUtil.getRawXml(checkXdocFile.toString(), content, content);
        final NodeList subsections = document.getElementsByTagName("subsection");

        for (int i = 0; i < subsections.getLength(); i++) {
            Node subsectionNode = subsections.item(i);
            if (subsectionNode instanceof Element) {
                Element subsectionElement = (Element) subsectionNode;
                if ("Description".equals(subsectionElement.getAttribute("name"))) {
                    for (Element childElement : getChildrenElementsByTagName(subsectionElement, "div")) {
                        String text = childElement.getTextContent();
                        if (text != null && !text.isBlank()) {
                            return text;
                        }
                    }

                    for (Element childElement : getChildrenElementsByTagName(subsectionElement, "p")) {
                        String text = childElement.getTextContent();
                        if (text != null && !text.isBlank()) {
                            return text;
                        }
                    }
                    
                    StringBuilder directTextContent = new StringBuilder();
                    NodeList directChildren = subsectionElement.getChildNodes();
                    for (int k = 0; k < directChildren.getLength(); k++) {
                        Node directChild = directChildren.item(k);
                        if (directChild.getNodeType() == Node.TEXT_NODE) {
                            directTextContent.append(directChild.getNodeValue());
                        }
                    }
                    if (!directTextContent.toString().isBlank()) {
                        return directTextContent.toString();
                    }

                    String fullSubsectionText = subsectionElement.getTextContent();
                    if (fullSubsectionText != null && !fullSubsectionText.isBlank()) {
                        return fullSubsectionText;
                    }
                }
            }
        }
        throw new AssertionError("No <subsection name=\"Description\"> with suitable content found in "
                + checkXdocFile);
    }

    /**
     * Parses a category index.xml file to extract information about the checks listed.
     * It iterates through all tables and their rows to find check names, hrefs, and descriptions.
     *
     * @param categoryIndexFile Path to the category's index.xml file.
     * @return A map where keys are check names (from <a> tag text) and values are CheckIndexInfo objects.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
     * @throws SAXException if any SAX errors occur during parsing.
     * @throws IOException if an I/O error occurs reading the file.
     * @throws AssertionError if no <table> is found in the index file.
     */
    private Map<String, CheckIndexInfo> parseCategoryIndex(Path categoryIndexFile)
            throws ParserConfigurationException, SAXException, IOException {
        final Map<String, CheckIndexInfo> indexedChecks = new HashMap<>();
        final String content = Files.readString(categoryIndexFile);
        final Document document = XmlUtil.getRawXml(categoryIndexFile.toString(), content, content);
        final NodeList tableNodes = document.getElementsByTagName("table");

        if (tableNodes.getLength() == 0) {
             throw new AssertionError("No <table> found in " + categoryIndexFile);
        }

        for (int tableIdx = 0; tableIdx < tableNodes.getLength(); tableIdx++) {
            Node tableNode = tableNodes.item(tableIdx);
            if (tableNode instanceof Element) {
                processTableElement((Element) tableNode, indexedChecks);
            }
        }
        return indexedChecks;
    }

    /**
     * Processes a single <table> element from a category index file.
     * Iterates over its rows, skipping a potential header row, and processes data rows.
     *
     * @param tableElement The <table> DOM element.
     * @param indexedChecks The map to populate with check information.
     */
    private void processTableElement(Element tableElement, Map<String, CheckIndexInfo> indexedChecks) {
        List<Element> rowElements = getChildrenElementsByTagName(tableElement, "tr");
        boolean isFirstRowInTable = true;

        for (Element rowElement : rowElements) {
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
     * Checks if a given table row element is a header row (i.e., contains <th> elements).
     *
     * @param rowElement The <tr> DOM element.
     * @return True if it's a header row, false otherwise.
     */
    private boolean isHeaderRow(Element rowElement) {
        return !getChildrenElementsByTagName(rowElement, "th").isEmpty();
    }

    /**
     * Processes a single data row (<tr> element with <td> children) from a category index table.
     * Extracts the check name, href, and description.
     *
     * @param rowElement The <tr> DOM element representing a data row.
     * @param indexedChecks The map to populate with check information.
     */
    private void processDataRow(Element rowElement, Map<String, CheckIndexInfo> indexedChecks) {
        List<Element> cellElements = getChildrenElementsByTagName(rowElement, "td");
        if (cellElements.size() >= 2) {
            Element nameCell = cellElements.get(0);
            Element descCell = cellElements.get(1);

            getFirstChildElementByTagName(nameCell, "a").ifPresent(anchorElement -> {
                if (anchorElement.hasAttribute("href")) {
                    String checkNameInIndex = anchorElement.getTextContent().trim();
                    String href = anchorElement.getAttribute("href");
                    String description = descCell.getTextContent();
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
     * @return A list of matching child elements. Returns an empty list if parent is null or no matches found.
     */
    private static List<Element> getChildrenElementsByTagName(Node parent, String tagName) {
        List<Element> elements = new ArrayList<>();
        if (parent != null) {
            NodeList children = parent.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child instanceof Element && tagName.equals(child.getNodeName())) {
                    elements.add((Element) child);
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
     * @return An Optional containing the first matching child element, or an empty Optional if none found or parent is null.
     */
    private static Optional<Element> getFirstChildElementByTagName(Node parent, String tagName) {
        if (parent != null) {
            NodeList children = parent.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child instanceof Element && tagName.equals(child.getNodeName())) {
                    return Optional.of((Element) child);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Normalizes a string by trimming whitespace, replacing non-breaking spaces,
     * and collapsing multiple internal whitespace characters into a single space.
     *
     * @param text The text to normalize.
     * @return The normalized text, or an empty string if the input is null.
     */
    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\u00a0", " ").trim().replaceAll("\\s+", " ");
    }
} 