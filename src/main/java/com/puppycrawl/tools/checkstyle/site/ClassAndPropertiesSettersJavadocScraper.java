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

import java.beans.Introspector;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public class ClassAndPropertiesSettersJavadocScraper extends AbstractJavadocCheck {

    /** Name of the module being scraped. */
    private static String moduleName;

    /**
     * Map of scraped javadocs - name of property, javadoc detail node.
     * The class javadoc is stored too, with the key being the module name.
     */
    private static final Map<String, DetailNode> JAVADOCS = new LinkedHashMap<>();

    /** List of setter methods that the scraper ignores but shouldn't. */
    private static final List<String> SETTER_EXCEPTIONS = List.of(
        "AbstractFileSetCheck.setFileExtensions"
    );

    /**
     * Set the name of the module to be scraped.
     *
     * @param moduleName the module name.
     */
    public static void setModuleName(String moduleName) {
        ClassAndPropertiesSettersJavadocScraper.moduleName = moduleName;
    }

    /**
     * Get the module property setter javadocs.
     *
     * @return the module property setter javadocs map.
     */
    public static Map<String, DetailNode> getJavadocs() {
        return Collections.unmodifiableMap(JAVADOCS);
    }

    /** Clear all scraped information. */
    public static void clear() {
        JAVADOCS.clear();
        moduleName = null;
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
            if (CheckUtil.isSetterMethod(methodDef) && isMethodOfScrapedModule(methodDef)
                    || SETTER_EXCEPTIONS.contains(moduleName + "." + methodName)) {
                final String propertyName = getPropertyName(methodName);
                JAVADOCS.put(propertyName, ast);
            }
        }
        else if (BlockCommentPosition.isOnClass(blockCommentAst)) {
            final DetailAST classDef = getParentAst(blockCommentAst, TokenTypes.CLASS_DEF);
            final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
            if (className.equals(moduleName)) {
                JAVADOCS.put(moduleName, ast);
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
}
