///
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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks location of annotation on language elements.
 * By default, Check enforce to locate annotations immediately after
 * documentation block and before target element, annotation should be located
 * on separate line from target element. This check also verifies that the annotations
 * are on the same indenting level as the annotated element if they are not on the same line.
 * </div>
 *
 * <p>
 * Attention: Elements that cannot have JavaDoc comments like local variables are not in the
 * scope of this check even though a token type like {@code VARIABLE_DEF} would match them.
 * </p>
 *
 * <p>
 * Attention: Annotations among modifiers are ignored (looks like false-negative)
 * as there might be a problem with annotations for return types:
 * </p>
 * <pre>
 * public @Nullable Long getStartTimeOrNull() { ... }
 * </pre>
 *
 * <p>
 * Such annotations are better to keep close to type.
 * Due to limitations, Checkstyle can not examine the target of an annotation.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 * <pre>
 * &#64;Override
 * &#64;Nullable
 * public String getNameIfPresent() { ... }
 * </pre>
 * <ul>
 * <li>
 * Property {@code allowSamelineMultipleAnnotations} - Allow annotation(s) to be located on
 * the same line as target element.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowSamelineParameterizedAnnotation} - Allow one and only parameterized
 * annotation to be located on the same line as target element.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowSamelineSingleParameterlessAnnotation} - Allow single parameterless
 * annotation to be located on the same line as target element.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PACKAGE_DEF">
 * PACKAGE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
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
 * {@code annotation.location}
 * </li>
 * <li>
 * {@code annotation.location.alone}
 * </li>
 * </ul>
 *
 * @since 6.0
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

    /**
     * Allow single parameterless annotation to be located on the same line as
     * target element.
     */
    private boolean allowSamelineSingleParameterlessAnnotation = true;

    /**
     * Allow one and only parameterized annotation to be located on the same line as
     * target element.
     */
    private boolean allowSamelineParameterizedAnnotation;

    /**
     * Allow annotation(s) to be located on the same line as
     * target element.
     */
    private boolean allowSamelineMultipleAnnotations;

    /**
     * Setter to allow single parameterless annotation to be located on the same line as
     * target element.
     *
     * @param allow User's value of allowSamelineSingleParameterlessAnnotation.
     * @since 6.1
     */
    public final void setAllowSamelineSingleParameterlessAnnotation(boolean allow) {
        allowSamelineSingleParameterlessAnnotation = allow;
    }

    /**
     * Setter to allow one and only parameterized annotation to be located on the same line as
     * target element.
     *
     * @param allow User's value of allowSamelineParameterizedAnnotation.
     * @since 6.4
     */
    public final void setAllowSamelineParameterizedAnnotation(boolean allow) {
        allowSamelineParameterizedAnnotation = allow;
    }

    /**
     * Setter to allow annotation(s) to be located on the same line as
     * target element.
     *
     * @param allow User's value of allowSamelineMultipleAnnotations.
     * @since 6.0
     */
    public final void setAllowSamelineMultipleAnnotations(boolean allow) {
        allowSamelineMultipleAnnotations = allow;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // ignore variable def tokens that are not field definitions
        if (ast.getType() != TokenTypes.VARIABLE_DEF
                || ast.getParent().getType() == TokenTypes.OBJBLOCK) {
            DetailAST node = ast.findFirstToken(TokenTypes.MODIFIERS);
            if (node == null) {
                node = ast.findFirstToken(TokenTypes.ANNOTATIONS);
            }
            checkAnnotations(node, getExpectedAnnotationIndentation(node));
        }
    }

    /**
     * Returns an expected annotation indentation.
     * The expected indentation should be the same as the indentation of the target node.
     *
     * @param node modifiers or annotations node.
     * @return the annotation indentation.
     */
    private static int getExpectedAnnotationIndentation(DetailAST node) {
        return node.getColumnNo();
    }

    /**
     * Checks annotations positions in code:
     * 1) Checks whether the annotations locations are correct.
     * 2) Checks whether the annotations have the valid indentation level.
     *
     * @param modifierNode modifiers node.
     * @param correctIndentation correct indentation of the annotation.
     */
    private void checkAnnotations(DetailAST modifierNode, int correctIndentation) {
        DetailAST annotation = modifierNode.getFirstChild();

        while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {
            final boolean hasParameters = isParameterized(annotation);

            if (!isCorrectLocation(annotation, hasParameters)) {
                log(annotation,
                        MSG_KEY_ANNOTATION_LOCATION_ALONE, getAnnotationName(annotation));
            }
            else if (annotation.getColumnNo() != correctIndentation && !hasNodeBefore(annotation)) {
                log(annotation, MSG_KEY_ANNOTATION_LOCATION,
                    getAnnotationName(annotation), annotation.getColumnNo(), correctIndentation);
            }
            annotation = annotation.getNextSibling();
        }
    }

    /**
     * Checks whether an annotation has parameters.
     *
     * @param annotation annotation node.
     * @return true if the annotation has parameters.
     */
    private static boolean isParameterized(DetailAST annotation) {
        return TokenUtil.findFirstTokenByPredicate(annotation, ast -> {
            return ast.getType() == TokenTypes.EXPR
                || ast.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR;
        }).isPresent();
    }

    /**
     * Returns the name of the given annotation.
     *
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
     * 3) checks annotation location;
     *
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
            || !hasNodeBeside(annotation);
    }

    /**
     * Checks whether an annotation node has any node before on the same line.
     *
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
     *
     * @param annotation annotation node.
     * @return true if an annotation node has any node before or after on the same line.
     */
    private static boolean hasNodeBeside(DetailAST annotation) {
        return hasNodeBefore(annotation) || hasNodeAfter(annotation);
    }

    /**
     * Checks whether an annotation node has any node after on the same line.
     *
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

}
