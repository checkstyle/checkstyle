////
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
///

package com.puppycrawl.tools.checkstyle.site;

import java.nio.file.Path;
import java.util.Locale;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.module.xdoc.XdocSink;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

/**
 * A macro that inserts a link to the parent module.
 */
@Component(role = Macro.class, hint = "parent-module")
public class ParentModuleMacro extends AbstractMacro {
    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        // until https://github.com/checkstyle/checkstyle/issues/13426
        if (!(sink instanceof XdocSink)) {
            throw new MacroExecutionException("Expected Sink to be an XdocSink.");
        }
        final String moduleName = (String) request.getParameter("moduleName");
        final Object instance = SiteUtil.getModuleInstance(moduleName);
        final Class<?> clss = instance.getClass();
        createParentModuleParagraph((XdocSink) sink, clss, moduleName);
    }

    /**
     * Creates a paragraph with a link to the parent module.
     *
     * @param sink the sink to write to.
     * @param clss the class of the module.
     * @param moduleName the module name.
     * @throws MacroExecutionException if the parent module cannot be found.
     */
    private static void createParentModuleParagraph(XdocSink sink, Class<?> clss, String moduleName)
        throws MacroExecutionException {
        final String parentModule = SiteUtil.getParentModule(clss);
        final String linkToParentModule = getLinkToParentModule(parentModule, moduleName);

        sink.setInsertNewline(false);
        sink.paragraph();
        sink.setInsertNewline(true);
        final String indentLevel10 = SiteUtil.getNewlineAndIndentSpaces(10);
        sink.rawText(indentLevel10);
        sink.link(linkToParentModule);
        sink.text(parentModule);
        sink.link_();
        final String indentLevel8 = SiteUtil.getNewlineAndIndentSpaces(8);
        sink.rawText(indentLevel8);
        sink.paragraph_();
    }

    /**
     * Returns relative link to the parent module for the given module class.
     *
     * @param parentModule parent module name.
     * @param moduleName the module name we are looking for the parent of.
     * @return relative link to the parent module.
     * @throws MacroExecutionException if link to the parent module cannot be constructed.
     */
    private static String getLinkToParentModule(String parentModule, String moduleName)
        throws MacroExecutionException {
        final Path templatePath = SiteUtil.getTemplatePath(moduleName);
        if (templatePath == null) {
            throw new MacroExecutionException(
                String.format(Locale.ROOT, "Could not find template for %s", moduleName));
        }
        final Path templatePathParent = templatePath.getParent();
        if (templatePathParent == null) {
            throw new MacroExecutionException("Failed to get parent path for " + templatePath);
        }
        return templatePathParent
            .relativize(Path.of("src", "site/xdoc", "config.xml"))
            .toString()
            .replace(".xml", ".html")
            .replace('\\', '/')
            + "#" + parentModule;
    }
}
