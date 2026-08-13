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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

/**
 * A macro that generates an "In this article" table of contents for an xdoc
 * page. Every canonical section key in {@link #SECTION_NAMES} is attempted
 * for every page; the anchor id used in the link is read directly from a
 * matching {@code <subsection>} tag's real {@code id} attribute, so links
 * can never drift from what the page actually renders, and any section not
 * present in a given file's source is silently skipped -- pages do not need
 * to declare which sections they have. Examples and Use Cases subsections
 * additionally get a collapsible nested entry list, titled from the
 * descriptive paragraph associated with each example in the source -- never
 * from the example's own property values -- so the title always reflects
 * what the page author actually wrote.
 */
@Component(role = Macro.class, hint = "sitetoc")
public class TocMacro extends AbstractMacro {

    /** Section key/name: Description. */
    private static final String SECTION_DESCRIPTION = "Description";

    /** Section key/name: Properties. */
    private static final String SECTION_PROPERTIES = "Properties";

    /** Section key/name: Examples. */
    private static final String SECTION_EXAMPLES = "Examples";

    /** Section key: UseCases. */
    private static final String SECTION_USE_CASES_KEY = "UseCases";

    /** Closing quote and angle bracket used when terminating an HTML attribute. */
    private static final String QUOTE_CLOSE_TAG = "\">";

    /** Fallback title when no descriptive paragraph is available. */
    private static final String DEFAULT_TITLE = "Default configuration";

    /** A single double quote character, used to open HTML attribute values. */
    private static final String QUOTE = "\"";

    /**
     * Matches each Example/UseCase id-tagged paragraph independently. Unlike
     * an approach that also requires an adjacent {@code <macro name="example">}
     * tag, this makes no assumption about what immediately follows the
     * paragraph, since other markup (lists, extra paragraphs) can legally sit
     * between the id-tagged paragraph and its example macro. Matching each
     * anchor independently also guarantees {@code find()} advances past every
     * one in turn, so none can be skipped or have their content swallowed by
     * a neighboring match.
     */
    private static final Pattern ANCHOR_PATTERN = Pattern.compile(
            "<p\\s+id=\"((?:Example|UseCase)\\d+)-(config|raw)\"[^>]*>\\s*(.*?)\\s*</p>",
            Pattern.DOTALL);

    /**
     * Matches a single plain (non id-tagged) paragraph. The {@code (?=[\s>])}
     * lookahead after {@code <p} is required so this can never accidentally
     * match the start of an unrelated tag such as {@code <param>} -- without
     * it, {@code <p} alone matches the first two characters of "param" too.
     * This pattern is intentionally not anchored to any particular position;
     * {@link #findImmediatelyPrecedingPlainParagraph} finds every match in
     * the preceding text and then separately verifies true adjacency.
     */
    private static final Pattern PLAIN_P_PATTERN = Pattern.compile(
            "<p(?=[\\s>])(?![^>]*\\bid=)[^>]*>\\s*(.*?)\\s*</p>", Pattern.DOTALL);

    /** Matches when a string is entirely whitespace (or empty), used for adjacency checks. */
    private static final Pattern WHITESPACE_ONLY_PATTERN = Pattern.compile("\\A\\s*\\z");

    /**
     * Matches a title that, once normalized, is nothing but a bare label
     * such as "Configuration" or "Notes" with no real descriptive content.
     * Only titles matching this are eligible to be replaced by a preceding
     * paragraph's text; titles with genuine content are always kept as-is,
     * so an unrelated nearby paragraph can never override a good title.
     */
    private static final Pattern TRIVIAL_LABEL_PATTERN = Pattern.compile(
            "(?i)^(?:configuration|notes?|example)$");

    /** Strips a trailing standalone "Example"/"Example:" artifact from scraped text. */
    private static final Pattern TRAILING_EXAMPLE_PATTERN = Pattern.compile(
            "(?i)\\s*Example:?\\s*$");

    /** Strips the common "To configure the check to produce a violation on/when" lead-in. */
    private static final Pattern LEAD_IN_PATTERN = Pattern.compile(
            "^\\s*To configure(?: the check| the Check)?"
                    + "(?:\\s+to\\s+(?:produce\\s+a\\s+violation)?)?"
                    + "(?:\\s+on)?"
                    + "(?:\\s+when)?\\s*",
            Pattern.CASE_INSENSITIVE);

