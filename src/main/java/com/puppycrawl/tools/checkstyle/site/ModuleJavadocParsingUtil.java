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

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.sink.Sink;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * Utility class for parsing javadocs of modules.
 */
public final class ModuleJavadocParsingUtil {
    /** New line escape character. */
    public static final String NEWLINE = System.lineSeparator();
    /** A newline with 8 spaces of indentation. */
    public static final String INDENT_LEVEL_8 = SiteUtil.getNewlineAndIndentSpaces(8);
    /** A newline with 10 spaces of indentation. */
    public static final String INDENT_LEVEL_10 = SiteUtil.getNewlineAndIndentSpaces(10);
    /** A newline with 12 spaces of indentation. */
    public static final String INDENT_LEVEL_12 = SiteUtil.getNewlineAndIndentSpaces(12);
    /** A newline with 14 spaces of indentation. */
    public static final String INDENT_LEVEL_14 = SiteUtil.getNewlineAndIndentSpaces(14);
    /** A newline with 16 spaces of indentation. */
    public static final String INDENT_LEVEL_16 = SiteUtil.getNewlineAndIndentSpaces(16);
    /** A newline with 18 spaces of indentation. */
    public static final String INDENT_LEVEL_18 = SiteUtil.getNewlineAndIndentSpaces(18);
    /** A newline with 20 spaces of indentation. */
    public static final String INDENT_LEVEL_20 = SiteUtil.getNewlineAndIndentSpaces(20);
    /** A set of all html tags that need to be considered as text formatting for this macro. */
    public static final Set<String> HTML_TEXT_FORMAT_TAGS = Set.of("<code>", "<a", "</a>", "<b>",
        "</b>", "<strong>", "</strong>", "<i>", "</i>", "<em>", "</em>", "<small>", "</small>",
        "<ins>", "<sub>", "<sup>");
    /** "Notes:" javadoc marking. */
    public static final String NOTES = "Notes:";
    /** "Notes:" line. */
    public static final Pattern NOTES_LINE = Pattern.compile("\\s*" + NOTES + "$");
    /** "Notes:" line with new line accounted. */
    public static final Pattern NOTES_LINE_WITH_NEWLINE = Pattern.compile("\r?\n\\s?" + NOTES);

    /**
     * Private utility constructor.
     */
    private ModuleJavadocParsingUtil() {
    }

    /**
     * Gets properties of the specified module.
     *
     * @param moduleName name of module.
     * @return set of properties name if present, otherwise null.
     * @throws MacroExecutionException if the module could not be retrieved.
     */
    public static Set<String> getPropertyNames(String moduleName)
            throws MacroExecutionException {
        final Object instance = SiteUtil.getModuleInstance(moduleName);
        final Class<?> clss = instance.getClass();

        return SiteUtil.getPropertiesForDocumentation(clss, instance);
    }

    /**
     * Determines whether the given HTML node marks the start of the "Notes" section.
     *
     * @param htmlElement html element to check.
     * @return true if the element starts the "Notes" section, false otherwise.
     */
    private static boolean isStartOfNotesSection(DetailNode htmlElement) {
        final DetailNode paragraphNode = JavadocUtil.findFirstToken(
            htmlElement, JavadocTokenTypes.PARAGRAPH);
        final Optional<DetailNode> liNode = getLiTagNode(htmlElement);

        return paragraphNode != null && JavadocMetadataScraper.isChildNodeTextMatches(
            paragraphNode, NOTES_LINE)
            || liNode.isPresent() && JavadocMetadataScraper.isChildNodeTextMatches(
                liNode.get(), NOTES_LINE);
    }

    /**
     * Gets the node of Li HTML tag.
     *
     * @param htmlElement html element to get li tag from.
     * @return Optional of li tag node.
     */
    public static Optional<DetailNode> getLiTagNode(DetailNode htmlElement) {
        return Optional.of(htmlElement)
            .map(element -> JavadocUtil.findFirstToken(element, JavadocTokenTypes.HTML_TAG))
            .map(element -> JavadocUtil.findFirstToken(element, JavadocTokenTypes.HTML_ELEMENT))
            .map(element -> JavadocUtil.findFirstToken(element, JavadocTokenTypes.LI));
    }

