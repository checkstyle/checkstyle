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

package com.sun.checkstyle.test.base;

import java.io.IOException;
import java.util.Set;

import org.checkstyle.base.AbstractItModuleTestSupport;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

public abstract class AbstractSunModuleTestSupport extends AbstractItModuleTestSupport {

    private static final String XML_NAME = "/sun_checks.xml";

    private static final Configuration CONFIGURATION;

    private static final Set<Class<?>> CHECKSTYLE_MODULES;

    static {
        try {
            CONFIGURATION = ConfigurationLoader.loadConfiguration(XML_NAME,
                new PropertiesExpander(System.getProperties()));
        }
        catch (CheckstyleException ex) {
            throw new IllegalStateException(ex);
        }
        try {
            CHECKSTYLE_MODULES = CheckUtil.getCheckstyleModules();
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    protected ModuleCreationOption findModuleCreationOption(String moduleName) {
        ModuleCreationOption moduleCreationOption = ModuleCreationOption.IN_CHECKER;

        for (Class<?> moduleClass : CHECKSTYLE_MODULES) {
            if (moduleClass.getSimpleName().equals(moduleName)
                    || moduleClass.getSimpleName().equals(moduleName + "Check")) {
                if (ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(moduleClass)
                        || ModuleReflectionUtil.isTreeWalkerFilterModule(moduleClass)) {
                    moduleCreationOption = ModuleCreationOption.IN_TREEWALKER;
                }
                break;
            }
        }

        return moduleCreationOption;
    }

    /**
     * Returns {@link Configuration} instance for the given module name.
     * This implementation uses {@link #getModuleConfig(String, String)} method inside.
     *
     * @param moduleName module name.
     * @return {@link Configuration} instance for the given module name.
     */
    protected static Configuration getModuleConfig(String moduleName) {
        return getModuleConfig(moduleName, null);
    }

    /**
     * Returns {@link Configuration} instance for the given module name.
     * This implementation uses {@link #getModuleConfig(String)} method inside.
     *
     * @param moduleName module name.
     * @param moduleId module id.
     * @return {@link Configuration} instance for the given module name.
     * @throws IllegalStateException if there is a problem retrieving the module or config.
     */
    protected static Configuration getModuleConfig(String moduleName, String moduleId) {
        return getModuleConfig(CONFIGURATION, moduleName, moduleId);
    }

}
