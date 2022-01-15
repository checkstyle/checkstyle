////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.sun.checkstyle.test.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.checkstyle.base.AbstractItModuleTestSupport;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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

    @Override
    protected DefaultConfiguration createModuleConfig(Class<?> clazz) {
        return new DefaultConfiguration(clazz.getName());
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
        final Configuration result;
        final List<Configuration> configs = getModuleConfigs(moduleName);
        if (configs.size() == 1) {
            result = configs.get(0);
        }
        else if (configs.isEmpty()) {
            throw new IllegalStateException("no instances of the Module was found: " + moduleName);
        }
        else if (moduleId == null) {
            throw new IllegalStateException("multiple instances of the same Module are detected");
        }
        else {
            result = configs.stream().filter(conf -> {
                try {
                    return conf.getProperty("id").equals(moduleId);
                }
                catch (CheckstyleException ex) {
                    throw new IllegalStateException("problem to get ID attribute from " + conf, ex);
                }
            })
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("problem with module config"));
        }

        return result;
    }

    /**
     * Returns a list of all {@link Configuration} instances for the given module name.
     *
     * @param moduleName module name.
     * @return {@link Configuration} instance for the given module name.
     */
    protected static List<Configuration> getModuleConfigs(String moduleName) {
        final List<Configuration> result = new ArrayList<>();
        for (Configuration currentConfig : CONFIGURATION.getChildren()) {
            if ("TreeWalker".equals(currentConfig.getName())) {
                for (Configuration moduleConfig : currentConfig.getChildren()) {
                    if (moduleName.equals(moduleConfig.getName())) {
                        result.add(moduleConfig);
                    }
                }
            }
            else if (moduleName.equals(currentConfig.getName())) {
                result.add(currentConfig);
            }
        }
        return result;
    }

}