    /**
     * Writes the given javadoc chunk into xdoc.
     *
     * @param javadocPortion javadoc text.
     * @param sink sink of the macro.
     */
    public static void writeOutJavadocPortion(String javadocPortion, Sink sink) {
        final String[] javadocPortionLinesSplit = javadocPortion.split(NEWLINE
            .replace("\r", ""));

        sink.rawText(javadocPortionLinesSplit[0]);
        String lastHtmlTag = javadocPortionLinesSplit[0];

        for (int index = 1; index < javadocPortionLinesSplit.length; index++) {
            final String currentLine = javadocPortionLinesSplit[index].trim();
            final String processedLine;

            if (currentLine.isEmpty()) {
                processedLine = NEWLINE;
            }
            else if (currentLine.startsWith("<")
                && !startsWithTextFormattingHtmlTag(currentLine)) {

                processedLine = INDENT_LEVEL_8 + currentLine;
                lastHtmlTag = currentLine;
            }
            else if (lastHtmlTag.contains("<pre")) {
                final String currentLineWithPreservedIndent = javadocPortionLinesSplit[index]
                    .substring(1);

                processedLine = NEWLINE + currentLineWithPreservedIndent;
            }
            else {
                processedLine = INDENT_LEVEL_10 + currentLine;
            }

            sink.rawText(processedLine);
        }

    }

