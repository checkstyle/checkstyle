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

import java.util.Locale;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Filter;
import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

/**
 * A macro that inserts a link to the parent module.
 */
@Component(role = Macro.class, hint = "parent-module")
public class ParentModuleMacro extends AbstractMacro {
    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String moduleName = (String) request.getParameter("moduleName");
        final ModuleFactory moduleFactory;
        try {
            final ClassLoader cl = ParentModuleMacro.class.getClassLoader();
            final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
            moduleFactory = new PackageObjectFactory(packageNames, cl);
            final Object instance = moduleFactory.createModule(moduleName);
            final Class<?> clss = instance.getClass();
            System.out.println(clss.getSuperclass());
            sink.paragraph();
            sink.link("");
            sink.text(getParentModule(clss, moduleName));
            sink.link_();
            sink.paragraph_();
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("Failed to create module factory", ex);
        }
    }

    /**
     * Returns the parent module name for the given module class. Returns either
     * "TreeWalker" or "Checker". Returns null if the module class is null.
     *
     * @param moduleClass the module class.
     * @param moduleName the module name we are looking for the parent of.
     * @return the parent module name as a string.
     * @throws MacroExecutionException if the parent module cannot be found.
     */
    // -@cs[ReturnCount] Too complex to break apart.
    private static String getParentModule(Class<?> moduleClass, String moduleName)
                throws MacroExecutionException {
        if (moduleClass == AbstractCheck.class
                || moduleClass == TreeWalkerFilter.class) {
            return "TreeWalker";
        }
        else if (moduleClass == AbstractFileSetCheck.class
                || moduleClass == Filter.class) {
            return "Checker";
        }
        else if (moduleClass != null) {
            return getParentModule(moduleClass.getSuperclass(), moduleName);
        }
        else {
            final String message = String.format(Locale.ROOT,
                    "Failed to find parent module for %s", moduleName);
            throw new MacroExecutionException(message);
        }
    }
}
