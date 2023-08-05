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

import java.util.Collections;
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
 * Class for scraping class javadoc and all property setter javadocs from the
 * given checkstyle module.
 */
@FileStatefulCheck
public class ClassAndSettersJavadocScraper extends AbstractJavadocCheck {

    /** Name of the module being scraped. */
    private static String moduleName;

    /** The module javadoc. */
    private static DetailNode moduleJavadoc;

    /** Map of the module property setter javadocs. */
    private static final Map<String, DetailNode> MODULE_PROPERTY_SETTER_JAVADOCS = new TreeMap<>();

    /**
     * Set the module name to be scraped.
     *
     * @param moduleName the module name.
     */
    public static void setModuleName(String moduleName) {
        ClassAndSettersJavadocScraper.moduleName = moduleName;
    }

    /**
     * Set the module javadoc.
     *
     * @param moduleJavadoc the module javadoc.
     */
    public static void setModuleJavadoc(DetailNode moduleJavadoc) {
        ClassAndSettersJavadocScraper.moduleJavadoc = moduleJavadoc;
    }

    /** Clear the module property setter javadocs of any previous information. */
    public static void clearModulePropertySetterJavadocs() {
        MODULE_PROPERTY_SETTER_JAVADOCS.clear();
    }

    /**
     * Get the module property setter javadocs.
     *
     * @return the module property setter javadocs map.
     */
    public static Map<String, DetailNode> getModulePropertySetterJavadocs() {
        return Collections.unmodifiableMap(MODULE_PROPERTY_SETTER_JAVADOCS);
    }

    /**
     * Get the module javadoc.
     *
     * @return the module javadoc.
     */
    public static DetailNode getModuleJavadoc() {
        return moduleJavadoc;
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
            final DetailAST methodDef = getParentAst(blockCommentAst, TokenTypes.METHOD_DEF);
            final String methodName = methodDef.findFirstToken(TokenTypes.IDENT).getText();
            if (CheckUtil.isSetterMethod(methodDef) && isMethodOfScrapedModule(methodDef)) {
                MODULE_PROPERTY_SETTER_JAVADOCS.put(methodName, ast);
            }
        }
        else if (BlockCommentPosition.isOnClass(blockCommentAst)) {
            final DetailAST classDef = getParentAst(blockCommentAst, TokenTypes.CLASS_DEF);
            final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
            if (className.equals(moduleName)) {
                moduleJavadoc = ast;
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
        final DetailAST classDef = getParentAst(methodDef, TokenTypes.CLASS_DEF);

        boolean isMethodOfModule = false;
        if (classDef != null) {
            final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
            isMethodOfModule = className.equals(moduleName);
        }

        return isMethodOfModule;
    }

    /**
     * Get the parent node of the given type. Traverses up the tree until it finds
     * the given type.
     *
     * @param ast the node to start traversing from.
     * @param type the type of the parent node to find.
     * @return the parent node of the given type, or null if not found.
     */
    private static DetailAST getParentAst(DetailAST ast, int type) {
        DetailAST node = ast.getParent();

        while (node != null && node.getType() != type) {
            node = node.getParent();
        }

        return node;
    }
}