    /**
     * Checks if given line starts with HTML text-formatting tag.
     *
     * @param line line to check on.
     * @return whether given line starts with HTML text-formatting tag.
     */
    public static boolean startsWithTextFormattingHtmlTag(String line) {
        boolean result = false;

        for (String tag : HTML_TEXT_FORMAT_TAGS) {
            if (line.startsWith(tag)) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Gets the description of module from module javadoc.
     *
     * @param moduleJavadoc module javadoc.
     * @param propertyNames property names set.
     * @return module description.
     */
    public static String getModuleDescription(DetailNode moduleJavadoc, Set<String> propertyNames) {
        final int descriptionEndIndex = getDescriptionEndIndex(moduleJavadoc, propertyNames);

        return JavadocMetadataScraper.constructSubTreeText(moduleJavadoc, 0, descriptionEndIndex);
    }

    /**
     * Gets the end index of the description.
     *
     * @param moduleJavadoc javadoc of module.
     * @param propertyNamesSet Set with property names.
     * @return the end index.
     */
    public static int getDescriptionEndIndex(DetailNode moduleJavadoc,
                                              Set<String> propertyNamesSet) {
        int descriptionEndIndex = -1;

        final int notesStartingIndex =
            getNotesSectionStartIndex(moduleJavadoc);
        final int propertiesSectionStartingIndex =
            getPropertySectionStartIndex(moduleJavadoc, propertyNamesSet);
        final int parentStartingIndex =
            getParentSectionStartIndex(moduleJavadoc);

        if (notesStartingIndex > -1) {
            descriptionEndIndex += notesStartingIndex;
        }
        else if (propertiesSectionStartingIndex > -1) {
            descriptionEndIndex += propertiesSectionStartingIndex;
        }
        else if (parentStartingIndex > -1) {
            descriptionEndIndex += parentStartingIndex;
        }
        else {
            descriptionEndIndex += getModuleSinceVersionTagStartIndex(moduleJavadoc);
        }

        return descriptionEndIndex;
    }

    /**
     * Gets the start index of the Notes section.
     *
     * @param moduleJavadoc javadoc of module.
     * @return start index.
     */
    public static int getNotesSectionStartIndex(DetailNode moduleJavadoc) {
        int notesStartIndex = -1;

        for (DetailNode node : moduleJavadoc.getChildren()) {
            if (node.getType() == JavadocTokenTypes.HTML_ELEMENT
                && isStartOfNotesSection(node)) {

                notesStartIndex += node.getIndex();
                break;
            }
        }

        return notesStartIndex;
    }

    /**
     * Gets the start index of property section in module's javadoc.
     *
     * @param moduleJavadoc javadoc of module.
     * @param propertyNames set with property names.
     * @return index of property section.
     */
    public static int getPropertySectionStartIndex(DetailNode moduleJavadoc,
                                                   Set<String> propertyNames) {
        int propertySectionStartIndex = -1;

        if (!propertyNames.isEmpty()) {
            final String somePropertyName = propertyNames.iterator().next();
            final Optional<DetailNode> somePropertyModuleNode =
                SiteUtil.getPropertyJavadocNodeInModule(somePropertyName, moduleJavadoc);

            if (somePropertyModuleNode.isPresent()) {
                propertySectionStartIndex = JavadocMetadataScraper.getParentIndexOf(
                    somePropertyModuleNode.get());
            }
        }

        return propertySectionStartIndex;
    }

    /**
     * Gets the starting index of the "Parent is" paragraph in module's javadoc.
     *
     * @param moduleJavadoc javadoc of module.
     * @return start index of parent subsection.
     */
    public static int getParentSectionStartIndex(DetailNode moduleJavadoc) {
        int parentStartIndex = -1;

        for (DetailNode node : moduleJavadoc.getChildren()) {
            if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                final DetailNode paragraphNode = JavadocUtil.findFirstToken(
                    node, JavadocTokenTypes.PARAGRAPH);
                if (paragraphNode != null && JavadocMetadataScraper.isParentText(paragraphNode)) {
                    parentStartIndex = node.getIndex();
                    break;
                }
            }
        }

        return parentStartIndex;
    }

    /**
     * Gets the starting index of the "@since" version tag in module's javadoc.
     *
     * @param moduleJavadoc javadoc of module.
     * @return start index of "@since".
     */
    public static int getModuleSinceVersionTagStartIndex(DetailNode moduleJavadoc) {
        return SiteUtil.getNodesOfSpecificType(moduleJavadoc.getChildren(),
                JavadocTokenTypes.JAVADOC_TAG).stream()
            .filter(javadocTag -> {
                return JavadocUtil
                    .findFirstToken(javadocTag, JavadocTokenTypes.SINCE_LITERAL) != null;
            })
            .map(DetailNode::getIndex)
            .findFirst()
            .orElse(-1);
    }

    /**
     * Gets the Notes section of module from module javadoc.
     *
     * @param moduleJavadoc module javadoc.
     * @param propertyNames property names set.
     * @return Notes section of module.
     */
    public static String getModuleNotes(DetailNode moduleJavadoc, Set<String> propertyNames) {
        final String result;

        final int notesStartIndex = getNotesSectionStartIndex(moduleJavadoc);

        if (notesStartIndex < 0) {
            result = "";
        }
        else {
            final int notesEndIndex = getNotesEndIndex(moduleJavadoc, propertyNames);

            final String unprocessedNotes = JavadocMetadataScraper.constructSubTreeText(
                moduleJavadoc, notesStartIndex, notesEndIndex);

            result = NOTES_LINE_WITH_NEWLINE.matcher(unprocessedNotes).replaceAll("");
        }

        return result;
    }

    /**
     * Gets the end index of the Notes.
     *
     * @param moduleJavadoc javadoc of module.
     * @param propertyNamesSet Set with property names.
     * @return the end index.
     */
    public static int getNotesEndIndex(DetailNode moduleJavadoc,
                                        Set<String> propertyNamesSet) {
        int notesEndIndex = -1;

        final int parentStartingIndex = getParentSectionStartIndex(moduleJavadoc);
        final int propertiesSectionStartingIndex =
            getPropertySectionStartIndex(moduleJavadoc, propertyNamesSet);

        if (propertiesSectionStartingIndex > -1) {
            notesEndIndex += propertiesSectionStartingIndex;
        }
        else if (parentStartingIndex > -1) {
            notesEndIndex += parentStartingIndex;
        }
        else {
            notesEndIndex += getModuleSinceVersionTagStartIndex(moduleJavadoc);
        }

        return notesEndIndex;
    }

    /**
     * Checks whether property is to contain tokens.
     *
     * @param propertyField property field.
     * @return true if property is to contain tokens, false otherwise.
     */
    public static boolean isPropertySpecialTokenProp(Field propertyField) {
        boolean result = false;

        if (propertyField != null) {
            final XdocsPropertyType fieldXdocAnnotation =
                propertyField.getAnnotation(XdocsPropertyType.class);

            result = fieldXdocAnnotation != null
                && fieldXdocAnnotation.value() == PropertyType.TOKEN_ARRAY;
        }

        return result;
    }

}
