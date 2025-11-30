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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Macro to generate check summaries for a specific category.
 *
 * <p>This macro outputs table rows for all checks in a specified category,
 * allowing category index pages to automatically stay in sync with Javadoc changes.
 *
 * <p>Usage in xdoc files:
 * <pre>
 * &lt;table&gt;
 *   &lt;macro name="generate-checks-indexes"&gt;
 *     &lt;param name="package" value="javadoc"/&gt;
 *   &lt;/macro&gt;
 * &lt;/table&gt;
 * </pre>
 *
 * <p>The package parameter should match the package name under
 * com.puppycrawl.tools.checkstyle.checks (e.g., "annotation", "blocks", "coding").
 * For miscellaneous checks, use "misc" which includes both root-level checks
 * and checks in the indentation package.
 */
@Component(role = Macro.class, hint = "generate-checks-indexes")
public class CategoryCheckSummaries extends AbstractMacro {

    /** Path component for source directory. */
    private static final String SRC = "src";

    /** Path component for checks directory. */
    private static final String CHECKS = "checks";

    /** Root path for Java check files. */
    private static final Path JAVA_CHECKS_ROOT = Path.of(
            SRC, "main", "java", "com", "puppycrawl", "tools", "checkstyle", CHECKS);

    /** Maximum line width for summary text in table cells. */
    private static final int MAX_LINE_WIDTH = 80;

    /** Misc category name. */
    private static final String MISC = "misc";

    /** Annotation category name. */
    private static final String ANNOTATION = "annotation";

    /** Map of category remappings. */
    private static final Map<String, String> CATEGORY_REMAPPING = Map.of(
            "indentation", MISC
    );

    /** Replacement for &amp;. */
    private static final String AND_XML = "&amp;";

    /** Map to override category for specific holder classes. */
    private static final Map<String, String> HOLDER_CATEGORY_OVERRIDE = Map.of(
            "SuppressWarningsHolder", ANNOTATION,
            "SuppressXpathWarningsHolder", ANNOTATION
    );

    /** Map of category folder names to their display titles for holder sections. */
    private static final Map<String, String> CATEGORY_TITLES = Map.ofEntries(
            Map.entry(ANNOTATION, "Annotation"),
            Map.entry("blocks", "Block"),
            Map.entry("coding", "Coding"),
            Map.entry("design", "Design"),
            Map.entry("filters", "Filter"),
            Map.entry("header", "Header"),
            Map.entry("imports", "Import"),
            Map.entry("javadoc", "Javadoc Comment"),
            Map.entry("metrics", "Metric"),
            Map.entry(MISC, "Miscellaneous"),
            Map.entry("modifier", "Modifier"),
            Map.entry("naming", "Naming Convention"),
            Map.entry("regexp", "Regexp"),
            Map.entry("sizes", "Size Violation"),
            Map.entry("whitespace", "Whitespace")
    );

    /** Current line length for indentation. */
    private static final int CURRENT_LINE_LENGTH = 14;

    /** Table cell opening tag. */
    private static final String TD_OPEN = "            <td>\n";

    /** Table cell closing tag. */
    private static final String TD_CLOSE = "            </td>\n";

    /** Newline character. */
    private static final String NEWLINE = "\n";

    /** Indented newline for wrapping. */
    private static final String INDENT_NEWLINE = "\n              ";

    /** Code tag opening. */
    private static final String CODE_OPEN = "<code>";

    /** Code tag closing. */
    private static final String CODE_CLOSE = "</code>";

    /** Link template format string. */
    private static final String LINK_TEMPLATE =
            "              <a href=\"%s\">%n                %s%n              </a>%n";

    /** Table row template format string. */
    private static final String ROW_TEMPLATE =
            "          <tr>%n%s%s%s%s%s%s%s          </tr>%n";

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String packageParam = (String) request.getParameter("package");
        final boolean hasValidPackage = packageParam != null && !packageParam.isEmpty();

