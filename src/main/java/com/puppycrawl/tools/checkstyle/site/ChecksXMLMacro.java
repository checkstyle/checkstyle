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
 */
@Component(role = Macro.class, hint = "checks")
public class ChecksXMLMacro extends AbstractMacro {

    private static final Pattern STRUCTURAL_TAG_PATTERN =
            Pattern.compile("(?is)</?(?:div|p|span|em|strong)[^>]*>");
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
     * Preserves inline formatting tags like &lt;code&gt;.
     */
    private static String sanitize(String html) {
        if (html == null) {
            return "";
        }

        String cleaned = html;
        cleaned = ANCHOR_TAG_PATTERN.matcher(cleaned).replaceAll("");
        cleaned = STRUCTURAL_TAG_PATTERN.matcher(cleaned).replaceAll("");
        cleaned = cleaned.replaceAll("\\s+", " ").trim();

        return cleaned;
    }
}