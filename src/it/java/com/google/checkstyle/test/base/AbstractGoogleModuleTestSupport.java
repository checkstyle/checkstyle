///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.google.checkstyle.test.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.checkstyle.base.AbstractItModuleTestSupport;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

public abstract class AbstractGoogleModuleTestSupport extends AbstractItModuleTestSupport {

    private static final String XML_NAME = "/google_checks.xml";

    private static final Configuration CONFIGURATION;

    private static final Set<Class<?>> CHECKSTYLE_MODULES;

    private static final Set<String> CHECKER_CHILDREN = new HashSet<>(Arrays.asList(
            "BeforeExecutionExclusionFileFilter",
            "SeverityMatchFilter",
            "SuppressionFilter",
            "SuppressionSingleFilter",
            "SuppressWarningsFilter",
            "SuppressWithNearbyTextFilter",
            "SuppressWithPlainTextCommentFilter",
            "JavadocPackage",
            "NewlineAtEndOfFile",
            "UniqueProperties",
            "OrderedProperties",
            "RegexpMultiline",
            "RegexpSingleline",
            "RegexpOnFilename",
            "FileLength",
            "LineLength",
            "FileTabCharacter"
    ));

    static {
        try {
            final Properties properties = new Properties();
            properties.put("org.checkstyle.google.severity", "error");
            final PropertiesExpander expander = new PropertiesExpander(properties);
            CONFIGURATION = ConfigurationLoader.loadConfiguration(XML_NAME,
                    expander);
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

    /**
     * Returns a list of all {@link Configuration} instances for the given module IDs.
     *
     * @param moduleIds module IDs.
     * @return List of {@link Configuration} instances.
     * @throws CheckstyleException if there is an error with the config.
     */
    protected static List<Configuration> getModuleConfigsByIds(String... moduleIds)
            throws CheckstyleException {
        return getModuleConfigsByIds(CONFIGURATION, moduleIds);
    }

    /**
     * Performs verification of the file with the given file path against the whole config.
     *
     * @param filePath file path to verify.
     * @throws Exception if exception occurs during verification process.
     */
    public void verifyWithWholeConfig(String filePath) throws Exception {
        verifyWithItConfig(CONFIGURATION, filePath);
    }

    // until https://github.com/checkstyle/checkstyle/issues/14937
    /**
     * Performs verification of the file with the given file path against config.
     * It uses the specified list of modules to load them from config for validation.
     *
     * @param listOfModules list of modules to load from config.
     * @param filePath file path to verify.
     * @throws Exception if exception occurs during verification process.
     */
    public final void verifyWithConfigParser(String[] listOfModules,
           String filePath) throws Exception {
        final DefaultConfiguration googleConfig = new DefaultConfiguration(ROOT_MODULE_NAME);
        final DefaultConfiguration treeWalkerConfig =
                new DefaultConfiguration(TreeWalker.class.getSimpleName());

        for (String module : listOfModules) {
            final List<Configuration> children =
                    getConfigChildren(module);
            if (CHECKER_CHILDREN.contains(module)) {
                for (Configuration child : children) {
                    googleConfig.addChild(child);
                }
            }
            else {
                for (Configuration child : children) {
                    treeWalkerConfig.addChild(child);
                }
            }
        }

        if (treeWalkerConfig.getChildren().length > 0) {
            googleConfig.addChild(treeWalkerConfig);
        }

        verifyWithItConfig(googleConfig, filePath);
    }

    /**
     * Gets the specified children module(s) from the config.
     *
     * @param module the children module to get. It can be either a module name or module id.
     * @return list of children modules.
     * @throws CheckstyleException if there is a problem for getting the module.
     */
    private static List<Configuration> getConfigChildren(String module)
            throws CheckstyleException {
        final List<Configuration> children = new ArrayList<>();

        try {
            children.add(getModuleConfig(module));
        }
        catch (IllegalStateException ex) {
            children.addAll(getModuleConfigsByIds(module));
        }

        return children;
    }
}
