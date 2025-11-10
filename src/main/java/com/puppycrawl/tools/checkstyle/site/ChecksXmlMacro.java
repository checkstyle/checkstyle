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

package com.puppycrawl.tools.checkstyle.site;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.api.DetailNode;

/**
 * Macro to generate table rows for all Checkstyle modules.
 * Includes every Check.java file that has a Javadoc.
 * Uses href path structure based on src/site/xdoc/checks.
 */
@Component(role = Macro.class, hint = "checks")
public class ChecksXmlMacro extends AbstractMacro {

    /** Path component for source directory. */
    private static final String SRC = "src";

    /** Path component for checks directory. */
    private static final String CHECKS = "checks";

    /** Root path for Java check files. */
    private static final Path JAVA_CHECKS_ROOT = Path.of(
            SRC, "main", "java", "com", "puppycrawl", "tools", "checkstyle", CHECKS);

    /** Root path for site check XML files. */
    private static final Path SITE_CHECKS_ROOT = Path.of(SRC, "site", "xdoc", CHECKS);

    /** Maximum line width considering indentation. */
    private static final int MAX_LINE_WIDTH = 86;

    /** XML file extension. */
    private static final String XML_EXTENSION = ".xml";

    /** HTML file extension. */
    private static final String HTML_EXTENSION = ".html";

    /** TD opening tag. */
    private static final String TD_TAG = "<td>";

    /** TD closing tag. */
    private static final String TD_CLOSE_TAG = "</td>";

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final Map<String, String> xmlHrefMap = buildXmlHtmlMap();
        final Map<String, CheckInfo> infos = new TreeMap<>();

        processCheckFiles(infos, xmlHrefMap);

        final String ind10 = ModuleJavadocParsingUtil.INDENT_LEVEL_10;
        final String ind12 = ModuleJavadocParsingUtil.INDENT_LEVEL_12;
        final String ind14 = ModuleJavadocParsingUtil.INDENT_LEVEL_14;

        final StringBuilder normalRows = new StringBuilder(4096);
        final StringBuilder holderRows = new StringBuilder(512);

        buildTableRows(infos, normalRows, holderRows, ind10, ind12, ind14);

        sink.rawText(normalRows.toString());

