////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Check location of annotation on language elements.
 * By default, Check enforce to locate annotations immediately after
 * documentation block and before target element, annotation should be located
 * on separate line from target element.
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * &#64;Override
 * &#64;Nullable
 * public String getNameIfPresent() { ... }
 * </pre>
 *
 * <p>
 * Check have following options:
 * </p>
 * <ul>
 * <li>mAllowSamelineMultipleAnnotations - to allow annotation to be located on
 * the same line as target element. Default value is false.
 * </li>
 *
 * <li>
 * mAllowSamelineSingleParameterlessAnnotation - to allow single parameterless
 * annotation to be located on the same line as target element. Default value is false.
 * </li>
 *
 * <li>
 * mAllowSamelineParametrizedAnnotation - to allow parameterized annotation
 * to be located on the same line as target element. Default value is false.
 * </li>
 * </ul>
 * <br/>
 * <p>
 * Example to allow single parameterless annotation on the same line:
 * </p>
 * <pre>
 * &#64;Override public int hashCode() { ... }
 * </pre>
 *
 * <p>Use following configuration:
 * <pre>
 * &lt;module name=&quot;AnnotationLocation&quot;&gt;
 *    &lt;property name=&quot;allowSamelineMultipleAnnotations&quot; value=&quot;false&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineSingleParameterlessAnnotation&quot;
 *    value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineParametrizedAnnotation&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <br/>
 * <p>
 * Example to allow multiple parameterized annotations on the same line:
 * </p>
 * <pre>
 * &#64;SuppressWarnings("deprecation") &#64;Mock DataLoader loader;
 * </pre>
 *
 * <p>Use following configuration:
 * <pre>
 * &lt;module name=&quot;AnnotationLocation&quot;&gt;
 *    &lt;property name=&quot;allowSamelineMultipleAnnotations&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineSingleParameterlessAnnotation&quot;
 *    value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineParametrizedAnnotation&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <br/>
 * <p>
 * Example to allow multiple parameterless annotations on the same line:
 * </p>
 * <pre>
 * &#64;Partial &#64;Mock DataLoader loader;
 * </pre>
 *
 * <p>Use following configuration:
 * <pre>
 * &lt;module name=&quot;AnnotationLocation&quot;&gt;
 *    &lt;property name=&quot;allowSamelineMultipleAnnotations&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineSingleParameterlessAnnotation&quot;
 *    value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineParametrizedAnnotation&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author maxvetrenko
 */
public class AnnotationLocationCheck extends Check
{
    /**
     * Some javadoc.
     */
    private boolean mAllowSamelineSingleParameterlessAnnotation = true;

    /**
     * Some javadoc.
     */
    private boolean mAllowSamelineParametrizedAnnotation;

    /**
     * Some javadoc.
     */
    private boolean mAllowSamelineMultipleAnnotations;

    /**
     * Some javadoc.
     * @param aAllow Some javadoc.
     */
    public final void setAllowSamelineSingleParameterlessAnnotation(boolean aAllow)
    {
        mAllowSamelineSingleParameterlessAnnotation = aAllow;
    }

    /**
     * Some javadoc.
     * @param aAllow Some javadoc.
     */
    public final void setAllowSamelineParametrizedAnnotation(boolean aAllow)
    {
        mAllowSamelineParametrizedAnnotation = aAllow;
    }

    /**
     * Some javadoc.
     * @param aAllow Some javadoc.
     */
    public final void setAllowSamelineMultipleAnnotations(boolean aAllow)
    {
        mAllowSamelineMultipleAnnotations = aAllow;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST modifiersNode = aAST.findFirstToken(TokenTypes.MODIFIERS);

        if (hasAnnotations(modifiersNode)) {
            checkAnnotations(modifiersNode, getAnnotationLevel(modifiersNode));
        }
    }

