////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import antlr.collections.AST;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.Utils;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <p>
 * Checks for illegal instantiations where a factory method is preferred.
 * </p>
 * <p>
 * Rationale: Depending on the project, for some classes it might be
 * preferable to create instances through factory methods rather than
 * calling the constructor.
 * </p>
 * <p>
 * A simple example is the java.lang.Boolean class, to save memory and CPU
 * cycles it is preferable to use the predeifined constants TRUE and FALSE.
 * Constructor invocations should be replaced by calls to Boolean.valueOf().
 * </p>
 * <p>
 * Some extremely performance sensitive projects may require the use of factory
 * methods for other classes as well, to enforce the usage of number caches or
 * object pools.
 * </p>
 * <p>
 * Limitations: It is currently not possible to specify array classes.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="IllegalInstantiation"/&gt;
 * </pre>
 * @author lkuehne
 */
public class IllegalInstantiationCheck
    extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "instantiation.avoid";

    /** {@link java.lang} package as string */
    private static final String JAVA_LANG = "java.lang.";

    /** Set of fully qualified classnames. E.g. "java.lang.Boolean" */
    private final Set<String> illegalClasses = Sets.newHashSet();

    /** name of the package */
    private String pkgName;

    /** the imports for the file */
    private final Set<FullIdent> imports = Sets.newHashSet();

    /** the class names defined in the file */
    private final Set<String> classNames = Sets.newHashSet();

    /** the instantiations in the file */
    private final Set<DetailAST> instantiations = Sets.newHashSet();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.CLASS_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        // Return an empty array to not allow user to change configuration.
        return new int[] {};
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        super.beginTree(rootAST);
        pkgName = null;
        imports.clear();
        instantiations.clear();
        classNames.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_NEW:
                processLiteralNew(ast);
                break;
            case TokenTypes.PACKAGE_DEF:
                processPackageDef(ast);
                break;
            case TokenTypes.IMPORT:
                processImport(ast);
                break;
            case TokenTypes.CLASS_DEF:
                processClassDef(ast);
                break;
            default:
                throw new IllegalArgumentException("Unknown type " + ast);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (DetailAST literalNewAST : instantiations) {
            postprocessLiteralNew(literalNewAST);
        }
    }

    /**
     * Collects classes defined in the source file. Required
     * to avoid false alarms for local vs. java.lang classes.
     *
     * @param ast the classdef token.
     */
    private void processClassDef(DetailAST ast) {
        final DetailAST identToken = ast.findFirstToken(TokenTypes.IDENT);
        final String className = identToken.getText();
        classNames.add(className);
    }

    /**
     * Perform processing for an import token
     * @param ast the import token
     */
    private void processImport(DetailAST ast) {
        final FullIdent name = FullIdent.createFullIdentBelow(ast);
        // Note: different from UnusedImportsCheck.processImport(),
        // '.*' imports are also added here
        imports.add(name);
    }

    /**
     * Perform processing for an package token
     * @param ast the package token
     */
    private void processPackageDef(DetailAST ast) {
        final DetailAST packageNameAST = ast.getLastChild()
                .getPreviousSibling();
        final FullIdent packageIdent =
                FullIdent.createFullIdent(packageNameAST);
        pkgName = packageIdent.getText();
    }

    /**
     * Collects a "new" token.
     * @param ast the "new" token
     */
    private void processLiteralNew(DetailAST ast) {
        if (ast.getParent().getType() == TokenTypes.METHOD_REF) {
            return;
        }
        instantiations.add(ast);
    }

    /**
     * Processes one of the collected "new" tokens when treewalking
     * has finished.
     * @param ast the "new" token.
     */
    private void postprocessLiteralNew(DetailAST ast) {
        final DetailAST typeNameAST = ast.getFirstChild();
        final AST nameSibling = typeNameAST.getNextSibling();
        if (nameSibling != null
                && nameSibling.getType() == TokenTypes.ARRAY_DECLARATOR) {
            // ast == "new Boolean[]"
            return;
        }

        final FullIdent typeIdent = FullIdent.createFullIdent(typeNameAST);
        final String typeName = typeIdent.getText();
        final int lineNo = ast.getLineNo();
        final int colNo = ast.getColumnNo();
        final String fqClassName = getIllegalInstantiation(typeName);
        if (fqClassName != null) {
            log(lineNo, colNo, MSG_KEY, fqClassName);
        }
    }

    /**
     * Checks illegal instantiations.
     * @param className instantiated class, may or may not be qualified
     * @return the fully qualified class name of className
     * or null if instantiation of className is OK
     */
    private String getIllegalInstantiation(String className) {
        if (illegalClasses.contains(className)) {
            return className;
        }

        final int clsNameLen = className.length();
        final int pkgNameLen = pkgName == null ? 0 : pkgName.length();

        for (String illegal : illegalClasses) {

            final int illegalLen = illegal.length();
            if (isStandardClass(className, clsNameLen, illegal, illegalLen)) {
                return illegal;
            }
            if (isSamePackage(className, clsNameLen, pkgNameLen, illegal, illegalLen)) {
                return illegal;
            }
            final String importArg = checkImportStatements(className);
            if (importArg != null) {
                return importArg;
            }
        }
        return null;
    }

    /**
     * check import statements
     * @param className name of the class
     * @return value of illegal instatiated type
     */
    private String checkImportStatements(String className) {
        // import statements
        for (FullIdent importLineText : imports) {
            final String importArg = importLineText.getText();
            if (importArg.endsWith(".*")) {
                final String fqClass =
                    importArg.substring(0, importArg.length() - 1)
                    + className;
                // assume that illegalInsts only contain existing classes
                // or else we might create a false alarm here
                if (illegalClasses.contains(fqClass)) {
                    return fqClass;
                }
            }
            else {
                if (Utils.baseClassname(importArg).equals(className)
                    && illegalClasses.contains(importArg)) {
                    return importArg;
                }
            }
        }
        return null;
    }

    /**
     * check that type is of the sme package
     * @param className class name
     * @param clsNameLen lengh of class name
     * @param pkgNameLen package name
     * @param illegal illegal value
     * @param illegalLen illegal value length
     * @return true if type of the same package
     */
    private boolean isSamePackage(String className, int clsNameLen, int pkgNameLen,
                                  String illegal, int illegalLen) {
        // class from same package

        // the toplevel package (pkgName == null) is covered by the
        // "illegalInsts.contains(className)" check above

        // the test is the "no garbage" version of
        // illegal.equals(pkgName + "." + className)
        return pkgName != null
                && clsNameLen == illegalLen - pkgNameLen - 1
                && illegal.charAt(pkgNameLen) == '.'
                && illegal.endsWith(className)
                && illegal.startsWith(pkgName);
    }

    /**
     * is Standard Class
     * @param className class name
     * @param clsNameLen class name length
     * @param illegal illegal value
     * @param illegalLen illegal value length
     * @return true if type is standard
     */
    private boolean isStandardClass(String className, int clsNameLen, String illegal,
                                    int illegalLen) {
        // class from java.lang
        if (illegalLen - JAVA_LANG.length() == clsNameLen
            && illegal.endsWith(className)
            && illegal.startsWith(JAVA_LANG)) {
            // java.lang needs no import, but a class without import might
            // also come from the same file or be in the same package.
            // E.g. if a class defines an inner class "Boolean",
            // the expression "new Boolean()" refers to that class,
            // not to java.lang.Boolean

            final boolean isSameFile = classNames.contains(className);
            final boolean isSamePackage = isSamePackage(className);

            if (!(isSameFile || isSamePackage)) {
                return true;
            }
        }
        return false;
    }

    /**
     * is class of the same package
     * @param className class name
     * @return true if same package class
     */
    private boolean isSamePackage(String className) {
        boolean isSamePackage = false;
        try {
            final ClassLoader classLoader = getClassLoader();
            if (classLoader != null) {
                final String fqName = pkgName + "." + className;
                classLoader.loadClass(fqName);
                // no ClassNotFoundException, fqName is a known class
                isSamePackage = true;
            }
        }
        catch (final ClassNotFoundException ex) {
            // not a class from the same package
            isSamePackage = false;
        }
        return isSamePackage;
    }

    /**
     * Sets the classes that are illegal to instantiate.
     * @param classNames a comma seperate list of class names
     */
    public void setClasses(String classNames) {
        illegalClasses.clear();
        final StringTokenizer tok = new StringTokenizer(classNames, ",");
        while (tok.hasMoreTokens()) {
            illegalClasses.add(tok.nextToken());
        }
    }
}
