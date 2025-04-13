////
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks for illegal instantiations where a factory method is preferred.
 * </div>
 *
 * <p>
 * Rationale: Depending on the project, for some classes it might be
 * preferable to create instances through factory methods rather than
 * calling the constructor.
 * </p>
 *
 * <p>
 * A simple example is the {@code java.lang.Boolean} class.
 * For performance reasons, it is preferable to use the predefined constants
 * {@code TRUE} and {@code FALSE}.
 * Constructor invocations should be replaced by calls to {@code Boolean.valueOf()}.
 * </p>
 *
 * <p>
 * Some extremely performance sensitive projects may require the use of factory
 * methods for other classes as well, to enforce the usage of number caches or
 * object pools.
 * </p>
 *
 * <p>
 * There is a limitation that it is currently not possible to specify array classes.
 * </p>
 * <ul>
 * <li>
 * Property {@code classes} - Specify fully qualified class names that should not be instantiated.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code instantiation.avoid}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@FileStatefulCheck
public class IllegalInstantiationCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "instantiation.avoid";

    /** {@link java.lang} package as string. */
    private static final String JAVA_LANG = "java.lang.";

    /** The imports for the file. */
    private final Set<FullIdent> imports = new HashSet<>();

    /** The class names defined in the file. */
    private final Set<String> classNames = new HashSet<>();

    /** The instantiations in the file. */
    private final Set<DetailAST> instantiations = new HashSet<>();

    /** Specify fully qualified class names that should not be instantiated. */
    private Set<String> classes = new HashSet<>();

    /** Name of the package. */
    private String pkgName;

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
            TokenTypes.LITERAL_NEW,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.CLASS_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
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
        instantiations.forEach(this::postProcessLiteralNew);
    }

    /**
     * Collects classes defined in the source file. Required
     * to avoid false alarms for local vs. java.lang classes.
     *
     * @param ast the class def token.
     */
    private void processClassDef(DetailAST ast) {
        final DetailAST identToken = ast.findFirstToken(TokenTypes.IDENT);
        final String className = identToken.getText();
        classNames.add(className);
    }

    /**
     * Perform processing for an import token.
     *
     * @param ast the import token
     */
    private void processImport(DetailAST ast) {
        final FullIdent name = FullIdent.createFullIdentBelow(ast);
        // Note: different from UnusedImportsCheck.processImport(),
        // '.*' imports are also added here
        imports.add(name);
    }

    /**
     * Perform processing for an package token.
     *
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
     *
     * @param ast the "new" token
     */
    private void processLiteralNew(DetailAST ast) {
        if (ast.getParent().getType() != TokenTypes.METHOD_REF) {
            instantiations.add(ast);
        }
    }

    /**
     * Processes one of the collected "new" tokens when walking tree
     * has finished.
     *
     * @param newTokenAst the "new" token.
     */
    private void postProcessLiteralNew(DetailAST newTokenAst) {
        final DetailAST typeNameAst = newTokenAst.getFirstChild();
        final DetailAST nameSibling = typeNameAst.getNextSibling();
        if (nameSibling.getType() != TokenTypes.ARRAY_DECLARATOR) {
            // ast != "new Boolean[]"
            final FullIdent typeIdent = FullIdent.createFullIdent(typeNameAst);
            final String typeName = typeIdent.getText();
            final String fqClassName = getIllegalInstantiation(typeName);
            if (fqClassName != null) {
                log(newTokenAst, MSG_KEY, fqClassName);
            }
        }
    }

    /**
     * Checks illegal instantiations.
     *
     * @param className instantiated class, may or may not be qualified
     * @return the fully qualified class name of className
     *     or null if instantiation of className is OK
     */
    private String getIllegalInstantiation(String className) {
        String fullClassName = null;

        if (classes.contains(className)) {
            fullClassName = className;
        }
        else {
            final int pkgNameLen;

            if (pkgName == null) {
                pkgNameLen = 0;
            }
            else {
                pkgNameLen = pkgName.length();
            }

            for (String illegal : classes) {
                if (isSamePackage(className, pkgNameLen, illegal)
                    || isStandardClass(className, illegal)) {
                    fullClassName = illegal;
                }
                else {
                    fullClassName = checkImportStatements(className);
                }

                if (fullClassName != null) {
                    break;
                }
            }
        }
        return fullClassName;
    }

    /**
     * Check import statements.
     *
     * @param className name of the class
     * @return value of illegal instantiated type
     */
    private String checkImportStatements(String className) {
        String illegalType = null;
        // import statements
        for (FullIdent importLineText : imports) {
            String importArg = importLineText.getText();
            if (importArg.endsWith(".*")) {
                importArg = importArg.substring(0, importArg.length() - 1)
                    + className;
            }
            if (CommonUtil.baseClassName(importArg).equals(className)
                && classes.contains(importArg)) {
                illegalType = importArg;
                break;
            }
        }
        return illegalType;
    }

    /**
     * Check that type is of the same package.
     *
     * @param className class name
     * @param pkgNameLen package name
     * @param illegal illegal value
     * @return true if type of the same package
     */
    private boolean isSamePackage(String className, int pkgNameLen, String illegal) {
        // class from same package

        // the top level package (pkgName == null) is covered by the
        // "illegalInstances.contains(className)" check above

        // the test is the "no garbage" version of
        // illegal.equals(pkgName + "." + className)
        return pkgName != null
            && className.length() == illegal.length() - pkgNameLen - 1
            && illegal.charAt(pkgNameLen) == '.'
            && illegal.endsWith(className)
            && illegal.startsWith(pkgName);
    }

    /**
     * Is Standard Class.
     *
     * @param className class name
     * @param illegal illegal value
     * @return true if type is standard
     */
    private boolean isStandardClass(String className, String illegal) {
        boolean isStandardClass = false;
        // class from java.lang
        if (illegal.length() - JAVA_LANG.length() == className.length()
            && illegal.endsWith(className)
            && illegal.startsWith(JAVA_LANG)) {
            // java.lang needs no import, but a class without import might
            // also come from the same file or be in the same package.
            // E.g. if a class defines an inner class "Boolean",
            // the expression "new Boolean()" refers to that class,
            // not to java.lang.Boolean

            final boolean isSameFile = classNames.contains(className);

            if (!isSameFile) {
                isStandardClass = true;
            }
        }
        return isStandardClass;
    }

    /**
     * Setter to specify fully qualified class names that should not be instantiated.
     *
     * @param names class names
     * @since 3.0
     */
    public void setClasses(String... names) {
        classes = Arrays.stream(names).collect(Collectors.toUnmodifiableSet());
    }

}
