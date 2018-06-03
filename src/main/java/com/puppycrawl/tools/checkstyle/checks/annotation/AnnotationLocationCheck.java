////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Check location of annotation on language elements.
 * By default, Check enforce to locate annotations immediately after
 * documentation block and before target element, annotation should be located
 * on separate line from target element.
 * <p>
 * Attention: Annotations among modifiers are ignored (looks like false-negative)
 * as there might be a problem with annotations for return types.
 * </p>
 * <pre>public @Nullable Long getStartTimeOrNull() { ... }</pre>.
 * <p>
 * Such annotations are better to keep close to type.
 * Due to limitations, Checkstyle can not examine the target of an annotation.
 * </p>
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
 * The check has the following options:
 * </p>
 * <ul>
 * <li>allowSamelineMultipleAnnotations - to allow annotation to be located on
 * the same line as the target element. Default value is false.
 * </li>
 *
 * <li>
 * allowSamelineSingleParameterlessAnnotation - to allow single parameterless
 * annotation to be located on the same line as the target element. Default value is false.
 * </li>
 *
 * <li>
 * allowSamelineParameterizedAnnotation - to allow parameterized annotation
 * to be located on the same line as the target element. Default value is false.
 * </li>
 * </ul>
 * <br>
 * <p>
 * Example to allow single parameterless annotation on the same line:
 * </p>
 * <pre>
 * &#64;Override public int hashCode() { ... }
 * </pre>
 *
 * <p>Use the following configuration:
 * <pre>
 * &lt;module name=&quot;AnnotationLocation&quot;&gt;
 *    &lt;property name=&quot;allowSamelineMultipleAnnotations&quot; value=&quot;false&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineSingleParameterlessAnnotation&quot;
 *    value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineParameterizedAnnotation&quot; value=&quot;false&quot;
 *    /&gt;
 * &lt;/module&gt;
 * </pre>
 * <br>
 * <p>
 * Example to allow multiple parameterized annotations on the same line:
 * </p>
 * <pre>
 * &#64;SuppressWarnings("deprecation") &#64;Mock DataLoader loader;
 * </pre>
 *
 * <p>Use the following configuration:
 * <pre>
 * &lt;module name=&quot;AnnotationLocation&quot;&gt;
 *    &lt;property name=&quot;allowSamelineMultipleAnnotations&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineSingleParameterlessAnnotation&quot;
 *    value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineParameterizedAnnotation&quot; value=&quot;true&quot;
 *    /&gt;
 * &lt;/module&gt;
 * </pre>
 * <br>
 * <p>
 * Example to allow multiple parameterless annotations on the same line:
 * </p>
 * <pre>
 * &#64;Partial &#64;Mock DataLoader loader;
 * </pre>
 *
 * <p>Use the following configuration:
 * <pre>
 * &lt;module name=&quot;AnnotationLocation&quot;&gt;
 *    &lt;property name=&quot;allowSamelineMultipleAnnotations&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineSingleParameterlessAnnotation&quot;
 *    value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineParameterizedAnnotation&quot; value=&quot;false&quot;
 *    /&gt;
 * &lt;/module&gt;
 * </pre>
 * <br>
 * <p>
 * The following example demonstrates how the check validates annotation of method parameters,
 * catch parameters, foreach, for-loop variable definitions.
 * </p>
 *
 * <p>Configuration:
 * <pre>
 * &lt;module name=&quot;AnnotationLocation&quot;&gt;
 *    &lt;property name=&quot;allowSamelineMultipleAnnotations&quot; value=&quot;false&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineSingleParameterlessAnnotation&quot;
 *    value=&quot;false&quot;/&gt;
 *    &lt;property name=&quot;allowSamelineParameterizedAnnotation&quot; value=&quot;false&quot;
 *    /&gt;
 *    &lt;property name=&quot;tokens&quot; value=&quot;VARIABLE_DEF, PARAMETER_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>Code example
 * {@code
 * ...
 * public void test(&#64;MyAnnotation String s) { // OK
 *   ...
 *   for (&#64;MyAnnotation char c : s.toCharArray()) { ... }  // OK
 *   ...
 *   try { ... }
 *   catch (&#64;MyAnnotation Exception ex) { ... } // OK
 *   ...
 *   for (&#64;MyAnnotation int i = 0; i &lt; 10; i++) { ... } // OK
 *   ...
 *   MathOperation c = (&#64;MyAnnotation int a, &#64;MyAnnotation int b) -&gt; a + b; // OK
 *   ...
 * }
 * }
 *
 */
@StatelessCheck
public class AnnotationLocationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_LOCATION_ALONE = "annotation.location.alone";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_LOCATION = "annotation.location";

    /** Array of single line annotation parents. */
    private static final int[] SINGLELINE_ANNOTATION_PARENTS = {TokenTypes.FOR_EACH_CLAUSE,
                                                                TokenTypes.PARAMETER_DEF,
                                                                TokenTypes.FOR_INIT, };

    /**
     * If true, it allows single prameterless annotation to be located on the same line as
     * target element.
     */
    private boolean allowSamelineSingleParameterlessAnnotation = true;

    /**
     * If true, it allows parameterized annotation to be located on the same line as
     * target element.
     */
    private boolean allowSamelineParameterizedAnnotation;

    /**
     * If true, it allows annotation to be located on the same line as
     * target element.
     */
    private boolean allowSamelineMultipleAnnotations;

    /**
     * Sets if allow same line single parameterless annotation.
     * @param allow User's value of allowSamelineSingleParameterlessAnnotation.
     */
    public final void setAllowSamelineSingleParameterlessAnnotation(boolean allow) {
        allowSamelineSingleParameterlessAnnotation = allow;
    }

    /**
     * Sets if allow parameterized annotation to be located on the same line as
     * target element.
     * @param allow User's value of allowSamelineParameterizedAnnotation.
     */
    public final void setAllowSamelineParameterizedAnnotation(boolean allow) {
        allowSamelineParameterizedAnnotation = allow;
    }

    /**
     * Sets if allow annotation to be located on the same line as
     * target element.
     * @param allow User's value of allowSamelineMultipleAnnotations.
     */
    public final void setAllowSamelineMultipleAnnotations(boolean allow) {
        allowSamelineMultipleAnnotations = allow;
    }

    @Override
    public int[] getDefaultTokens() {
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
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.TYPECAST,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.IMPLEMENTS_CLAUSE,
            TokenTypes.TYPE_ARGUMENT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.DOT,
            TokenTypes.ANNOTATION_FIELD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST modifiersNode = ast.findFirstToken(TokenTypes.MODIFIERS);

        if (hasAnnotations(modifiersNode)) {
            checkAnnotations(modifiersNode, getExpectedAnnotationIndentation(modifiersNode));
        }
    }

    /**
     * Checks whether a given modifier node has an annotation.
     * @param modifierNode modifier node.
     * @return true if the given modifier node has the annotation.
     */
    private static boolean hasAnnotations(DetailAST modifierNode) {
        return modifierNode != null
            && modifierNode.findFirstToken(TokenTypes.ANNOTATION) != null;
    }

    /**
     * Returns an expected annotation indentation.
     * The expected indentation should be the same as the indentation of the node
     * which is the parent of the target modifier node.
     * @param modifierNode modifier node.
     * @return the annotation indentation.
     */
    private static int getExpectedAnnotationIndentation(DetailAST modifierNode) {
        return modifierNode.getParent().getColumnNo();
    }

    /**
     * Checks annotations positions in code:
     * 1) Checks whether the annotations locations are correct.
     * 2) Checks whether the annotations have the valid indentation level.
     * @param modifierNode modifiers node.
     * @param correctIndentation correct indentation of the annotation.
     */
    private void checkAnnotations(DetailAST modifierNode, int correctIndentation) {
        DetailAST annotation = modifierNode.getFirstChild();

        while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {
            final boolean hasParameters = isParameterized(annotation);

            if (!isCorrectLocation(annotation, hasParameters)) {
                log(annotation.getLineNo(),
                        MSG_KEY_ANNOTATION_LOCATION_ALONE, getAnnotationName(annotation));
            }
            else if (annotation.getColumnNo() != correctIndentation && !hasNodeBefore(annotation)) {
                log(annotation.getLineNo(), MSG_KEY_ANNOTATION_LOCATION,
                    getAnnotationName(annotation), annotation.getColumnNo(), correctIndentation);
            }
            annotation = annotation.getNextSibling();
        }
    }

    /**
     * Checks whether an annotation has parameters.
     * @param annotation annotation node.
     * @return true if the annotation has parameters.
     */
    private static boolean isParameterized(DetailAST annotation) {
        return annotation.findFirstToken(TokenTypes.EXPR) != null;
    }

    /**
     * Returns the name of the given annotation.
     * @param annotation annotation node.
     * @return annotation name.
     */
    private static String getAnnotationName(DetailAST annotation) {
        DetailAST identNode = annotation.findFirstToken(TokenTypes.IDENT);
        if (identNode == null) {
            identNode = annotation.findFirstToken(TokenTypes.DOT).findFirstToken(TokenTypes.IDENT);
        }
        return identNode.getText();
    }

    /**
     * Checks whether an annotation has a correct location.
     * Annotation location is considered correct
     * if {@link AnnotationLocationCheck#allowSamelineMultipleAnnotations} is set to true.
     * The method also:
     * 1) checks parameterized annotation location considering
     * the value of {@link AnnotationLocationCheck#allowSamelineParameterizedAnnotation};
     * 2) checks parameterless annotation location considering
     * the value of {@link AnnotationLocationCheck#allowSamelineSingleParameterlessAnnotation};
     * 3) checks annotation location considering the elements
     * of {@link AnnotationLocationCheck#SINGLELINE_ANNOTATION_PARENTS};
     * @param annotation annotation node.
     * @param hasParams whether an annotation has parameters.
     * @return true if the annotation has a correct location.
     */
    private boolean isCorrectLocation(DetailAST annotation, boolean hasParams) {
        final boolean allowingCondition;

        if (hasParams) {
            allowingCondition = allowSamelineParameterizedAnnotation;
        }
        else {
            allowingCondition = allowSamelineSingleParameterlessAnnotation;
        }
        return allowSamelineMultipleAnnotations
            || allowingCondition && !hasNodeBefore(annotation)
            || !allowingCondition && (!hasNodeBeside(annotation)
            || isAllowedPosition(annotation, SINGLELINE_ANNOTATION_PARENTS));
    }

    /**
     * Checks whether an annotation node has any node before on the same line.
     * @param annotation annotation node.
     * @return true if an annotation node has any node before on the same line.
     */
    private static boolean hasNodeBefore(DetailAST annotation) {
        final int annotationLineNo = annotation.getLineNo();
        final DetailAST previousNode = annotation.getPreviousSibling();

        return previousNode != null && annotationLineNo == previousNode.getLineNo();
    }

    /**
     * Checks whether an annotation node has any node before or after on the same line.
     * @param annotation annotation node.
     * @return true if an annotation node has any node before or after on the same line.
     */
    private static boolean hasNodeBeside(DetailAST annotation) {
        return hasNodeBefore(annotation) || hasNodeAfter(annotation);
    }

    /**
     * Checks whether an annotation node has any node after on the same line.
     * @param annotation annotation node.
     * @return true if an annotation node has any node after on the same line.
     */
    private static boolean hasNodeAfter(DetailAST annotation) {
        final int annotationLineNo = annotation.getLineNo();
        DetailAST nextNode = annotation.getNextSibling();

        if (nextNode == null) {
            nextNode = annotation.getParent().getNextSibling();
        }

        return annotationLineNo == nextNode.getLineNo();
    }

    /**
     * Checks whether position of annotation is allowed.
     * @param annotation annotation token.
     * @param allowedPositions an array of allowed annotation positions.
     * @return true if position of annotation is allowed.
     */
    private static boolean isAllowedPosition(DetailAST annotation, int... allowedPositions) {
        boolean allowed = false;
        for (int position : allowedPositions) {
            if (isInSpecificCodeBlock(annotation, position)) {
                allowed = true;
                break;
            }
        }
        return allowed;
    }

    /**
     * Checks whether the scope of a node is restricted to a specific code block.
     * @param node node.
     * @param blockType block type.
     * @return true if the scope of a node is restricted to a specific code block.
     */
    private static boolean isInSpecificCodeBlock(DetailAST node, int blockType) {
        boolean returnValue = false;
        for (DetailAST token = node.getParent(); token != null; token = token.getParent()) {
            final int type = token.getType();
            if (type == blockType) {
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }

}
