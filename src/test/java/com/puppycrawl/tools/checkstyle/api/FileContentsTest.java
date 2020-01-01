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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

public class FileContentsTest {

    @Test
    public void testTextFileName() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("123", "456")));

        assertEquals("filename", fileContents.getText().getFile().getName(), "Invalid file name");
        assertArrayEquals(new String[] {"123", "456"}, fileContents.getLines(), "Invalid array");
        assertEquals("filename", fileContents.getFileName(), "Invalid file name");
    }

    @Test
    public void testIsLineBlank() {
        assertFalse(
                new FileContents(
                        new FileText(new File("filename"), Collections.singletonList("123")))
                                .lineIsBlank(0), "Invalid result");
        assertTrue(
                new FileContents(new FileText(new File("filename"), Collections.singletonList("")))
                        .lineIsBlank(0), "Invalid result");
    }

    @Test
    public void testLineIsComment() {
        assertFalse(
                new FileContents(
                        new FileText(new File("filename"), Collections.singletonList("123")))
                                .lineIsComment(0), "Invalid result");
        assertTrue(
                new FileContents(
                        new FileText(new File("filename"), Collections.singletonList(" // abc")))
                                .lineIsComment(0), "Invalid result");
    }

    @Test
    public void testDeprecatedAbbreviatedMethod() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("123", "456")));
        fileContents.reportSingleLineComment(1, 1);
        fileContents.reportBlockComment(1, 1, 1, 1);

        final Comment cppComment = new Comment(new String[] {"23"}, 1, 1, 2);
        final Comment cComment = new Comment(new String[] {"2"}, 1, 1, 1);
        final String lineComment = fileContents.getSingleLineComments().get(1).toString();
        assertEquals(cppComment.toString(), lineComment, "Invalid cpp comment");
        final String blockComment = fileContents.getBlockComments().get(1).get(0).toString();
        assertEquals(cComment.toString(), blockComment, "Invalid c comment");
    }

    @Test
    public void testSinglelineCommentNotIntersect() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //  ")));
        fileContents.reportSingleLineComment(1, 2);
        assertFalse(fileContents.hasIntersectionWithComment(1, 0, 1, 1),
                "Should return false when there is no intersection");
    }

    @Test
    public void testSinglelineCommentIntersect() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //   ")));
        fileContents.reportSingleLineComment("type", 1, 2);
        assertTrue(fileContents.hasIntersectionWithComment(1, 5, 1, 6),
                "Should return true when comments intersect");
    }

    @Test
    public void testReportCppComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("   //  ")));
        fileContents.reportSingleLineComment(1, 2);
        final Map<Integer, TextBlock> cppComments = fileContents.getSingleLineComments();

        assertEquals(
                new Comment(new String[] {" //  "}, 2, 1, 6).toString(),
                cppComments.get(1).toString(), "Invalid comment");
    }

    @Test
    public void testHasIntersectionWithSingleLineComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("     ", "  //test   ",
                        "  //test   ", "  //test   ")));
        fileContents.reportSingleLineComment(4, 4);

        assertTrue(fileContents.hasIntersectionWithComment(1, 3, 4, 6),
                "Should return true when comments intersect");
    }

    @Test
    public void testReportComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //   ")));
        fileContents.reportBlockComment("type", 1, 2, 1, 2);
        final Map<Integer, List<TextBlock>> comments = fileContents.getBlockComments();

        assertEquals(
                new Comment(new String[] {"/"}, 2, 1, 2).toString(),
                comments.get(1).get(0).toString(), "Invalid comment");
    }

    @Test
    public void testReportBlockCommentSameLine() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("/* a */ /* b */ ")));
        fileContents.reportBlockComment("type", 1, 0, 1, 6);
        fileContents.reportBlockComment("type", 1, 8, 1, 14);
        final Map<Integer, List<TextBlock>> comments = fileContents.getBlockComments();

        assertEquals(Arrays.asList(
            new Comment(new String[] {"/* a */"}, 0, 1, 6),
            new Comment(new String[] {"/* b */"}, 8, 1, 14)
        ).toString(), comments.get(1).toString(), "Invalid comment");
    }

    @Test
    public void testReportBlockCommentMultiLine() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("/*", "c", "*/")));
        fileContents.reportBlockComment("type", 1, 0, 3, 1);
        final Map<Integer, List<TextBlock>> comments = fileContents.getBlockComments();

        assertEquals(Collections.singletonList(
            new Comment(new String[] {"/*", "c", "*/"}, 0, 3, 1)
        ).toString(), comments.get(1).toString(), "Invalid comment");
    }

    @Test
    public void testReportBlockCommentJavadoc() {
        final FileContents fileContents = new FileContents(new FileText(new File("filename"),
                Arrays.asList("/** A */", "", "//", "/**/", "/* B */")));
        fileContents.reportBlockComment("type", 1, 0, 1, 7);
        fileContents.reportBlockComment("type", 4, 0, 4, 3);
        fileContents.reportBlockComment("type", 5, 0, 5, 6);

        assertNull(fileContents.getJavadocBefore(1), "Invalid comment");
        assertEquals(new Comment(new String[] {"/** A */"}, 0, 1, 7).toString(),
            fileContents.getJavadocBefore(4).toString(), "Invalid comment");
        assertNull(fileContents.getJavadocBefore(5), "Invalid comment");
        assertNull(fileContents.getJavadocBefore(6), "Invalid comment");
    }

    @Test
    public void testHasIntersectionWithBlockComment() {
        final FileContents fileContents = new FileContents(new FileText(new File("filename"),
                        Arrays.asList("  /* */    ", "    ", "  /* test   ", "  */  ", "   ")));
        fileContents.reportBlockComment(1, 2, 1, 5);
        fileContents.reportBlockComment(3, 2, 4, 2);

        assertTrue(fileContents.hasIntersectionWithComment(2, 2, 3, 6),
                "Should return true when comments intersect");
    }

    @Test
    public void testHasIntersectionWithBlockComment2() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("  /* */    ", "    ", " ")));
        fileContents.reportBlockComment(1, 2, 1, 5);

        assertFalse(fileContents.hasIntersectionWithComment(2, 2, 3, 6),
                "Should return false when there is no intersection");
    }

    @Test
    public void testReportJavadocComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  /** */   ")));
        fileContents.reportBlockComment(1, 2, 1, 6);
        final TextBlock comment = fileContents.getJavadocBefore(2);

        assertEquals(
                new Comment(new String[] {"/** *"}, 2, 1, 6).toString(),
                comment.toString(), "Invalid comment");
    }

    @Test
    public void testReportJavadocComment2() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  /** */   ")));
        fileContents.reportBlockComment(1, 2, 1, 6);
        final TextBlock comment = fileContents.getJavadocBefore(2);

        assertEquals(
                new Comment(new String[] {"/** *"}, 2, 1, 6).toString(),
                comment.toString(), "Invalid comment");
    }

    @Test
    public void testInPackageInfo() {
        final FileContents fileContents = new FileContents(new FileText(
                new File("filename.package-info.java"),
                Collections.singletonList("  //   ")));

        assertTrue(fileContents.inPackageInfo(), "Should return true when in package info");
    }

    @Test
    public void testNotInPackageInfo() {
        final FileContents fileContents = new FileContents(new FileText(
                new File("filename.java"),
                Collections.singletonList("  //   ")));

        assertFalse(fileContents.inPackageInfo(), "Should return false when not in package info");
    }

    @Test
    public void testGetJavadocBefore() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("    ")));
        final Map<Integer, TextBlock> javadoc = new HashMap<>();
        javadoc.put(0, new Comment(new String[] {"// "}, 2, 1, 2));
        Whitebox.setInternalState(fileContents, "javadocComments", javadoc);
        final TextBlock javadocBefore = fileContents.getJavadocBefore(2);

        assertEquals(
                new Comment(new String[] {"// "}, 2, 1, 2).toString(),
                javadocBefore.toString(), "Invalid before javadoc");
    }

    @Test
    public void testExtractBlockComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("   ", "    ", "  /* test   ",
                        "  */  ", "   ")));
        fileContents.reportBlockComment(3, 2, 4, 2);
        final Map<Integer, List<TextBlock>> blockComments =
            fileContents.getBlockComments();
        final String[] text = blockComments.get(3).get(0).getText();

        assertArrayEquals(new String[] {"/* test   ", "  *"}, text, "Invalid comment text");
    }

    @Test
    public void testHasIntersectionEarlyOut() throws Exception {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.emptyList()));
        final Map<Integer, List<TextBlock>> clangComments = Whitebox.getInternalState(fileContents,
                "clangComments");
        final TextBlock textBlock = new Comment(new String[] {""}, 1, 1, 1);
        clangComments.put(1, Collections.singletonList(textBlock));
        clangComments.put(2, null);

        assertTrue((Boolean) Whitebox.invokeMethod(fileContents,
                "hasIntersectionWithBlockComment", 1, 1, 1, 1),
            "Invalid results");
    }

}
