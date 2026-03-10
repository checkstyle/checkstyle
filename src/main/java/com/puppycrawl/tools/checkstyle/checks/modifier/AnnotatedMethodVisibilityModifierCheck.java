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
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
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
 * @since 13.4.0
 */
@FileStatefulCheck
public class AnnotatedMethodVisibilityModifierCheck extends AbstractCheck {

    /**
     * Message key for violation.
     */
    public static final String MSG_KEY = "annotated.visibility.modifier";

    /**
     * "protected" visibility string.
     */
    private static final String PROTECTED = "protected";

    /**
     * "public" visibility string.
     */
    private static final String PUBLIC = "public";

    /**
     * "private" visibility string.
     */
    private static final String PRIVATE = "private";

    /**
     * "package-private" visibility string.
     */
    private static final String PACKAGE_PRIVATE = "package-private";

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
     * Allowed visibility values.
     * Acceptable values: public, protected, package-private, private.
     */
    private final Set<String> visibility = new HashSet<>(
            Set.of(PROTECTED, PACKAGE_PRIVATE));

    /**
     * Set of non star imports.
     */
    private final Map<String, String> importedAnnotations = new HashMap<>();

    /**
     * Set of star imports.
     */
    private final Set<String> starImports = new HashSet<>();

    /**
     * Current package.
     */
    private String currentPackage;

    /**
     * Setter for annotation canonical names.
     *
     * @param values comma-separated fully qualified annotation names
     * @since 13.4.0
     */
    public void setAnnotations(String... values) {
        annotations.clear();
        annotations.addAll(Arrays.asList(values));
    }

    /**
     * Setter for allowed visibility modifiers.
     * Allowed values:
     * public, protected, private, package-private
     *
     * @param values comma-separated visibility names
     * @since 13.4.0
     */
    public void setVisibility(String... values) {
        visibility.clear();
        visibility.addAll(Arrays.asList(values));
    }

    @Override
    public void beginTree(DetailAST rootAST) {
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
        if (ast.getParent().getType() == TokenTypes.OBJBLOCK
                && hasConfiguredAnnotation(ast)) {
            final VisibilityInfo info = getVisibilityInfo(ast);
            if (!visibility.contains(info.visibility())) {
                log(info.node(), MSG_KEY, info.visibility());
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
     * Extracts visibility information from the AST node.
     *
     * @param ast node to inspect
     * @return visibility information record
     */
    private static VisibilityInfo getVisibilityInfo(DetailAST ast) {
        final DetailAST modifiers =
                NullUtil.notNull(ast.findFirstToken(TokenTypes.MODIFIERS));
        String visibility = PACKAGE_PRIVATE;
        DetailAST node = NullUtil.notNull(ast.findFirstToken(TokenTypes.IDENT));
        final DetailAST publicModifier = modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC);
        final DetailAST protectedModifier = modifiers.findFirstToken(TokenTypes.LITERAL_PROTECTED);
        final DetailAST privateModifier = modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE);
        if (publicModifier != null) {
            visibility = PUBLIC;
            node = publicModifier;
        }
        else if (protectedModifier != null) {
            visibility = PROTECTED;
            node = protectedModifier;
        }
        else if (privateModifier != null) {
            visibility = PRIVATE;
            node = privateModifier;
        }
        else if (isInsideInterface(ast)) {
            visibility = PUBLIC;
        }
        return new VisibilityInfo(visibility, node);
    }

    /**
     * Determines whether the AST node is inside an interface.
     *
     * @param ast node to inspect
     * @return true if inside interface
     */
    private static boolean isInsideInterface(DetailAST ast) {
        boolean result = false;
        DetailAST parent = ast.getParent();
        while (parent != null) {
            if (parent.getType() == TokenTypes.INTERFACE_DEF) {
                result = true;
            }
            parent = parent.getParent();
        }
        return result;
    }

    /**
     * Record holding visibility information.
     *
     * @param visibility visibility value
     * @param node AST node where violation should be reported
     */
    private record VisibilityInfo(String visibility, DetailAST node) {

    }

}
