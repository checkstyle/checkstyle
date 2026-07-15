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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
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
 * Generates {@code search-index.json} from the Checkstyle XDoc source files.
 *
 * <p>This is a plain Java {@code main()} class - no Maven plugin API required.
 * It is invoked by {@code exec-maven-plugin} during the {@code process-classes}
 * phase so the index is ready when Maven Site copies static resources.</p>
 *
 * <p>Output is written as a JSON file. The search widget fetches this file
 * using the fetch API and parses it to populate the search index.</p>
 *
 * <h2>Key design decisions</h2>
 * <ul>
 *   <li><b>No duplicates.</b> Only plain {@code .xml} files are processed for
 *       check/filter/filefilter directories. The {@code .xml.template} and
 *       {@code .xml.vm} siblings are pre-render source files that would produce
 *       identical URLs and duplicate entries. A secondary URL-keyed dedup guard
 *       is also applied across the entire output list.</li>
 *
 *   <li><b>Identifiable example titles.</b> Both {@code -config} and
 *       {@code -code} example paragraphs are indexed.  Their titles use the
 *       pattern {@code "<CheckName>: Example1 [config]"} and
 *       {@code "<CheckName>: Example1 [code]"} so users can distinguish a
 *       configuration snippet from its matching Java code example in search
 *       results.</li>
 *
 *   <li><b>Full general-page indexing.</b> Each meaningful {@code <section>}
 *       in general documentation pages (e.g. {@code config_system_properties},
 *       {@code writingchecks}, {@code cmdline}) is indexed as its own entry
 *       with the full section text used for keyword extraction - not just the
 *       first sentence. This makes page-internal headings discoverable.</li>
 *
 *   <li><b>Disambiguated generic titles.</b> Structural section names that are
 *       repeated across many pages (e.g. "Overview", "Debug", "Contributing")
 *       are prefixed with the page title, yielding e.g.
 *       "Eclipse IDE: Debug" instead of a bare "Debug" that collides with
 *       "IntelliJ IDE: Debug".</li>
 *
 *   <li><b>Junk pages excluded.</b> Release notes, auto-generated style
 *       coverage reports and bare category aggregator stubs are skipped.</li>
 * </ul>
 *
 * <p>Usage (called by exec-maven-plugin in pom.xml):</p>
 * <pre>
 *   java SearchIndexGenerator &lt;xdocsDir&gt; &lt;outputFilePath&gt;
 *   java SearchIndexGenerator src/site/xdoc target/site/search-index.json
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

    /** String literal for colon separator used in disambiguated titles. */
    private static final String TITLE_SEPARATOR = ": ";

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

    /** String literal for Example document type. */
    private static final String EXAMPLE_TYPE = "Example";

    /** String literal for Property document type. */
    private static final String PROPERTY_TYPE = "Property";

    /** String literal for subsection element. */
    private static final String SUBSECTION = "subsection";

    /** String literal for name attribute. */
    private static final String NAME_ATTR = "name";

    /** String literal for id attribute. */
    private static final String ID_ATTR = "id";

    /** String literal for index.xml. */
    private static final String INDEX_XML = "index.xml";

    /** Constant for the filters directory. */
    private static final String FILTERS_DIR = "filters";

    /** Constant for the filefilters directory. */
    private static final String FILEFILTERS_DIR = "filefilters";

    /** Constant for the index file name. */
    private static final String INDEX_HTML = "index.html";

    /** String literal for Content. */
    private static final String CONTENT = "Content";

    /** String literal for the Examples subsection name. */
    private static final String EXAMPLES_SUBSECTION = "examples";

    /** String literal for body element. */
    private static final String BODY = "body";

    /** String literal for section element. */
    private static final String SECTION = "section";

    /** String literal for title element. */
    private static final String TITLE = "title";

    /** String literal for description element. */
    private static final String DESCRIPTION = "description";

    /** String literal for anchor separator. */
    private static final String ANCHOR_SEPARATOR = "#";

    /** String literal for path separator in URLs. */
    private static final String PATH_SEPARATOR = "/";

    /** String literal for the Properties subsection name fragment. */
    private static final String PROPERTIES_FRAGMENT = "propert";

    /** Exception message prefix used when an XDoc file fails to parse. */
    private static final String PARSE_FAILURE_MSG = "Failed to parse XDoc file: ";

    /**
     * Suffix label appended to example titles for configuration snippets.
     * Yields e.g. "AnnotationLocation: Example1 [config]".
     */
    private static final String EXAMPLE_LABEL_CONFIG = " [config]";

    /**
     * Suffix label appended to example titles for Java code examples.
     * Yields e.g. "AnnotationLocation: Example1 [code]".
     */
    private static final String EXAMPLE_LABEL_CODE = " [code]";

    /** Magic number for minimum word length. */
    private static final int MIN_WORD_LENGTH = 2;

    /** Magic number for maximum keywords. */
    private static final int MAX_KEYWORDS = 15;

    /** Magic number for maximum description length. */
    private static final int MAX_DESCRIPTION_LENGTH = 150;

    /** Whitespace pattern. */
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    /** Non-alphanumeric pattern. */
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-z0-9]+");

    /**
     * Matches only plain {@code .xml} files (not {@code .xml.vm} or
     * {@code .xml.template}).  Used when scanning check/filter/filefilter
     * directories to avoid processing pre-render source templates and
     * producing duplicate index entries.
     */
    private static final Pattern PLAIN_XML = Pattern.compile("\\.xml$");

    /**
     * Matches {@code .xml}, {@code .xml.vm} and {@code .xml.template}.
     * Used only for URL building (stripping the extension to produce a
     * {@code .html} path) and for the general-pages scanner where we
     * want to exclude templates by name rather than by extension.
     */
    private static final Pattern DOC_EXTENSION =
            Pattern.compile("\\.xml$|\\.xml\\.vm$|\\.xml\\.template$");

    /**
     * Matches {@code config_<category>.xml} files that redirect to check category pages.
     * Captures the category name (e.g. "metrics" from "config_metrics.xml") in group 1.
     */
    private static final Pattern CONFIG_CATEGORY =
          Pattern.compile("^config_(.+)\\.xml$");

    /**
     * Matches an example paragraph {@code id} attribute that has a suffix of
     * either {@code -config} or {@code -code}, capturing the base label
     * (e.g. "Example1") in group 1 and the type ("config" or "code") in
     * group 2.
     *
     * <p>Example ids found in XDoc source:</p>
     * <ul>
     *   <li>{@code id="Example1-config"} -&gt; label "Example1", type "config"</li>
     *   <li>{@code id="Example1-code"}   -&gt; label "Example1", type "code"</li>
     * </ul>
     */
    private static final Pattern EXAMPLE_PARAGRAPH_ID =
            Pattern.compile("^(Example\\d+)-(config|code)$");

    /**
     * Generic section/subsection names that are structurally repeated across
     * many unrelated general pages (IDE setup guides, writing-* guides, etc).
     * On their own they are meaningless in search results ("Debug" appears
     * identically in eclipse.xml, idea.xml, and netbeans.xml) so when one of
     * these is used as a section title it is always disambiguated with the
     * source page's own title, e.g. "Eclipse IDE: Debug".
     */
    private static final Set<String> GENERIC_SECTION_NAMES = new HashSet<>(Arrays.asList(
            "overview", DESCRIPTION, EXAMPLES_SUBSECTION, "example", "debug",
            "contributing", "limitations", "parameters", "installation"
    ));

    /**
     * Display names for the check category subdirectories under
     * {@code checks/}, keyed by lowercase directory name. Every directory
     * that exists under {@code checks/} must have an entry here -
     * {@link #processChecksDirectory} fails fast if one is missing, so a
     * contributor adding a new category is forced to register its display
     * name instead of getting a guessed-at label.
     */
    private static final Map<String, String> CHECKS_CATEGORY_DISPLAY_NAMES = new LinkedHashMap<>();

    static {
        CHECKS_CATEGORY_DISPLAY_NAMES.put("annotation", "Annotations");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("blocks", "Block Checks");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("coding", "Coding");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("design", "Class Design");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("header", "Headers");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("imports", "Imports");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("javadoc", "Javadoc Comments");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("metrics", "Metrics");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("misc", "Miscellaneous");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("modifier", "Modifiers");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("naming", "Naming Conventions");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("regexp", "Regexp");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("sizes", "Size Violations");
        CHECKS_CATEGORY_DISPLAY_NAMES.put("whitespace", "Whitespace");
    }

    /** Stop words: too generic to be useful as search keywords. */
    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "a", "an", "the", "and", "or", "of", "to", "in", "is", "it",
            "that", "this", "for", "on", "with", "are", "be", "by", "at",
            "as", "if", "its", "from", "which", "whether", "can", "will",
            "has", "have", "not", "also", "only", "any", "all", "each",
            "more", "than", "when", "then", "into", "such", "use", "used",
            "check", CHECKS, "checkstyle"
    ));

    /** Accumulated search index entries. */
    private List<SearchIndexEntry> entries;

    /** Deduplication guard for URLs. */
    private Set<String> seenUrls;

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
     * @param args args[0] = path to src/xdocs, args[1] = output file path
     * @throws IOException on file write failure
     * @throws IllegalArgumentException if args are missing
     * @throws IllegalStateException if xdocsDir is missing
     */
    private void execute(String... args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException(
                    "Usage: SearchIndexGenerator <xdocsDir> <outputFilePath>");
        }

        final Path xdocsPath = Path.of(args[0]);
        final Path outputFilePath = Path.of(args[1]);
        final File xdocsDir = xdocsPath.toFile();

        if (!Files.exists(xdocsPath)) {
            final String error = "[SearchIndex] ERROR: xdocsDir not found: "
                    + xdocsPath.toAbsolutePath();
            throw new IllegalStateException(error);
        }

        seenUrls = new LinkedHashSet<>();
        entries = new ArrayList<>();

        final Path checksPath = xdocsPath.resolve(CHECKS);
        if (Files.exists(checksPath)) {
            processChecksDirectory(checksPath.toFile(), xdocsDir);
        }

        final Path filtersPath = xdocsPath.resolve(FILTERS_DIR);
        if (Files.exists(filtersPath)) {
            processDirectory(filtersPath.toFile(), xdocsDir,
                    "Filters", "Filter");
        }

        final Path fileFiltersPath = xdocsPath.resolve(FILEFILTERS_DIR);
        if (Files.exists(fileFiltersPath)) {
            processDirectory(fileFiltersPath.toFile(), xdocsDir,
                    "File Filters", "File Filter");
        }

        processGeneralPages(xdocsDir);
        writeJson(entries, outputFilePath);

    }

    /**
     * Walks {@code src/xdocs/checks/} and processes each category subdirectory.
     *
     * <p>Every directory found here must have a corresponding entry in
     * {@link #CHECKS_CATEGORY_DISPLAY_NAMES}; an unmapped directory likely
     * means a new check category was added without registering its display
     * name, so this fails fast rather than guessing a label from the
     * directory name.</p>
     *
     * @param checksDir the checks root directory
     * @param xdocsDir  the xdocs root (used for URL building)
     * @throws IllegalStateException if {@code checksDir} cannot be listed, or
     *         if one of its subdirectories has no entry in
     *         {@link #CHECKS_CATEGORY_DISPLAY_NAMES}
     */
    private void processChecksDirectory(File checksDir, File xdocsDir) {
        final File[] categoryDirs = checksDir.listFiles(File::isDirectory);
        if (categoryDirs == null) {
            throw new IllegalStateException(
                    "Unable to list check category directories under: " + checksDir);
        }

        Arrays.sort(categoryDirs);
        for (File categoryDir : categoryDirs) {
            final String dirName = categoryDir.getName().toLowerCase(Locale.ROOT);
            final String category = CHECKS_CATEGORY_DISPLAY_NAMES.get(dirName);
            if (category == null) {
                throw new IllegalStateException(
                        "No display name registered for check category directory '"
                                + dirName + "' in CHECKS_CATEGORY_DISPLAY_NAMES. "
                                + "Please add one.");
            }
            processDirectory(categoryDir, xdocsDir, category, "Check");
        }
    }

    /**
     * Processes all <b>plain</b> {@code .xml} files in a directory
     * (non-recursive). {@code index.xml} files and any file whose name ends
     * with {@code .xml.template} or {@code .xml.vm} are skipped.
     *
     * <p>Skipping templates is critical: every check page has a sibling
     * {@code *.xml.template} file that resolves to the <em>same</em> HTML
     * URL. Without this filter both files would be processed, producing two
     * identical (or near-identical) main entries plus doubled example and
     * property entries for every check.</p>
     *
     * <p>For each plain {@code .xml} file, the main check/filter entry,
     * per-example entries (both config and code), and per-property entries
     * are added.</p>
     *
     * @param dir      directory to scan
     * @param xdocsDir xdocs root (used for URL building)
     * @param category category label for all entries in this directory
     * @param type     document type ("Check", "Filter", "File Filter")
     */
    private void processDirectory(File dir, File xdocsDir,
                                  String category, String type) {
        final File[] xmlFiles = dir.listFiles(file -> {
            return file.isFile()
                    && PLAIN_XML.matcher(file.getName()).find()
                    && !INDEX_XML.equals(file.getName());
        });

        if (xmlFiles != null) {
            Arrays.sort(xmlFiles);
            for (File xmlFile : xmlFiles) {
                processXmlFile(xmlFile, xdocsDir, category, type);
            }
        }
    }

    /**
     * Parses a single check/filter XDoc file and adds its main, example, and
     * property entries to the index.
     *
     * <p>A parse failure here means the source XDoc itself is malformed,
     * which is a real problem with the documentation rather than something
     * safe to skip - so this fails the build instead of logging a warning
     * and silently continuing.</p>
     *
     * @param xmlFile  the XDoc source file to process
     * @param xdocsDir xdocs root (used for URL building)
     * @param category category label for entries from this file
     * @param type     document type ("Check", "Filter", "File Filter")
     * @throws IllegalStateException if {@code xmlFile} cannot be parsed
     */
    private void processXmlFile(File xmlFile, File xdocsDir, String category, String type) {
        try {
            final Document doc = parseXml(xmlFile);
            final String baseUrl = buildUrl(xmlFile, xdocsDir);

            addIfNew(buildMainEntry(doc, xmlFile, category, type, baseUrl));

            for (SearchIndexEntry entry : extractExampleEntries(doc, baseUrl, category)) {
                addIfNew(entry);
            }
            for (SearchIndexEntry entry : extractPropertyEntries(doc, baseUrl, category)) {
                addIfNew(entry);
            }
        }
        catch (IOException | SAXException | ParserConfigurationException exception) {
            throw new IllegalStateException(PARSE_FAILURE_MSG + xmlFile, exception);
        }
    }

    /**
     * Adds entries for the top-level general documentation pages.
     *
     * <p>Each remaining page is indexed per top-level {@code <section>},
     * using the section's full text content for keyword extraction so
     * page-internal headings are fully discoverable. Generic structural
     * section names (see {@link #GENERIC_SECTION_NAMES}) are disambiguated
     * by prefixing the page's own title.</p>
     *
     * @param xdocsDir the xdocs root directory
     */
    private void processGeneralPages(File xdocsDir) {
        final File[] xmlFiles = xdocsDir.listFiles(file -> {
            final String name = file.getName();
            return file.isFile()
                    && PLAIN_XML.matcher(name).find();
        });

        if (xmlFiles != null) {
            Arrays.sort(xmlFiles);
            for (File xmlFile : xmlFiles) {
                processGeneralPage(xmlFile);
            }
        }
    }

    /**
     * Parses a single general-documentation XDoc page and adds its
     * per-section entries to the index.
     *
     * <p>A parse failure here means the source XDoc itself is malformed, so
     * this fails the build instead of logging a warning and continuing.</p>
     *
     * @param xmlFile the XDoc source file to process
     * @throws IllegalStateException if {@code xmlFile} cannot be parsed
     */
    private void processGeneralPage(File xmlFile) {
        try {
            for (SearchIndexEntry entry : buildGeneralPageEntries(xmlFile)) {
                addIfNew(entry);
            }
        }
        catch (IOException | SAXException | ParserConfigurationException exception) {
            throw new IllegalStateException(PARSE_FAILURE_MSG + xmlFile, exception);
        }
    }

    /**
     * Builds the main search entry representing an entire check/filter document.
     *
     * @param doc      the parsed XDoc document
     * @param xmlFile  the source file
     * @param category category label for this file's entry
     * @param type     document type ("Check", "Filter", etc.)
     * @param baseUrl  the page url without anchor
     * @return an entry representing the document
     */
    private static SearchIndexEntry buildMainEntry(Document doc, File xmlFile,
                                                   String category, String type,
                                                   String baseUrl) {
        final Element body = requireBody(doc, xmlFile.toString());
        final NodeList sections = body.getElementsByTagName(SECTION);

        final String title = extractTitle(doc, xmlFile, sections);
        final String description = extractAggregateDescription(sections);
        final String keywords = extractAggregateKeywords(title, sections);

        return new SearchIndexEntry(title, baseUrl, category, type, description, keywords);
    }

    /**
     * Builds one search entry per top-level {@code <section>} in a general
     * documentation page, using each section's full text for keyword
     * extraction so that page-internal content is fully discoverable.
     *
     * <p>Generic structural section names (see {@link #GENERIC_SECTION_NAMES})
     * are disambiguated as {@code "<page title>: <section name>"} to avoid
     * collisions across pages (e.g. "Eclipse IDE: Debug" vs
     * "IntelliJ IDE: Debug").</p>
     *
     * @param xmlFile the XDoc source file to parse
     * @return list of entries, one per top-level section found
     * @throws ParserConfigurationException on XML parser setup failure
     * @throws SAXException on XML parse error
     * @throws IOException on file read failure
     */
    private static List<SearchIndexEntry> buildGeneralPageEntries(File xmlFile)
            throws ParserConfigurationException, SAXException, IOException {
        final List<SearchIndexEntry> results = new ArrayList<>();
        final Document doc = parseXml(xmlFile);
        final Element body = requireBody(doc, xmlFile.toString());
        final NodeList sections = body.getElementsByTagName(SECTION);
        final String pageUrl = resolvePageUrl(xmlFile, xmlFile.getParentFile());
        final String pageTitle = derivePageTitle(doc, xmlFile);

        if (sections.getLength() == 0) {
            final String fullText = WHITESPACE.matcher(body.getTextContent())
                    .replaceAll(SPACE).trim();
            final String description = extractFirstSentenceOrTruncated(fullText);
            final String keywords = extractKeywordsFromText(
                    pageTitle + SPACE + fullText);
            results.add(new SearchIndexEntry(
                    pageTitle, pageUrl, GENERAL, GENERAL, description, keywords));
        }
        else {
            for (int index = 0; index < sections.getLength(); index++) {
                final Element section = (Element) sections.item(index);
                if (body.equals(section.getParentNode())) {
                    final String sectionName = section.getAttribute(NAME_ATTR).trim();
                    if (!sectionName.isEmpty() && !CONTENT.equalsIgnoreCase(sectionName)) {

                        final String entryTitle = disambiguateTitle(sectionName, pageTitle);
                        final String anchor = doxiaAnchorFor(sectionName);
                        final String url = pageUrl + ANCHOR_SEPARATOR + anchor;

                        final String sectionText = WHITESPACE.matcher(section.getTextContent())
                                .replaceAll(SPACE).trim();
                        final String description = extractFirstSentenceOrTruncated(sectionText);
                        final String keywords = extractKeywordsFromText(
                                pageTitle + SPACE + sectionName + SPACE + sectionText);

                        results.add(new SearchIndexEntry(
                                entryTitle, url, GENERAL, GENERAL, description, keywords));
                    }
                }
            }
        }

        return results;
    }

    /**
     * Extracts per-example search entries from a check/filter document.
     *
     * <p>Both {@code -config} and {@code -code} example paragraphs are
     * indexed so users can find both the configuration snippet and the
     * corresponding Java code example independently in search results.</p>
     *
     * <p>Titles use the pattern {@code "<CheckName>: Example1 [config]"} and
     * {@code "<CheckName>: Example1 [code]"} to make the type immediately
     * visible in search result listings without needing to open the page.</p>
     *
     * <p>Confirmed XDoc template structure for the Examples subsection:</p>
     * <pre>
     *   &lt;p id="Example1-config"&gt;To configure the check...&lt;/p&gt;
     *   &lt;macro name="example"&gt;&lt;param name="type" value="config"/&gt;&lt;/macro&gt;
     *   &lt;p id="Example1-code"&gt;Example:&lt;/p&gt;
     *   &lt;macro name="example"&gt;&lt;param name="type" value="code"/&gt;&lt;/macro&gt;
     * </pre>
     *
     * @param doc      the parsed XDoc document
     * @param baseUrl  the page url without anchor
     * @param category category label
     * @return list of per-example entries (both config and code); empty if
     *         none found
     */
    private static List<SearchIndexEntry> extractExampleEntries(Document doc,
                                                                String baseUrl,
                                                                String category) {
        final List<SearchIndexEntry> exampleEntries = new ArrayList<>();
        final Element body = requireBody(doc, baseUrl);
        final NodeList sections = body.getElementsByTagName(SECTION);

        for (int sectionIdx = 0; sectionIdx < sections.getLength(); sectionIdx++) {
            final Element section = (Element) sections.item(sectionIdx);
            final String checkName = section.getAttribute(NAME_ATTR).trim();
            final Element examplesSubsection =
                    findSubsectionByPrefix(section, EXAMPLES_SUBSECTION);

            if (examplesSubsection == null) {
                continue;
            }

            final NodeList paragraphs = examplesSubsection.getElementsByTagName("p");

            for (int paragraphIndex = 0; paragraphIndex < paragraphs.getLength();
                 paragraphIndex++) {
                final Element paragraph = (Element) paragraphs.item(paragraphIndex);
                final SearchIndexEntry entry = buildExampleEntry(
                        paragraph, checkName, baseUrl, category);
                if (entry != null) {
                    exampleEntries.add(entry);
                }
            }
        }

        return exampleEntries;
    }

    /**
     * Builds a single example entry from a paragraph element.
     *
     * @param paragraph the paragraph element containing the example
     * @param checkName the name of the check
     * @param baseUrl the base URL for the page
     * @param category the category label
     * @return a SearchIndexEntry if the paragraph matches the example pattern,
     *         null otherwise
     */
    private static SearchIndexEntry buildExampleEntry(Element paragraph,
                                                       String checkName,
                                                       String baseUrl,
                                                       String category) {
        final String id = paragraph.getAttribute(ID_ATTR);
        final Matcher matcher = EXAMPLE_PARAGRAPH_ID.matcher(id);
        SearchIndexEntry result = null;

        if (matcher.matches()) {
            final String exampleLabel = matcher.group(1);
            final String exampleType = matcher.group(2);

            final String labelSuffix;
            if ("config".equals(exampleType)) {
                labelSuffix = EXAMPLE_LABEL_CONFIG;
            }
            else {
                labelSuffix = EXAMPLE_LABEL_CODE;
            }

            final String introText = WHITESPACE
                    .matcher(paragraph.getTextContent())
                    .replaceAll(SPACE).trim();

            final String title = checkName + TITLE_SEPARATOR
                    + exampleLabel + labelSuffix;
            final String url = baseUrl + ANCHOR_SEPARATOR + id;
            final String description =
                    truncate(introText, MAX_DESCRIPTION_LENGTH);
            final String keywords = extractKeywordsFromText(
                    checkName + SPACE + exampleLabel
                            + SPACE + exampleType + SPACE + introText);

            result = new SearchIndexEntry(
                    title, url, category, EXAMPLE_TYPE,
                    description, keywords);
        }

        return result;
    }

    /**
     * Extracts per-property search entries from a check/filter document.
     *
     * <p>Each row of the Properties table is indexed under the title
     * {@code "<CheckName>: <propertyName>"} and linked to the property's
     * own anchor on the page.</p>
     *
     * @param doc      the parsed XDoc document
     * @param baseUrl  the page url without anchor
     * @param category category label
     * @return list of per-property entries; empty if none found
     */
    private static List<SearchIndexEntry> extractPropertyEntries(Document doc,
                                                                 String baseUrl,
                                                                 String category) {
        final List<SearchIndexEntry> propertyEntries = new ArrayList<>();
        final Element body = requireBody(doc, baseUrl);
        final NodeList sections = body.getElementsByTagName(SECTION);

        for (int sectionIdx = 0; sectionIdx < sections.getLength(); sectionIdx++) {
            final Element section = (Element) sections.item(sectionIdx);
            final Element propertiesSubsection =
                    findSubsectionByPrefix(section, PROPERTIES_FRAGMENT);

            if (propertiesSubsection != null) {
                final String checkName = section.getAttribute(NAME_ATTR).trim();
                extractPropertiesFromRows(propertiesSubsection, checkName, baseUrl,
                        category, propertyEntries);
            }
        }

        return propertyEntries;
    }

    /**
     * Extracts property entries from table rows and adds them to the list.
     *
     * @param propertiesSubsection the properties subsection element
     * @param checkName the check name
     * @param baseUrl the page url without anchor
     * @param category category label
     * @param propertyEntries the list to add entries to
     */
    private static void extractPropertiesFromRows(Element propertiesSubsection,
                                                  String checkName,
                                                  String baseUrl,
                                                  String category,
                                                  List<SearchIndexEntry> propertyEntries) {
        final NodeList rows = propertiesSubsection.getElementsByTagName("tr");

        for (int rowIdx = 1; rowIdx < rows.getLength(); rowIdx++) {
            final Element row = (Element) rows.item(rowIdx);
            final NodeList cells = row.getElementsByTagName("td");
            if (cells.getLength() >= 2) {
                processPropertyRow(cells, checkName, baseUrl, category, propertyEntries);
            }
        }
    }

    /**
     * Processes a single property row and adds an entry if valid.
     *
     * @param cells the table cells
     * @param checkName the check name
     * @param baseUrl the page url without anchor
     * @param category category label
     * @param propertyEntries the list to add entries to
     */
    private static void processPropertyRow(NodeList cells,
                                           String checkName,
                                           String baseUrl,
                                           String category,
                                           List<SearchIndexEntry> propertyEntries) {
        final String propName = WHITESPACE
                .matcher(cells.item(0).getTextContent())
                .replaceAll(SPACE).trim();

        if (!propName.isEmpty()) {
            final String propDesc = WHITESPACE
                    .matcher(cells.item(1).getTextContent())
                    .replaceAll(SPACE).trim();

            final String title = checkName + TITLE_SEPARATOR + propName;
            final String url = baseUrl + ANCHOR_SEPARATOR + propName;
            final String description = truncate(propDesc, MAX_DESCRIPTION_LENGTH);
            final String keywords = extractKeywordsFromText(
                    checkName + SPACE + propName + SPACE + propDesc);

            propertyEntries.add(new SearchIndexEntry(
                    title, url, category, PROPERTY_TYPE,
                    description, keywords));
        }
    }

    /**
     * Adds an entry to the output list only if its URL has not been seen
     * before. This is a secondary guard that catches any duplicates that
     * slip through the primary filter (only processing plain {@code .xml}
     * files), e.g. if a check has the same example paragraph id repeated
     * across two sections.
     *
     * @param entry the entry to conditionally add
     */
    private void addIfNew(SearchIndexEntry entry) {
        if (seenUrls.add(entry.url())) {
            entries.add(entry);
        }
    }

    /**
     * Finds a subsection within a section whose lowercased name contains the
     * given fragment (e.g. "examples" or "propert" to match "Properties").
     *
     * @param section  the section to search
     * @param fragment lowercase fragment to match against the subsection name
     * @return the matching subsection element, or {@code null} if not found
     */
    private static Element findSubsectionByPrefix(Element section, String fragment) {
        final NodeList subsections = section.getElementsByTagName(SUBSECTION);
        Element result = null;
        for (int index = 0; index < subsections.getLength(); index++) {
            final Element sub = (Element) subsections.item(index);
            if (sub.getAttribute(NAME_ATTR).trim()
                    .toLowerCase(Locale.ROOT).contains(fragment)) {
                result = sub;
                break;
            }
        }
        return result;
    }

    /**
     * Parses the XML file into a Document with external entity resolution
     * disabled for security.
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
     * Returns the document's {@code <body>} element, failing fast if it is
     * absent. Every XDoc page processed by this generator is expected to
     * have one; its absence indicates a malformed source file that should
     * be fixed rather than silently skipped or producing an empty entry.
     *
     * @param doc        the parsed document
     * @param identifier file path or URL used to identify the source in the
     *                   error message
     * @return the body element
     * @throws IllegalStateException if {@code doc} has no {@code <body>} element
     */
    private static Element requireBody(Document doc, String identifier) {
        final NodeList bodies = doc.getElementsByTagName(BODY);
        if (bodies.getLength() == 0) {
            throw new IllegalStateException(
                    "XDoc file is missing a <body> element: " + identifier);
        }
        final Element body = (Element) bodies.item(0);
        if (body == null) {
            throw new IllegalStateException(
                    "XDoc file has a null <body> element: " + identifier);
        }
        return body;
    }

    /**
     * Extracts the document title from the {@code <title>} element, falling
     * back to the first non-empty, non-"Content" section name, and finally
     * to a capitalised version of the file name.
     *
     * @param doc      the document
     * @param xmlFile  the source file
     * @param sections the list of sections
     * @return the title string, never empty
     */
    private static String extractTitle(Document doc, File xmlFile, NodeList sections) {
        final NodeList titles = doc.getElementsByTagName(TITLE);
        String title = "";
        if (titles.getLength() > 0) {
            title = titles.item(0).getTextContent().trim();
        }

        if ((title.isEmpty() || CONTENT.equalsIgnoreCase(title))
                && sections.getLength() > 0) {
            final String firstSection =
                    ((Element) sections.item(0)).getAttribute(NAME_ATTR).trim();
            if (!firstSection.isEmpty() && !CONTENT.equalsIgnoreCase(firstSection)) {
                title = firstSection;
            }
        }

        if (title.isEmpty() || CONTENT.equalsIgnoreCase(title)) {
            final String name =
                    xmlFile.getName().replaceFirst(DOC_EXTENSION.pattern(), "");
            title = capitalise(name.replace('_', ' '));
        }
        return title;
    }

    /**
     * Aggregates description from sections, taking the first non-empty
     * Description subsection found across all sections in the document.
     *
     * @param sections list of sections
     * @return description string, possibly empty
     */
    private static String extractAggregateDescription(NodeList sections) {
        String description = "";
        for (int index = 0; index < sections.getLength(); index++) {
            description = extractDescription((Element) sections.item(index));
            if (!description.isEmpty()) {
                break;
            }
        }
        return description;
    }

    /**
     * Aggregates keywords from sections using all section text so that the
     * main check entry is discoverable by any term in the document.
     *
     * @param title    the document title
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
        final Element sub = findSubsectionByPrefix(section, DESCRIPTION);
        String result = "";
        if (sub != null) {
            final String text = WHITESPACE.matcher(sub.getTextContent())
                    .replaceAll(SPACE).trim();
            result = extractFirstSentenceOrTruncated(text);
        }
        return result;
    }

    /**
     * Derives a fallback page title from the document's {@code <title>}
     * element or, failing that, from the filename.
     *
     * @param doc     the parsed document
     * @param xmlFile the source file
     * @return a non-empty title string
     */
    private static String derivePageTitle(Document doc, File xmlFile) {
        final NodeList titles = doc.getElementsByTagName(TITLE);
        String title = "";
        if (titles.getLength() > 0) {
            title = titles.item(0).getTextContent().trim();
        }
        if (title.isEmpty()) {
            final String name =
                    xmlFile.getName().replaceFirst(DOC_EXTENSION.pattern(), "");
            title = capitalise(name.replace('_', ' '));
        }
        return title;
    }

    /**
     * Disambiguates a section title when it is a generic, structurally
     * repeated header (see {@link #GENERIC_SECTION_NAMES}).
     * Non-generic section names are returned unchanged.
     *
     * @param sectionName the raw section name
     * @param pageTitle   the owning page's own title
     * @return either {@code sectionName} unchanged, or
     *         {@code "<pageTitle>: <sectionName>"} if generic
     */
    private static String disambiguateTitle(String sectionName, String pageTitle) {
        final String result;
        if (GENERIC_SECTION_NAMES.contains(sectionName.toLowerCase(Locale.ROOT))) {
            result = pageTitle + TITLE_SEPARATOR + sectionName;
        }
        else {
            result = sectionName;
        }
        return result;
    }

    /**
     * Converts a Doxia {@code <section name="...">} value into the anchor id
     * Doxia generates for it in the rendered HTML by replacing runs of
     * whitespace with single underscores.
     *
     * @param sectionName the raw {@code name} attribute value
     * @return the anchor id Doxia would render for this section name
     */
    private static String doxiaAnchorFor(String sectionName) {
        return WHITESPACE.matcher(sectionName.trim()).replaceAll("_");
    }

    /**
     * Returns the first sentence of the given text (up to and including the
     * first period), or the text truncated to {@link #MAX_DESCRIPTION_LENGTH}
     * with an ellipsis if no period is found within range.
     *
     * @param text the source text, already whitespace-normalised
     * @return first sentence or truncated text
     */
    private static String extractFirstSentenceOrTruncated(String text) {
        final String result;
        final int dot = text.indexOf('.');
        if (dot > 0) {
            result = text.substring(0, dot + 1).trim();
        }
        else {
            result = truncate(text, MAX_DESCRIPTION_LENGTH);
        }
        return result;
    }

    /**
     * Truncates text to the given max length, appending an ellipsis if
     * truncation occurred.
     *
     * @param text      the text to truncate
     * @param maxLength maximum length before truncation
     * @return original text if short enough, otherwise truncated with ellipsis
     */
    private static String truncate(String text, int maxLength) {
        final String result;
        if (text.length() > maxLength) {
            result = text.substring(0, maxLength) + ELLIPSIS;
        }
        else {
            result = text;
        }
        return result;
    }

    /**
     * Builds the root-relative URL for an XDoc file, without any anchor.
     * Always uses forward slashes regardless of OS.
     *
     * @param xmlFile  the source XDoc file
     * @param xdocsDir the xdocs root directory
     * @return root-relative URL string with no anchor
     */
    private static String buildUrl(File xmlFile, File xdocsDir) {
        return xdocsDir.toPath()
                .relativize(xmlFile.toPath())
                .toString()
                .replace(File.separatorChar, '/')
                .replaceFirst(DOC_EXTENSION.pattern(), ".html");
    }

    /**
     * Resolves the correct URL for a general page file. For {@code config_<category>.xml} files
     * that redirect to check category pages, maps to {@code checks/<category>/index.html} instead
     * of the file path.
     *
     * @param xmlFile  the source XDoc file
     * @param xdocsDir the xdocs root directory
     * @return the resolved URL
     */
    private static String resolvePageUrl(File xmlFile, File xdocsDir) {
        String url = buildUrl(xmlFile, xdocsDir);
        final Matcher matcher = CONFIG_CATEGORY.matcher(xmlFile.getName());
        if (matcher.find()) {
            final String category = matcher.group(1);
            if (CHECKS_CATEGORY_DISPLAY_NAMES.containsKey(category)) {
                url = CHECKS + PATH_SEPARATOR + category + PATH_SEPARATOR + INDEX_HTML;
            }
            else if (FILTERS_DIR.equals(category) || FILEFILTERS_DIR.equals(category)) {
                url = category + PATH_SEPARATOR + INDEX_HTML;
            }
        }
        return url;
    }

    /**
     * Extracts keywords from free-form text by splitting on non-word
     * characters and filtering short and stop words.
     *
     * @param text input text
     * @return comma-separated keyword string (up to {@link #MAX_KEYWORDS} words)
     */
    private static String extractKeywordsFromText(String text) {
        String result = "";
        if (text != null && !text.isEmpty()) {
            result = NON_ALPHANUMERIC.splitAsStream(text.toLowerCase(Locale.ROOT))
                    .filter(word -> {
                        return word.length() >= MIN_WORD_LENGTH
                                && !STOP_WORDS.contains(word);
                    })
                    .distinct()
                    .limit(MAX_KEYWORDS)
                    .collect(Collectors.joining(COMMA_STR));
        }
        return result;
    }

    /**
     * Writes all index entries to the output file.
     *
     * @param indexEntries the list of entries to serialise
     * @param outputFilePath the full path to the output file
     * @throws IOException on file write failure
     */
    private static void writeJson(List<SearchIndexEntry> indexEntries, Path outputFilePath)
            throws IOException {

        final Path outputPath = outputFilePath.getParent();
        if (outputPath != null) {
            Files.createDirectories(outputPath);
        }

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(
                outputFilePath))) {
            writer.println("[");

            final int size = indexEntries.size();
            for (int index = 0; index < size; index++) {
                final String comma;
                if (index < size - 1) {
                    comma = COMMA_STR;
                }
                else {
                    comma = "";
                }
                writer.println("  " + indexEntries.get(index).toJson() + comma);
            }
            writer.println("]");
        }
    }

    /**
     * Capitalises the first character of a string.
     *
     * @param input the string to capitalise
     * @return string with first character uppercased, or input unchanged if
     *         empty
     */
    private static String capitalise(String input) {
        String result = input;
        if (input != null && !input.isEmpty()) {
            result = Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
        return result;
    }

}
