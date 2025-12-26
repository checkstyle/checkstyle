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

package com.puppycrawl.tools.checkstyle.site;

import java.beans.Introspector;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.BlockCommentPosition;

/**
 * Class for scraping class javadoc and all property setter javadocs from the
 * given checkstyle module.
 */
@FileStatefulCheck
public class ClassAndPropertiesSettersJavadocScraper extends AbstractJavadocCheck {

    /** Name of the module being scraped. */
    private static String moduleName = "";

    /**
     * Initialize the scraper. Clears static context and sets the module name.
     *
     * @param newModuleName the module name.
     */
    public static void initialize(String newModuleName) {
        JavadocScraperResultUtil.clearData();
        moduleName = newModuleName;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailAST blockCommentAst = getBlockCommentAst();
        if (BlockCommentPosition.isOnMethod(blockCommentAst)) {
            final DetailAST methodDef = getParentAst(blockCommentAst, TokenTypes.METHOD_DEF);
            if (methodDef != null
                    && isSetterMethod(methodDef)
                    && isMethodOfScrapedModule(methodDef)) {
                final String methodName = methodDef.findFirstToken(TokenTypes.IDENT).getText();
                final String propertyName = getPropertyName(methodName);
                JavadocScraperResultUtil.putPropertyJavadocNode(propertyName, ast);
            }

        }
        else if (BlockCommentPosition.isOnClass(blockCommentAst)) {
            final DetailAST classDef = getParentAst(blockCommentAst, TokenTypes.CLASS_DEF);
            if (classDef != null) {
                final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
                if (className.equals(moduleName)) {
                    JavadocScraperResultUtil.setModuleJavadocNode(ast);
                }
            }
        }
    }

    /**
     * Checks if the given method is a method of the module being scraped. Traverses
     * parent nodes until it finds the class definition and checks if the class name
     * is the same as the module name. We want to avoid scraping javadocs from
     * inner classes.
     *
     * @param methodDef the method definition.
     * @return true if the method is a method of the given module, false otherwise.
     */
    private static boolean isMethodOfScrapedModule(DetailAST methodDef) {
        final DetailAST classDef = getParentAst(methodDef, TokenTypes.CLASS_DEF);

        var isMethodOfModule = false;
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

    /**
     * Get the property name from the setter method name. For example, getPropertyName("setFoo")
     * returns "foo". This method removes the "set" prefix and decapitalizes the first letter
     * of the property name.
     *
     * @param setterName the setter method name.
     * @return the property name.
     */
    private static String getPropertyName(String setterName) {
        return Introspector.decapitalize(setterName.substring("set".length()));
    }

    /**
     * Returns whether an AST represents a setter method.
     *
     * @param ast the AST to check with
     * @return whether the AST represents a setter method
     */
    private static boolean isSetterMethod(DetailAST ast) {
        var setterMethod = false;

        if (ast.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            final String name = type.getNextSibling().getText();
            final Pattern setterPattern = Pattern.compile("^set[A-Z].*");

            setterMethod = setterPattern.matcher(name).matches();
        }
        return setterMethod;
    }
}
