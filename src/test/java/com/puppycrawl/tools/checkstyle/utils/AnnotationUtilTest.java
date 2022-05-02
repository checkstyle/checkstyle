////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AnnotationUtilTest {

    @Test
    public void testIsProperUtilsClass() {
        try {
            isUtilsClassHasPrivateConstructor(AnnotationUtil.class);
            assertWithMessage("Exception is expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("do not instantiate.");
        }
    }

    @Test
    public void testContainsAnnotationNull() {
        try {
            AnnotationUtil.containsAnnotation(null);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("the ast is null");
        }
    }

    @Test
    public void testContainsAnnotationNull2() {
        try {
            AnnotationUtil.containsAnnotation(null, "");
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("the ast is null");
        }
    }

    @Test
    public void testContainsAnnotationFalse() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(1);
        assertWithMessage("AnnotationUtil should not contain " + ast)
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
        assertWithMessage("AnnotationUtil should not contain " + ast)
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
        assertWithMessage("AnnotationUtil should contain " + ast)
                .that(AnnotationUtil.containsAnnotation(ast))
                .isTrue();
    }

    @Test
    public void testAnnotationHolderNull() {
        try {
            AnnotationUtil.getAnnotationHolder(null);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("the ast is null");
        }
    }

    @Test
    public void testAnnotationNull() {
        try {
            AnnotationUtil.getAnnotation(null, null);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("the ast is null");
        }
    }

    @Test
    public void testAnnotationNull2() {
        try {
            AnnotationUtil.getAnnotation(new DetailAstImpl(), null);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("the annotation is null");
        }
    }

    @Test
    public void testAnnotationEmpty() {
        try {
            AnnotationUtil.getAnnotation(new DetailAstImpl(), "");
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("the annotation is empty or spaces");
        }
    }

    @Test
    public void testContainsAnnotationWithNull() {
        try {
            AnnotationUtil.getAnnotation(null, "");
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("the ast is null");
        }
    }

    @Test
    public void testContainsAnnotationListWithNullAst() {
        try {
            AnnotationUtil.containsAnnotation(null, Set.of("Override"));
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("the ast is null");
        }
    }

    @Test
    public void testContainsAnnotationListWithNullList() {
        final DetailAST ast = new DetailAstImpl();
        final Set<String> annotations = null;
        try {
            AnnotationUtil.containsAnnotation(ast, annotations);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("annotations cannot be null");
        }
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
    public void testContainsAnnotationListWithEmptyAnnotationNode() {
        final DetailAstImpl ast = new DetailAstImpl();
        final DetailAstImpl modifiersAst = create(
                TokenTypes.MODIFIERS,
                create(
                        TokenTypes.ANNOTATION,
                        create(
                                TokenTypes.DOT,
                                create(
                                        TokenTypes.IDENT,
                                        "Override")
                        )
                )
        );
        ast.addChild(modifiersAst);
        final Set<String> annotations = Set.of("Override");
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertWithMessage("The dot-ident variation should also work")
                .that(result)
                .isTrue();
    }

    @Test
    public void testContainsAnnotationListWithNoMatchingAnnotation() {
        final DetailAstImpl ast = new DetailAstImpl();
        final DetailAstImpl modifiersAst = create(
                TokenTypes.MODIFIERS,
                create(
                        TokenTypes.ANNOTATION,
                        create(
                                TokenTypes.DOT,
                                create(
                                        TokenTypes.IDENT,
                                        "Override")
                        )
                )
        );
        ast.addChild(modifiersAst);
        final Set<String> annotations = Set.of("Deprecated");
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertWithMessage("No matching annotation found")
            .that(result)
            .isFalse();
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

        assertWithMessage("Annotation should contain " + astForTest)
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

        assertWithMessage("Annotation should not contain " + astForTest)
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

        assertWithMessage("Annotation should contain " + astForTest)
                .that(AnnotationUtil.containsAnnotation(astForTest, "Annotation"))
                .isTrue();
    }

    private static DetailAstImpl create(int tokenType) {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(tokenType);
        return ast;
    }

    private static DetailAstImpl create(int tokenType, String text) {
        final DetailAstImpl ast = create(tokenType);
        ast.setText(text);
        return ast;
    }

    private static DetailAstImpl create(int tokenType, DetailAstImpl child) {
        final DetailAstImpl ast = create(tokenType);
        ast.addChild(child);
        return ast;
    }
}
