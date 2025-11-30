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
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Macro to generate table rows for all Checkstyle modules.
 * Includes every Check.java file that has a Javadoc.
 * Uses href path structure based on src/site/xdoc/checks.
 * Usage:
 * <pre>
 * &lt;macro name="allCheckSummaries"/&gt;
 * </pre>
 *
 * <p>Supports optional "package" parameter to filter checks by package.
 * When package parameter is provided, only checks from that package are included.
 * Usage:
 * <pre>
 * &lt;macro name="allCheckSummaries"&gt;
 *   &lt;param name="package" value="annotation"/&gt;
 * &lt;/macro&gt;
 * </pre>
 */
@Component(role = Macro.class, hint = "allCheckSummaries")
public class AllCheckSummaries extends AbstractMacro {

    /** Initial capacity for StringBuilder in wrapSummary method. */
    public static final int CAPACITY = 3000;

    /**
     * Matches common HTML tags such as paragraph, div, span, strong, and em.
     * Used to remove formatting tags from the Javadoc HTML content.
     * Note: anchor tags are preserved.
     */
    private static final Pattern TAG_PATTERN =
            Pattern.compile("(?i)</?(?:p|div|span|strong|em)[^>]*>");

    /** Whitespace regex pattern string. */
    private static final String WHITESPACE_REGEX = "\\s+";

    /**
     * Matches one or more whitespace characters.
     * Used to normalize spacing in sanitized text.
     */
    private static final Pattern SPACE_PATTERN = Pattern.compile(WHITESPACE_REGEX);

    /**
     * Matches '&amp;' characters that are not part of a valid HTML entity.
     */
    private static final Pattern AMP_PATTERN = Pattern.compile("&(?![a-zA-Z#0-9]+;)");

    /**
     * Pattern to match href attributes in anchor tags.
     * Captures the URL within the href attribute, including any newlines.
     * DOTALL flag allows . to match newlines, making the pattern work across line breaks.
     */
    private static final Pattern HREF_PATTERN =
            Pattern.compile("href\\s*=\\s*['\"]([^'\"]*)['\"]",
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    /** Path component for source directory. */
    private static final String SRC = "src";

    /** Path component for checks directory. */
    private static final String CHECKS = "checks";

    /** Root path for Java check files. */
    private static final Path JAVA_CHECKS_ROOT = Path.of(
            SRC, "main", "java", "com", "puppycrawl", "tools", "checkstyle", CHECKS);

    /** Root path for site check XML files. */
    private static final Path SITE_CHECKS_ROOT = Path.of(SRC, "site", "xdoc", CHECKS);

    /** XML file extension. */
    private static final String XML_EXTENSION = ".xml";

    /** HTML file extension. */
    private static final String HTML_EXTENSION = ".html";

    /** TD opening tag. */
    private static final String TD_TAG = "<td>";

    /** TD closing tag. */
    private static final String TD_CLOSE_TAG = "</td>";

    /** Package name for miscellaneous checks. */
    private static final String MISC_PACKAGE = "misc";

    /** Package name for annotation checks. */
    private static final String ANNOTATION_PACKAGE = "annotation";

    /** HTML table closing tag. */
    private static final String TABLE_CLOSE_TAG = "</table>";

    /** HTML div closing tag. */
    private static final String DIV_CLOSE_TAG = "</div>";

    /** HTML section closing tag. */
    private static final String SECTION_CLOSE_TAG = "</section>";

    /** HTML div wrapper opening tag. */
    private static final String DIV_WRAPPER_TAG = "<div class=\"wrapper\">";

    /** HTML table opening tag. */
    private static final String TABLE_OPEN_TAG = "<table>";

    /** HTML anchor separator. */
    private static final String ANCHOR_SEPARATOR = "#";

    /** Regex replacement for first capture group. */
    private static final String FIRST_CAPTURE_GROUP = "$1";

    /** Maximum line width for complete line including indentation. */
    private static final int MAX_LINE_WIDTH_TOTAL = 100;

    /** Indentation width for INDENT_LEVEL_14 (14 spaces). */
    private static final int INDENT_WIDTH = 14;

    /** Maximum content width excluding indentation. */
    private static final int MAX_CONTENT_WIDTH = MAX_LINE_WIDTH_TOTAL - INDENT_WIDTH;

    /** Closing anchor tag. */
    private static final String CLOSING_ANCHOR_TAG = "</a>";

    /** Pattern to match trailing spaces before closing code tags. */
    private static final Pattern CODE_SPACE_PATTERN = Pattern.compile(WHITESPACE_REGEX
            + "(" + CLOSING_ANCHOR_TAG.substring(0, 2) + "code>)");

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String packageFilter = (String) request.getParameter("package");

        final Map<String, String> xmlHrefMap = buildXmlHtmlMap();
        final Map<String, CheckInfo> infos = new TreeMap<>();

        processCheckFiles(infos, xmlHrefMap, packageFilter);

        final StringBuilder normalRows = new StringBuilder(4096);
        final StringBuilder holderRows = new StringBuilder(512);

        buildTableRows(infos, normalRows, holderRows);

        sink.rawText(normalRows.toString());
        if (packageFilter == null && !holderRows.isEmpty()) {
            appendHolderSection(sink, holderRows);
        }
        else if (packageFilter != null && !holderRows.isEmpty()) {
            appendFilteredHolderSection(sink, holderRows, packageFilter);
        }
    }

