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
 * A macro that inserts a description of module from its Javadoc.
 */
@Component(role = Macro.class, hint = "description")
public class DescriptionMacro extends AbstractMacro {

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

        final int descriptionEndIndex = getDescriptionEndIndex(moduleJavadoc, propertyNames);
        final String moduleDescription = JavadocMetadataScraper.constructSubTreeText(
            moduleJavadoc, 0, descriptionEndIndex);

        ModuleJavadocParsingUtil.writeOutJavadocPortion(moduleDescription, sink);

    }

    /**
     * Gets the end index of the description.
     *
     * @param moduleJavadoc javadoc of module.
     * @param propertyNamesSet Set with property names.
     * @return the end index.
     */
    private static int getDescriptionEndIndex(DetailNode moduleJavadoc,
                                              Set<String> propertyNamesSet) {
        int descriptionEndIndex = -1;

        final int notesStartingIndex =
            ModuleJavadocParsingUtil.getNotesSectionStartIndex(moduleJavadoc);
        if (notesStartingIndex > -1) {
            descriptionEndIndex += notesStartingIndex;
        }
        else if (propertyNamesSet.isEmpty()) {
            descriptionEndIndex += ModuleJavadocParsingUtil.getParentSectionStartIndex(
                moduleJavadoc);
        }
        else {
            descriptionEndIndex += ModuleJavadocParsingUtil.getPropertySectionStartIndex(
                moduleJavadoc, propertyNamesSet);
        }

        return descriptionEndIndex;
    }

}
