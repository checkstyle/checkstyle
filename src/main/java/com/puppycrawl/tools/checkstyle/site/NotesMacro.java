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
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * A macro that inserts a notes subsection of module from its Javadoc.
 */
@Component(role = Macro.class, hint = "notes")
public class NotesMacro extends AbstractMacro {

    /** "Notes:" line with new line accounted. */
    public static final Pattern NOTES_LINE_WITH_NEWLINE = Pattern.compile("\r?\n\\s?"
        + ModuleJavadocParsingUtil.NOTES);

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final Path modulePath = Path.of((String) request.getParameter("modulePath"));
        final String moduleName = CommonUtil.getFileNameWithoutExtension(modulePath.toString());

        final Set<String> propertyNames = ModuleJavadocParsingUtil.getPropertyNames(moduleName);

        final DetailNode moduleJavadoc = SiteUtil.getModuleJavadoc(moduleName, modulePath);
        if (moduleJavadoc == null) {
            throw new MacroExecutionException(
                "Javadoc of module " + moduleName + " is not found.");
        }

        final int notesStartIndex = ModuleJavadocParsingUtil
            .getNotesSectionStartIndex(moduleJavadoc);
        final int notesEndIndex = getNotesEndIndex(moduleJavadoc, propertyNames);

        final String unprocessedModuleNotes = JavadocMetadataScraper.constructSubTreeText(
            moduleJavadoc, notesStartIndex, notesEndIndex);
        final String moduleNotes = NOTES_LINE_WITH_NEWLINE.matcher(unprocessedModuleNotes)
            .replaceAll("");

        ModuleJavadocParsingUtil.writeOutJavadocPortion(moduleNotes, sink);

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
            notesEndIndex += ModuleJavadocParsingUtil.getParentSectionStartIndex(moduleJavadoc);
        }
        else {
            notesEndIndex += ModuleJavadocParsingUtil.getPropertySectionStartIndex(
                moduleJavadoc, propertyNamesSet);
        }

        return notesEndIndex;
    }

}
