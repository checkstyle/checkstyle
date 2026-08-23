///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;

/**
 * <div>
 * Checks that there are no module imports.
 * </div>
 *
 * <p>
 * Rationale: Module import declarations ({@code import module M;}) import, on
 * demand, every public top level type exported by a module and by any
 * modules it transitively reads. This is a much broader, less explicit
 * surface than single type or on demand package imports, making it harder to
 * tell where a type comes from, and it increases the risk of ambiguous
 * references between same named types in different exported packages.
 * Disallowing module imports keeps imports explicit and predictable.
 * </p>
 *
 * @since 13.11.0
 */
@FileStatefulCheck
public class AvoidModuleImportCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.avoidModule";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_COUNT = "import.avoidModuleCount";

    /**
     * Specify module names for which {@code import module} declarations are allowed.
     */
    private final Set<String> excludes = new HashSet<>();

    /**
     * Maximum number of allowed module imports.
     */
    private int maxAllowedModuleImports;

    /**
     * Counter for used module imports.
     */
    private int currentModuleImportsCount;

    /**
     * Creates a new {@code AvoidModuleImportCheck} instance.
     */
    public AvoidModuleImportCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.MODULE_IMPORT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    /**
     * Setter to specify modules allowed to import.
     *
     * @param excludesParam module names
     * @since 3.11.0
     */
    public void setExcludes(String... excludesParam) {
        excludes.addAll(Arrays.asList(excludesParam));
    }

    /**
     * Setter to control number of module imports allowed.
     *
     * @param count the number of module imports allowed
     * @since 13.11.0
     */
    public void setMaxAllowedModuleImports(int count) {
        maxAllowedModuleImports = count;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        currentModuleImportsCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        currentModuleImportsCount++;
        final DetailAST module = NullUtil.notNull(ast.getFirstChild());
        final DetailAST startingDot = NullUtil.notNull(module.getNextSibling());
        final String name = FullIdent.createFullIdent(startingDot).getText();
        if (currentModuleImportsCount > maxAllowedModuleImports
                && !excludes.contains(name)) {
            if (maxAllowedModuleImports > 0) {
                log(ast, MSG_COUNT, maxAllowedModuleImports);
            }
            else {
                log(ast, MSG_KEY, name);
            }
        }
    }

}