    /**
     * Scans Java sources and populates info map with modules having Javadoc.
     *
     * @param infos map of collected module info
     * @param xmlHrefMap map of XML-to-HTML hrefs
     * @param packageFilter optional package to filter by, null for all
     * @throws MacroExecutionException if file walk fails
     */
    private static void processCheckFiles(Map<String, CheckInfo> infos,
                                          Map<String, String> xmlHrefMap,
                                          String packageFilter)
            throws MacroExecutionException {
        try {
            final List<Path> checkFiles = new ArrayList<>();
            Files.walkFileTree(JAVA_CHECKS_ROOT, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (isCheckOrHolderFile(file)) {
                        checkFiles.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            checkFiles.forEach(path -> processCheckFile(path, infos, xmlHrefMap, packageFilter));
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
        final Path fileName = path.getFileName();
        return fileName != null
                && (fileName.toString().endsWith("Check.java")
                || fileName.toString().endsWith("Holder.java"))
                && Files.isRegularFile(path);
    }

    /**
     * Checks if a module is a holder type.
     *
     * @param moduleName the module name
     * @return true if the module is a holder, false otherwise
     */
    private static boolean isHolder(String moduleName) {
        return moduleName.endsWith("Holder");
    }

    /**
     * Processes a single check class file and extracts metadata.
     *
     * @param path the check class file
     * @param infos map of results
     * @param xmlHrefMap map of XML hrefs
     * @param packageFilter optional package to filter by, null for all
     * @throws IllegalArgumentException if macro execution fails
     */
    private static void processCheckFile(Path path, Map<String, CheckInfo> infos,
                                         Map<String, String> xmlHrefMap,
                                         String packageFilter) {
        try {
            final String moduleName = CommonUtil.getFileNameWithoutExtension(path.toString());
            final DetailNode javadoc = SiteUtil.getModuleJavadoc(moduleName, path);
            if (javadoc != null) {
                String description = getDescriptionIfPresent(javadoc);
                if (description != null) {
                    description = sanitizeAnchorUrls(description);

                    final String[] moduleInfo = determineModuleInfo(path, moduleName);
                    final String packageName = moduleInfo[1];
                    final String simpleName = moduleInfo[0];
                    if (packageFilter == null || packageFilter.equals(packageName)) {
                        final String summary = sanitizeAndFirstSentence(description);
                        final String href = resolveHref(xmlHrefMap, packageName, simpleName,
                                packageFilter);
                        infos.put(simpleName, new CheckInfo(simpleName, href, summary));
                    }
                }
            }

        }
        catch (MacroExecutionException exceptionThrown) {
            throw new IllegalArgumentException(exceptionThrown);
        }
    }

    /**
     * Determines the simple name and package name for a check module.
     *
     * @param path the check class file
     * @param moduleName the full module name
     * @return array with [simpleName, packageName]
     */
    private static String[] determineModuleInfo(Path path, String moduleName) {
        String packageName = extractCategoryFromJavaPath(path);

        if ("indentation".equals(packageName)) {
            packageName = MISC_PACKAGE;
        }
        if (isHolder(moduleName)) {
            packageName = ANNOTATION_PACKAGE;
        }
        final String simpleName;
        if (isHolder(moduleName)) {
            simpleName = moduleName;
        }
        else {
            simpleName = moduleName.substring(0, moduleName.length() - "Check".length());
        }

        return new String[] {simpleName, packageName};
    }

    /**
     * Returns the module description if present and non-empty.
     *
     * @param javadoc the parsed Javadoc node
     * @return the description text, or {@code null} if not present
     */
    @Nullable
    private static String getDescriptionIfPresent(DetailNode javadoc) {
        String result = null;
        if (javadoc != null) {
            try {
                if (ModuleJavadocParsingUtil
                        .getModuleSinceVersionTagStartNode(javadoc) != null) {
                    final String desc = ModuleJavadocParsingUtil.getModuleDescription(javadoc);
                    if (!desc.isEmpty()) {
                        result = desc;
                    }
                }
            }
            catch (IllegalStateException exception) {
                result = null;
            }
        }
        return result;
    }

    /**
     * Builds HTML rows for both normal and holder check modules.
     *
     * @param infos map of collected module info
     * @param normalRows builder for normal check rows
     * @param holderRows builder for holder check rows
     */
    private static void buildTableRows(Map<String, CheckInfo> infos,
                                       StringBuilder normalRows,
                                       StringBuilder holderRows) {
        for (CheckInfo info : infos.values()) {
            final String row = buildTableRow(info);
            if (isHolder(info.simpleName)) {
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
     * @return the HTML table row as a string
     */
    private static String buildTableRow(CheckInfo info) {
        final String ind10 = ModuleJavadocParsingUtil.INDENT_LEVEL_10;
        final String ind12 = ModuleJavadocParsingUtil.INDENT_LEVEL_12;
        final String ind14 = ModuleJavadocParsingUtil.INDENT_LEVEL_14;
        final String ind16 = ModuleJavadocParsingUtil.INDENT_LEVEL_16;

        final String cleanSummary = sanitizeAnchorUrls(info.summary);

        return ind10 + "<tr>"
                + ind12 + TD_TAG
                + ind14
                + "<a href=\""
                + info.link
                + "\">"
                + ind16 + info.simpleName
                + ind14 + CLOSING_ANCHOR_TAG
                + ind12 + TD_CLOSE_TAG
                + ind12 + TD_TAG
                + ind14 + wrapSummary(cleanSummary)
                + ind12 + TD_CLOSE_TAG
                + ind10 + "</tr>";
    }

    /**
     * Removes leading newline characters from a StringBuilder.
     *
     * @param builder the StringBuilder to process
     */
    private static void removeLeadingNewline(StringBuilder builder) {
        while (!builder.isEmpty() && Character.isWhitespace(builder.charAt(0))) {
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
        final String holderSection = ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + TABLE_CLOSE_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_6
                + DIV_CLOSE_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_4
                + SECTION_CLOSE_TAG
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
                + DIV_WRAPPER_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + TABLE_OPEN_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_10
                + holderRows;
        sink.rawText(holderSection);
    }

    /**
     * Appends the filtered Holder Checks section for package views.
     *
     * @param sink the output sink
     * @param holderRows the holder rows content
     * @param packageName the package name
     */
    private static void appendFilteredHolderSection(Sink sink, StringBuilder holderRows,
                                                    String packageName) {
        final String packageTitle = getPackageDisplayName(packageName);
        final String holderSection = ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + TABLE_CLOSE_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_6
                + DIV_CLOSE_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_4
                + SECTION_CLOSE_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_4
                + "<section name=\"" + packageTitle + " Holder Checks\">"
                + ModuleJavadocParsingUtil.INDENT_LEVEL_6
                + DIV_WRAPPER_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_8
                + TABLE_OPEN_TAG
                + ModuleJavadocParsingUtil.INDENT_LEVEL_10
                + holderRows;
        sink.rawText(holderSection);
    }

    /**
     * Get display name for package (capitalize first letter).
     *
     * @param packageName the package name
     * @return the capitalized package name
     */
    private static String getPackageDisplayName(String packageName) {
        final String result;
        if (packageName == null || packageName.isEmpty()) {
            result = packageName;
        }
        else {
            result = packageName.substring(0, 1).toUpperCase(Locale.ENGLISH)
                    + packageName.substring(1);
        }
        return result;
    }

    /**
     * Builds map of XML file names to HTML documentation paths.
     *
     * @return map of lowercase check names to hrefs
     */
    private static Map<String, String> buildXmlHtmlMap() {
        final Map<String, String> map = new TreeMap<>();
        if (Files.exists(SITE_CHECKS_ROOT)) {
            try {
                final List<Path> xmlFiles = new ArrayList<>();
                Files.walkFileTree(SITE_CHECKS_ROOT, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        if (isValidXmlFile(file)) {
                            xmlFiles.add(file);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });

                xmlFiles.forEach(path -> addXmlHtmlMapping(path, map));
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
        final Path fileName = path.getFileName();
        return fileName != null
                && !("index" + XML_EXTENSION).equalsIgnoreCase(fileName.toString())
                && path.toString().endsWith(XML_EXTENSION)
                && Files.isRegularFile(path);
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
     * When packageFilter is null, returns full path: checks/category/filename.html#CheckName
     * When packageFilter is set, returns relative path: filename.html#CheckName
     *
     * @param xmlMap map of XML file names to HTML paths
     * @param category the category of the check
     * @param simpleName simple name of the check
     * @param packageFilter optional package filter, null for all checks
     * @return the resolved href for the check
     */
    private static String resolveHref(Map<String, String> xmlMap, String category,
                                      String simpleName, @Nullable String packageFilter) {
        final String lower = simpleName.toLowerCase(Locale.ROOT);
        final String href = xmlMap.get(lower);
        final String result;

        if (href != null) {
            if (packageFilter == null) {
                result = href + ANCHOR_SEPARATOR + simpleName;
            }
            else {
                final int lastSlash = href.lastIndexOf('/');
                final String filename;
                if (lastSlash >= 0) {
                    filename = href.substring(lastSlash + 1);
                }
                else {
                    filename = href;
                }
                result = filename + ANCHOR_SEPARATOR + simpleName;
            }
        }
        else {
            if (packageFilter == null) {
                result = String.format(Locale.ROOT, "%s/%s/%s.html%s%s",
                        CHECKS, category, lower, ANCHOR_SEPARATOR, simpleName);
            }
            else {
                result = String.format(Locale.ROOT, "%s.html%s%s",
                        lower, ANCHOR_SEPARATOR, simpleName);
            }
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
            result = MISC_PACKAGE;
        }
        else {
            result = parent.toString().replace('\\', '/');
        }
        return result;
    }

    /**
     * Sanitizes URLs within anchor tags by removing whitespace from href attributes.
     *
     * @param html the HTML string containing anchor tags
     * @return the HTML with sanitized URLs
     */
    private static String sanitizeAnchorUrls(String html) {
        final String result;
        if (html == null || html.isEmpty()) {
            result = html;
        }
        else {
            final Matcher matcher = HREF_PATTERN.matcher(html);
            final StringBuilder buffer = new StringBuilder(html.length());

            while (matcher.find()) {
                final String originalUrl = matcher.group(1);
                final String cleanedUrl = SPACE_PATTERN.matcher(originalUrl).replaceAll("");
                final String replacement = "href=\""
                        + Matcher.quoteReplacement(cleanedUrl) + "\"";
                matcher.appendReplacement(buffer, replacement);
            }
            matcher.appendTail(buffer);

            result = buffer.toString();
        }
        return result;
    }

    /**
     * Sanitizes HTML and extracts first sentence.
     * Preserves anchor tags while removing other HTML formatting.
     * Also cleans whitespace from URLs in href attributes.
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
            String cleaned = sanitizeAnchorUrls(html);
            cleaned = TAG_PATTERN.matcher(cleaned).replaceAll("");
            cleaned = SPACE_PATTERN.matcher(cleaned).replaceAll(" ").trim();
            cleaned = AMP_PATTERN.matcher(cleaned).replaceAll("&amp;");
            cleaned = CODE_SPACE_PATTERN.matcher(cleaned).replaceAll(FIRST_CAPTURE_GROUP);
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
     * Preserves URLs in anchor tags by breaking after the opening tag's closing bracket.
     *
     * @param text the text to wrap
     * @return the wrapped text
     */
    private static String wrapSummary(String text) {
        String wrapped = "";

        if (text != null && !text.isEmpty()) {
            final String sanitized = sanitizeAnchorUrls(text);

            final String indent = ModuleJavadocParsingUtil.INDENT_LEVEL_14;
            final String clean = sanitized.trim();

            final StringBuilder result = new StringBuilder(CAPACITY);
            int cleanIndex = 0;
            final int cleanLen = clean.length();

            while (cleanIndex < cleanLen) {
                final int remainingChars = cleanLen - cleanIndex;

                if (remainingChars <= MAX_CONTENT_WIDTH) {
                    result.append(indent)
                            .append(clean.substring(cleanIndex))
                            .append('\n');
                    break;
                }

                final int idealBreak = cleanIndex + MAX_CONTENT_WIDTH;
                final int actualBreak = calculateBreakPoint(clean, cleanIndex, idealBreak);

                result.append(indent)
                        .append(clean, cleanIndex, actualBreak);

                cleanIndex = actualBreak;
                while (cleanIndex < cleanLen && clean.charAt(cleanIndex) == ' ') {
                    cleanIndex++;
                }
            }

            wrapped = result.toString().trim();
        }

        return wrapped;
    }

    /**
     * Calculates the appropriate break point for text wrapping.
     * Handles anchor tags specially to avoid breaking URLs.
     *
     * @param clean the cleaned text to process
     * @param cleanIndex current position in text
     * @param idealBreak ideal break position
     * @return the actual break position
     */
    private static int calculateBreakPoint(String clean, int cleanIndex, int idealBreak) {
        final int anchorStart = clean.indexOf("<a ", cleanIndex);
        final int anchorOpenEnd;
        if (anchorStart == -1) {
            anchorOpenEnd = -1;
        }
        else {
            anchorOpenEnd = clean.indexOf('>', anchorStart);
        }

        final int actualBreak;
        if (shouldBreakAfterAnchorOpen(anchorStart, anchorOpenEnd, idealBreak)) {
            actualBreak = anchorOpenEnd + 1;
        }
        else if (shouldBreakAfterAnchorContent(anchorStart, anchorOpenEnd,
                idealBreak, clean)) {
            actualBreak = anchorOpenEnd + 1;
        }
        else {
            actualBreak = findSafeBreakPoint(clean, cleanIndex, idealBreak);
        }

        return actualBreak;
    }

    /**
     * Determines if break should occur after anchor opening tag.
     *
     * @param anchorStart start position of anchor tag
     * @param anchorOpenEnd end position of anchor opening tag
     * @param idealBreak ideal break position
     * @return true if should break after anchor opening
     */
    private static boolean shouldBreakAfterAnchorOpen(int anchorStart, int anchorOpenEnd,
                                                      int idealBreak) {
        return anchorStart != -1 && anchorStart < idealBreak
                && anchorOpenEnd != -1 && anchorOpenEnd >= idealBreak;
    }

    /**
     * Determines if break should occur after anchor content.
     *
     * @param anchorStart start position of anchor tag
     * @param anchorOpenEnd end position of anchor opening tag
     * @param idealBreak ideal break position
     * @param clean the text being processed
     * @return true if should break after anchor content
     */
    private static boolean shouldBreakAfterAnchorContent(int anchorStart, int anchorOpenEnd,
                                                         int idealBreak, String clean) {
        final boolean result;
        if (anchorStart != -1 && anchorStart < idealBreak
                && anchorOpenEnd != -1 && anchorOpenEnd < idealBreak) {
            final int anchorCloseStart = clean.indexOf(CLOSING_ANCHOR_TAG, anchorOpenEnd);
            result = anchorCloseStart != -1 && anchorCloseStart >= idealBreak;
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Finds a safe break point at a space character.
     *
     * @param text the text to search
     * @param start the start index
     * @param idealBreak the ideal break position
     * @return the actual break position
     */
    private static int findSafeBreakPoint(String text, int start, int idealBreak) {
        final int actualBreak;
        final int lastSpace = text.lastIndexOf(' ', idealBreak);

        if (lastSpace > start && lastSpace >= start + MAX_CONTENT_WIDTH / 2) {
            actualBreak = lastSpace;
        }
        else {
            actualBreak = idealBreak;
        }

        return actualBreak;
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

        /**
         * Constructs an info record.
         *
         * @param simpleName check simple name
         * @param link documentation link
         * @param summary module summary
         */
        private CheckInfo(String simpleName, String link, String summary) {
            this.simpleName = simpleName;
            this.link = link;
            this.summary = summary;
        }
    }
}
