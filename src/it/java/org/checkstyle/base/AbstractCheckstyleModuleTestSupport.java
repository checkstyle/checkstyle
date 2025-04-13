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

package org.checkstyle.base;

import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

public abstract class AbstractCheckstyleModuleTestSupport extends AbstractItModuleTestSupport {

    @Override
    protected ModuleCreationOption findModuleCreationOption(String moduleName) {
        ModuleCreationOption moduleCreationOption = ModuleCreationOption.IN_CHECKER;

        if (!ROOT_MODULE_NAME.equals(moduleName)) {
            try {
                final Class<?> moduleClass = Class.forName(moduleName);
                if (ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(moduleClass)
                    || ModuleReflectionUtil.isTreeWalkerFilterModule(moduleClass)) {
                    moduleCreationOption = ModuleCreationOption.IN_TREEWALKER;
                }
            }
            catch (ClassNotFoundException ignore) {
                // ignore exception, assume it is not part of TreeWalker
            }
        }

        return moduleCreationOption;
    }

}
