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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

public class FileContentsTest {

    @Test
    @SuppressWarnings("deprecation")
    public void testDeprecatedCtor() {
        // just to make UT coverage 100%
        FileContents o = new FileContents("filename.java", new String[]{"1", "2"});
        o.getFilename();
    }

    @Test
    public void testCppCommentNotIntersect() {
        // just to make UT coverage 100%
        FileContents o = new FileContents(
                FileText.fromLines(new File("filename"), Arrays.asList("  //  ")));
        o.reportCppComment(1, 2);
        assertFalse(o.hasIntersectionWithComment(1, 0, 1, 1));
    }

    @Test
    public void testCppCommentIntersect() {
        // just to make UT coverage 100%
        FileContents o = new FileContents(
                FileText.fromLines(new File("filename"), Arrays.asList("  //   ")));
        o.reportCppComment(1, 2);
        assertTrue(o.hasIntersectionWithComment(1, 5, 1, 6));

    }
}
