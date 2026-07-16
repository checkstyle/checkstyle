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

package com.puppycrawl.tools.checkstyle.site;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.maven.doxia.macro.MacroExecutionException;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.BlockCommentPosition;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Class for scraping class javadoc and all property setter javadocs from the
 * given checkstyle module.
 */
@FileStatefulCheck
public class ClassAndPropertiesSettersJavadocScraper extends AbstractJavadocCheck {

    /** Name of the module being scraped. */
    private static String moduleName = "";

    /** The instance of the module. */
    private static Object moduleInstance = new Object();

    /** The properties of the module. */
    private static Set<String> properties = Set.of();

    /** Map of property names to their setter javadoc nodes. */
    private final Map<String, DetailNode> setterNodes = new HashMap<>();

    /**
     * Creates a new {@code ClassAndPropertiesSettersJavadocScraper} instance.
     */
    public ClassAndPropertiesSettersJavadocScraper() {
        // no code by default
    }
    /**
     * Initialize the scraper. Clears static context and sets the module name.
     *
     * @param newModuleName the module name.
     * @param instance the module instance.
     * @param propertiesSet the set of properties to document.
     */

    public static void initialize(String newModuleName, Object instance,
                                  Set<String> propertiesSet) {
        JavadocScraperResultUtil.clearData();
        moduleName = newModuleName;
        moduleInstance = instance;
        if (propertiesSet == null) {
            properties = Set.of();
        }
        else {
            properties = Collections.unmodifiableSet(new TreeSet<>(propertiesSet));
        }
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
            handleMethodComment(ast, blockCommentAst);
        }
        else if (BlockCommentPosition.isOnField(blockCommentAst)) {
            handleFieldComment(ast, blockCommentAst);
        }
        else if (BlockCommentPosition.isOnClass(blockCommentAst)) {
            handleClassComment(ast, blockCommentAst);
        }
    }

    /**
     * Processes method Javadoc. If the method is a setter for a property of the
     * module being scraped, the Javadoc node is stored.
     *
     * @param ast the Javadoc node.
     * @param blockCommentAst the block comment AST.
     */
    private void handleMethodComment(DetailNode ast, DetailAST blockCommentAst) {
        final DetailAST methodDef = getParentAst(blockCommentAst, TokenTypes.METHOD_DEF);

        if (methodDef != null
                && isSetterMethod(methodDef)
                && isMethodOfScrapedModule(methodDef)) {
            final String methodName = TokenUtil.getIdent(methodDef).getText();
            final String propertyName = getPropertyName(methodName);
            setterNodes.put(propertyName, ast);
        }
    }

    /**
     * Processes field Javadoc. If the field is a known property of the module
     * being scraped, the Javadoc node is stored.
     *
     * @param ast the Javadoc node.
     * @param blockCommentAst the block comment AST.
     */
    private void handleFieldComment(DetailNode ast, DetailAST blockCommentAst) {
        final DetailAST fieldDef = getParentAst(blockCommentAst, TokenTypes.VARIABLE_DEF);

        if (fieldDef != null && isMethodOfScrapedModule(fieldDef)) {
            final String fieldName = TokenUtil.getIdent(fieldDef).getText();
            if (isKnownProperty(fieldName)) {
                setterNodes.put(fieldName, ast);
            }
        }
    }

    /**
     * Checks if the field name is a known property that should be documented.
     *
     * @param fieldName the name of the field.
     * @return true if it is a known property, false otherwise.
     */
    private static boolean isKnownProperty(String fieldName) {
        final boolean result;
        if (properties.isEmpty()) {
            result = SiteUtil.VIOLATE_EXECUTION_ON_NON_TIGHT_HTML.equals(fieldName);
        }
        else {
            result = properties.contains(fieldName);
        }
        return result;
    }

    /**
     * Processes class Javadoc. Extracts module metadata such as 'since' version,
     * description, and notes.
     *
     * @param ast the Javadoc node.
     * @param blockCommentAst the block comment AST.
     */
    private static void handleClassComment(DetailNode ast, DetailAST blockCommentAst) {
        final DetailAST classDef = getParentAst(blockCommentAst, TokenTypes.CLASS_DEF);
        if (classDef != null) {
            final String className = TokenUtil.getIdent(classDef).getText();

            final boolean isModuleNameNotEmpty = moduleName != null && !moduleName.isEmpty();

            final boolean isSameClass = className.equals(moduleName);

            final boolean isModuleInstanceValid = moduleInstance != null
                    && moduleInstance.getClass() != Object.class;

            if (isModuleNameNotEmpty && isSameClass && isModuleInstanceValid) {

                final String moduleSinceVersion =
                        ModuleJavadocParsingUtil.getModuleSinceVersion(ast);
                JavadocScraperResultUtil.setModuleSinceVersion(moduleSinceVersion);

                final String moduleDescription =
                        ModuleJavadocParsingUtil.getModuleDescription(ast);
                JavadocScraperResultUtil.setModuleDescription(moduleDescription);

                final String moduleNotes =
                        ModuleJavadocParsingUtil.getModuleNotes(ast);
                JavadocScraperResultUtil.setModuleNotes(moduleNotes);
            }
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        final Set<String> propsToProcess;
        if (properties.isEmpty()) {
            propsToProcess = setterNodes.keySet();
        }
        else {
            propsToProcess = properties;
        }

        for (String property : propsToProcess) {
            final boolean isRealInstance = moduleInstance != null
                    && moduleInstance.getClass() != Object.class;
            if (isRealInstance && !setterNodes.containsKey(property)) {
                continue;
            }
            try {
                final PropertyDetails details = createPropertyDetails(property);
                JavadocScraperResultUtil.putPropertyDetails(property, details);
            }
            catch (MacroExecutionException ignored) {
            // Property details cannot be created for this property, skip it.
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
            final String className = TokenUtil.getIdent(classDef).getText();
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
        boolean setterMethod = false;

        if (ast.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            final String name = type.getNextSibling().getText();
            final Pattern setterPattern = Pattern.compile("^set[A-Z].*");

            setterMethod = setterPattern.matcher(name).matches();
        }
        return setterMethod;
    }

    /**
     * Creates a PropertyDetails object for the given property.
     *
     * @param propertyName the name of the property.
     * @return the PropertyDetails object.
     * @throws MacroExecutionException if an error occurs
     */
    private PropertyDetails createPropertyDetails(String propertyName)
            throws MacroExecutionException {
        final DetailNode setterJavadoc = setterNodes.get(propertyName);
        final Class<?> instanceClass;
        if (moduleInstance == null) {
            instanceClass = Object.class;
        }
        else {
            instanceClass = moduleInstance.getClass();
        }

        final String description = SiteUtil.getPropertyDescriptionForXdoc(propertyName,
                setterJavadoc, moduleName);
        final String moduleSinceVersion = JavadocScraperResultUtil.getModuleSinceVersion();
        final String since = SiteUtil.getPropertySinceVersion(moduleSinceVersion,
                setterJavadoc);

        final PropertyDetails.Builder builder = new PropertyDetails.Builder()
                .name(propertyName)
                .description(description)
                .sinceVersion(since);

        final boolean isDefaultInstance = moduleInstance == null
                || moduleInstance.getClass() == Object.class;

        final PropertyDetails result;
        if (isDefaultInstance) {
            result = builder.build();
        }
        else {
            final Field field = SiteUtil.getField(instanceClass, propertyName);
            result = SiteUtil.constructPropertyDetails(builder, moduleInstance, field,
                    propertyName, moduleName);
        }
        return result;
    }

}
