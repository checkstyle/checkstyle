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
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.sink.Sink;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraperUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * Utility class for parsing javadocs of modules.
 */
public final class ModuleJavadocParsingUtil {
    /** New line escape character. */
    public static final String NEWLINE = System.lineSeparator();
    /** A newline with 4 spaces of indentation. */
    public static final String INDENT_LEVEL_4 = SiteUtil.getNewlineAndIndentSpaces(4);
    /** A newline with 6 spaces of indentation. */
    public static final String INDENT_LEVEL_6 = SiteUtil.getNewlineAndIndentSpaces(6);
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
        final DetailNode htmlContentNode = JavadocUtil.findFirstToken(
            htmlElement, JavadocCommentsTokenTypes.HTML_CONTENT);

        return htmlContentNode != null && JavadocMetadataScraperUtil.isChildNodeTextMatches(
            htmlContentNode, NOTES_LINE);
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
     * @return module description.
     */
    public static String getModuleDescription(DetailNode moduleJavadoc) {
        final DetailNode descriptionEndNode = getDescriptionEndNode(moduleJavadoc);

        return JavadocMetadataScraperUtil.constructSubTreeText(moduleJavadoc, descriptionEndNode);
    }

    /**
     * Gets the end node of the description.
     *
     * @param moduleJavadoc javadoc of module.
     * @return the end index.
     */
    public static DetailNode getDescriptionEndNode(DetailNode moduleJavadoc) {
        final DetailNode descriptionEndNode;

        final DetailNode notesStartingNode =
            getNotesSectionStartNode(moduleJavadoc);

        if (notesStartingNode != null) {
            descriptionEndNode = notesStartingNode.getPreviousSibling();
        }
        else {
            descriptionEndNode = getNodeBeforeJavadocTags(moduleJavadoc);
        }

        return descriptionEndNode;
    }

    /**
     * Gets the start node of the Notes section.
     *
     * @param moduleJavadoc javadoc of module.
     * @return start node.
     */
    public static DetailNode getNotesSectionStartNode(DetailNode moduleJavadoc) {
        DetailNode notesStartNode = null;
        DetailNode node = moduleJavadoc.getFirstChild();

        while (node != null) {
            if (node.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT) {
                boolean found = false;
                if (JavadocUtil.isTag(node, "ul")) {
                    final DetailNode htmlContentNode = JavadocUtil.findFirstToken(
                        node, JavadocCommentsTokenTypes.HTML_CONTENT);
                    if (isStartOfNotesSection(htmlContentNode.getFirstChild())) {
                        notesStartNode = node;
                        found = true;
                    }
                }
                else if ((JavadocUtil.isTag(node, "p")
                            || JavadocUtil.isTag(node, "li"))
                            && isStartOfNotesSection(node)) {
                    notesStartNode = node;
                    found = true;
                }
                if (found) {
                    break;
                }
            }
            node = node.getNextSibling();
        }

        return notesStartNode;
    }

    /**
     * Gets the node representing the start of the {@code @since} version tag
     * in the module's Javadoc.
     *
     * @param moduleJavadoc the root Javadoc node of the module
     * @return the {@code @since} tag start node, or {@code null} if not found
     */
    public static DetailNode getModuleSinceVersionTagStartNode(DetailNode moduleJavadoc) {
        return JavadocUtil.getAllNodesOfType(
                moduleJavadoc, JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG).stream()
            .filter(javadocTag -> {
                return javadocTag.getFirstChild().getType()
                        == JavadocCommentsTokenTypes.SINCE_BLOCK_TAG;
            })
            .findFirst()
            .orElse(null);
    }

    /**
     * Gets the node of module's javadoc whose next sibling is a node that defines a javadoc tag.
     *
     * @param moduleJavadoc the root Javadoc node of the module
     * @return the node that precedes node defining javadoc tag if present,
     *     otherwise just the last node of module's javadoc.
     */
    public static DetailNode getNodeBeforeJavadocTags(DetailNode moduleJavadoc) {
        DetailNode nodeBeforeJavadocTags = moduleJavadoc.getFirstChild();

        while (nodeBeforeJavadocTags.getNextSibling() != null
                && nodeBeforeJavadocTags.getNextSibling().getType()
                    != JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG) {

            nodeBeforeJavadocTags = nodeBeforeJavadocTags.getNextSibling();
        }

        return nodeBeforeJavadocTags;
    }

    /**
     * Gets the Notes section of module from module javadoc.
     *
     * @param moduleJavadoc module javadoc.
     * @return Notes section of module.
     */
    public static String getModuleNotes(DetailNode moduleJavadoc) {
        final String result;

        final DetailNode notesStartNode = getNotesSectionStartNode(moduleJavadoc);

        if (notesStartNode == null) {
            result = "";
        }
        else {
            final DetailNode notesEndNode = getNodeBeforeJavadocTags(moduleJavadoc);

            final String unprocessedNotes =
                    JavadocMetadataScraperUtil.constructSubTreeText(
                        notesStartNode, notesEndNode);
            result = NOTES_LINE_WITH_NEWLINE.matcher(unprocessedNotes).replaceAll("");
        }

        return result;
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