    /** Strips inline HTML tags left in scraped paragraph text except {@code <code>} tags. */
    private static final Pattern TAG_PATTERN = Pattern.compile(
            "</?(?!code\\b)[a-zA-Z][^>]*>");

    /** Collapses any run of whitespace (including newlines) into a single space. */
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    /** Matches a subsection tag and captures its name/id attributes, in either order. */
    private static final Pattern SUBSECTION_TAG_PATTERN = Pattern.compile(
            "<subsection\\s+(?:name=\"([^\"]*)\"\\s+id=\"([^\"]*)\""
                    + "|id=\"([^\"]*)\"\\s+name=\"([^\"]*)\")");

    /** Regex group index of the {@code id} attribute when it appears second (name, id order). */
    private static final int GROUP_ID_NAME_FIRST = 2;

    /** Regex group index of the {@code id} attribute when it appears first (id, name order). */
    private static final int GROUP_ID_ID_FIRST = 3;

    /** Regex group index of the {@code name} attribute when it appears second (id, name order). */
    private static final int GROUP_NAME_ID_FIRST = 4;

    /** Closing anchor/list-item tag pair used to end a TOC entry. */
    private static final String LI_CLOSE = "</a></li>\n";

    /**
     * Maps every canonical section key, in the fixed order TOC entries
     * should appear, to the subsection's {@code name} attribute value. Every
     * key is attempted for every page; any section not present in a given
     * file's source is silently skipped by {@link #writeSectionEntry}.
     */
    private static final Map<String, String> SECTION_NAMES = new LinkedHashMap<>();

    static {
        SECTION_NAMES.put(SECTION_DESCRIPTION, SECTION_DESCRIPTION);
        SECTION_NAMES.put(SECTION_PROPERTIES, SECTION_PROPERTIES);
        SECTION_NAMES.put(SECTION_EXAMPLES, SECTION_EXAMPLES);
        SECTION_NAMES.put(SECTION_USE_CASES_KEY, "Use Cases");
        SECTION_NAMES.put("ExampleOfUsage", "Example of Usage");
        SECTION_NAMES.put("ViolationMessages", "Violation Messages");
        SECTION_NAMES.put("FullyQualifiedName", "Fully Qualified Name");
        SECTION_NAMES.put("ParentModule", "Parent Module");
    }

    /** Section keys that get nested sub-entries. */
    private static final Set<String> NESTED_SECTIONS =
            Set.of(SECTION_EXAMPLES, SECTION_USE_CASES_KEY);

    /**
     * Creates a new {@code TocMacro} instance.
     */
    public TocMacro() {
        // no code by default
    }

    @Override
    public void execute(Sink sink, MacroRequest request) {
        final String sourceContent = request.getSourceContent();

        sink.rawText("<div class=\"toc-panel\">\n");
        sink.rawText("  <input type=\"checkbox\" id=\"toc-toggle\" "
                + "class=\"toc-toggle-checkbox\" checked=\"checked\"/>\n");
        sink.rawText("  <label for=\"toc-toggle\" class=\"toc-toggle-arrow\" "
                + "title=\"Collapse\"/>\n");
        sink.rawText("  <div class=\"toc-content\">\n");
        sink.rawText("    <p class=\"toc-heading\">On This Page</p>\n");
        sink.rawText("    <ul class=\"toc-list\">\n");

        for (String section : SECTION_NAMES.keySet()) {
            writeSectionEntry(sink, section, sourceContent);
        }

        sink.rawText("    </ul>\n");
        sink.rawText("  </div>\n");
        sink.rawText("</div>");
    }