        if (hasValidPackage) {
            final List<CheckInfo> checks = discoverChecksForCategory(packageParam);
            final List<CheckInfo> regularChecks = new ArrayList<>();
            final List<CheckInfo> holders = new ArrayList<>();

            for (CheckInfo check : checks) {
                if (check.isHolder) {
                    holders.add(check);
                }
                else {
                    regularChecks.add(check);
                }
            }

            final StringBuilder content = new StringBuilder(2000);

            if (!regularChecks.isEmpty()) {
                content.append(buildTableContent(regularChecks));
            }

            if (!holders.isEmpty()) {
                content.append("      </div>\n    </section>\n\n    <section name=\"")
                        .append(getCategoryTitle(packageParam))
                        .append(" Holder Checks\">\n      <div class=\"wrapper\">\n")
                        .append(buildTableContent(holders));
            }

            sink.rawText(content.toString());
        }
    }

    /**
     * Get the display title for a category.
     *
     * @param category the category folder name
     * @return the display title
     */
    private static String getCategoryTitle(String category) {
        return CATEGORY_TITLES.getOrDefault(category,
                category.substring(0, 1).toUpperCase(Locale.ROOT) + category.substring(1));
    }

    /**
     * Build the complete table HTML content with proper formatting.
     *
     * @param checks the list of checks to include
     * @return complete table HTML with proper indentation
     */
    private static String buildTableContent(Collection<CheckInfo> checks) {
        final StringBuilder content = new StringBuilder(checks.size() * 300);
        content.append("<table>\n");

        for (CheckInfo info : checks) {
            appendTableRow(content, info);
        }
        content.append("        </table>");
        return content.toString();
    }

    /**
     * Appends a single table row for a check module with proper formatting and line wrapping.
     *
     * @param content the StringBuilder to append the row to
     * @param checkInfo check module information
     */
    private static void appendTableRow(StringBuilder content, CheckInfo checkInfo) {
        final String escapedLink = escapeXmlToString(checkInfo.link);
        final String escapedName = escapeXmlToString(checkInfo.simpleName);

        final StringBuilder summaryBuilder = new StringBuilder(200);
        appendFormattedSummary(summaryBuilder, checkInfo.summary);

        final String linkHtml = String.format(Locale.ROOT, LINK_TEMPLATE,
                escapedLink, escapedName);
        final String row = String.format(Locale.ROOT, ROW_TEMPLATE,
                TD_OPEN, linkHtml, TD_CLOSE, TD_OPEN,
                summaryBuilder, TD_CLOSE, "");
        content.append(row);
    }

    /**
     * Escape XML special characters and return as a new string.
     *
     * @param text the text to escape
     * @return the escaped text
     */
    private static String escapeXmlToString(String text) {
        final StringBuilder builder = new StringBuilder(text.length() + 20);
        escapeXml(builder, text);
        return builder.toString();
    }

    /**
     * Escape XML special characters and append to StringBuilder.
     *
     * @param builder the StringBuilder to append to
     * @param text the text to escape
     */
    private static void escapeXml(StringBuilder builder, String text) {
        for (int index = 0; index < text.length(); index++) {
            final char ch = text.charAt(index);
            switch (ch) {
                case '&':
                    builder.append(AND_XML);
                    break;
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\'':
                    builder.append("&apos;");
                    break;
                default:
                    builder.append(ch);
                    break;
            }
        }
    }

    /**
     * Append summary text with proper line wrapping at 80 characters.
     *
     * <p>This method performs three types of formatting:
     * <ul>
     *   <li>Empty summary handling</li>
     *   <li>Inline {@code <code>} block preservation</li>
     *   <li>Word wrapping with indentation and max line width limits</li>
     * </ul>
     *
     * @param builder the string builder to append formatted text to
     * @param summary the raw summary text, possibly containing {@code <code>} blocks
     */
    private static void appendFormattedSummary(StringBuilder builder, String summary) {

        final boolean isEmpty = summary == null || summary.isEmpty();

        if (isEmpty) {
            builder.append("              \n");
        }
        else {

            builder.append("              ");

            int currentLineLength = CURRENT_LINE_LENGTH;
            int pos = 0;
            boolean needsSpace = false;

            while (pos < summary.length()) {

                final CodeResult codeResult = extractCodeBlock(summary, pos);
                if (codeResult.isCodeBlockFound()) {
                    currentLineLength = appendCodeBlock(
                            builder, codeResult.codeBlock(), currentLineLength, needsSpace);
                    pos = codeResult.endPos();
                    needsSpace = true;
                    continue;
                }

                final WordResult wordResult = extractNextWord(summary, pos);
                if (wordResult.isEmptyWord()) {
                    pos++;
                    continue;
                }

                currentLineLength = appendWord(
                        builder, wordResult.word(), currentLineLength, needsSpace);
                pos = wordResult.nextPos();
                needsSpace = true;
            }

            builder.append('\n');
        }

    }

    /**
     * Attempts to extract a {@code <code>} block starting at the given position.
     *
     * @param text the input summary text
     * @param pos  the starting position to check
     * @return a {@link CodeResult} containing whether a block was found,
     *         the block text, and the ending position
     */
    private static CodeResult extractCodeBlock(String text, int pos) {

        boolean found = false;
        String block = "";
        int endPos = pos;

        if (text.startsWith(CODE_OPEN, pos)) {
            final int codeEnd = text.indexOf(CODE_CLOSE, pos);
            if (codeEnd != -1) {
                final int fullEnd = codeEnd + CODE_CLOSE.length();
                block = text.substring(pos, fullEnd);
                endPos = fullEnd;
                found = true;
            }
        }

        return new CodeResult(found, block, endPos);
    }

    /**
     * Appends a code block to the builder, performing line wrapping only when needed.
     *
     * @param builder the builder to append to
     * @param codeBlock the code block text including tags
     * @param currentLength current number of characters on the line
     * @param needsSpace whether a space is required before appending
     * @return the updated line length
     */
    private static int appendCodeBlock(StringBuilder builder, String codeBlock,
                                       int currentLength, boolean needsSpace) {

        int resultLength = currentLength;

        if (needsSpace && resultLength > CURRENT_LINE_LENGTH) {
            builder.append(' ');
            resultLength++;
        }

        final int blockLen = codeBlock.length();
        if (resultLength + blockLen > MAX_LINE_WIDTH
                && resultLength > CURRENT_LINE_LENGTH) {
            builder.append(INDENT_NEWLINE);
            resultLength = CURRENT_LINE_LENGTH;
        }

        builder.append(codeBlock);
        resultLength += blockLen;

        return resultLength;
    }

    /**
     * Extracts the next word delimited by space or end of string.
     *
     * @param text the input summary text
     * @param pos  the current scanning position
     * @return a {@link WordResult} containing the word and next position
     */
    private static WordResult extractNextWord(String text, int pos) {

        int nextSpace = text.indexOf(' ', pos);
        if (nextSpace == -1) {
            nextSpace = text.length();
        }

        final String word = text.substring(pos, nextSpace);
        final int nextPos = nextSpace + 1;

        return new WordResult(word, nextPos);
    }

    /**
     * Appends a normal word to the builder while ensuring wrapping rules.
     *
     * @param builder the builder to append to
     * @param word the text of the word
     * @param currentLength the number of characters currently on the line
     * @param needsSpace whether a space should be inserted before this word
     * @return the new current line length
     */
    private static int appendWord(StringBuilder builder, String word,
                                  int currentLength, boolean needsSpace) {

        int resultLength = currentLength;
        final int wordLength = word.length();

        if (needsSpace && resultLength > CURRENT_LINE_LENGTH) {
            if (resultLength + 1 + wordLength > MAX_LINE_WIDTH) {
                builder.append(INDENT_NEWLINE);
                resultLength = CURRENT_LINE_LENGTH;
            }
            else {
                builder.append(' ');
                resultLength++;
            }
        }
        else if (resultLength + wordLength > MAX_LINE_WIDTH
                && resultLength > CURRENT_LINE_LENGTH) {
            builder.append(INDENT_NEWLINE);
            resultLength = CURRENT_LINE_LENGTH;
        }

        builder.append(word);
        resultLength += wordLength;

        return resultLength;
    }

    /**
     * Discover all checks for a specific category.
     *
     * @param requestedCategory the category to filter by
     * @return list of checks in the category, sorted by name
     * @throws MacroExecutionException if discovery fails
     */
    private static List<CheckInfo> discoverChecksForCategory(String requestedCategory)
            throws MacroExecutionException {
        final List<CheckInfo> result = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(JAVA_CHECKS_ROOT)) {
            final List<Path> checkFiles = paths
                    .filter(CategoryCheckSummaries::isCheckOrHolderFile)
                    .toList();

            for (Path path : checkFiles) {
                processCheckFile(path, requestedCategory, result);
            }
        }
        catch (IOException ioe) {
            throw new MacroExecutionException("Failed to walk Java checks directory", ioe);
        }

        Collections.sort(result,
                (first, second) -> first.simpleName.compareToIgnoreCase(second.simpleName));
        return result;
    }

    /**
     * Process a single check file and add it to the list if it matches the category.
     *
     * @param path the path to the check file
     * @param requestedCategory the category to filter by
     * @param checks the list to add matching checks to
     */
    private static void processCheckFile(Path path, String requestedCategory,
                                         List<CheckInfo> checks) {
        final String moduleName = CommonUtil.getFileNameWithoutExtension(path.toString());
        final boolean isHolder = moduleName.endsWith("Holder");
        final String simpleName = extractSimpleName(moduleName, isHolder);

        try {
            final DetailNode javadoc = SiteUtil.getModuleJavadoc(moduleName, path);
            final boolean hasValidJavadoc = javadoc != null;

            if (hasValidJavadoc) {
                final String desc = getModuleDescriptionSafe(javadoc);
                final boolean hasValidDescription = desc != null && !desc.isEmpty();

                if (hasValidDescription) {
                    String category = extractCategoryFromJavaPath(path);

                    if (isHolder) {
                        category = HOLDER_CATEGORY_OVERRIDE.getOrDefault(simpleName, category);
                    }

                    if (requestedCategory.equals(category)) {
                        final String summary = sanitizeAndFirstSentence(desc);
                        final String href = resolveHrefForCheck(simpleName);
                        final CheckInfo info = new CheckInfo(simpleName, href, summary, isHolder);
                        checks.add(info);
                    }
                }
            }
        }
        catch (MacroExecutionException ignored) {
            // swallow individual file failures
        }
    }

    /**
     * Extract the simple name from the module name.
     *
     * @param moduleName the full module name
     * @param isHolder whether this is a holder class
     * @return the simple name
     */
    private static String extractSimpleName(String moduleName, boolean isHolder) {
        final String simpleName;

        if (isHolder) {
            simpleName = moduleName;
        }
        else {
            simpleName = moduleName.substring(0, moduleName.length() - "Check".length());
        }

        return simpleName;
    }

    /**
     * Checks if a path points to a Check or Holder Java file.
     *
     * @param path the path to check
     * @return true if the path is a check or holder file
     */
    private static boolean isCheckOrHolderFile(Path path) {
        boolean result = false;

        if (Files.isRegularFile(path)) {
            final Path fileName = path.getFileName();
            final boolean hasFileName = fileName != null;

            if (hasFileName) {
                final String name = fileName.toString();
                final boolean isCheckFile = name.endsWith("Check.java");
                final boolean isHolderFile = name.endsWith("Holder.java");
                final boolean isPackageInfo = !"package-info.java".equals(name);
                result = (isCheckFile || isHolderFile) && isPackageInfo;
            }
        }

        return result;
    }

    /**
     * Extract category from Java file path relative to JAVA_CHECKS_ROOT.
     *
     * @param javaPath the path to the Java file
     * @return the category name
     */
    private static String extractCategoryFromJavaPath(Path javaPath) {
        final Path rel = JAVA_CHECKS_ROOT.relativize(javaPath);
        final Path parent = rel.getParent();
        final String rawCategory;

        if (parent == null) {
            rawCategory = MISC;
        }
        else {
            final String parentStr = parent.toString().replace('\\', '/');
            final int slashIndex = parentStr.indexOf('/');
            if (slashIndex > 0) {
                rawCategory = parentStr.substring(0, slashIndex);
            }
            else {
                rawCategory = parentStr;
            }
        }

        return CATEGORY_REMAPPING.getOrDefault(rawCategory, rawCategory);
    }

    /**
     * Resolve the href for a check.
     *
     * @param simpleName the simple name of the check
     * @return the href string
     */
    private static String resolveHrefForCheck(String simpleName) {
        final String lower = simpleName.toLowerCase(Locale.ROOT);
        return String.format(Locale.ROOT, "%s.html#%s", lower, simpleName);
    }

    /**
     * Get module description safely without throwing.
     *
     * @param javadoc the javadoc detail node
     * @return the module description or null
     */
    private static String getModuleDescriptionSafe(DetailNode javadoc) {
        String result = null;

        if (javadoc != null) {
            try {
                final boolean hasSinceTag =
                        ModuleJavadocParsingUtil.getModuleSinceVersionTagStartNode(javadoc)
                                != null;
                if (hasSinceTag) {
                    result = ModuleJavadocParsingUtil.getModuleDescription(javadoc);
                }
            }
            catch (IllegalStateException ise) {
                result = null;
            }
        }

        return result;
    }

    /**
     * Sanitize HTML and extract first sentence.
     *
     * @param html the HTML string to sanitize
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
            cleaned = cleaned.replaceAll(CODE_OPEN, "");
            cleaned = cleaned.replaceAll(CODE_CLOSE, "");
            cleaned = cleaned.replaceAll("\\s+", " ");
            cleaned = cleaned.replaceAll("&(?![a-zA-Z#0-9]+;)", AND_XML);
            result = extractFirstSentence(cleaned);
        }

        return result;
    }

    /**
     * Extract the first sentence from text.
     *
     * @param text the text to extract from
     * @return the first sentence
     */
    // -@cs[CyclomaticComplexity] Sentence extraction logic requires multiple conditions
    private static String extractFirstSentence(String text) {
        String result = "";

        if (text != null && !text.isEmpty()) {
            int end = -1;

            for (int index = 0; index < text.length(); index++) {
                final boolean isPeriod = text.charAt(index) == '.';
                final boolean isLastChar = index == text.length() - 1;
                final boolean nextIsWhitespace = !isLastChar
                        && Character.isWhitespace(text.charAt(index + 1));
                final boolean nextIsTag = !isLastChar && text.charAt(index + 1) == '<';

                if (isPeriod && (isLastChar || nextIsWhitespace || nextIsTag)) {
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
     * Simple data holder for a check entry.
     */
    private static final class CheckInfo {

        /** Simple name of the check. */
        private final String simpleName;

        /** Link to the check documentation. */
        private final String link;

        /** Summary description of the check. */
        private final String summary;

        /** Whether this is a holder class. */
        private final boolean isHolder;

        /**
         * Create a new CheckInfo.
         *
         * @param simpleName the simple name
         * @param link the documentation link
         * @param summary the summary description
         * @param isHolder whether this is a holder
         */
        private CheckInfo(String simpleName, String link, String summary, boolean isHolder) {
            this.simpleName = simpleName;
            this.link = link;
            this.summary = summary;
            this.isHolder = isHolder;
        }
    }

    /**
     * Represents the result of checking for a {@code <code>} block.
     *
     * @param found whether a code block was detected
     * @param codeBlock the full code block text
     * @param endPos the ending position after the block
     */
    private record CodeResult(boolean found, String codeBlock, int endPos) {

        /**
         * Returns whether this is a code block detected in the summary.
         *
         * @return true if code block exists
         */
        /* package */ boolean isCodeBlockFound() {
            return found;
        }
    }

    /**
     * Holds a parsed word and the next scanning position.
     *
     * @param word the extracted word
     * @param nextPos the position after this word
     */
    private record WordResult(String word, int nextPos) {

        /**
         * Returns true if the extracted word was empty.
         *
         * @return true if empty
         */
        /* package */ boolean isEmptyWord() {
            return word.isEmpty();
        }
    }
}
