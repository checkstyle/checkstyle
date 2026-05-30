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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Generates {@code search-index.js} from the Checkstyle XDoc source files.
 *
 * <p>This is a plain Java {@code main()} class - no Maven plugin API required.
 * It is invoked by {@code exec-maven-plugin} during the {@code pre-site} phase
 * so the index is ready when Maven Site copies static resources.</p>
 *
 * <p>Output is written as a JavaScript file declaring a global variable rather
 * than a raw JSON file. This allows the search widget to work when pages are
 * opened directly via {@code file://} (e.g. IntelliJ's built-in server) without
 * triggering CORS/XHR restrictions, while also working identically over HTTP.</p>
 *
 * <p>Output format — {@code target/site/search-index.js}:</p>
 * <pre>
 * var CHECKSTYLE_SEARCH_INDEX = [
 *   {"title":"MethodName","url":"checks/naming/methodname.html#MethodName",...},
 *   ...
 * ];
 * </pre>
 *
 * <p>Usage (called by exec-maven-plugin in pom.xml):</p>
 * <pre>
 *   java SearchIndexGenerator &lt;xdocsDir&gt; &lt;outputDir&gt;
 *   java SearchIndexGenerator src/xdocs target/site
 * </pre>
 */
public final class SearchIndexGenerator {

    /** String literal for checks directory. */
    private static final String CHECKS = "checks";

    /** String literal for comma. */
    private static final String COMMA_STR = ",";

    /** String literal for space. */
    private static final String SPACE = " ";

    /** Character literal for space. */
    private static final char SPACE_CHAR = ' ';

    /** String literal for ellipsis. */
    private static final String ELLIPSIS = "...";

    /** String literal for external general entities feature. */
    private static final String EXTERNAL_GENERAL_ENTITIES =
            "http://xml.org/sax/features/external-general-entities";

    /** String literal for external parameter entities feature. */
    private static final String EXTERNAL_PARAMETER_ENTITIES =
            "http://xml.org/sax/features/external-parameter-entities";

    /** String literal for General category. */
    private static final String GENERAL = "General";

    /** String literal for subsection element. */
    private static final String SUBSECTION = "subsection";

    /** String literal for name attribute. */
    private static final String NAME_ATTR = "name";

    /** String literal for index.xml. */
    private static final String INDEX_XML = "index.xml";

    /** String literal for Content. */
    private static final String CONTENT = "Content";

    /** Log message for skipping files. */
    private static final String SKIPPING_MSG = "[SearchIndex] WARN: skipping {0} - {1}";

    /** Magic number for minimum word length. */
    private static final int MIN_WORD_LENGTH = 2;

    /** Magic number for maximum keywords. */
    private static final int MAX_KEYWORDS = 15;

    /** Magic number for minimum description text length. */
    private static final int MIN_DESCRIPTION_TEXT_LENGTH = 20;

    /** Magic number for maximum description length. */
    private static final int MAX_DESCRIPTION_LENGTH = 150;

    /** Whitespace pattern. */
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    /** Non-alphanumeric pattern. */
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-z0-9]+");

    /** Document extension pattern (excluding templates). */
    private static final Pattern DOC_EXTENSION = Pattern.compile("\\.xml(\\.vm)?$");

    /** Stop words: too generic to be useful as search keywords. */
    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "a", "an", "the", "and", "or", "of", "to", "in", "is", "it",
            "that", "this", "for", "on", "with", "are", "be", "by", "at",
            "as", "if", "its", "from", "which", "whether", "can", "will",
            "has", "have", "not", "also", "only", "any", "all", "each",
            "more", "than", "when", "then", "into", "such", "use", "used",
            "check", CHECKS, "checkstyle"
    ));

    /** Category mapping: XDoc subdirectory name to display label. */
    private static final Map<String, String> CATEGORY_MAP = new LinkedHashMap<>();

    static {
        CATEGORY_MAP.put("annotation", "Annotation");
        CATEGORY_MAP.put("blocks", "Block Checks");
        CATEGORY_MAP.put("coding", "Coding");
        CATEGORY_MAP.put("design", "Class Design");
        CATEGORY_MAP.put("header", "Headers");
        CATEGORY_MAP.put("imports", "Imports");
        CATEGORY_MAP.put("javadoc", "Javadoc Comments");
        CATEGORY_MAP.put("metrics", "Metrics");
        CATEGORY_MAP.put("misc", "Miscellaneous");
        CATEGORY_MAP.put("modifier", "Modifiers");
        CATEGORY_MAP.put("naming", "Naming Conventions");
        CATEGORY_MAP.put("regexp", "Regexp");
        CATEGORY_MAP.put("sizes", "Size Violations");
        CATEGORY_MAP.put("whitespace", "Whitespace");
    }

    /** Logger for this class. */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /** Prevent instantiation. */
    private SearchIndexGenerator() {
    }

    /**
     * Main entry point called by exec-maven-plugin.
     *
     * @param args args[0] = path to src/xdocs, args[1] = path to target/site
     * @throws IOException on file write failure
     * @throws IllegalArgumentException if args are missing
     * @throws IllegalStateException if xdocsDir is missing
     * @noinspectionreason UseOfSystemOutOrSystemErr - main method of a CLI utility
     */
    public static void main(String... args) throws IOException {
        new SearchIndexGenerator().execute(args);
    }

    /**
     * Internal execution method to avoid static context for the logger.
     *
     * @param args args[0] = path to src/xdocs, args[1] = path to target/site
     * @throws IOException on file write failure
     * @throws IllegalArgumentException if args are missing
     * @throws IllegalStateException if xdocsDir is missing
     */
    private void execute(String... args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException(
                    "Usage: SearchIndexGenerator <xdocsDir> <outputDir>");
        }

        final Path xdocsPath = Path.of(args[0]);
        final Path outputPath = Path.of(args[1]);
        final File xdocsDir = xdocsPath.toFile();
        final File outputDir = outputPath.toFile();

        if (!Files.exists(xdocsPath)) {
            final String error = "[SearchIndex] ERROR: xdocsDir not found: "
                    + xdocsPath.toAbsolutePath();
            throw new IllegalStateException(error);
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "[SearchIndex] Reading XDocs from: {0}", xdocsPath);
        }

        final List<SearchIndexEntry> entries = new ArrayList<>();

        final Path checksPath = xdocsPath.resolve(CHECKS);
        if (Files.exists(checksPath)) {
            processChecksDirectory(checksPath.toFile(), xdocsDir, entries);
        }

        final Path filtersPath = xdocsPath.resolve("filters");
        if (Files.exists(filtersPath)) {
            processDirectory(filtersPath.toFile(), xdocsDir, "Filters", "Filter", entries);
        }

        final Path fileFiltersPath = xdocsPath.resolve("filefilters");
        if (Files.exists(fileFiltersPath)) {
            processDirectory(fileFiltersPath.toFile(), xdocsDir,
                    "File Filters", "File Filter", entries);
        }

        processGeneralPages(xdocsDir, entries);
        writeJs(entries, outputDir);

        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "[SearchIndex] Done - {0} entries indexed.",
                    entries.size());
        }
    }

    /**
     * Walks {@code src/xdocs/checks/} and processes each category subdirectory.
     *
     * @param checksDir the checks root directory
     * @param xdocsDir  the xdocs root (used for URL building)
     * @param entries   list to append entries to
     */
    private void processChecksDirectory(File checksDir, File xdocsDir,
                                        List<SearchIndexEntry> entries) {

        final File[] categoryDirs = checksDir.listFiles(File::isDirectory);
        if (categoryDirs != null) {
            for (File categoryDir : categoryDirs) {
                final String dirName = categoryDir.getName().toLowerCase(Locale.ROOT);
                final String category = CATEGORY_MAP.getOrDefault(dirName,
                        capitalise(dirName));
                processDirectory(categoryDir, xdocsDir, category, "Check", entries);
            }
        }
    }

    /**
     * Processes all {@code .xml} files in a directory (non-recursive).
     * {@code index.xml} files are skipped as they are navigation/TOC pages.
     *
     * @param dir      directory to scan
     * @param xdocsDir xdocs root (used for URL building)
     * @param category category label for all entries in this directory
     * @param type     document type ("Check", "Filter", "File Filter")
     * @param entries  list to append entries to
     */
    private void processDirectory(File dir, File xdocsDir,
                                  String category, String type,
                                  List<SearchIndexEntry> entries) {

        final File[] xmlFiles = dir.listFiles(file -> {
            final String name = file.getName();
            return file.isFile() && DOC_EXTENSION.matcher(name).find()
                    && !INDEX_XML.equals(file.getName());
        });

        if (xmlFiles != null) {
            for (File xmlFile : xmlFiles) {
                try {
                    entries.add(parseXdocFile(xmlFile, xdocsDir, category, type));
                }
                catch (IOException | SAXException | ParserConfigurationException exception) {
                    if (logger.isLoggable(Level.WARNING)) {
                        logger.log(Level.WARNING, SKIPPING_MSG,
                                new Object[] {xmlFile.getName(), exception.getMessage()});
                    }
                }
            }
        }
    }

    /**
     * Adds entries for the well-known top-level general documentation pages.
     * These pages don't follow the check template so minimal metadata is extracted.
     *
     * @param xdocsDir the xdocs root directory
     * @param entries  list to append entries to
     */
    private void processGeneralPages(File xdocsDir,
                                     List<SearchIndexEntry> entries) {

        final File[] xmlFiles = xdocsDir.listFiles(file -> {
            final String name = file.getName();
            return file.isFile() && DOC_EXTENSION.matcher(name).find()
                    && !INDEX_XML.equals(name)
                    && !"checks.xml".equals(name)
                    && !name.startsWith("config_")
                    && !name.startsWith("releasenotes_old_");
        });

        if (xmlFiles != null) {
            Arrays.sort(xmlFiles);
            for (File xmlFile : xmlFiles) {
                try {
                    entries.add(parseXdocFile(xmlFile, xdocsDir, GENERAL, GENERAL));
                }
                catch (IOException | SAXException | ParserConfigurationException exception) {
                    if (logger.isLoggable(Level.WARNING)) {
                        logger.log(Level.WARNING, SKIPPING_MSG,
                                new Object[] {xmlFile.getName(), exception.getMessage()});
                    }
                }
            }
        }
    }

    /**
     * Parses one XDoc file and returns a single {@link SearchIndexEntry} representing
     * the entire document.
     *
     * @param xmlFile the XDoc source file to parse
     * @param xdocsDir xdocs root directory (for URL calculation)
     * @param category category label for this file's entries
     * @param type     document type ("Check", "Filter", etc.)
     * @return an entry representing the document
     * @throws ParserConfigurationException on XML parser setup failure
     * @throws SAXException on XML parse error
     * @throws IOException on file read failure
     */
    private static SearchIndexEntry parseXdocFile(File xmlFile,
                                                  File xdocsDir, String category, String type)
            throws ParserConfigurationException, SAXException, IOException {
        final Document doc = parseXml(xmlFile);

        final NodeList bodies = doc.getElementsByTagName("body");
        if (bodies.getLength() == 0) {
            throw new IOException("No body element found in XDoc: " + xmlFile.getName());
        }

        final Element body = (Element) bodies.item(0);
        final NodeList sections = body.getElementsByTagName("section");
        final String title = extractTitle(doc, xmlFile, sections);
        final String url = buildUrl(xmlFile, xdocsDir);
        final String description = extractAggregateDescription(sections, xmlFile);
        final String keywords = extractAggregateKeywords(title, sections);

        return new SearchIndexEntry(title, url, category, type, description, keywords);
    }

    /**
     * Parses the XML file into a Document.
     *
     * @param xmlFile the XDoc source file
     * @return the parsed Document
     * @throws ParserConfigurationException on XML parser setup failure
     * @throws SAXException on XML parse error
     * @throws IOException on file read failure
     */
    private static Document parseXml(File xmlFile)
            throws ParserConfigurationException, SAXException, IOException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(EXTERNAL_GENERAL_ENTITIES, false);
        factory.setFeature(EXTERNAL_PARAMETER_ENTITIES, false);

        final DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(null);

        final Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    /**
     * Extracts the document title.
     *
     * @param doc the document
     * @param xmlFile the source file
     * @param sections the list of sections
     * @return the title string
     */
    private static String extractTitle(Document doc, File xmlFile, NodeList sections) {
        final NodeList titles = doc.getElementsByTagName("title");
        String title = "";
        if (titles.getLength() > 0) {
            title = titles.item(0).getTextContent().trim();
        }

        if ((title.isEmpty() || CONTENT.equalsIgnoreCase(title)) && sections.getLength() > 0) {
            final String firstSection = ((Element) sections.item(0))
                    .getAttribute(NAME_ATTR).trim();
            if (!firstSection.isEmpty() && !CONTENT.equalsIgnoreCase(firstSection)) {
                title = firstSection;
            }
        }

        if (title.isEmpty() || CONTENT.equalsIgnoreCase(title)) {
            final String name = xmlFile.getName().replaceFirst(DOC_EXTENSION.pattern(), "");
            title = capitalise(name.replace('_', ' '));
        }
        return title;
    }

    /**
     * Aggregates description from sections.
     *
     * @param sections list of sections
     * @param xmlFile the source file
     * @return description string
     * @throws ParserConfigurationException on XML parser setup failure
     * @throws SAXException on XML parse error
     * @throws IOException on file read failure
     */
    private static String extractAggregateDescription(NodeList sections, File xmlFile)
            throws ParserConfigurationException, SAXException, IOException {
        String description = "";
        for (int index = 0; index < sections.getLength(); index++) {
            description = extractDescription((Element) sections.item(index));
            if (!description.isEmpty()) {
                break;
            }
        }
        if (description.isEmpty()) {
            description = extractPageDescription(xmlFile);
        }
        return description;
    }

    /**
     * Aggregates keywords from sections.
     *
     * @param title the document title
     * @param sections list of sections
     * @return keywords string
     */
    private static String extractAggregateKeywords(String title, NodeList sections) {
        final StringBuilder keywordSource = new StringBuilder(title);
        for (int index = 0; index < sections.getLength(); index++) {
            final Element section = (Element) sections.item(index);
            keywordSource.append(SPACE_CHAR)
                .append(section.getAttribute(NAME_ATTR))
                .append(SPACE_CHAR)
                .append(section.getTextContent());
        }
        return extractKeywordsFromText(keywordSource.toString());
    }

    /**
     * Extracts the first sentence of the Description subsection.
     * Returns an empty string if no Description subsection is found.
     *
     * @param section the {@code <section>} element to search
     * @return first sentence of the description, or empty string
     */
    private static String extractDescription(Element section) {
        final NodeList subsections = section.getElementsByTagName(SUBSECTION);
        String result = "";

        final int subsectionsLength = subsections.getLength();
        for (int index = 0; index < subsectionsLength; index++) {
            final Element sub = (Element) subsections.item(index);
            if ("description".equals(
                    sub.getAttribute(NAME_ATTR).trim().toLowerCase(Locale.ROOT))) {
                final String text = WHITESPACE.matcher(sub.getTextContent())
                        .replaceAll(SPACE).trim();
                final int dot = text.indexOf('.');
                if (dot > 0) {
                    result = text.substring(0, dot + 1).trim();
                }
                else if (text.length() > MAX_DESCRIPTION_LENGTH) {
                    result = text.substring(0, MAX_DESCRIPTION_LENGTH) + ELLIPSIS;
                }
                else {
                    result = text;
                }
                break;
            }
        }
        return result;
    }

    /**
     * Extracts a short description from a general page by reading the first
     * non-trivial paragraph found in the document body.
     *
     * @param xmlFile the XDoc file to parse
     * @return first paragraph text, truncated to 150 characters.
     * @throws ParserConfigurationException on XML parser setup failure
     * @throws SAXException on XML parse error
     * @throws IOException on file read failure
     */
    private static String extractPageDescription(File xmlFile)
            throws ParserConfigurationException, SAXException, IOException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(EXTERNAL_GENERAL_ENTITIES, false);
        final DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(null);
        final Document doc = builder.parse(xmlFile);
        final NodeList paragraphs = doc.getElementsByTagName("p");
        String result = "";
        final int paragraphsLength = paragraphs.getLength();
        for (int index = 0; index < paragraphsLength; index++) {
            final String text = WHITESPACE.matcher(paragraphs.item(index).getTextContent())
                    .replaceAll(SPACE).trim();
            final int textLength = text.length();
            if (textLength > MIN_DESCRIPTION_TEXT_LENGTH) {
                if (textLength > MAX_DESCRIPTION_LENGTH) {
                    result = text.substring(0, MAX_DESCRIPTION_LENGTH) + ELLIPSIS;
                }
                else {
                    result = text;
                }
                break;
            }
        }
        return result;
    }

    /**
     * Builds the root-relative URL for a section in an XDoc file.
     *
     * <p>Examples:</p>
     * <pre>
     *   src/xdocs/checks/naming/methodname.xml + "MethodName"
     *     - "checks/naming/methodname.html#MethodName"
     *
     *   src/xdocs/filters/suppresswarnings.xml + "SuppressWarnings"
     *     - "filters/suppresswarnings.html#SuppressWarnings"
     * </pre>
     *
     * @param xmlFile     the source XDoc file
     * @param xdocsDir    the xdocs root directory
     * @return root-relative URL string with anchor
     */
    private static String buildUrl(File xmlFile, File xdocsDir) {
        return xdocsDir.toPath()
                .relativize(xmlFile.toPath())
                .toString()
                .replace(File.separatorChar, '/')
                .replaceFirst(DOC_EXTENSION.pattern(), ".html");
    }

    /**
     * Extracts keywords from free-form text by splitting on non-word characters
     * and filtering short and stop words.
     *
     * @param text input text
     * @return comma-separated keyword string (up to 15 words).
     */
    private static String extractKeywordsFromText(String text) {
        String result = "";
        if (text != null && !text.isEmpty()) {
            result = NON_ALPHANUMERIC.splitAsStream(text.toLowerCase(Locale.ROOT))
                    .filter(word -> word.length() >= MIN_WORD_LENGTH && !STOP_WORDS.contains(word))
                    .distinct()
                    .limit(MAX_KEYWORDS)
                    .collect(Collectors.joining(COMMA_STR));
        }
        return result;
    }

    /**
     * Writes all index entries to {@code target/site/search-index.js}.
     *
     * <p>Output format is a JavaScript global variable declaration so the
     * data is available immediately without an XHR fetch, allowing the search
     * widget to work when pages are opened via {@code file://} as well as HTTP.
     * </p>
     *
     * <p>Format:</p>
     * <pre>
     * /* Auto-generated - do not edit manually *&#47;
     * var CHECKSTYLE_SEARCH_INDEX = [
     *   {"title":"...", "url":"...", ...},
     *   ...
     * ];
     * </pre>
     *
     * @param entries   the list of entries to serialise
     * @param outputDir target output directory (target/site)
     * @throws IOException on file write failure
     */
    private void writeJs(List<SearchIndexEntry> entries, File outputDir) throws IOException {

        final Path outputPath = outputDir.toPath();
        Files.createDirectories(outputPath);

        final Path outPath = outputPath.resolve("search-index.js");

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(outPath,
                StandardCharsets.UTF_8))) {
            writer.println("/* Auto-generated by SearchIndexGenerator - do not edit manually */");
            writer.println("var CHECKSTYLE_SEARCH_INDEX = [");

            final int entriesSize = entries.size();
            for (int index = 0; index < entriesSize; index++) {
                final String comma;
                if (index < entriesSize - 1) {
                    comma = COMMA_STR;
                }
                else {
                    comma = "";
                }
                writer.println("  " + entries.get(index).toJson() + comma);
            }
            writer.println("];");
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "[SearchIndex] Written: {0}", outPath.toAbsolutePath());
        }
    }

    /**
     * Capitalises the first character of a string.
     * Used for directory names not found in {@link #CATEGORY_MAP}.
     *
     * @param input the string to capitalise
     * @return string with first character uppercased, or input unchanged if empty.
     */
    private static String capitalise(String input) {
        String result = input;
        if (input != null && !input.isEmpty()) {
            result = Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
        return result;
    }
}
