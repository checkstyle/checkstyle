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

import static com.puppycrawl.tools.checkstyle.JavaParser.parseFile;
import static com.puppycrawl.tools.checkstyle.JavaParser.Options;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper;
import com.puppycrawl.tools.checkstyle.site.SiteUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

@Component(role = Macro.class, hint = "description")
public class DescriptionMacro extends AbstractMacro {

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String modulePathString = (String)request.getParameter("modulePath");
        final Path modulePath = Paths.get(modulePathString);
        final String moduleName = CommonUtil.getFileNameWithoutExtension(modulePath.toString());

        File checkFile = modulePath.toFile();

        final Object instance = SiteUtil.getModuleInstance(moduleName);
        final Class<?> clss = instance.getClass();

        final Set<String> properties = SiteUtil.getPropertiesForDocumentation(clss, instance);
        final Map<String, DetailNode> propertiesJavadocs = SiteUtil
                .getPropertiesJavadocs(properties, moduleName, modulePath);

        final DetailNode moduleJavadoc = propertiesJavadocs.get(moduleName);
    }
}
