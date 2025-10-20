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
public class ChecksXmlMacro extends AbstractMacro {

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
            throw new MacroExecutionException(
                    "Javadoc of module " + moduleName + " not found.");
        }

        final String moduleDescription =
                ModuleJavadocParsingUtil.getModuleDescription(moduleJavadoc);
        final String cleanDescription = sanitize(moduleDescription);
        final String summarySentence = extractFirstSentence(cleanDescription);

        final String textToWrap = summarySentence.trim();
        final String formatted = wrapText(textToWrap, 70);
        sink.rawText(formatted);
    }

    /**
     * Extracts the first sentence (until the first period followed by whitespace or end).
     *
     * @param description the full module description text
     * @return first sentence of description
     */
    private static String extractFirstSentence(String description) {
        String result = "";
        if (description != null) {
            int endIndex = -1;
            final int descriptionLength = description.length();
            for (int index = 0; index < descriptionLength; index++) {
                if (description.charAt(index) == '.') {
                    final boolean hasNext = index + 1 < descriptionLength;
                    if (!hasNext
                            || (hasNext && (Character.isWhitespace(description.charAt(index + 1))
                            || description.charAt(index + 1) == '<'))) {
                        endIndex = index;
                        break;
                    }
                }
            }
            if (endIndex == -1) {
                result = description;
            } else {
                result = description.substring(0, endIndex + 1);
            }
        }
        return result.trim();
    }

    /**
     * Wraps text at approximately {@code wrapLimit} characters without breaking words.
     * Ensures total line length (including indentation and XML tags) ≤ 100 chars.
     *
     * @param text the text to wrap
     * @param wrapLimit maximum number of characters per line before wrapping
     * @return wrapped text
     */
    private static String wrapText(String text, int wrapLimit) {
        String result = "";

        if (!text.isEmpty()) {
            final StringBuilder wrapped = new StringBuilder();
            String remaining = text;
            int remainingLength = remaining.length();
            while (remainingLength > wrapLimit) {
                int breakIndex = remaining.lastIndexOf(' ', wrapLimit);
                if (breakIndex <= 0 || breakIndex > remainingLength) {
                    breakIndex = Math.min(wrapLimit, remainingLength);
                }

                final int safeBreakIndex = Math.min(Math.max(0, breakIndex), remainingLength);
                if (safeBreakIndex > 0) {
                    wrapped.append(remaining, 0, safeBreakIndex).append('\n');
                }

                final int safeIndex = Math.min(Math.max(0, breakIndex), remainingLength);
                remaining = remaining.substring(safeIndex).trim();
                remainingLength = remaining.length();
            }

            wrapped.append(remaining);
            result = wrapped.toString();
        }

        return result;
    }

    /**
     * Cleans up unwanted HTML tags, leaving readable text only.
     * Preserves inline formatting tags like {@code <code>}.
     *
     * @param html the HTML text to clean
     * @return sanitized text without unwanted tags
     */
    private static String sanitize(String html) {
        String cleaned = "";
        if (html != null) {
            cleaned = ANCHOR_TAG_PATTERN.matcher(html).replaceAll("");
            cleaned = STRUCTURAL_TAG_PATTERN.matcher(cleaned).replaceAll("");
            cleaned = cleaned.replaceAll("\\s+", " ");
        }
        return cleaned.trim();
    }
}
