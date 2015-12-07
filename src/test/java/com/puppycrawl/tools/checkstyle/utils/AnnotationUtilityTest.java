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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AnnotationUtilityTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        try {
            assertUtilsClassHasPrivateConstructor(AnnotationUtility.class);
        }
        catch (InvocationTargetException ex) {
            assertEquals("do not instantiate.", ex.getCause().getMessage());
        }
    }

    @Test
    public void testContainsAnnotationNull() {
        try {
            AnnotationUtility.containsAnnotation(null);
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testContainsAnnotationNull2() {
        try {
            AnnotationUtility.containsAnnotation(null, "");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testContainsAnnotationFalse() {
        final DetailAST ast = new DetailAST();
        ast.setType(1);
        Assert.assertFalse(AnnotationUtility.containsAnnotation(ast));
    }

    @Test
    public void testContainsAnnotationFalse2() {
        final DetailAST ast = new DetailAST();
        ast.setType(1);
        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);
        Assert.assertFalse(AnnotationUtility.containsAnnotation(ast));
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
        Assert.assertTrue(AnnotationUtility.containsAnnotation(ast));
    }

    @Test
    public void testAnnotationHolderNull() {
        try {
            AnnotationUtility.getAnnotationHolder(null);
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testAnnotationNull() {
        try {
            AnnotationUtility.getAnnotation(null, null);
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the ast is null", ex.getMessage());
        }
    }

    @Test
    public void testAnnotationNull2() {
        try {
            AnnotationUtility.getAnnotation(new DetailAST(), null);
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the annotation is null", ex.getMessage());
        }
    }

    @Test
    public void testAnnotationEmpty() {
        try {
            AnnotationUtility.getAnnotation(new DetailAST(), "");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the annotation is empty or spaces", ex.getMessage());
        }
    }
}