    /**
     * Some javadoc.
     * @param aModifierNode Some javadoc.
     * @param aCorrectLevel Some javadoc.
     */
    private void checkAnnotations(DetailAST aModifierNode, int aCorrectLevel)
    {
        DetailAST annotation = aModifierNode.getFirstChild();

        while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {
            final boolean hasParameters = isParameterized(annotation);

            if (!isCorrectLocation(annotation, hasParameters)) {
                log(annotation.getLineNo(),
                    "annotation.location.alone", getAnnotationName(annotation));
            }
            else if (annotation.getColumnNo() != aCorrectLevel && !hasNodeBefore(annotation)) {
                log(annotation.getLineNo(), "annotation.location",
                    getAnnotationName(annotation), annotation.getColumnNo(), aCorrectLevel);
            }
            annotation = annotation.getNextSibling();
        }
    }

    /**
     * Some javadoc.
     * @param aAnnotation Some javadoc.
     * @param aHasParams Some javadoc.
     * @return Some javadoc.
     */
    private boolean isCorrectLocation(DetailAST aAnnotation, boolean aHasParams)
    {
        final boolean allowingCondition = aHasParams ? mAllowSamelineParametrizedAnnotation
            : mAllowSamelineSingleParameterlessAnnotation;
        return allowingCondition && !hasNodeBefore(aAnnotation)
            || !allowingCondition && !hasNodeBeside(aAnnotation)
            || mAllowSamelineMultipleAnnotations;
    }

    /**
     * Some javadoc.
     * @param aAnnotation Some javadoc.
     * @return Some javadoc.
     */
    private static String getAnnotationName(DetailAST aAnnotation)
    {
        DetailAST idenNode = aAnnotation.findFirstToken(TokenTypes.IDENT);
        if (idenNode == null) {
            idenNode = aAnnotation.findFirstToken(TokenTypes.DOT).findFirstToken(TokenTypes.IDENT);
        }
        return idenNode.getText();
    }

    /**
     * Some javadoc.
     * @param aAnnotation Some javadoc.
     * @return Some javadoc.
     */
    private static boolean hasNodeAfter(DetailAST aAnnotation)
    {
        final int annotationLineNo = aAnnotation.getLineNo();
        DetailAST nextNode = aAnnotation.getNextSibling();

        if (nextNode == null) {
            nextNode = aAnnotation.getParent().getNextSibling();
        }

        return nextNode != null && annotationLineNo == nextNode.getLineNo();
    }

    /**
     * Some javadoc.
     * @param aAnnotation Some javadoc.
     * @return Some javadoc.
     */
    private static boolean hasNodeBefore(DetailAST aAnnotation)
    {
        final int annotationLineNo = aAnnotation.getLineNo();
        final DetailAST previousNode = aAnnotation.getPreviousSibling();

        return previousNode != null && annotationLineNo == previousNode.getLineNo();
    }

    /**
     * Some javadoc.
     * @param aAnnotation Some javadoc.
     * @return Some javadoc.
     */
    private static boolean hasNodeBeside(DetailAST aAnnotation)
    {
        return hasNodeBefore(aAnnotation) || hasNodeAfter(aAnnotation);
    }

    /**
     * Some javadoc.
     * @param aModifierNode Some javadoc.
     * @return Some javadoc.
     */
    private static int getAnnotationLevel(DetailAST aModifierNode)
    {
        return aModifierNode.getParent().getColumnNo();
    }

    /**
     * Some javadoc.
     * @param aAnnotation Some javadoc.
     * @return Some javadoc.
     */
    private static boolean isParameterized(DetailAST aAnnotation)
    {
        return aAnnotation.findFirstToken(TokenTypes.EXPR) != null;
    }

    /**
     * Some javadoc.
     * @param aModifierNode Some javadoc.
     * @return Some javadoc.
     */
    private static boolean hasAnnotations(DetailAST aModifierNode)
    {
        return aModifierNode.findFirstToken(TokenTypes.ANNOTATION) != null;
    }
}
