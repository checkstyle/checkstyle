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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

/**
 * A macro that renders a plain-HTML "In this article" table of contents
 * for a check, filter, or file filter XDoc page.
 *
 * <p>Usage in {@code .xml.template} files:</p>
 * <pre>
 *   &lt;macro name="toc"&gt;
 *     &lt;param name="id" value="AnnotationLocation"/&gt;
 *     &lt;param name="sections"
 *            value="Description,Notes,Properties,Examples,
 *                   ExampleOfUsage,ViolationMessages,
 *                   FullyQualifiedName,ParentModule"/&gt;
 *   &lt;/macro&gt;
 * </pre>
 *
 * <p>The {@code id} parameter must match the {@code name} attribute of the
 * enclosing {@code &lt;section&gt;} element (i.e. the check or filter name).
 * It is used to build Doxia subsection anchor ids following the pattern
 * {@code &lt;SectionName&gt;_&lt;SubsectionToken&gt;}.</p>
 *
 * <p>The {@code sections} parameter is a comma-separated ordered list of
 * subsection tokens matching the subsections present on the specific page.
 * Each template declares exactly the sections it has, so pages with no
 * Properties, no Notes, or extra sections all render correctly without
 * any assumptions in this class. Supported tokens and their display names
 * (verified against {@code XdocsPagesTest}):</p>
 * <ul>
 *   <li>{@code Description} - "Description"</li>
 *   <li>{@code Notes} - "Notes"</li>
 *   <li>{@code RuleDescription} - "Rule Description"</li>
 *   <li>{@code Metadata} - "Metadata"</li>
 *   <li>{@code Properties} - "Properties"</li>
 *   <li>{@code Examples} - "Examples"</li>
 *   <li>{@code ExampleOfUsage} - "Example of Usage"</li>
 *   <li>{@code ViolationMessages} - "Violation Messages"</li>
 *   <li>{@code FullyQualifiedName} - "Fully Qualified Name"</li>
 *   <li>{@code ParentModule} - "Parent Module"</li>
 * </ul>
 *
 * <p>Output is a plain {@code &lt;div&gt;} containing a {@code &lt;ul&gt;}
 * of {@code &lt;a href="#..."&gt;} anchor links - no JavaScript required,
 * works on all devices including mobile.</p>
 */
@Component(role = Macro.class, hint = "toc")
public class TocMacro extends AbstractMacro {

    /** Parameter name for the check/filter section id. */
    private static final String PARAM_ID = "id";

    /** Parameter name for the ordered comma-separated list of section tokens. */
    private static final String PARAM_SECTIONS = "sections";

    /** Separator used between section tokens in the {@code sections} parameter. */
    private static final String SECTION_SEPARATOR = ",";

    /** Doxia anchor separator between section name and subsection token. */
    private static final String ANCHOR_SEP = "_";

    /** Opening HTML for the TOC container. */
    private static final String DIV_OPEN =
        "<div class=\"in-this-article\">\n<p><b>In this article:</b></p>\n<ul>\n";

    /** Closing HTML for the TOC container. */
    private static final String DIV_CLOSE = "</ul>\n</div>\n";

    /** HTML template for a single TOC list item. */
    private static final String LI_FORMAT = "<li><a href=\"#%s\">%s</a></li>%n";

    /** Common message prefix used for both missing and blank required parameter errors. */
    private static final String ERROR_REQUIRED_PARAM_PREFIX = "TocMacro: required parameter '";

    /** Shared literal for the "Description" section token/display name. */
    private static final String SECTION_DESCRIPTION = "Description";

    /** Shared literal for the "Notes" section token/display name. */
    private static final String SECTION_NOTES = "Notes";

    /** Shared literal for the "Metadata" section token/display name. */
    private static final String SECTION_METADATA = "Metadata";

    /** Shared literal for the "Properties" section token/display name. */
    private static final String SECTION_PROPERTIES = "Properties";

    /** Shared literal for the "Examples" section token/display name. */
    private static final String SECTION_EXAMPLES = "Examples";

    /**
     * Maps each recognised section token to its display label, in canonical
     * page order (verified against {@code XdocsPagesTest.validateCheckSection}).
     * Rendering order in the TOC is controlled by the {@code sections}
     * parameter in each template, not by insertion order here.
     */
    private static final Map<String, String> SECTION_DISPLAY_NAMES = new LinkedHashMap<>();

