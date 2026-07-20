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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

/**
 * A macro that generates an "In this article" table of contents for an xdoc
 * page. Top-level entries come from the {@code sections} param, each mapped
 * to a subsection {@code name} attribute to search for in the source; the
 * anchor id used in the link is read directly from that matching
 * {@code <subsection>} tag's real {@code id} attribute, rather than assumed
 * from a naming convention, so links can never drift from what the page
 * actually renders. Examples and Use Cases subsections additionally get
 * nested entries, titled either by the first non-default
 * {@code property=value} found in the example's referenced source file, or
 * by a shortened form of the example's descriptive paragraph when no
 * distinguishing property can be found.
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

    /** Base directory that example {@code path} params are resolved against. */
    private static final String EXAMPLES_BASE_DIR = "src/xdocs-examples/";

    /** Matches an Example/UseCase paragraph followed immediately by its example macro. */
    private static final Pattern ITEM_WITH_PATH_PATTERN = Pattern.compile(
            "<p\\s+id=\"((?:Example|UseCase)\\d+)-config\"[^>]*>\\s*(.*?)\\s*</p>\\s*"
                    + "<macro\\s+name=\"example\">\\s*"
                    + "<param\\s+name=\"path\"\\s+value=\"([^\"]+)\"\\s*/>",
            Pattern.DOTALL);

    /** Matches a property assignment inside an example's embedded config comment. */
    private static final Pattern PROPERTY_PATTERN = Pattern.compile(
            "<property\\s+name=\"([^\"]+)\"\\s+value=\"([^\"]*)\"\\s*/>");

    /** Strips the common "To configure the check to produce a violation on/when" lead-in. */
    private static final Pattern LEAD_IN_PATTERN = Pattern.compile(
            "^\\s*To configure(?: the check)?"
                    + "(?:\\s+to\\s+(?:produce\\s+a\\s+violation)?)?"
                    + "(?:\\s+on)?"
                    + "(?:\\s+when)?\\s*",
            Pattern.CASE_INSENSITIVE);

    /** Strips inline HTML tags left in scraped paragraph text, except <code> tags. */
    private static final Pattern TAG_PATTERN = Pattern.compile(
            "</?(?!code\\b)[a-zA-Z][^>]*>");

    /** Collapses any run of whitespace (including newlines) into a single space. */
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    /** Matches a subsection tag and captures its name/id attributes, in either order. */
    private static final Pattern SUBSECTION_TAG_PATTERN = Pattern.compile(
            "<subsection\\s+(?:name=\"([^\"]*)\"\\s+id=\"([^\"]*)\""
                    + "|id=\"([^\"]*)\"\\s+name=\"([^\"]*)\")");

    /** Maximum characters before a nested title is truncated with an ellipsis. */
    private static final int MAX_TITLE_LENGTH = 42;

    /** Property name to always skip when deriving a property=value title. */
    private static final String TOKENS_PROPERTY = "tokens";

    /** Regex group index of the {@code id} attribute when it appears second (name, id order). */
    private static final int GROUP_ID_NAME_FIRST = 2;

    /** Regex group index of the {@code id} attribute when it appears first (id, name order). */
    private static final int GROUP_ID_ID_FIRST = 3;

    /** Regex group index of the {@code name} attribute when it appears second (id, name order). */
    private static final int GROUP_NAME_ID_FIRST = 4;

    /** Maps a {@code sections} param key to the subsection's {@code name} attribute value. */
    private static final Map<String, String> SECTION_NAMES = Map.ofEntries(
            Map.entry(SECTION_DESCRIPTION, SECTION_DESCRIPTION),
            Map.entry(SECTION_PROPERTIES, SECTION_PROPERTIES),
            Map.entry(SECTION_EXAMPLES, SECTION_EXAMPLES),
            Map.entry(SECTION_USE_CASES_KEY, "Use Cases"),
            Map.entry("ExampleOfUsage", "Example of Usage"),
            Map.entry("ViolationMessages", "Violation Messages"),
            Map.entry("FullyQualifiedName", "Fully Qualified Name"),
            Map.entry("ParentModule", "Parent Module")
    );

    /** Section keys (from the {@code sections} param) that get nested sub-entries. */
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
        final String sectionsParam = (String) request.getParameter("sections");
        final String modulePath = (String) request.getParameter("modulePath");
        final String sourceContent = request.getSourceContent();

        final List<String> sections = List.of(sectionsParam.split(","));

        final Map<String, PropertyDetails> propertyDetails = loadPropertyDetails(modulePath);
        final Path repoRoot = resolveRepoRoot(modulePath);
        final TocContext context = new TocContext(sourceContent, propertyDetails, repoRoot);

        sink.rawText("<div class=\"toc-panel\">\n");
        sink.rawText("  <input type=\"checkbox\" id=\"toc-toggle\" "
                + "class=\"toc-toggle-checkbox\" checked=\"checked\"/>\n");
        sink.rawText("  <label for=\"toc-toggle\" class=\"toc-toggle-arrow\" "
                + "title=\"Collapse\"></label>\n");
        sink.rawText("  <div class=\"toc-content\">\n");
        sink.rawText("    <p class=\"toc-heading\">On This Page</p>\n");
        sink.rawText("    <ul class=\"toc-list\">\n");

        for (String section : sections) {
            writeSectionEntry(sink, section, context);
        }

        sink.rawText("    </ul>\n");
        sink.rawText("  </div>\n");
        sink.rawText("</div>");
    }

    /**
     * Writes a single top-level {@code <li>}, with nested Example/UseCase
     * entries when the section is Examples or Use Cases. The anchor id is
     * read directly from the matching {@code <subsection>} tag's actual
     * {@code id} attribute in the source, rather than assumed from a naming
     * convention, so the link can never drift from what the page really
     * renders.
     *
     * @param sink sink to write to.
     * @param section the raw section key.
     * @param context shared per-page context for resolving anchors and titles.
     */
    private static void writeSectionEntry(Sink sink, String section, TocContext context) {
        final String sectionName = SECTION_NAMES.get(section);

        if (sectionName != null) {
            final Optional<String> anchorId =
                    findSubsectionAnchor(context.sourceContent(), sectionName);

            if (anchorId.isPresent()) {
                final String anchor = anchorId.get();
                sink.rawText("          <li><a href=\"#" + anchor + QUOTE_CLOSE_TAG
                        + sectionName + "</a>");

                if (NESTED_SECTIONS.contains(section)) {
                    sink.rawText("\n");
                    final String body =
                            extractSectionBody(context.sourceContent(), anchor);
                    writeNestedItems(sink, body, context);
                    sink.rawText("          </li>\n");
                }
                else {
                    sink.rawText("</li>\n");
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
     * within a subsection's body text, titled by property=value when
     * possible, falling back to a shortened descriptive sentence. Titles
     * are shown in full and allowed to wrap across lines, rather than being
     * truncated with an ellipsis, so the whole label is always readable.
     *
     * @param sink sink to write to.
     * @param sectionBody the raw text of the subsection.
     * @param context shared per-page context for resolving titles.
     */
    private static void writeNestedItems(Sink sink, String sectionBody, TocContext context) {
        final Matcher itemMatcher = ITEM_WITH_PATH_PATTERN.matcher(sectionBody);
        boolean hasItems = false;

        while (itemMatcher.find()) {
            if (!hasItems) {
                sink.rawText("            <ul class=\"toc-sublist\">\n");
                hasItems = true;
            }
            final String anchorId = itemMatcher.group(1);
            final String rawParagraph = itemMatcher.group(2);
            final String fallbackTitle;
            if (rawParagraph == null) {
                fallbackTitle = DEFAULT_TITLE;
            }
            else {
                fallbackTitle = toSentenceTitle(rawParagraph);
            }
            final String examplePath = itemMatcher.group(GROUP_ID_ID_FIRST);

            final String title;
            if (examplePath == null) {
                title = fallbackTitle;
            }
            else {
                title = derivePropertyTitle(examplePath, context).orElse(fallbackTitle);
            }

            sink.rawText("              <li><a href=\"#" + anchorId + "-config" + QUOTE_CLOSE_TAG
                    + title + "</a></li>\n");
        }

        if (hasItems) {
            sink.rawText("            </ul>\n");
        }
    }

    /**
     * Loads documented property defaults for the current module, if a
     * {@code modulePath} param was supplied. Returns an empty map otherwise,
     * or if lookup fails for any reason -- property-based titling is a
     * nice-to-have, not something that should break page generation.
     *
     * @param modulePath path to the module's Java source, or {@code null}.
     * @return a map of property name to its documented details.
     */
    private static Map<String, PropertyDetails> loadPropertyDetails(String modulePath) {
        Map<String, PropertyDetails> result = Map.of();
        if (modulePath != null) {
            try {
                final Path modulePathObj = Path.of(modulePath.replace('\\', '/'));
                final Path fileName = modulePathObj.getFileName();
                if (fileName != null) {
                    final String moduleName = fileName.toString().replace("Check.java", "");
                    final Object instance = SiteUtil.getModuleInstance(moduleName);
                    result = SiteUtil.buildPropertyDetails(
                            SiteUtil.getPropertiesForDocumentation(instance.getClass(), instance),
                            moduleName, modulePathObj, instance);
                }
            }
            catch (MacroExecutionException ignored) {
                result = Map.of();
            }
        }
        return result;
    }

    /**
     * Derives the repository root from the module's source path so example
     * resource paths (relative to {@link #EXAMPLES_BASE_DIR}) can be resolved
     * to an absolute file location.
     *
     * @param modulePath path to the module's Java source, or {@code null}.
     * @return the repository root, or the current working directory if
     *         {@code modulePath} is absent or doesn't contain the expected marker.
     */
    private static Path resolveRepoRoot(String modulePath) {
        Path result = Path.of("");
        if (modulePath != null) {
            final String normalized = modulePath.replace('\\', '/');
            final int marker = normalized.indexOf("src/main/java");
            if (marker > 0) {
                result = Path.of(normalized.substring(0, marker));
            }
        }
        return result;
    }

    /**
     * Reads an example's referenced source file and finds the first property
     * whose value differs from its documented default, skipping {@code tokens}.
     *
     * @param examplePath the {@code path} param value from the example macro.
     * @param context shared per-page context providing property defaults and repo root.
     * @return an "name=value" title, or empty if the file can't be read or
     *         every property in it matches its documented default.
     */
    private static Optional<String> derivePropertyTitle(String examplePath, TocContext context) {
        Optional<String> result = Optional.empty();
        final Map<String, PropertyDetails> propertyDetails = context.propertyDetails();
        if (!propertyDetails.isEmpty()) {
            try {
                final Path fullPath = context.repoRoot()
                        .resolve(EXAMPLES_BASE_DIR + examplePath.replace('\\', '/'));
                final String content = Files.readString(fullPath);
                final Matcher propMatcher = PROPERTY_PATTERN.matcher(content);

                while (propMatcher.find() && result.isEmpty()) {
                    result = extractPropertyTitle(propMatcher, propertyDetails);
                }
            }
            catch (IOException ignored) {
                result = Optional.empty();
            }
        }
        return result;
    }

    /**
     * Extracts a property title from a matcher group if the property differs
     * from its default value.
     *
     * @param propMatcher the property matcher positioned at a match.
     * @param propertyDetails the documented property defaults.
     * @return an "name=value" title, or empty if the property matches default.
     */
    private static Optional<String> extractPropertyTitle(
            Matcher propMatcher, Map<String, PropertyDetails> propertyDetails) {
        final String name = propMatcher.group(1);
        final String value = propMatcher.group(2);
        Optional<String> result = Optional.empty();

        if (name != null && value != null && !TOKENS_PROPERTY.equals(name)) {
            final PropertyDetails details = propertyDetails.get(name);
            final boolean isDefault = details == null
                    || Objects.equals(details.getDefaultValue(), value);
            if (!isDefault) {
                result = Optional.of(name + "=" + value);
            }
        }
        return result;
    }

    /**
     * Converts a raw "ExampleN-config"/"UseCaseN-config" paragraph into a
     * short sentence-based title by removing inline markup, normalizing
     * whitespace, stripping the common lead-in clause, and trimming the
     * trailing colon. Used as a fallback when no distinguishing property
     * can be found.
     *
     * @param rawParagraph the paragraph's raw inner text.
     * @return a short, human-readable title.
     */
    private static String toSentenceTitle(String rawParagraph) {
        String text = TAG_PATTERN.matcher(rawParagraph).replaceAll("");
        text = normalizeWhitespace(text);
        text = LEAD_IN_PATTERN.matcher(text).replaceFirst("");
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

    /**
     * Bundles the per-page context needed while writing TOC entries, so
     * helper methods don't need long parameter lists.
     *
     * @param sourceContent the full template source text.
     * @param propertyDetails documented property defaults for this module.
     * @param repoRoot repository root, for resolving example file paths.
     */
    private record TocContext(String sourceContent,
                              Map<String, PropertyDetails> propertyDetails, Path repoRoot) {
    }

}
