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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks the ordering and placement of module import declarations. Features are:
 * </div>
 * <ul>
 * <li>
 * position of module imports: ensures that module imports are placed above or below
 * all type and static imports (see
 * <a href="https://checkstyle.org/property_types.html#ModuleImportOrderOption">
 * ModuleImportOrderOption</a>)
 * </li>
 * <li>
 * sorts module imports: ensures that module imports are sorted lexicographically
 * by qualified module name, in
 * <a href="https://en.wikipedia.org/wiki/ASCII#Order">ASCII sort order</a>
 * </li>
 * <li>
 * adds a separation between module imports and other imports: ensures that the module
 * import block is separated from type and static imports by, at least, one blank
 * line or comment
 * </li>
 * </ul>
 *
 * <p>
 * This check only validates module imports. It observes type and static imports to
 * locate the boundary of the module import block, but does not validate their order.
 * Use {@code ImportOrder} alongside this check for those.
 * </p>
 *
 * @since 13.11.0
 */
@FileStatefulCheck
public class ModuleImportOrderCheck extends AbstractCheck {

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when a module import is not placed above or below all type and
     * static imports, as required by the configured option.
     */
    public static final String MSG_POSITION = "module.import.position";

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when the module import block is not separated from type and
     * static imports by a blank line.
     */
    public static final String MSG_SEPARATION = "module.import.separation";

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when module imports are not sorted lexicographically by
     * qualified module name.
     */
    public static final String MSG_ORDERING_LEX = "module.import.ordering.lex";

    /** Imports of the current file in order of appearance. */
    private final List<ImportEntry> imports = new ArrayList<>();

    /**
     * Specify policy on the position of module imports relative to type and
     * static imports.
     */
    private ModuleImportOrderOption option = ModuleImportOrderOption.TOP;

    /**
     * Control whether the module import block should be separated from type and
     * static imports by, at least, one blank line or comment.
     */
    private boolean separated;

    /**
     * Creates a new {@code ModuleImportOrderCheck} instance.
     */
    public ModuleImportOrderCheck() {
        // no code by default
    }

    /**
     * Setter to specify policy on the position of module imports relative to type
     * and static imports.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 13.11.0
     */
    public void setOption(String optionStr) {
        option = ModuleImportOrderOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Setter to control whether the module import block should be separated from
     * type and static imports by, at least, one blank line or comment.
     *
     * @param separated whether the module import block should be separated.
     * @since 13.11.0
     */
    public void setSeparated(boolean separated) {
        this.separated = separated;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.MODULE_IMPORT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        imports.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final FullIdent ident;
        if (ast.getType() == TokenTypes.IMPORT) {
            ident = FullIdent.createFullIdentBelow(ast);
        }
        else {
            ident = FullIdent.createFullIdent(ast.getFirstChild().getNextSibling());
        }
        imports.add(new ImportEntry(ident.getText(),
                ast.getType() == TokenTypes.MODULE_IMPORT, ast));
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        checkLexicographicalOrder();
        final boolean misplaced = checkPosition();
        if (separated && !misplaced) {
            checkSeparation();
        }
    }

    /**
     * Checks that module imports are sorted lexicographically. Each module import
     * is compared with the previous module import, regardless of any type or
     * static imports between them.
     */
    private void checkLexicographicalOrder() {
        String previousModule = null;
        for (final ImportEntry entry : imports) {
            if (entry.module()) {
                if (previousModule != null && previousModule.compareTo(entry.name()) > 0) {
                    log(entry.ast(), MSG_ORDERING_LEX, entry.name(), previousModule);
                }
                previousModule = entry.name();
            }
        }
    }

    /**
     * Checks that module imports are placed above or below all type and static
     * imports, according to the configured option.
     *
     * @return true if any position violation was logged.
     */
    private boolean checkPosition() {
        boolean violation = false;
        boolean seenNonModule = false;
        if (option == ModuleImportOrderOption.TOP) {
            for (final ImportEntry entry : imports) {
                if (seenNonModule && entry.module()) {
                    log(entry.ast(), MSG_POSITION, entry.name());
                    violation = true;
                }
                seenNonModule = seenNonModule || !entry.module();
            }
        }
        else {
            for (int index = imports.size() - 1; index >= 0; index--) {
                final ImportEntry entry = imports.get(index);
                if (seenNonModule && entry.module()) {
                    log(entry.ast(), MSG_POSITION, entry.name());
                    violation = true;
                }
                seenNonModule = seenNonModule || !entry.module();
            }
        }
        return violation;
    }

    /**
     * Checks that the module import block is separated from the adjacent type and
     * static import block by, at least, one blank line or comment. This method is
     * only invoked when module imports are correctly positioned, so all module
     * imports form a single block above or below all other imports.
     */
    private void checkSeparation() {
        int lastModuleIndex = -1;
        int firstModuleIndex = -1;
        for (int index = 0; index < imports.size(); index++) {
            if (imports.get(index).module()) {
                if (firstModuleIndex == -1) {
                    firstModuleIndex = index;
                }
                lastModuleIndex = index;
            }
        }

        final int boundaryIndex;
        if (option == ModuleImportOrderOption.TOP) {
            boundaryIndex = lastModuleIndex + 1;
        }
        else {
            boundaryIndex = firstModuleIndex;
        }

        if (boundaryIndex > 0 && boundaryIndex < imports.size()) {
            final ImportEntry boundary = imports.get(boundaryIndex);
            final ImportEntry previous = imports.get(boundaryIndex - 1);
            if (boundary.getStartLineNumber() - previous.getEndLineNumber() < 2) {
                log(boundary.ast(), MSG_SEPARATION, boundary.name());
            }
        }
    }

    /**
     * Contains import attributes as import full path, module flag and import AST.
     *
     * @param name fully qualified name of the import
     * @param module whether the import is a module import
     * @param ast import AST
     */
    private record ImportEntry(String name, boolean module, DetailAST ast) {

        /**
         * Get import start line number from ast.
         *
         * @return import start line from ast.
         */
        /* package */ int getStartLineNumber() {
            return ast.getLineNo();
        }

        /**
         * Get import end line number from ast.
         *
         * <p>
         * <b>Note:</b> It can be different from <b>startLineNumber</b> when import
         * statement spans multiple lines.
         * </p>
         *
         * @return import end line from ast.
         */
        /* package */ int getEndLineNumber() {
            return ast.getLastChild().getLineNo();
        }
    }

}
