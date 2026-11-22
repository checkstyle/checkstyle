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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck.MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.instantiate;

import java.io.File;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck;

public class AnnotationUtilTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/annotationutil";
    }

    @Test
    public void testIsProperUtilsClass() {
        final ReflectiveOperationException exc =
                getExpectedThrowable(ReflectiveOperationException.class, () -> {
                    instantiate(AnnotationUtil.class);
                });
        assertWithMessage("Invalid exception message")
            .that(exc)
            .hasCauseThat()
            .hasMessageThat()
            .isEqualTo("do not instantiate.");
    }

    @Test
    public void testContainsAnnotationNull() {
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.containsAnnotation(null);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("the ast is null");
    }

    @Test
    public void testContainsAnnotationNull2() {
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.containsAnnotation(null, "");
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("the ast is null");
    }

    @Test
    public void testContainsAnnotationFalse() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(1);
        assertWithMessage("AnnotationUtil should not contain %s", ast)
                .that(AnnotationUtil.containsAnnotation(ast))
                .isFalse();
    }

    @Test
    public void testContainsAnnotationFalse2() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(1);
        final DetailAstImpl ast2 = new DetailAstImpl();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);
        assertWithMessage("AnnotationUtil should not contain %s", ast)
                .that(AnnotationUtil.containsAnnotation(ast))
                .isFalse();
    }

    @Test
    public void testContainsAnnotationTrue() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(1);
        final DetailAstImpl ast2 = new DetailAstImpl();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);
        final DetailAstImpl ast3 = new DetailAstImpl();
        ast3.setType(TokenTypes.ANNOTATION);
        ast2.addChild(ast3);
        assertWithMessage("AnnotationUtil should contain %s", ast)
                .that(AnnotationUtil.containsAnnotation(ast))
                .isTrue();
    }

    @Test
    public void testAnnotationHolderNull() {
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.getAnnotationHolder(null);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("the ast is null");
    }

    @Test
    public void testAnnotationNull() {
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.getAnnotation(null, null);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("the ast is null");
    }

    @Test
    public void testAnnotationNull2() {
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.getAnnotation(new DetailAstImpl(), null);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("the annotation is null");
    }

    @Test
    public void testAnnotationEmpty() {
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.getAnnotation(new DetailAstImpl(), "");
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("the annotation is empty or spaces");
    }

    @Test
    public void testContainsAnnotationWithNull() {
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.getAnnotation(null, "");
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("the ast is null");
    }

    @Test
    public void testContainsAnnotationListWithNullAst() {
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.containsAnnotation(null, Set.of("Override"));
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("the ast is null");
    }

    @Test
    public void testContainsAnnotationListWithNullList() {
        final DetailAST ast = new DetailAstImpl();
        final Set<String> annotations = null;
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    AnnotationUtil.containsAnnotation(ast, annotations);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("annotations cannot be null");
    }

    @Test
    public void testContainsAnnotationListWithEmptyList() {
        final DetailAST ast = new DetailAstImpl();
        final Set<String> annotations = Set.of();
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertWithMessage("An empty set should lead to a false result")
            .that(result)
            .isFalse();
    }

    @Test
    public void testContainsAnnotationListWithNoAnnotationNode() {
        final DetailAstImpl ast = new DetailAstImpl();
        final DetailAstImpl modifiersAst = new DetailAstImpl();
        modifiersAst.setType(TokenTypes.MODIFIERS);
        ast.addChild(modifiersAst);
        final Set<String> annotations = Set.of("Override");
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertWithMessage("An empty ast should lead to a false result")
            .that(result)
            .isFalse();
    }

    @Test
    public void testContainsAnnotationListWithNoMatchingAnnotation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputAnnotationUtilNoMatchingAnnotation.java"), expected);
    }

    @Test
    public void testContainsAnnotation() {
        final DetailAstImpl astForTest = new DetailAstImpl();
        astForTest.setType(TokenTypes.PACKAGE_DEF);
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAstImpl annotations = new DetailAstImpl();
        final DetailAstImpl annotation = new DetailAstImpl();
        final DetailAstImpl annotationNameHolder = new DetailAstImpl();
        final DetailAstImpl annotationName = new DetailAstImpl();
        annotations.setType(TokenTypes.ANNOTATIONS);
        annotation.setType(TokenTypes.ANNOTATION);
        annotationNameHolder.setType(TokenTypes.AT);
        annotationName.setText("Annotation");

        annotationNameHolder.setNextSibling(annotationName);
        annotation.setFirstChild(annotationNameHolder);
        annotations.setFirstChild(annotation);
        child.setNextSibling(annotations);
        astForTest.setFirstChild(child);

        assertWithMessage("Annotation should contain %s", astForTest)
                .that(AnnotationUtil.containsAnnotation(astForTest, "Annotation"))
                .isTrue();
    }

    @Test
    public void testContainsAnnotationWithStringFalse() {
        final DetailAstImpl astForTest = new DetailAstImpl();
        astForTest.setType(TokenTypes.PACKAGE_DEF);
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAstImpl annotations = new DetailAstImpl();
        final DetailAstImpl annotation = new DetailAstImpl();
        final DetailAstImpl annotationNameHolder = new DetailAstImpl();
        final DetailAstImpl annotationName = new DetailAstImpl();
        annotations.setType(TokenTypes.ANNOTATIONS);
        annotation.setType(TokenTypes.ANNOTATION);
        annotationNameHolder.setType(TokenTypes.AT);
        annotationName.setText("Annotation");

        annotationNameHolder.setNextSibling(annotationName);
        annotation.setFirstChild(annotationNameHolder);
        annotations.setFirstChild(annotation);
        child.setNextSibling(annotations);
        astForTest.setFirstChild(child);

        assertWithMessage("Annotation should not contain %s", astForTest)
                .that(AnnotationUtil.containsAnnotation(astForTest, "AnnotationBad"))
                .isFalse();
    }

    @Test
    public void testContainsAnnotationWithComment() {
        final DetailAstImpl astForTest = new DetailAstImpl();
        astForTest.setType(TokenTypes.PACKAGE_DEF);
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAstImpl annotations = new DetailAstImpl();
        final DetailAstImpl annotation = new DetailAstImpl();
        final DetailAstImpl annotationNameHolder = new DetailAstImpl();
        final DetailAstImpl annotationName = new DetailAstImpl();
        final DetailAstImpl comment = new DetailAstImpl();
        annotations.setType(TokenTypes.ANNOTATIONS);
        annotation.setType(TokenTypes.ANNOTATION);
        annotationNameHolder.setType(TokenTypes.AT);
        comment.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        annotationName.setText("Annotation");

        annotationNameHolder.setNextSibling(annotationName);
        annotation.setFirstChild(comment);
        comment.setNextSibling(annotationNameHolder);
        annotations.setFirstChild(annotation);
        child.setNextSibling(annotations);
        astForTest.setFirstChild(child);

        assertWithMessage("Annotation should contain %s", astForTest)
                .that(AnnotationUtil.containsAnnotation(astForTest, "Annotation"))
                .isTrue();
    }

    @Test
    public void testContainsAnnotationOnRecordComponent() throws Exception {
        final DetailAST ast = JavaParser.parseFile(new File(
                getPath("InputAnnotationUtilRecordAndEnumConstant.java")),
                JavaParser.Options.WITH_COMMENTS);
        final DetailAST component = findNode(ast, TokenTypes.RECORD_COMPONENT_DEF, "y");
        final DetailAST annotations = component.findFirstToken(TokenTypes.ANNOTATIONS);

        assertWithMessage("Annotation holder of record component should be %s", annotations)
                .that(AnnotationUtil.getAnnotationHolder(component))
                .isSameInstanceAs(annotations);
        assertWithMessage("Record component should not contain %s", "Deprecated")
                .that(AnnotationUtil.containsAnnotation(component, "Deprecated"))
                .isFalse();
        assertWithMessage("Record component should not contain any annotation")
                .that(AnnotationUtil.containsAnnotation(component))
                .isFalse();
        assertWithMessage("Annotation should not be found on record component")
                .that(AnnotationUtil.getAnnotation(component, "Deprecated"))
                .isNull();
    }

    @Test
    public void testContainsAnnotationOnAnnotatedRecordComponent() throws Exception {
        final DetailAST ast = JavaParser.parseFile(new File(
                getPath("InputAnnotationUtilRecordAndEnumConstant.java")),
                JavaParser.Options.WITH_COMMENTS);
        final DetailAST component = findNode(ast, TokenTypes.RECORD_COMPONENT_DEF, "x");
        final DetailAST annotation = component.findFirstToken(TokenTypes.ANNOTATIONS)
                .getFirstChild();

        assertWithMessage("Record component should contain %s", "Deprecated")
                .that(AnnotationUtil.containsAnnotation(component, "Deprecated"))
                .isTrue();
        assertWithMessage("Record component should contain an annotation")
                .that(AnnotationUtil.containsAnnotation(component))
                .isTrue();
        assertWithMessage("Annotation on record component should be %s", annotation)
                .that(AnnotationUtil.getAnnotation(component, "Deprecated"))
                .isSameInstanceAs(annotation);
    }

    @Test
    public void testContainsAnnotationOnAnnotatedEnumConstant() throws Exception {
        final DetailAST ast = JavaParser.parseFile(new File(
                getPath("InputAnnotationUtilRecordAndEnumConstant.java")),
                JavaParser.Options.WITH_COMMENTS);
        final DetailAST constant = findNode(ast, TokenTypes.ENUM_CONSTANT_DEF, "LEGACY");
        final DetailAST annotations = constant.findFirstToken(TokenTypes.ANNOTATIONS);
        final DetailAST annotation = annotations.getFirstChild();

        assertWithMessage("Annotation holder of enum constant should be %s", annotations)
                .that(AnnotationUtil.getAnnotationHolder(constant))
                .isSameInstanceAs(annotations);
        assertWithMessage("Enum constant should contain %s", "Deprecated")
                .that(AnnotationUtil.containsAnnotation(constant, "Deprecated"))
                .isTrue();
        assertWithMessage("Enum constant should contain an annotation")
                .that(AnnotationUtil.containsAnnotation(constant))
                .isTrue();
        assertWithMessage("Annotation on enum constant should be %s", annotation)
                .that(AnnotationUtil.getAnnotation(constant, "Deprecated"))
                .isSameInstanceAs(annotation);
    }

    @Test
    public void testContainsAnnotationOnNodeWithoutAnnotationHolder() throws Exception {
        final DetailAST ast = JavaParser.parseFile(new File(
                getPath("InputAnnotationUtilRecordAndEnumConstant.java")),
                JavaParser.Options.WITH_COMMENTS);
        final DetailAST literal = findNode(ast, TokenTypes.LITERAL_INT, null);

        assertWithMessage("Node without annotation holder should not contain %s", "Deprecated")
                .that(AnnotationUtil.containsAnnotation(literal, "Deprecated"))
                .isFalse();
        assertWithMessage("Annotation should not be found on node without annotation holder")
                .that(AnnotationUtil.getAnnotation(literal, "Deprecated"))
                .isNull();
    }

    /**
     * Finds the first node of the given type in the tree.
     *
     * @param ast the root of the tree to search
     * @param type the token type to look for
     * @param identText the text of the IDENT child to match, or null to match any node
     * @return the first matching node, or null if there is none
     */
    private static DetailAST findNode(DetailAST ast, int type, String identText) {
        DetailAST result = null;
        if (ast.getType() == type && (identText == null || identText.equals(getIdentText(ast)))) {
            result = ast;
        }
        else {
            DetailAST child = ast.getFirstChild();
            while (result == null && child != null) {
                result = findNode(child, type, identText);
                child = child.getNextSibling();
            }
        }
        return result;
    }

    /**
     * Returns the text of the IDENT child of the given node.
     *
     * @param ast the node to get the IDENT text of
     * @return the IDENT text, or null if the node has no IDENT child
     */
    private static String getIdentText(DetailAST ast) {
        String result = null;
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (ident != null) {
            result = ident.getText();
        }
        return result;
    }

    @Test
    public void testCompactNoUnchecked() throws Exception {

        final String[] expected = {
            "16:20: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "22:28: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "41:36: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:28: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:49: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAnnotationUtil1.java"), expected);
    }

    @Test
    public void testValuePairAnnotation() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAnnotationUtil2.java"), expected);
    }

}
