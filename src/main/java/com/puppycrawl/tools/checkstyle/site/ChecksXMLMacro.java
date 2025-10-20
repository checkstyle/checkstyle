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

import java.nio.file.Path;
import java.util.regex.Pattern;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * A macro that inserts the first sentence (summary) of a Check module's Javadoc,
 * cleaned of HTML tags and links for safe inclusion in xdoc XML.
 *
 * <p>This class is used during site generation to dynamically populate
 * {@code checks.xml} content using module-level documentation.</p>
 */
@Component(role = Macro.class, hint = "checks")
public class ChecksXMLMacro extends AbstractMacro {

    /** Pattern to remove structural HTML tags like div, p, span, em, strong. */
    private static final Pattern STRUCTURAL_TAG_PATTERN =
            Pattern.compile("(?is)</?(?:div|p|span|em|strong)[^>]*>");

    /** Pattern to remove HTML anchor tags (<a>...</a>). */
    private static final Pattern ANCHOR_TAG_PATTERN =
            Pattern.compile("(?is)<a[^>]*?>|</a>");

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final Object param = request.getParameter("modulePath");
        if (param == null) {
            throw new MacroExecutionException("Parameter 'modulePath' is required.");
        }

        final Path modulePath = Path.of((String) param);
        final String moduleName = CommonUtil.getFileNameWithoutExtension(modulePath.toString());

        final DetailNode moduleJavadoc = SiteUtil.getModuleJavadoc(moduleName, modulePath);
        if (moduleJavadoc == null) {
            throw new MacroExecutionException("Javadoc of module " + moduleName + " not found.");
        }

        final String moduleDescription = ModuleJavadocParsingUtil.getModuleDescription(moduleJavadoc);

        final String cleanDescription = sanitize(moduleDescription);
        final String summarySentence = extractFirstSentence(cleanDescription);

        sink.rawText(summarySentence);
    }

    /**
     * Extracts the first sentence (until the first period followed by whitespace or end).
     *
     * @param description the full module description text
     * @return first sentence of description
     */
    private static String extractFirstSentence(String description) {
        if (description == null) {
            return "";
        }

        int endIndex = -1;
        for (int i = 0; i < description.length(); i++) {
            if (description.charAt(i) == '.') {
                if (i == description.length() - 1
                        || Character.isWhitespace(description.charAt(i + 1))
                        || description.charAt(i + 1) == '<') {
                    endIndex = i;
                    break;
                }
            }
        }

        if (endIndex != -1) {
            return description.substring(0, endIndex + 1).trim();
        }
        return description.trim();
    }

    /**
     * Cleans up unwanted HTML tags, leaving readable text only.
     * Preserves inline formatting tags like {@code <code>}.
     *
     * @param html the HTML text to clean
     * @return sanitized text without unwanted tags
     */
    private static String sanitize(String html) {
        if (html == null) {
            return "";
        }

        String cleaned = html;
        cleaned = ANCHOR_TAG_PATTERN.matcher(cleaned).replaceAll("");
        cleaned = STRUCTURAL_TAG_PATTERN.matcher(cleaned).replaceAll("");
        return cleaned.replaceAll("\\s+", " ").trim();
    }
}