    /**
     * Writes a single top-level {@code <li>}, with a collapsible nested
     * Example/UseCase entry list when the section is Examples or Use Cases.
     * The anchor id is read directly from the matching {@code <subsection>}
     * tag's actual {@code id} attribute in the source, rather than assumed
     * from a naming convention, so the link can never drift from what the
     * page really renders. If no matching {@code <subsection>} exists in
     * the source, nothing is written for this section -- pages are not
     * required to have every canonical section.
     *
     * @param sink sink to write to.
     * @param section the canonical section key.
     * @param sourceContent the full template source text.
     */
    private static void writeSectionEntry(Sink sink, String section, String sourceContent) {
        final String sectionName = SECTION_NAMES.get(section);

        if (sectionName != null) {
            final Optional<String> anchorId =
                    findSubsectionAnchor(sourceContent, sectionName);

            if (anchorId.isPresent()) {
                final String anchor = anchorId.get();

                if (NESTED_SECTIONS.contains(section)) {
                    final String body = extractSectionBody(sourceContent, anchor);
                    sink.rawText("          <li>\n");
                    final String toggleId = "toc-sub-" + section;
                    sink.rawText("            <input type=\"checkbox\" id=\"" + toggleId
                            + "\" class=\"toc-sub-toggle-checkbox\" checked=\"checked\"/>\n");
                    sink.rawText("            <label for=\""
                            + toggleId + "\" class=\"toc-sub-toggle\">"
                            + "<a href=\"#" + anchor + QUOTE_CLOSE_TAG + sectionName + "</a>"
                            + "<span class=\"toc-sub-arrow\"/></label>\n");
                    writeNestedItems(sink, body);
                    sink.rawText("          </li>\n");
                }
                else {
                    sink.rawText("          <li><a href=\"#" + anchor + QUOTE_CLOSE_TAG
                            + sectionName + LI_CLOSE);
                }
            }
        }
    }

    /**
     * Finds the actual {@code id} attribute value of the {@code <subsection>}
     * tag whose {@code name} attribute matches the given section name.
     *
     * @param sourceContent the full template source text.
     * @param sectionName the subsection's {@code name} attribute value to match.
     * @return the subsection's real id, or empty if no matching tag is found.
     */
    private static Optional<String> findSubsectionAnchor(String sourceContent,
                                                         String sectionName) {
        final Matcher matcher = SUBSECTION_TAG_PATTERN.matcher(sourceContent);
        Optional<String> result = Optional.empty();

        while (matcher.find() && result.isEmpty()) {
            final String nameNameFirst = matcher.group(1);
            final String name;
            if (nameNameFirst != null) {
                name = nameNameFirst;
            }
            else {
                name = matcher.group(GROUP_NAME_ID_FIRST);
            }
            final String idNameFirst = matcher.group(GROUP_ID_NAME_FIRST);
            final String id;
            if (idNameFirst != null) {
                id = idNameFirst;
            }
            else {
                id = matcher.group(GROUP_ID_ID_FIRST);
            }
            if (sectionName.equals(name)) {
                result = Optional.of(id);
            }
        }
        return result;
    }

    /**
     * Extracts the text of one subsection from the full source, bounded by
     * that subsection's opening tag and the next subsection's opening tag.
     *
     * @param sourceContent the full template source text.
     * @param anchorId the subsection's id attribute value.
     * @return the subsection's raw inner text, or an empty string if not found.
     */
    private static String extractSectionBody(String sourceContent, String anchorId) {
        String body = "";
        final int start = sourceContent.indexOf("id=\"" + anchorId + QUOTE);
        if (start >= 0) {
            final int nextSubsection = sourceContent.indexOf("<subsection", start + 1);
            final int end;
            if (nextSubsection >= 0) {
                end = nextSubsection;
            }
            else {
                end = sourceContent.length();
            }
            body = sourceContent.substring(start, end);
        }
        return body;
    }

    /**
     * Writes nested {@code <li>} entries for each Example/UseCase found
     * within a subsection's body text. Each id-tagged paragraph is matched
     * independently via {@link #ANCHOR_PATTERN}, so every example produces
     * exactly one entry, in source order, and none can be skipped or have
     * its content merged with a neighboring example. Titles are shown in
     * full and allowed to wrap across lines, rather than being truncated
     * with an ellipsis, so the whole label is always readable.
     *
     * @param sink sink to write to.
     * @param sectionBody the raw text of the subsection.
     */
    private static void writeNestedItems(Sink sink, String sectionBody) {
        final Matcher itemMatcher = ANCHOR_PATTERN.matcher(sectionBody);
        boolean hasItems = false;

        while (itemMatcher.find()) {
            if (!hasItems) {
                sink.rawText("            <ul class=\"toc-sublist\">\n");
                hasItems = true;
            }
            final String anchorId = itemMatcher.group(1);
            final String suffix = itemMatcher.group(2);
            final String ownParagraph = itemMatcher.group(3);
            final String title = resolveTitle(sectionBody, itemMatcher.start(), ownParagraph);

            sink.rawText("              <li><a href=\"#" + anchorId
                    + "-" + suffix + "\" class=\"toc-sublink\">" + title + LI_CLOSE);
        }

        if (hasItems) {
            sink.rawText("            </ul>\n");
        }
    }

