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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.google.common.collect.ImmutableMap;

public class FileContentsTest {

    @Test
    @SuppressWarnings("deprecation")
    public void testDeprecatedAbbreviatedMethod() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents("filename", "123", "456");
        fileContents.reportCppComment(1, 1);
        fileContents.reportCComment(1, 1, 1, 1);

        final Comment cppComment = new Comment(new String[] {"23"}, 1, 1, 2);
        final Comment cComment = new Comment(new String[] {"2"}, 1, 1, 1);
        assertEquals("Invalid cpp comment", cppComment.toString(),
                fileContents.getCppComments().get(1).toString());
        assertEquals("Invalid c comment", cComment.toString(),
                fileContents.getCComments().get(1).get(0).toString());
    }

    @Test
    public void testSinglelineCommentNotIntersect() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //  ")));
        fileContents.reportSingleLineComment(1, 2);
        assertFalse("Should return false when there is no intersection",
                fileContents.hasIntersectionWithComment(1, 0, 1, 1));
    }

    @Test
    public void testSinglelineCommentIntersect() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //   ")));
        fileContents.reportSingleLineComment(1, 2);
        assertTrue("Should return true when comments intersect",
                fileContents.hasIntersectionWithComment(1, 5, 1, 6));
    }

    @Test
    public void testReportCppComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("   //  ")));
        fileContents.reportCppComment(1, 2);
        final Map<Integer, TextBlock> cppComments = fileContents.getCppComments();

        assertEquals("Invalid comment",
                new Comment(new String[] {" //  "}, 2, 1, 6).toString(),
                cppComments.get(1).toString());
    }

    @Test
    public void testHasIntersectionWithSingleLineComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("     ", "  //test   ",
                        "  //test   ", "  //test   ")));
        fileContents.reportCppComment(4, 4);

        assertTrue("Should return true when comments intersect",
                fileContents.hasIntersectionWithComment(1, 3, 4, 6));
    }

    @Test
    public void testReportComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //   ")));
        fileContents.reportCComment(1, 2, 1, 2);
        final ImmutableMap<Integer, List<TextBlock>> comments = fileContents.getCComments();

        assertEquals("Invalid comment",
                new Comment(new String[] {"/"}, 2, 1, 2).toString(),
                comments.get(1).get(0).toString());
    }

    @Test
    public void testHasIntersectionWithBlockComment() {
        final FileContents fileContents = new FileContents(new FileText(new File("filename"),
                        Arrays.asList("  /* */    ", "    ", "  /* test   ", "  */  ", "   ")));
        fileContents.reportCComment(1, 2, 1, 5);
        fileContents.reportCComment(3, 2, 4, 2);

        assertTrue("Should return true when comments intersect",
                fileContents.hasIntersectionWithComment(2, 2, 3, 6));
    }

    @Test
    public void testHasIntersectionWithBlockComment2() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("  /* */    ", "    ", " ")));
        fileContents.reportCComment(1, 2, 1, 5);

        assertFalse("Should return false when there is no intersection",
                fileContents.hasIntersectionWithComment(2, 2, 3, 6));
    }

    @Test
    public void testInPackageInfo() {
        final FileContents fileContents = new FileContents(new FileText(
                new File("filename.package-info.java"),
                Collections.singletonList("  //   ")));

        assertTrue("Should return true when in package info", fileContents.inPackageInfo());
    }

    @Test
    public void testGetJavadocBefore() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("    ")));
        final Map<Integer, TextBlock> javadoc = new HashMap<>();
        javadoc.put(0, new Comment(new String[] {"// "}, 2, 1, 2));
        Whitebox.setInternalState(fileContents, "javadocComments", javadoc);
        final TextBlock javadocBefore = fileContents.getJavadocBefore(2);

        assertEquals("Invalid before javadoc",
                new Comment(new String[] {"// "}, 2, 1, 2).toString(),
                javadocBefore.toString());
    }

    @Test
    public void testExtractBlockComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("   ", "    ", "  /* test   ",
                        "  */  ", "   ")));
        fileContents.reportCComment(3, 2, 4, 2);
        final ImmutableMap<Integer, List<TextBlock>> blockComments =
            fileContents.getBlockComments();
        final String[] text = blockComments.get(3).get(0).getText();

        assertArrayEquals("Invalid comment text", new String[] {"/* test   ", "  *"}, text);
    }
}
