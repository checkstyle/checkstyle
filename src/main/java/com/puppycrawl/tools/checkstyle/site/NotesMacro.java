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
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * A macro that inserts a notes subsection of module from its Javadoc.
 */
@Component(role = Macro.class, hint = "notes")
public class NotesMacro extends AbstractMacro {

    /** New line escape character. */
    private static final String NEWLINE = "\n";
    /** A newline with 8 spaces of indentation. */
    private static final String INDENT_LEVEL_8 = SiteUtil.getNewlineAndIndentSpaces(8);
    /** A newline with 10 spaces of indentation. */
    private static final String INDENT_LEVEL_10 = SiteUtil.getNewlineAndIndentSpaces(10);

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final Path modulePath = Paths.get((String) request.getParameter("modulePath"));
        final String moduleName = CommonUtil.getFileNameWithoutExtension(modulePath.toString());

        final Set<String> propertyNames = getPropertyNames(moduleName);
        final Map<String, DetailNode> propertiesJavadocs = SiteUtil.getPropertiesJavadocs(
            propertyNames, moduleName, modulePath);

        final DetailNode moduleJavadoc = propertiesJavadocs.get(moduleName);

        final int notesStartIndex = getNotesStartIndex(moduleJavadoc);
        final int notesEndIndex = getNotesEndIndex(moduleJavadoc, propertyNames);

        final String moduleNotes = JavadocMetadataScraper.constructSubTreeText(
            moduleJavadoc, notesStartIndex, notesEndIndex);

        writeOutNotes(moduleNotes, sink);

    }

    /**
     * Assigns values to each instance variable.
     *
     * @param moduleName name of module.
     * @return set of property names.
     * @throws MacroExecutionException if the module could not be retrieved.
     */
    private static Set<String> getPropertyNames(String moduleName)
            throws MacroExecutionException {
        final Object instance = SiteUtil.getModuleInstance(moduleName);
        final Class<?> clss = instance.getClass();

        return SiteUtil.getPropertiesForDocumentation(clss, instance);
    }

    /**
     * Gets the start index of the Notes section.
     *
     * @param moduleJavadoc javadoc of module.
     * @return start index.
     */
    private static int getNotesStartIndex(DetailNode moduleJavadoc) {
        int notesStartIndex = 0;

        for (DetailNode node : moduleJavadoc.getChildren()) {
            if (node.getType() == JavadocTokenTypes.TEXT) {
                if ("Notes:".equals(node.getText())) {
                    notesStartIndex = node.getIndex();
                    break;
                }
            }
        }

        return notesStartIndex;
    }

    /**
     * Gets the end index of the Notes.
     *
     * @param moduleJavadoc javadoc of module.
     * @param propertyNamesSet Set with property names.
     * @return the end index.
     */
    private static int getNotesEndIndex(DetailNode moduleJavadoc,
                                              Set<String> propertyNamesSet) {
        int notesEndIndex = -1;

        if (propertyNamesSet.isEmpty()) {
            notesEndIndex += getParentSectionStartIndex(moduleJavadoc);
        }
        else {
            final String somePropertyName = propertyNamesSet.iterator().next();

            final Optional<DetailNode> somePropertyModuleNode =
                SiteUtil.getPropertyJavadocNodeInModule(
                    somePropertyName, moduleJavadoc);

            if (somePropertyModuleNode.isPresent()) {
                notesEndIndex += JavadocMetadataScraper
                    .getParentIndexOf(somePropertyModuleNode.get());
            }
        }

        return notesEndIndex;
    }

    /**
     * Gets the start index of the parent subsection in module's JavaDoc.
     *
     * @param moduleJavadoc javadoc of module.
     * @return start index of parent subsection.
     */
    private static int getParentSectionStartIndex(DetailNode moduleJavadoc) {
        int parentStartIndex = 0;

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
     * Writes the notes into xdoc.
     *
     * @param notes notes of the module.
     * @param sink sink of the macro.
     */
    private static void writeOutNotes(String notes, Sink sink) {
        final String[] moduleNotesLinesSplit = notes.split(NEWLINE);

        sink.rawText(moduleNotesLinesSplit[0]);
        String previousProcessedLine = moduleNotesLinesSplit[0];
        for (int index = 1; index < moduleNotesLinesSplit.length; index++) {
            final String currentLine = moduleNotesLinesSplit[index].trim();
            final String processedLine;

            if (currentLine.isEmpty()) {
                processedLine = NEWLINE;
            }
            else if (currentLine.startsWith("<")
                && !currentLine.startsWith("<code>") && !currentLine.startsWith("<a")) {
                processedLine = INDENT_LEVEL_8 + currentLine;
            }
            else if (index > 1
                && (previousProcessedLine.contains("<pre")
                    || !previousProcessedLine.startsWith(INDENT_LEVEL_8))) {

                final String currentLineWithPreservedIndent = moduleNotesLinesSplit[index]
                    .substring(1);
                processedLine = NEWLINE + currentLineWithPreservedIndent;
            }
            else {
                processedLine = INDENT_LEVEL_10 + currentLine;
            }

            sink.rawText(processedLine);

            previousProcessedLine = processedLine;
        }

    }

}
