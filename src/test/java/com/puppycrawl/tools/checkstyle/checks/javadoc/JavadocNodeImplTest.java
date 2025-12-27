///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;

public class JavadocNodeImplTest {

    @Test
    public void testToString() {
        final JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        javadocNode.setType(JavadocCommentsTokenTypes.EQUALS);
        javadocNode.setLineNumber(1);
        javadocNode.setColumnNumber(2);
        javadocNode.setText("=");

        final String result = javadocNode.toString();

        assertWithMessage("Invalid toString result")
            .that(result)
            .isEqualTo("=[1x2]");
    }

    @Test
    public void testGetColumnNumber() {
        final JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        javadocNode.setColumnNumber(1);

        final int result = javadocNode.getColumnNumber();

        assertWithMessage("Invalid column number")
            .that(result)
            .isEqualTo(1);
    }

    @Test
    public void testSetNextSibling() {
        final JavadocNodeImpl root = new JavadocNodeImpl();
        final JavadocNodeImpl firstChild = new JavadocNodeImpl();
        final JavadocNodeImpl secondChild = new JavadocNodeImpl();

        root.addChild(firstChild);
        firstChild.setNextSibling(secondChild);

        final JavadocNodeImpl result = (JavadocNodeImpl) secondChild.getParent();

        assertWithMessage("Invalid parent")
            .that(result)
            .isSameInstanceAs(root);
    }

    @Test
    public void testFindFirstToken() {
        final JavadocNodeImpl root = new JavadocNodeImpl();
        final JavadocNodeImpl firstChild = new JavadocNodeImpl();
        firstChild.setType(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG);
        final JavadocNodeImpl secondChild = new JavadocNodeImpl();
        secondChild.setType(JavadocCommentsTokenTypes.TEXT);
        final JavadocNodeImpl thirdChild = new JavadocNodeImpl();
        thirdChild.setType(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG);

        root.addChild(firstChild);
        root.addChild(secondChild);
        root.addChild(thirdChild);

        assertWithMessage("Invalid result")
            .that(firstChild.findFirstToken(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG))
            .isNull();
        final DetailNode tag = root.findFirstToken(JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG);
        assertWithMessage("Invalid result")
            .that(tag)
            .isEqualTo(firstChild);
        final DetailNode text = root.findFirstToken(JavadocCommentsTokenTypes.TEXT);
        assertWithMessage("Invalid result")
            .that(text)
            .isEqualTo(secondChild);
        assertWithMessage("Invalid result")
            .that(root.findFirstToken(0))
            .isNull();
    }

}
