///
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

package com.google.checkstyle.test.base;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.checkstyle.base.AbstractItModuleTestSupport;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

public abstract class AbstractGoogleModuleTestSupport extends AbstractItModuleTestSupport {

    private static final String XML_NAME = "/google_checks.xml";

    private static final Configuration CONFIGURATION;

    private static final Set<Class<?>> CHECKSTYLE_MODULES;

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
     * Performs verification of the file with the given file path against the whole config.
     *
     * @param filePath file path to verify.
     * @throws Exception if exception occurs during verification process.
     */
    protected void verifyWithWholeConfig(String filePath) throws Exception {
        verifyWithItConfig(CONFIGURATION, filePath);
    }
}