    static {
        SECTION_DISPLAY_NAMES.put(SECTION_DESCRIPTION, SECTION_DESCRIPTION);
        SECTION_DISPLAY_NAMES.put(SECTION_NOTES, SECTION_NOTES);
        SECTION_DISPLAY_NAMES.put("RuleDescription", "Rule Description");
        SECTION_DISPLAY_NAMES.put(SECTION_METADATA, SECTION_METADATA);
        SECTION_DISPLAY_NAMES.put(SECTION_PROPERTIES, SECTION_PROPERTIES);
        SECTION_DISPLAY_NAMES.put(SECTION_EXAMPLES, SECTION_EXAMPLES);
        SECTION_DISPLAY_NAMES.put("ExampleOfUsage", "Example of Usage");
        SECTION_DISPLAY_NAMES.put("ViolationMessages", "Violation Messages");
        SECTION_DISPLAY_NAMES.put("FullyQualifiedName", "Fully Qualified Name");
        SECTION_DISPLAY_NAMES.put("ParentModule", "Parent Module");
    }

    @Override
    public void execute(Sink sink, MacroRequest request)
            throws MacroExecutionException {
        Objects.requireNonNull(sink, "sink must not be null");
        Objects.requireNonNull(request, "request must not be null");

        final String sectionId = getRequiredParameter(request, PARAM_ID);
        final String sectionsParam = getRequiredParameter(request, PARAM_SECTIONS);

        final List<String> tokens = Arrays.stream(sectionsParam.split(SECTION_SEPARATOR))
            .map(String::trim)
            .filter(token -> !token.isEmpty())
            .toList();

        if (tokens.isEmpty()) {
            throw new MacroExecutionException(
                "TocMacro: parameter 'sections' must contain at least one token.");
        }

        validateTokens(tokens, sectionId);
        sink.rawText(buildTocHtml(sectionId, tokens));
    }

    /**
     * Retrieves a required String parameter from the macro request, throwing
     * a descriptive {@link MacroExecutionException} if it is absent or blank.
     *
     * @param request   the macro request to read from
     * @param paramName the name of the parameter to retrieve
     * @return the trimmed parameter value, never null or blank
     * @throws MacroExecutionException if the parameter is absent or blank
     */
    private static String getRequiredParameter(MacroRequest request, String paramName)
            throws MacroExecutionException {
        final Object raw = request.getParameter(paramName);
        if (raw == null) {
            throw new MacroExecutionException(
                ERROR_REQUIRED_PARAM_PREFIX + paramName + "' is missing.");
        }
        final String value = raw.toString().trim();
        if (value.isEmpty()) {
            throw new MacroExecutionException(
                ERROR_REQUIRED_PARAM_PREFIX + paramName + "' must not be blank.");
        }
        return value;
    }

    /**
     * Validates that every token in the list is a recognised section name.
     *
     * @param tokens    the list of section tokens from the template
     * @param sectionId the check/filter name, used in error messages
     * @throws MacroExecutionException if any token is unrecognised
     */
    private static void validateTokens(List<String> tokens, String sectionId)
            throws MacroExecutionException {
        for (String token : tokens) {
            if (!SECTION_DISPLAY_NAMES.containsKey(token)) {
                throw new MacroExecutionException(
                    "TocMacro: unrecognised section token '"
                        + token + "' in 'sections' parameter for '"
                        + sectionId + "'. Valid tokens: "
                        + SECTION_DISPLAY_NAMES.keySet());
            }
        }
    }

    /**
     * Builds the complete TOC HTML string for the given section id and
     * ordered list of section tokens.
     *
     * <p>Anchor ids are derived from the subsection display name by replacing
     * spaces with underscores, which matches the Doxia convention used in the
     * explicit {@code id} attributes on each {@code <subsection>} element.
     * For example, display name {@code "Example of Usage"} becomes the anchor
     * suffix {@code "Example_of_Usage"}, yielding a full anchor id of
     * {@code "AvoidNestedBlocks_Example_of_Usage"}.</p>
     *
     * @param sectionId the check/filter section name, never null
     * @param tokens    ordered list of validated section tokens, never null or empty
     * @return the complete HTML string for the TOC div, never null
     */
    private static String buildTocHtml(String sectionId, List<String> tokens) {
        final StringBuilder html = new StringBuilder(256);
        html.append(DIV_OPEN);
        for (String token : tokens) {
            final String displayName = SECTION_DISPLAY_NAMES.get(token);
            final String anchorSuffix = displayName.replace(' ', '_');
            final String anchorId = sectionId + ANCHOR_SEP + anchorSuffix;
            html.append(String.format(Locale.ROOT, LI_FORMAT, anchorId, displayName));
        }
        html.append(DIV_CLOSE);
        return html.toString();
    }
}