        if (holderRows.length() > 0) {
            appendHolderSection(sink, holderRows);
        }
    }

    /**
     * Scans Java sources and populates info map with modules having Javadoc.
     *
     * @param infos map of collected module info
     * @param xmlHrefMap map of XML-to-HTML hrefs
     * @throws MacroExecutionException if file walk fails
     */
    private static void processCheckFiles(Map<String, CheckInfo> infos,
                                          Map<String, String> xmlHrefMap)
            throws MacroExecutionException {
        try (Stream<Path> paths = Files.walk(JAVA_CHECKS_ROOT)) {
            paths.filter(ChecksXmlMacro::isCheckOrHolderFile)
                    .forEach(path -> {
                        processCheckFile(path, infos, xmlHrefMap);
                    });
        }
        catch (IOException | IllegalStateException exception) {
            throw new MacroExecutionException("Failed to discover checks", exception);
        }
    }

    /**
     * Checks if a path is a Check or Holder Java file.
     *
     * @param path the path to check
     * @return true if the path is a Check or Holder file, false otherwise
     */
    private static boolean isCheckOrHolderFile(Path path) {
        final boolean result;
        if (Files.isRegularFile(path)) {
            final Path fileName = path.getFileName();
            if (fileName == null) {
                result = false;
            }
            else {
                final String name = fileName.toString();
                result = name.endsWith("Check.java") || name.endsWith("Holder.java");
            }
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Processes a single check class file and extracts metadata.
     *
     * @param path the check class file
     * @param infos map of results
     * @param xmlHrefMap map of XML hrefs
     * @throws IllegalArgumentException if macro execution fails
     */
    private static void processCheckFile(Path path, Map<String, CheckInfo> infos,
                                         Map<String, String> xmlHrefMap) {
        try {
            final String moduleName =
                    com.puppycrawl.tools.checkstyle.utils.CommonUtil
                            .getFileNameWithoutExtension(path.toString());
            final boolean isHolder = moduleName.endsWith("Holder");
            final String simpleName;
            if (isHolder) {
                simpleName = moduleName;
            }
            else {
                simpleName = moduleName.substring(0, moduleName.length() - "Check".length());
            }
            final DetailNode javadoc = SiteUtil.getModuleJavadoc(moduleName, path);
            if (javadoc != null) {
                processJavadoc(javadoc, path, simpleName,
                        isHolder, infos, xmlHrefMap);
            }
        }
        catch (MacroExecutionException exceptionThrown) {
            throw new IllegalArgumentException(exceptionThrown);
        }
    }

    /**
     * Parses module Javadoc and stores entry in the info map.
     *
     * @param javadoc parsed Javadoc
     * @param path Java source path
     * @param simpleName simple class name
     * @param isHolder whether module is a holder
     * @param infos output map
     * @param xmlHrefMap href mapping
     */
    private static void processJavadoc(DetailNode javadoc, Path path, String simpleName,
                                       boolean isHolder,
                                       Map<String, CheckInfo> infos,
                                       Map<String, String> xmlHrefMap) {
        final String desc = getModuleDescriptionSafe(javadoc);
        if (desc != null && !desc.isEmpty()) {
            final String summary = sanitizeAndFirstSentence(desc);
            final String category = extractCategoryFromJavaPath(path);
            final String href = resolveHref(xmlHrefMap, category, simpleName);
            infos.put(simpleName,
                    new CheckInfo(simpleName, href, summary, isHolder));
        }
    }

    /**
     * Retrieves Javadoc description node safely.
     *
     * @param javadoc DetailNode root
     * @return module description or null
     */
    private static String getModuleDescriptionSafe(DetailNode javadoc) {
        String result = null;
        if (javadoc != null) {
            try {
                if (ModuleJavadocParsingUtil
                        .getModuleSinceVersionTagStartNode(javadoc) != null) {
                    result = ModuleJavadocParsingUtil.getModuleDescription(javadoc);
                }
            }
            catch (IllegalStateException exception) {
                result = null;
            }
        }
        return result;
    }

    /**
     * Builds HTML rows for normal and holder check modules.
     *
     * @param infos map of collected module info
     * @param normalRows StringBuilder for normal check rows
     * @param holderRows StringBuilder for holder check rows
     * @param ind10 indentation level 10
     * @param ind12 indentation level 12
     * @param ind14 indentation level 14
     */
    private static void buildTableRows(Map<String, CheckInfo> infos,
                                       StringBuilder normalRows,
                                       StringBuilder holderRows,
                                       String ind10, String ind12, String ind14) {
        for (CheckInfo info : infos.values()) {
            final String row = buildTableRow(info, ind10, ind12, ind14);
            if (info.isHolder) {
                holderRows.append(row);
            }
            else {
                normalRows.append(row);
            }
        }
        removeLeadingNewline(normalRows);
        removeLeadingNewline(holderRows);
    }

    /**
     * Builds a single table row for a check module.
     *
     * @param info check module information
     * @param ind10 indentation level 10
     * @param ind12 indentation level 12
     * @param ind14 indentation level 14
     * @return the HTML table row as a string
     */
    private static String buildTableRow(CheckInfo info,
                                        String ind10, String ind12, String ind14) {
        return ind10 + "<tr>"
                + ind12 + TD_TAG
                + ind14
                + "<a href=\""
                + info.link
                + "\">"
                + info.simpleName + "</a>"
                + ind12 + TD_CLOSE_TAG
                + ind12 + TD_TAG
                + ind14 + wrapSummary(info.summary)
                + ind12 + TD_CLOSE_TAG
                + ind10 + "</tr>";
    }

    /**
     * Removes leading newline characters from a StringBuilder.
     *
     * @param builder the StringBuilder to process
     */
    private static void removeLeadingNewline(StringBuilder builder) {
        while (builder.length() > 0 && Character.isWhitespace(builder.charAt(0))) {
            builder.delete(0, 1);
        }
    }

    /**
     * Appends the Holder Checks HTML section.
     *
     * @param sink the output sink
     * @param holderRows the holder rows content
     */
    private static void appendHolderSection(Sink sink, StringBuilder holderRows) {
        final String holderSection = buildHolderSectionHtml(holderRows);
        sink.rawText(holderSection);
    }

    /**
     * Builds the HTML for the Holder Checks section.
     *
     * @param holderRows the holder rows content
     * @return the complete HTML section as a string
     */
    private static String buildHolderSectionHtml(StringBuilder holderRows) {
        return ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + "</table>"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_6
                + "</div>"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_4
                + "</section>"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_4
                + "<section name=\"Holder Checks\">"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_6
                + "<p>"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + "These checks aren't normal checks and are usually"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + "associated with a specialized filter to gather"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + "information the filter can't get on its own."
                + ModuleJavadocParsingUtil.INDENT_LEVEL_6
                + "</p>"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_6
                + "<div class=\"wrapper\">"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + "<table>"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_10
                + holderRows;
    }

    /**
     * Builds map of XML file names to HTML documentation paths.
     *
     * @return map of lowercase check names to hrefs
     */
    private static Map<String, String> buildXmlHtmlMap() {
        final Map<String, String> map = new TreeMap<>();
        if (Files.exists(SITE_CHECKS_ROOT)) {
            try (Stream<Path> paths = Files.walk(SITE_CHECKS_ROOT)) {
                paths.filter(ChecksXmlMacro::isValidXmlFile)
                        .forEach(path -> {
                            addXmlHtmlMapping(path, map);
                        });
            }
            catch (IOException ignored) {
                // ignore
            }
        }
        return map;
    }

    /**
     * Checks if a path is a valid XML file for processing.
     *
     * @param path the path to check
     * @return true if the path is a valid XML file, false otherwise
     */
    private static boolean isValidXmlFile(Path path) {
        final boolean result;
        if (Files.isRegularFile(path)
                && path.toString().endsWith(XML_EXTENSION)) {
            final Path fileName = path.getFileName();
            result = fileName != null
                    && !"index".concat(XML_EXTENSION)
                    .equalsIgnoreCase(fileName.toString());
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Adds XML-to-HTML mapping entry to map.
     *
     * @param path the XML file path
     * @param map the mapping to update
     */
    private static void addXmlHtmlMapping(Path path, Map<String, String> map) {
        final Path fileName = path.getFileName();
        if (fileName != null) {
            final String fileNameString = fileName.toString();
            final int extensionLength = 4;
            final String base = fileNameString.substring(0,
                            fileNameString.length() - extensionLength)
                    .toLowerCase(Locale.ROOT);
            final Path relativePath = SITE_CHECKS_ROOT.relativize(path);
            final String relativePathString = relativePath.toString();
            final String rel = relativePathString
                    .replace('\\', '/')
                    .replace(XML_EXTENSION, HTML_EXTENSION);
            map.put(base, CHECKS + "/" + rel);
        }
    }

    /**
     * Resolves the href for a given check module.
     *
     * @param xmlMap map of XML file names to HTML paths
     * @param category the category of the check
     * @param simpleName simple name of the check
     * @return the resolved href for the check
     */
    private static String resolveHref(Map<String, String> xmlMap, String category,
                                      String simpleName) {
        final String lower = simpleName.toLowerCase(Locale.ROOT);
        final String href = xmlMap.get(lower);
        final String result;
        if (href != null) {
            result = href + "#" + simpleName;
        }
        else {
            result = String.format(Locale.ROOT, "%s/%s/%s.html#%s",
                    CHECKS, category, lower, simpleName);
        }
        return result;
    }

    /**
     * Extracts category path from a Java file path.
     *
     * @param javaPath the Java source file path
     * @return the category path extracted from the Java path
     */
    private static String extractCategoryFromJavaPath(Path javaPath) {
        final Path rel = JAVA_CHECKS_ROOT.relativize(javaPath);
        final Path parent = rel.getParent();
        final String result;
        if (parent == null) {
            result = "";
        }
        else {
            result = parent.toString().replace('\\', '/');
        }
        return result;
    }

    /**
     * Sanitizes HTML and extracts first sentence.
     *
     * @param html the HTML string to process
     * @return the sanitized first sentence
     */
    private static String sanitizeAndFirstSentence(String html) {
        final String result;
        if (html == null || html.isEmpty()) {
            result = "";
        }
        else {
            String cleaned = html.replaceAll("<a[^>]*>([^<]*)</a>", "$1");
            cleaned = cleaned.replaceAll("(?i)</?(?:p|div|span|strong|em)[^>]*>", "");
            cleaned = cleaned.replaceAll("\\s+", " ").trim();
            cleaned = cleaned.replaceAll("&(?![a-zA-Z#0-9]+;)", "&amp;");
            result = extractFirstSentence(cleaned);
        }
        return result;
    }

    /**
     * Extracts first sentence from plain text.
     *
     * @param text the text to process
     * @return the first sentence extracted from the text
     */
    private static String extractFirstSentence(String text) {
        String result = "";
        if (text != null && !text.isEmpty()) {
            int end = -1;
            for (int index = 0; index < text.length(); index++) {
                if (text.charAt(index) == '.'
                        && (index == text.length() - 1
                        || Character.isWhitespace(text.charAt(index + 1))
                        || text.charAt(index + 1) == '<')) {
                    end = index;
                    break;
                }
            }
            if (end == -1) {
                result = text.trim();
            }
            else {
                result = text.substring(0, end + 1).trim();
            }
        }
        return result;
    }

    /**
     * Wraps long summaries to avoid exceeding line width.
     *
     * @param text the text to wrap
     * @return the wrapped text
     */
    private static String wrapSummary(String text) {
        final String result;
        if (text == null || text.isEmpty()) {
            result = "";
        }
        else if (text.length() <= MAX_LINE_WIDTH) {
            result = text;
        }
        else {
            result = performWrapping(text);
        }
        return result;
    }

    /**
     * Performs wrapping of summary text.
     *
     * @param text the text to wrap
     * @return the wrapped text
     */
    private static String performWrapping(String text) {
        final int textLength = text.length();
        final StringBuilder result = new StringBuilder(textLength + 100);
        int pos = 0;
        final String indent = ModuleJavadocParsingUtil.INDENT_LEVEL_14;
        boolean firstLine = true;

        while (pos < textLength) {
            final int end = Math.min(pos + MAX_LINE_WIDTH, textLength);
            if (end >= textLength) {
                if (!firstLine) {
                    result.append(indent);
                }
                result.append(text.substring(pos));
                break;
            }
            int breakPos = text.lastIndexOf(' ', end);
            if (breakPos <= pos) {
                breakPos = end;
            }
            if (!firstLine) {
                result.append(indent);
            }
            result.append(text, pos, breakPos);
            pos = breakPos + 1;
            firstLine = false;
        }
        return result.toString();
    }

    /**
     * Data holder for each Check module entry.
     */
    private static final class CheckInfo {
        /** Simple name of the check. */
        private final String simpleName;
        /** Documentation link. */
        private final String link;
        /** Short summary text. */
        private final String summary;
        /** Whether the module is a holder type. */
        private final boolean isHolder;

        /**
         * Constructs an info record.
         *
         * @param simpleName check simple name
         * @param link documentation link
         * @param summary module summary
         * @param isHolder whether holder
         * @noinspection unused
         * @noinspectionreason moduleName parameter is required for consistent API
         *      but not used in this implementation
         */
        private CheckInfo(String simpleName, String link,
                          String summary, boolean isHolder) {
            this.simpleName = simpleName;
            this.link = link;
            this.summary = summary;
            this.isHolder = isHolder;
        }
    }
}
