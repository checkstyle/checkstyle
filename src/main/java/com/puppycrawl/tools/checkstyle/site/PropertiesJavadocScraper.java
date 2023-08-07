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

import java.util.Map;
import java.util.TreeMap;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.BlockCommentPosition;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * Class for scraping all property setter javadocs from the given checkstyle module.
 */
@FileStatefulCheck
public class PropertiesJavadocScraper extends AbstractJavadocCheck {

    /** Name of the module being scraped. */
    private static String moduleName;

    /** Map of the module property setter javadocs. */
    private static Map<String, DetailAST> modulePropertySetterJavadocs = new TreeMap<>();

    /**
     * Set the module name to be scraped.
     *
     * @param moduleName the module name.
     */
    public static void setModuleName(String moduleName) {
        PropertiesJavadocScraper.moduleName = moduleName;
    }

    /** Clear the module property setter javadocs of any previous information. */
    public static void clearModulePropertySetterJavadocs() {
        modulePropertySetterJavadocs.clear();
    }

    /**
     * Get the module property setter javadocs.
     *
     * @return the module property setter javadocs map.
     */
    public static Map<String, DetailAST> getModulePropertySetterJavadocs() {
        return modulePropertySetterJavadocs;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailAST blockCommentAst = getBlockCommentAst();
        if (BlockCommentPosition.isOnMethod(blockCommentAst)) {
            final DetailAST methodModifier = blockCommentAst.getParent();
            final DetailAST methodDef = methodModifier.getParent();
            final String methodName = methodDef.findFirstToken(TokenTypes.IDENT).getText();
            if (CheckUtil.isSetterMethod(methodDef) && isMethodOfScrapedModule(methodDef)) {
                modulePropertySetterJavadocs.put(methodName, blockCommentAst);
            }
        }
    }

    /**
     * Checks if the given method is a method of the module being scraped. Traverses
     * parent nodes until it finds the class definition and checks if the class name
     * is the same as the module name.
     *
     * @param methodDef the method definition.
     * @return true if the method is a method of the given module, false otherwise.
     */
    private static boolean isMethodOfScrapedModule(DetailAST methodDef) {
        DetailAST node = methodDef.getParent();

        while (node != null && node.getType() != TokenTypes.CLASS_DEF) {
            node = node.getParent();
        }

        boolean isMethodOfModule = false;
        if (node != null) {
            final String className = node.findFirstToken(TokenTypes.IDENT).getText();
            isMethodOfModule = className.equals(moduleName);
        }

        return isMethodOfModule;
    }
}
