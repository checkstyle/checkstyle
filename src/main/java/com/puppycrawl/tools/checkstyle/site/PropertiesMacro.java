///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper;
import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

/**
 * A macro that gathers properties of a module and displays them in a table.
 */
@Component(role = Macro.class, hint = "properties")
public class PropertiesMacro extends AbstractMacro {
    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String moduleName = (String) request.getParameter("moduleName");

        final ClassLoader cl = PropertiesMacro.class.getClassLoader();
        final Set<String> packageNames;
        try {
            packageNames = PackageNamesLoader.getPackageNames(cl);
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("failure", ex);
        }
        final ModuleFactory moduleFactory = new PackageObjectFactory(packageNames, cl);

        final Object instance;
        try {
            instance = moduleFactory.createModule(moduleName);
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("fail", ex);
        }

        final Field[] fields = instance.getClass().getDeclaredFields();

        PropertiesMetadataScraper.resetModulePropertyDetails();
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
                        new DefaultConfiguration(PropertiesMetadataScraper.class.getName());
        final DefaultConfiguration defaultConfiguration =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration treeWalkerConfig =
                new DefaultConfiguration(TreeWalker.class.getName());
        defaultConfiguration.addProperty("charset", StandardCharsets.UTF_8.name());
        defaultConfiguration.addChild(treeWalkerConfig);
        treeWalkerConfig.addChild(scraperCheckConfig);
        try {
            checker.configure(defaultConfiguration);
            final List<File> validFiles = new ArrayList<>();
            validFiles.add(new File("src/main/java/com/puppycrawl/tools/checkstyle/checks/naming/AbstractClassNameCheck.java"));
            checker.process(validFiles);
            checker.destroy();
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("fail", ex);
        }
    }
}
