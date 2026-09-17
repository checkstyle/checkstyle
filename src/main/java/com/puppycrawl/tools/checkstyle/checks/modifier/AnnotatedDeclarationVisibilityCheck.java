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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;

/**
 * <div>
 * Checks that elements annotated with specified annotations
 * have only allowed visibility modifiers.
 * </div>
 *
 * <p>
 * This check enforces consistency between annotation presence and
 * declared visibility. If a configured annotation is found on a target
 * element, its visibility modifier must match one of the allowed values.
 * </p>
 *
 * @since 13.9.0
 */
@FileStatefulCheck
public class AnnotatedDeclarationVisibilityCheck extends AbstractCheck {

    /**
     * Message key for violation.
     */
    public static final String MSG_KEY = "annotated.visibility.modifier";

    /**
     * Dot.
     */
    private static final char DOT = '.';

    /**
     * Configured annotation canonical names.
     */
    private final Set<String> annotations = new HashSet<>(
            Set.of("com.google.common.annotations.VisibleForTesting"));

    /**
     * Set of non star imports.
     */
    private final Map<String, String> importedAnnotations = new HashMap<>();

    /**
     * Set of star imports.
     */
    private final Set<String> starImports = new HashSet<>();

    /**
     * Allowed visibility values.
     */
    private AccessModifierOption[] visibility = {
        AccessModifierOption.PROTECTED,
        AccessModifierOption.PACKAGE,
    };

    /**
     * Current package.
     */
    private String currentPackage;

    /**
     * Creates a new {@code AnnotatedDeclarationVisibilityCheck} instance.
     */
    public AnnotatedDeclarationVisibilityCheck() {
        // no code by default
    }

    /**
     * Setter for annotation canonical names.
     *
     * @param values comma-separated fully qualified annotation names
     * @since 13.9.0
     */
    public void setAnnotations(String... values) {
        annotations.clear();
        annotations.addAll(Arrays.asList(values));
    }

    /**
     * Setter for allowed visibility modifiers.
     * Allowed values:
     * public, protected, package, private.
     *
     * @param values allowed visibility values
     * @since 13.9.0
     */
    public void setVisibility(AccessModifierOption... values) {
        visibility = Arrays.copyOf(values, values.length);
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        currentPackage = "";
        importedAnnotations.clear();
        starImports.clear();
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            // annotation name resolution
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            // annotation name resolution
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            // tokens that can have annotations
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF -> handlePackage(ast);
            case TokenTypes.IMPORT -> handleImport(ast);
            default -> checkAnnotatedVisibility(ast);
        }
    }

    /**
     * Handles package declarations and stores the current package name.
     *
     * @param ast package definition node
     */
    private void handlePackage(DetailAST ast) {
        currentPackage =
                FullIdent.createFullIdent(ast.getLastChild().getPreviousSibling()).getText();
    }

    /**
     * Processes import statements and records imported annotations.
     *
     * @param ast import node
     */
    private void handleImport(DetailAST ast) {
        final String importText = FullIdent.createFullIdentBelow(ast).getText();
        if (importText.endsWith(".*")) {
            starImports.add(importText.substring(0, importText.length() - 2));
        }
        else {
            final int lastDot = importText.lastIndexOf(DOT);
            final String simple = importText.substring(lastDot + 1);
            importedAnnotations.put(simple, importText);
        }
    }

    /**
     * Checks the visibility of annotated elements.
     *
     * @param ast AST node to inspect
     */
    private void checkAnnotatedVisibility(DetailAST ast) {
        if ((ast.getType() != TokenTypes.VARIABLE_DEF
                || ast.getParent().getType() == TokenTypes.OBJBLOCK)
                && hasConfiguredAnnotation(ast)) {
            final AccessModifierOption accessModifierOption =
                CheckUtil.getAccessModifierFromModifiersToken(ast);
            if (!isAllowedVisibility(accessModifierOption)) {
                log(ast, MSG_KEY, getVisibilityName(accessModifierOption));
            }
        }
    }

    /**
     * Determines whether the AST node contains a configured annotation.
     *
     * @param ast AST node to inspect
     * @return true if the annotation is present
     */
    private boolean hasConfiguredAnnotation(DetailAST ast) {
        boolean result = false;
        final DetailAST modifiers =
                NullUtil.notNull(ast.findFirstToken(TokenTypes.MODIFIERS));
        DetailAST child = modifiers.getFirstChild();
        while (child != null) {
            final String annotationText =
                    AnnotationUtil.getAnnotationFullIdent(child);
            final String resolved = resolveAnnotation(annotationText);
            if (annotations.contains(resolved)) {
                result = true;
            }
            child = child.getNextSibling();
        }
        return result;
    }

    /**
     * Resolves the fully qualified name of an annotation.
     *
     * @param name annotation name
     * @return resolved canonical name
     */
    private String resolveAnnotation(String name) {
        String result = name;
        final String pkgCandidate = currentPackage + DOT + name;
        final String importCandidate = importedAnnotations.get(name);
        final String javaLangCandidate = "java.lang." + name;
        if (annotations.contains(pkgCandidate)) {
            result = pkgCandidate;
        }
        else if (importCandidate != null) {
            result = importCandidate;
        }
        else if (annotations.contains(javaLangCandidate)) {
            result = javaLangCandidate;
        }
        else {
            for (String starImport : starImports) {
                final String starCandidate = starImport + DOT + name;
                if (annotations.contains(starCandidate)) {
                    result = starCandidate;
                }
            }
        }
        return result;
    }

    /**
     * Checks whether the given visibility value is allowed.
     *
     * @param accessModifier visibility value
     * @return true if given visibility value is allowed
     */
    private boolean isAllowedVisibility(AccessModifierOption accessModifier) {
        return Arrays.stream(visibility)
                .anyMatch(modifier -> modifier == accessModifier);
    }

    /**
     * Gets the visibility value to display in violation messages.
     *
     * @param accessModifier visibility value
     * @return display name of the visibility value
     */
    private static String getVisibilityName(AccessModifierOption accessModifier) {
        final String result;
        if (accessModifier == AccessModifierOption.PACKAGE) {
            result = "package-private";
        }
        else {
            result = accessModifier.toString();
        }
        return result;
    }

}
