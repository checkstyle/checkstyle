////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AnnotationUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        try {
            isUtilsClassHasPrivateConstructor(AnnotationUtil.class, true);
            fail("Exception is expected");
        }
        catch (InvocationTargetException ex) {
            assertEquals("do not instantiate.", ex.getCause().getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testContainsAnnotationNull() {
        try {
            AnnotationUtil.containsAnnotation(null);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testContainsAnnotationNull2() {
        try {
            AnnotationUtil.containsAnnotation(null, "");
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testContainsAnnotationFalse() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(1);
        assertFalse(AnnotationUtil.containsAnnotation(ast),
                "AnnotationUtil should not contain " + ast);
    }

    @Test
    public void testContainsAnnotationFalse2() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(1);
        final DetailAstImpl ast2 = new DetailAstImpl();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);
        assertFalse(AnnotationUtil.containsAnnotation(ast),
                "AnnotationUtil should not contain " + ast);
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
        assertTrue(AnnotationUtil.containsAnnotation(ast), "AnnotationUtil should contain " + ast);
    }

    @Test
    public void testAnnotationHolderNull() {
        try {
            AnnotationUtil.getAnnotationHolder(null);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testAnnotationNull() {
        try {
            AnnotationUtil.getAnnotation(null, null);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testAnnotationNull2() {
        try {
            AnnotationUtil.getAnnotation(new DetailAstImpl(), null);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the annotation is null", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testAnnotationEmpty() {
        try {
            AnnotationUtil.getAnnotation(new DetailAstImpl(), "");
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the annotation is empty or spaces", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testContainsAnnotationWithNull() {
        try {
            AnnotationUtil.getAnnotation(null, "");
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testContainsAnnotationListWithNullAst() {
        try {
            AnnotationUtil.containsAnnotation(null, Collections.singletonList("Override"));
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testContainsAnnotationListWithNullList() {
        final DetailAST ast = new DetailAstImpl();
        final List<String> annotations = null;
        try {
            AnnotationUtil.containsAnnotation(ast, annotations);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("annotations cannot be null", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testContainsAnnotationListWithEmptyList() {
        final DetailAST ast = new DetailAstImpl();
        final List<String> annotations = new ArrayList<>();
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertFalse(result, "An empty list should lead to a false result");
    }

    @Test
    public void testContainsAnnotationListWithNoAnnotationNode() {
        final DetailAstImpl ast = new DetailAstImpl();
        final DetailAstImpl modifiersAst = new DetailAstImpl();
        modifiersAst.setType(TokenTypes.MODIFIERS);
        ast.addChild(modifiersAst);
        final List<String> annotations = Collections.singletonList("Override");
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertFalse(result, "An empty ast should lead to a false result");
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
        final List<String> annotations = Collections.singletonList("Override");
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertTrue(result, "The dot-ident variation should also work");
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
        final List<String> annotations = Collections.singletonList("Deprecated");
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertFalse(result, "No matching annotation found");
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

        assertTrue(AnnotationUtil.containsAnnotation(astForTest, "Annotation"),
                "Annotation should contain " + astForTest);
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

        assertFalse(AnnotationUtil.containsAnnotation(astForTest, "AnnotationBad"),
                "Annotation should not contain " + astForTest);
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

        assertTrue(AnnotationUtil.containsAnnotation(astForTest, "Annotation"),
                "Annotation should contain " + astForTest);
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
