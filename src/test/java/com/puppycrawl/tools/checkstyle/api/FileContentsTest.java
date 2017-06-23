////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collections;

import org.junit.Test;

public class FileContentsTest {

    @Test
    @SuppressWarnings("deprecation")
    public void testDeprecatedCtor() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents("filename.java", "1", "2");
        assertEquals("filename.java", fileContents.getFilename());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDeprecatedAbbreviatedMethod() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents("filename", "123", "456");
        fileContents.getCppComments();
        fileContents.getCComments();
        fileContents.reportCppComment(1, 1);
        fileContents.reportCComment(1, 1, 1, 1);
    }

    @Test
    public void testSinglelineCommentNotIntersect() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                FileText.fromLines(new File("filename"), Collections.singletonList("  //  ")));
        fileContents.reportSingleLineComment(1, 2);
        assertFalse(fileContents.hasIntersectionWithComment(1, 0, 1, 1));
    }

    @Test
    public void testSinglelineCommentIntersect() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                FileText.fromLines(new File("filename"), Collections.singletonList("  //   ")));
        fileContents.reportSingleLineComment(1, 2);
        assertTrue(fileContents.hasIntersectionWithComment(1, 5, 1, 6));

    }
}
