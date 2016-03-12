////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import com.google.common.base.CharMatcher;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods designed to work with annotations.
 *
 * @author Travis Schneeberger
 */
public final class AnnotationUtility {

    /**
     * Common message.
     */
    private static final String THE_AST_IS_NULL = "the ast is null";

    /**
     * Private utility constructor.
     * @throws UnsupportedOperationException if called
     */
    private AnnotationUtility() {
        throw new UnsupportedOperationException("do not instantiate.");
    }

    /**
     * Checks to see if the AST is annotated with
     * the passed in annotation.
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
        if (ast == null) {
            throw new IllegalArgumentException(THE_AST_IS_NULL);
        }
        return getAnnotation(ast, annotation) != null;
    }

    /**
     * Checks to see if the AST is annotated with
     * any annotation.
     *
     * @param ast the current node
     * @return true if contains an annotation
     */
    public static boolean containsAnnotation(final DetailAST ast) {
        if (ast == null) {
            throw new IllegalArgumentException(THE_AST_IS_NULL);
        }
        final DetailAST holder = getAnnotationHolder(ast);
        return holder != null && holder.branchContains(TokenTypes.ANNOTATION);
    }

    /**
     * Gets the AST that holds a series of annotations for the
     * potentially annotated AST.  Returns {@code null}
     * the passed in AST is not have an Annotation Holder.
     *
     * @param ast the current node
     * @return the Annotation Holder
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
     * Checks to see if the AST is annotated with
     * the passed in annotation and return the AST
     * representing that annotation.
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
     */
    public static DetailAST getAnnotation(final DetailAST ast,
        String annotation) {
        if (ast == null) {
            throw new IllegalArgumentException(THE_AST_IS_NULL);
        }

        if (annotation == null) {
            throw new IllegalArgumentException("the annotation is null");
        }

        if (CharMatcher.WHITESPACE.matchesAllOf(annotation)) {
            throw new IllegalArgumentException(
                    "the annotation is empty or spaces");
        }

        final DetailAST holder = getAnnotationHolder(ast);

        for (DetailAST child = holder.getFirstChild();
            child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.ANNOTATION) {
                final DetailAST firstChild = child.getFirstChild();
                final String name =
                    FullIdent.createFullIdent(firstChild.getNextSibling()).getText();
                if (annotation.equals(name)) {
                    return child;
                }
            }
        }

        return null;
    }

}
