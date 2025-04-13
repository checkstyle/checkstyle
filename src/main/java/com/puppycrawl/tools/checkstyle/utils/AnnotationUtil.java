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

package com.puppycrawl.tools.checkstyle.utils;

import java.util.Set;
import java.util.function.Predicate;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods designed to work with annotations.
 *
 */
public final class AnnotationUtil {

    /**
     * Common message.
     */
    private static final String THE_AST_IS_NULL = "the ast is null";

    /** {@link Override Override} annotation name. */
    private static final String OVERRIDE = "Override";

    /** Fully-qualified {@link Override Override} annotation name. */
    private static final String FQ_OVERRIDE = "java.lang." + OVERRIDE;

    /** Simple and fully-qualified {@link Override Override} annotation names. */
    private static final Set<String> OVERRIDE_ANNOTATIONS = Set.of(OVERRIDE, FQ_OVERRIDE);

    /**
     * Private utility constructor.
     *
     * @throws UnsupportedOperationException if called
     */
    private AnnotationUtil() {
        throw new UnsupportedOperationException("do not instantiate.");
    }

    /**
     * Checks if the AST is annotated with the passed in annotation.
     *
     * <p>
     * This method will not look for imports or package
     * statements to detect the passed in annotation.
     * </p>
     *
     * <p>
     * To check if an AST contains a passed in annotation
     * taking into account fully-qualified names
     * (ex: java.lang.Override, Override)
     * this method will need to be called twice. Once for each
     * name given.
     * </p>
     *
     * @param ast the current node
     * @param annotation the annotation name to check for
     * @return true if contains the annotation
     */
    public static boolean containsAnnotation(final DetailAST ast,
                                             String annotation) {
        return getAnnotation(ast, annotation) != null;
    }

    /**
     * Checks if the AST is annotated with any annotation.
     *
     * @param ast the current node
     * @return {@code true} if the AST contains at least one annotation
     * @throws IllegalArgumentException when ast is null
     */
    public static boolean containsAnnotation(final DetailAST ast) {
        final DetailAST holder = getAnnotationHolder(ast);
        return holder != null && holder.findFirstToken(TokenTypes.ANNOTATION) != null;
    }

    /**
     * Checks if the given AST element is annotated with any of the specified annotations.
     *
     * <p>
     * This method accepts both simple and fully-qualified names,
     * e.g. "Override" will match both java.lang.Override and Override.
     * </p>
     *
     * @param ast The type or method definition.
     * @param annotations A collection of annotations to look for.
     * @return {@code true} if the given AST element is annotated with
     *                      at least one of the specified annotations;
     *                      {@code false} otherwise.
     * @throws IllegalArgumentException when ast or annotations are null
     */
    public static boolean containsAnnotation(DetailAST ast, Set<String> annotations) {
        if (annotations == null) {
            throw new IllegalArgumentException("annotations cannot be null");
        }
        boolean result = false;
        if (!annotations.isEmpty()) {
            final DetailAST firstMatchingAnnotation = findFirstAnnotation(ast, annotationNode -> {
                final String annotationFullIdent = getAnnotationFullIdent(annotationNode);
                return annotations.contains(annotationFullIdent);
            });
            result = firstMatchingAnnotation != null;
        }
        return result;
    }

    /**
     * Gets the full ident text of the annotation AST.
     *
     * @param annotationNode The annotation AST.
     * @return The full ident text.
     */
    private static String getAnnotationFullIdent(DetailAST annotationNode) {
        final DetailAST identNode = annotationNode.findFirstToken(TokenTypes.IDENT);
        final String annotationString;

        // If no `IDENT` is found, then we have a `DOT` -> more than 1 qualifier
        if (identNode == null) {
            final DetailAST dotNode = annotationNode.findFirstToken(TokenTypes.DOT);
            annotationString = FullIdent.createFullIdent(dotNode).getText();
        }
        else {
            annotationString = identNode.getText();
        }

        return annotationString;
    }

    /**
     * Checks if the AST is annotated with {@code Override} or
     * {@code java.lang.Override} annotation.
     *
     * @param ast the current node
     * @return {@code true} if the AST contains Override annotation
     * @throws IllegalArgumentException when ast is null
     */
    public static boolean hasOverrideAnnotation(DetailAST ast) {
        return containsAnnotation(ast, OVERRIDE_ANNOTATIONS);
    }

    /**
     * Gets the AST that holds a series of annotations for the
     * potentially annotated AST.  Returns {@code null}
     * if the passed in AST does not have an Annotation Holder.
     *
     * @param ast the current node
     * @return the Annotation Holder
     * @throws IllegalArgumentException when ast is null
     */
    public static DetailAST getAnnotationHolder(DetailAST ast) {
        if (ast == null) {
            throw new IllegalArgumentException(THE_AST_IS_NULL);
        }

        final DetailAST annotationHolder;

        if (ast.getType() == TokenTypes.ENUM_CONSTANT_DEF
            || ast.getType() == TokenTypes.PACKAGE_DEF) {
            annotationHolder = ast.findFirstToken(TokenTypes.ANNOTATIONS);
        }
        else {
            annotationHolder = ast.findFirstToken(TokenTypes.MODIFIERS);
        }

        return annotationHolder;
    }

    /**
     * Checks if the AST is annotated with the passed in annotation
     * and returns the AST representing that annotation.
     *
     * <p>
     * This method will not look for imports or package
     * statements to detect the passed in annotation.
     * </p>
     *
     * <p>
     * To check if an AST contains a passed in annotation
     * taking into account fully-qualified names
     * (ex: java.lang.Override, Override)
     * this method will need to be called twice. Once for each
     * name given.
     * </p>
     *
     * @param ast the current node
     * @param annotation the annotation name to check for
     * @return the AST representing that annotation
     * @throws IllegalArgumentException when ast or annotations are null; when annotation is blank
     */
    public static DetailAST getAnnotation(final DetailAST ast,
                                          String annotation) {
        if (ast == null) {
            throw new IllegalArgumentException(THE_AST_IS_NULL);
        }

        if (annotation == null) {
            throw new IllegalArgumentException("the annotation is null");
        }

        if (CommonUtil.isBlank(annotation)) {
            throw new IllegalArgumentException(
                "the annotation is empty or spaces");
        }

        return findFirstAnnotation(ast, annotationNode -> {
            final DetailAST firstChild = annotationNode.findFirstToken(TokenTypes.AT);
            final String name =
                FullIdent.createFullIdent(firstChild.getNextSibling()).getText();
            return annotation.equals(name);
        });
    }

    /**
     * Checks if the given AST is annotated with at least one annotation that
     * matches the given predicate and returns the AST representing the first
     * matching annotation.
     *
     * <p>
     * This method will not look for imports or package
     * statements to detect the passed in annotation.
     * </p>
     *
     * @param ast the current node
     * @param predicate The predicate which decides if an annotation matches
     * @return the AST representing that annotation
     */
    private static DetailAST findFirstAnnotation(final DetailAST ast,
                                                 Predicate<DetailAST> predicate) {
        final DetailAST holder = getAnnotationHolder(ast);
        DetailAST result = null;
        for (DetailAST child = holder.getFirstChild();
             child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.ANNOTATION && predicate.test(child)) {
                result = child;
                break;
            }
        }

        return result;
    }

}