    /**
     * Resolves the title for one example. The id-tagged paragraph's own text
     * is preferred and used as-is whenever it carries real descriptive
     * content. Only when that text is a bare label ("Configuration:",
     * "Notes:", "Example:") with no descriptive value of its own does this
     * fall back to an immediately adjacent preceding plain paragraph -- and
     * only if that paragraph itself is not also a bare label. This ordering
     * ensures a genuine description already present on the id-tagged
     * paragraph is never overridden by an unrelated nearby paragraph.
     *
     * @param sectionBody the raw text of the subsection.
     * @param matchStart the offset where the id-tagged paragraph's match began.
     * @param ownParagraph the id-tagged paragraph's own raw inner text.
     * @return the resolved, human-readable title.
     */
    private static String resolveTitle(String sectionBody, int matchStart, String ownParagraph) {
        String title = toSentenceTitle(ownParagraph);
        if (TRIVIAL_LABEL_PATTERN.matcher(title).matches()) {
            final String preceding = findImmediatelyPrecedingPlainParagraph(
                    sectionBody, matchStart);
            if (preceding != null) {
                final String precedingTitle = toSentenceTitle(preceding);
                if (!TRIVIAL_LABEL_PATTERN.matcher(precedingTitle).matches()) {
                    title = precedingTitle;
                }
            }
        }
        return title;
    }

    /**
     * Looks immediately backward from the start of an id-tagged paragraph for
     * a plain (non id-tagged) sibling {@code <p>}, but only if it is directly
     * adjacent -- separated from the id-tagged paragraph by nothing but
     * whitespace. If anything else (a separator, code block, another tagged
     * paragraph) sits in between, no preceding paragraph is returned, so a
     * description can never be pulled in across another example's content.
     * This scans for every plain paragraph in the preceding text and takes
     * the last one, rather than relying on {@code find()}'s leftmost-match
     * behavior, since a lazy match anchored only at the end can otherwise
     * start from the first plain paragraph in the section and stretch
     * across several unrelated examples to reach that end point.
     *
     * @param sectionBody the raw text of the subsection.
     * @param matchStart the offset where the id-tagged paragraph's match began.
     * @return the plain paragraph's raw text, or {@code null} if none is
     *     directly adjacent.
     */
    private static String findImmediatelyPrecedingPlainParagraph(String sectionBody,
                                                                 int matchStart) {
        final String before = sectionBody.substring(0, matchStart);
        final Matcher plainMatcher = PLAIN_P_PATTERN.matcher(before);
        String lastGroup = null;
        int lastEnd = -1;
        while (plainMatcher.find()) {
            lastGroup = plainMatcher.group(1);
            lastEnd = plainMatcher.end();
        }
        String result = null;
        if (lastGroup != null
                && WHITESPACE_ONLY_PATTERN.matcher(before.substring(lastEnd)).matches()) {
            result = lastGroup;
        }
        return result;
    }

    /**
     * Converts a raw scraped paragraph into a short sentence-based title by
     * removing inline markup, normalizing whitespace, stripping the common
     * lead-in clause, stripping a trailing standalone "Example"/"Example:"
     * artifact, and trimming the trailing colon.
     *
     * @param rawParagraph the paragraph's raw inner text, or {@code null}.
     * @return a short, human-readable title.
     */
    private static String toSentenceTitle(String rawParagraph) {
        String text = DEFAULT_TITLE;
        if (rawParagraph != null) {
            text = TAG_PATTERN.matcher(rawParagraph).replaceAll("");
            text = normalizeWhitespace(text);
            text = LEAD_IN_PATTERN.matcher(text).replaceFirst("");
            text = TRAILING_EXAMPLE_PATTERN.matcher(text).replaceFirst("");
            text = text.strip();
            if (text.endsWith(":")) {
                text = text.substring(0, text.length() - 1);
            }
            if (text.isEmpty()) {
                text = DEFAULT_TITLE;
            }
            else {
                text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
            }
        }
        return text;
    }

    /**
     * Collapses internal newlines/indentation from raw xdoc paragraph text
     * into single spaces, so tooltips and labels render on one line.
     *
     * @param text the raw text, possibly containing multi-line whitespace.
     * @return the text with all whitespace runs collapsed to a single space.
     */
    private static String normalizeWhitespace(String text) {
        return WHITESPACE_PATTERN.matcher(text).replaceAll(" ").strip();
    }

}
