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

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.apache.commons.lang3.StringUtils;

/**
 * Contains utility methods designed to work with annotations.
 *
 * @author Travis Schneeberger
 */
public final class AnnotationUtility
{
    /**
     * private utility constructor.
     * @throws UnsupportedOperationException if called
     */
    private AnnotationUtility()
    {
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
     * @throws NullPointerException if the ast or
     * annotation is null
     */
    public static boolean containsAnnotation(final DetailAST ast,
        String annotation)
    {
        return AnnotationUtility.getAnnotation(ast, annotation) != null;
    }

    /**
     * Checks to see if the AST is annotated with
     * any annotation.
     *
     * @param ast the current node
     * @return true if contains an annotation
     * @throws NullPointerException if the ast is null
     */
    public static boolean containsAnnotation(final DetailAST ast)
    {
        final DetailAST holder = AnnotationUtility.getAnnotationHolder(ast);
        return holder != null && holder.branchContains(TokenTypes.ANNOTATION);
    }

    /**
     * Gets the AST that holds a series of annotations for the
     * potentially annotated AST.  Returns <code>null</code>
     * the passed in AST is not have an Annotation Holder.
     *
     * @param ast the current node
     * @return the Annotation Holder
     * @throws NullPointerException if the ast is null
     */
    public static DetailAST getAnnotationHolder(DetailAST ast)
    {
        if (ast == null) {
            throw new IllegalArgumentException("the ast is null");
        }

        final DetailAST annotationHolder;

        if (ast.getType() == TokenTypes.ENUM_CONSTANT_DEF
            || ast.getType() == TokenTypes.PACKAGE_DEF)
        {
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
     * @throws NullPointerException if the ast or
     * annotation is null
     */
    public static DetailAST getAnnotation(final DetailAST ast,
        String annotation)
    {
        if (ast == null) {
            throw new IllegalArgumentException("the ast is null");
        }

        if (annotation == null) {
            throw new IllegalArgumentException("the annotation is null");
        }

        if (StringUtils.isBlank(annotation)) {
            throw new IllegalArgumentException("the annotation"
                + "is empty or spaces");
        }

        final DetailAST holder = AnnotationUtility.getAnnotationHolder(ast);

        for (DetailAST child = holder.getFirstChild();
            child != null; child = child.getNextSibling())
        {
            if (child.getType() == TokenTypes.ANNOTATION) {
                final DetailAST at = child.getFirstChild();
                final String name =
                    FullIdent.createFullIdent(at.getNextSibling()).getText();
                if (annotation.equals(name)) {
                    return child;
                }
            }
        }

        return null;
    }

    /**
     * Checks to see what the passed in AST (representing
     * an annotation) is annotating.
     *
     * @param ast the AST representing an annotation.
     * @return the AST the annotation is annotating.
     * @throws NullPointerException if the ast is null
     * @throws IllegalArgumentException if the ast is not
     * an {@link TokenTypes#ANNOTATION}
     */
    public static DetailAST annotatingWhat(DetailAST ast)
    {
        if (ast == null) {
            throw new IllegalArgumentException("the ast is null");
        }

        if (ast.getType() != TokenTypes.ANNOTATION) {
            throw new IllegalArgumentException(
                "The ast is not an annotation. AST: " + ast);
        }

        return ast.getParent().getParent();
    }

    /**
     * Checks to see if the passed in AST (representing
     * an annotation) is annotating the passed in type.
     * @param ast the AST representing an annotation
     * @param tokenType the passed in type
     * @return true if the annotation is annotating a type
     * equal to the passed in type
     * @throws NullPointerException if the ast is null
     * @throws IllegalArgumentException if the ast is not
     * an {@link TokenTypes#ANNOTATION}
     */
    public static boolean isAnnotatingType(DetailAST ast, int tokenType)
    {
        final DetailAST astNode = AnnotationUtility.annotatingWhat(ast);
        return astNode.getType() == tokenType;
    }
}
