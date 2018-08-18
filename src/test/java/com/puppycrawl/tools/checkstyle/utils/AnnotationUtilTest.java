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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AnnotationUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        try {
            isUtilsClassHasPrivateConstructor(AnnotationUtil.class, true);
            Assert.fail("Exception is expected");
        }
        catch (InvocationTargetException ex) {
            assertEquals("Invalid exception message",
                    "do not instantiate.", ex.getCause().getMessage());
        }
    }

    @Test
    public void testContainsAnnotationNull() {
        try {
            AnnotationUtil.containsAnnotation(null);
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testContainsAnnotationNull2() {
        try {
            AnnotationUtil.containsAnnotation(null, "");
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testContainsAnnotationFalse() {
        final DetailAST ast = new DetailAST();
        ast.setType(1);
        assertFalse("AnnotationUtil should not contain " + ast,
                AnnotationUtil.containsAnnotation(ast));
    }

    @Test
    public void testContainsAnnotationFalse2() {
        final DetailAST ast = new DetailAST();
        ast.setType(1);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);
        assertFalse("AnnotationUtil should not contain " + ast,
                AnnotationUtil.containsAnnotation(ast));
    }

    @Test
    public void testContainsAnnotationTrue() {
        final DetailAST ast = new DetailAST();
        ast.setType(1);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);
        final DetailAST ast3 = new DetailAST();
        ast3.setType(TokenTypes.ANNOTATION);
        ast2.addChild(ast3);
        assertTrue("AnnotationUtil should contain " + ast,
                AnnotationUtil.containsAnnotation(ast));
    }

    @Test
    public void testAnnotationHolderNull() {
        try {
            AnnotationUtil.getAnnotationHolder(null);
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testAnnotationNull() {
        try {
            AnnotationUtil.getAnnotation(null, null);
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testAnnotationNull2() {
        try {
            AnnotationUtil.getAnnotation(new DetailAST(), null);
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "the annotation is null", ex.getMessage());
        }
    }

    @Test
    public void testAnnotationEmpty() {
        try {
            AnnotationUtil.getAnnotation(new DetailAST(), "");
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "the annotation is empty or spaces", ex.getMessage());
        }
    }

    @Test
    public void testContainsAnnotationWithNull() {
        try {
            AnnotationUtil.getAnnotation(null, "");
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testContainsAnnotationListWithNullAst() {
        try {
            AnnotationUtil.containsAnnotation(null, Collections.singletonList("Override"));
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testContainsAnnotationListWithNullList() {
        final DetailAST ast = new DetailAST();
        final List<String> annotations = null;
        try {
            AnnotationUtil.containsAnnotation(ast, annotations);
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "annotations cannot be null", ex.getMessage());
        }
    }

    @Test
    public void testContainsAnnotationListWithEmptyList() {
        final DetailAST ast = new DetailAST();
        final List<String> annotations = new ArrayList<>();
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertFalse("An empty list should lead to a false result", result);
    }

    @Test
    public void testContainsAnnotationListWithNoAnnotationNode() {
        final DetailAST ast = new DetailAST();
        final DetailAST modifiersAst = new DetailAST();
        modifiersAst.setType(TokenTypes.MODIFIERS);
        ast.addChild(modifiersAst);
        final List<String> annotations = Collections.singletonList("Override");
        final boolean result = AnnotationUtil.containsAnnotation(ast, annotations);
        assertFalse("An empty ast should lead to a false result", result);
    }

    @Test
    public void testContainsAnnotationListWithEmptyAnnotationNode() {
        final DetailAST ast = new DetailAST();
        final DetailAST modifiersAst = create(
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
        assertTrue("The dot-ident variation should also work", result);
    }

    @Test
    public void testContainsAnnotation() {
        final DetailAST astForTest = new DetailAST();
        astForTest.setType(TokenTypes.PACKAGE_DEF);
        final DetailAST child = new DetailAST();
        final DetailAST annotations = new DetailAST();
        final DetailAST annotation = new DetailAST();
        final DetailAST annotationNameHolder = new DetailAST();
        final DetailAST annotationName = new DetailAST();
        annotations.setType(TokenTypes.ANNOTATIONS);
        annotation.setType(TokenTypes.ANNOTATION);
        annotationNameHolder.setType(TokenTypes.AT);
        annotationName.setText("Annotation");

        annotationNameHolder.setNextSibling(annotationName);
        annotation.setFirstChild(annotationNameHolder);
        annotations.setFirstChild(annotation);
        child.setNextSibling(annotations);
        astForTest.setFirstChild(child);

        assertTrue("Annotation should contain " + astForTest,
                AnnotationUtil.containsAnnotation(astForTest, "Annotation"));
    }

    @Test
    public void testContainsAnnotationWithComment() {
        final DetailAST astForTest = new DetailAST();
        astForTest.setType(TokenTypes.PACKAGE_DEF);
        final DetailAST child = new DetailAST();
        final DetailAST annotations = new DetailAST();
        final DetailAST annotation = new DetailAST();
        final DetailAST annotationNameHolder = new DetailAST();
        final DetailAST annotationName = new DetailAST();
        final DetailAST comment = new DetailAST();
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

        assertTrue("Annotation should contain " + astForTest,
                AnnotationUtil.containsAnnotation(astForTest, "Annotation"));
    }

    private static DetailAST create(int tokenType) {
        final DetailAST ast = new DetailAST();
        ast.setType(tokenType);
        return ast;
    }

    private static DetailAST create(int tokenType, String text) {
        final DetailAST ast = create(tokenType);
        ast.setText(text);
        return ast;
    }

    private static DetailAST create(int tokenType, DetailAST child) {
        final DetailAST ast = create(tokenType);
        ast.addChild(child);
        return ast;
    }
}
