////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;


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
     * @param aAST the current node
     * @param aAnnotation the annotation name to check for
     * @return true if contains the annotation
     * @throws NullPointerException if the aAST or
     * aAnnotation is null
     */
    public static boolean containsAnnotation(final DetailAST aAST,
        String aAnnotation)
    {
        return AnnotationUtility.getAnnotation(aAST, aAnnotation) != null;
    }

    /**
     * Checks to see if the AST is annotated with
     * any annotation.
     *
     * @param aAST the current node
     * @return true if contains an annotation
     * @throws NullPointerException if the aAST is null
     */
    public static boolean containsAnnotation(final DetailAST aAST)
    {
        final DetailAST holder = AnnotationUtility.getAnnotationHolder(aAST);
        return holder != null && holder.branchContains(TokenTypes.ANNOTATION);
    }

    /**
     * Gets the AST that holds a series of annotations for the
     * potentially annotated AST.  Returns <code>null</code>
     * the passed in AST is not have an Annotation Holder.
     *
     * @param aAST the current node
     * @return the Annotation Holder
     * @throws NullPointerException if the aAST is null
     */
    public static DetailAST getAnnotationHolder(DetailAST aAST)
    {
        if (aAST == null) {
            throw new NullPointerException("the aAST is null");
        }

        final DetailAST annotationHolder;

        if (aAST.getType() == TokenTypes.ENUM_CONSTANT_DEF
            || aAST.getType() == TokenTypes.PACKAGE_DEF)
        {
            annotationHolder = aAST.findFirstToken(TokenTypes.ANNOTATIONS);
        }
        else {
            annotationHolder = aAST.findFirstToken(TokenTypes.MODIFIERS);
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
     * @param aAST the current node
     * @param aAnnotation the annotation name to check for
     * @return the AST representing that annotation
     * @throws NullPointerException if the aAST or
     * aAnnotation is null
     */
    public static DetailAST getAnnotation(final DetailAST aAST,
        String aAnnotation)
    {
        if (aAST == null) {
            throw new NullPointerException("the aAST is null");
        }

        if (aAnnotation == null) {
            throw new NullPointerException("the aAnnotation is null");
        }

        if (aAnnotation.trim().length() == 0) {
            throw new IllegalArgumentException("the aAnnotation"
                + "is empty or spaces");
        }

        final DetailAST holder = AnnotationUtility.getAnnotationHolder(aAST);

        for (DetailAST child = holder.getFirstChild();
            child != null; child = child.getNextSibling())
        {
            if (child.getType() == TokenTypes.ANNOTATION) {
                final DetailAST at = child.getFirstChild();
                final String aName =
                    FullIdent.createFullIdent(at.getNextSibling()).getText();
                if (aAnnotation.equals(aName)) {
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
     * @param aAST the AST representing an annotation.
     * @return the AST the annotation is annotating.
     * @throws NullPointerException if the aAST is null
     * @throws IllegalArgumentException if the aAST is not
     * an {@link TokenTypes#ANNOTATION}
     */
    public static DetailAST annotatingWhat(DetailAST aAST)
    {
        if (aAST == null) {
            throw new NullPointerException("the aAST is null");
        }

        if (aAST.getType() != TokenTypes.ANNOTATION) {
            throw new IllegalArgumentException(
                "The aAST is not an annotation. AST: " + aAST);
        }

        return aAST.getParent().getParent();
    }

    /**
     * Checks to see if the passed in AST (representing
     * an annotation) is annotating the passed in type.
     * @param aAST the AST representing an annotation
     * @param aTokenType the passed in type
     * @return true if the annotation is annotating a type
     * equal to the passed in type
     * @throws NullPointerException if the aAST is null
     * @throws IllegalArgumentException if the aAST is not
     * an {@link TokenTypes#ANNOTATION}
     */
    public static boolean isAnnotatingType(DetailAST aAST, int aTokenType)
    {
        final DetailAST ast = AnnotationUtility.annotatingWhat(aAST);
        return ast.getType() == aTokenType;
    }
}
