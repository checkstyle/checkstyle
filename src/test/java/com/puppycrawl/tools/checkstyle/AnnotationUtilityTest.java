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

import static com.puppycrawl.tools.checkstyle.TestUtils.assertUtilsClassHasPrivateConstructor;

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
            Assert.assertTrue("do not instantiate.".equals(ex.getCause().getMessage()));
        }
    }

    @Test
    public void testContainsAnnotationNull() throws ReflectiveOperationException {
        try {
            AnnotationUtility.containsAnnotation(null);
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            Assert.assertTrue("the ast is null".equals(ex.getMessage()));
        }
    }

    @Test
    public void testContainsAnnotationNull2() throws ReflectiveOperationException {
        try {
            AnnotationUtility.containsAnnotation(null, "");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            Assert.assertTrue("the ast is null".equals(ex.getMessage()));
        }
    }

    @Test
    public void testContainsAnnotationFalse() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(1);
        Assert.assertFalse(AnnotationUtility.containsAnnotation(ast));
    }

    @Test
    public void testContainsAnnotationFalse2() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(1);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);
        Assert.assertFalse(AnnotationUtility.containsAnnotation(ast));
    }

    @Test
    public void testContainsAnnotationTrue() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(1);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);
        DetailAST ast3 = new DetailAST();
        ast3.setType(TokenTypes.ANNOTATION);
        ast2.addChild(ast3);
        Assert.assertTrue(AnnotationUtility.containsAnnotation(ast));
    }

    @Test
    public void testAnnotationHolderNull() throws ReflectiveOperationException {
        try {
            AnnotationUtility.getAnnotationHolder(null);
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            Assert.assertTrue("the ast is null".equals(ex.getMessage()));
        }
    }

    @Test
    public void testAnnotationNull() throws ReflectiveOperationException {
        try {
            AnnotationUtility.getAnnotation(null, null);
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            Assert.assertTrue("the ast is null".equals(ex.getMessage()));
        }
    }

    @Test
    public void testAnnotationNull2() throws ReflectiveOperationException {
        try {
            AnnotationUtility.getAnnotation(new DetailAST(), null);
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            Assert.assertTrue("the annotation is null".equals(ex.getMessage()));
        }
    }

    @Test
    public void testAnnotationEmpty() throws ReflectiveOperationException {
        try {
            AnnotationUtility.getAnnotation(new DetailAST(), "");
            Assert.fail();
        }
        catch (IllegalArgumentException ex) {
            Assert.assertTrue("the annotation is empty or spaces"
                    .equals(ex.getMessage()));
        }
    }
}
