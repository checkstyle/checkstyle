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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class FileContentsTest {

    @Test
    public void testTextFileName() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("123", "456")));

        assertWithMessage("Invalid file name")
                .that(fileContents.getText().getFile().getName())
                .isEqualTo("filename");
        assertWithMessage("Invalid array")
                .that(fileContents.getLines())
                .isEqualTo(new String[] {"123", "456"});
        assertWithMessage("Invalid file name")
                .that(fileContents.getFileName())
                .isEqualTo("filename");
    }

    @Test
    public void testIsLineBlank() {
        assertWithMessage("Invalid result")
                .that(new FileContents(
                        new FileText(new File("filename"), Collections.singletonList("123")))
                                .lineIsBlank(0))
                .isFalse();
        assertWithMessage("Invalid result")
                .that(new FileContents(new FileText(new File("filename"),
                        Collections.singletonList("")))
                        .lineIsBlank(0))
                .isTrue();
    }

    @Test
    public void testLineIsComment() {
        assertWithMessage("Invalid result")
                .that(new FileContents(
                        new FileText(new File("filename"), Collections.singletonList("123")))
                                .lineIsComment(0))
                .isFalse();
        assertWithMessage("Invalid result")
                .that(new FileContents(
                        new FileText(new File("filename"), Collections.singletonList(" // abc")))
                                .lineIsComment(0))
                .isTrue();
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
        assertWithMessage("Invalid cpp comment")
                .that(lineComment)
                .isEqualTo(cppComment.toString());
        final String blockComment = fileContents.getBlockComments().get(1).get(0).toString();
        assertWithMessage("Invalid c comment")
                .that(blockComment)
                .isEqualTo(cComment.toString());
    }

    @Test
    public void testSinglelineCommentNotIntersect() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //  ")));
        fileContents.reportSingleLineComment(1, 2);
        assertWithMessage("Should return false when there is no intersection")
                .that(fileContents.hasIntersectionWithComment(1, 0, 1, 1))
                .isFalse();
    }

    @Test
    public void testSinglelineCommentIntersect() {
        // just to make UT coverage 100%
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //   ")));
        fileContents.reportSingleLineComment("type", 1, 2);
        assertWithMessage("Should return true when comments intersect")
                .that(fileContents.hasIntersectionWithComment(1, 5, 1, 6))
                .isTrue();
    }

    @Test
    public void testReportCppComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("   //  ")));
        fileContents.reportSingleLineComment(1, 2);
        final Map<Integer, TextBlock> cppComments = fileContents.getSingleLineComments();

        assertWithMessage("Invalid comment")
                .that(cppComments.get(1).toString())
                .isEqualTo(new Comment(new String[] {" //  "}, 2, 1, 6).toString());
    }

    @Test
    public void testHasIntersectionWithSingleLineComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("     ", "  //test   ",
                        "  //test   ", "  //test   ")));
        fileContents.reportSingleLineComment(4, 4);

        assertWithMessage("Should return true when comments intersect")
                .that(fileContents.hasIntersectionWithComment(1, 3, 4, 6))
                .isTrue();
    }

    @Test
    public void testReportComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  //   ")));
        fileContents.reportBlockComment("type", 1, 2, 1, 2);
        final Map<Integer, List<TextBlock>> comments = fileContents.getBlockComments();

        assertWithMessage("Invalid comment")
                .that(comments.get(1).get(0).toString())
                .isEqualTo(new Comment(new String[] {"/"}, 2, 1, 2).toString());
    }

    @Test
    public void testReportBlockCommentSameLine() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("/* a */ /* b */ ")));
        fileContents.reportBlockComment("type", 1, 0, 1, 6);
        fileContents.reportBlockComment("type", 1, 8, 1, 14);
        final Map<Integer, List<TextBlock>> comments = fileContents.getBlockComments();

        assertWithMessage("Invalid comment")
                .that(comments.get(1).toString())
                .isEqualTo(Arrays.asList(
                    new Comment(new String[] {"/* a */"}, 0, 1, 6),
                    new Comment(new String[] {"/* b */"}, 8, 1, 14)
                ).toString());
    }

    @Test
    public void testReportBlockCommentMultiLine() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("/*", "c", "*/")));
        fileContents.reportBlockComment("type", 1, 0, 3, 1);
        final Map<Integer, List<TextBlock>> comments = fileContents.getBlockComments();

        assertWithMessage("Invalid comment")
                .that(comments.get(1).toString())
                .isEqualTo(Collections.singletonList(
                    new Comment(new String[] {"/*", "c", "*/"}, 0, 3, 1)).toString()
            );
    }

    @Test
    public void testReportBlockCommentJavadoc() {
        final FileContents fileContents = new FileContents(new FileText(new File("filename"),
                Arrays.asList("/** A */", "", "//", "/**/", "/* B */")));
        fileContents.reportBlockComment("type", 1, 0, 1, 7);
        fileContents.reportBlockComment("type", 4, 0, 4, 3);
        fileContents.reportBlockComment("type", 5, 0, 5, 6);

        assertWithMessage("Invalid comment")
                .that(fileContents.getJavadocBefore(1))
                .isNull();
        assertWithMessage("Invalid comment")
                .that(fileContents.getJavadocBefore(4).toString())
                .isEqualTo(new Comment(new String[] {"/** A */"}, 0, 1, 7).toString());
        assertWithMessage("Invalid comment")
                .that(fileContents.getJavadocBefore(5))
                .isNull();
        assertWithMessage("Invalid comment")
                .that(fileContents.getJavadocBefore(6))
                .isNull();
    }

    @Test
    public void testHasIntersectionWithBlockComment() {
        final FileContents fileContents = new FileContents(new FileText(new File("filename"),
                        Arrays.asList("  /* */    ", "    ", "  /* test   ", "  */  ", "   ")));
        fileContents.reportBlockComment(1, 2, 1, 5);
        fileContents.reportBlockComment(3, 2, 4, 2);

        assertWithMessage("Should return true when comments intersect")
                .that(fileContents.hasIntersectionWithComment(2, 2, 3, 6))
                .isTrue();
    }

    @Test
    public void testHasIntersectionWithBlockComment2() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Arrays.asList("  /* */    ", "    ", " ")));
        fileContents.reportBlockComment(1, 2, 1, 5);

        assertWithMessage("Should return false when there is no intersection")
                .that(fileContents.hasIntersectionWithComment(2, 2, 3, 6))
                .isFalse();
    }

    @Test
    public void testReportJavadocComment() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  /** */   ")));
        fileContents.reportBlockComment(1, 2, 1, 6);
        final TextBlock comment = fileContents.getJavadocBefore(2);

        assertWithMessage("Invalid comment")
                .that(comment.toString())
                .isEqualTo(new Comment(new String[] {"/** *"}, 2, 1, 6).toString());
    }

    @Test
    public void testReportJavadocComment2() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("  /** */   ")));
        fileContents.reportBlockComment(1, 2, 1, 6);
        final TextBlock comment = fileContents.getJavadocBefore(2);

        assertWithMessage("Invalid comment")
                .that(comment.toString())
                .isEqualTo(new Comment(new String[] {"/** *"}, 2, 1, 6).toString());
    }

    /*
     * This method is deprecated due to usage of deprecated FileContents#inPackageInfo
     * we keep this method until https://github.com/checkstyle/checkstyle/issues/11723
     */
    @Deprecated(since = "10.2")
    @Test
    public void testInPackageInfo() {
        final FileContents fileContents = new FileContents(new FileText(
                new File("package-info.java"),
                Collections.singletonList("  //   ")));

        assertWithMessage("Should return true when in package info")
                .that(fileContents.inPackageInfo())
                .isTrue();
    }

    /*
     * This method is deprecated due to usage of deprecated FileContents#inPackageInfo
     * we keep this method until https://github.com/checkstyle/checkstyle/issues/11723
     */
    @Deprecated(since = "10.2")
    @Test
    public void testNotInPackageInfo() {
        final FileContents fileContents = new FileContents(new FileText(
                new File("some-package-info.java"),
                Collections.singletonList("  //   ")));

        assertWithMessage("Should return false when not in package info")
                .that(fileContents.inPackageInfo())
                .isFalse();
    }

    @Test
    public void testGetJavadocBefore() {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.singletonList("    ")));
        final Map<Integer, TextBlock> javadoc = new HashMap<>();
        javadoc.put(0, new Comment(new String[] {"// "}, 2, 1, 2));
        TestUtil.setInternalState(fileContents, "javadocComments", javadoc);
        final TextBlock javadocBefore = fileContents.getJavadocBefore(2);

        assertWithMessage("Invalid before javadoc")
                .that(javadocBefore.toString())
                .isEqualTo(new Comment(new String[] {"// "}, 2, 1, 2).toString());
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

        assertWithMessage("Invalid comment text")
                .that(text)
                .isEqualTo(new String[] {"/* test   ", "  *"});
    }

    @Test
    public void testHasIntersectionEarlyOut() throws Exception {
        final FileContents fileContents = new FileContents(
                new FileText(new File("filename"), Collections.emptyList()));
        final Map<Integer, List<TextBlock>> clangComments = TestUtil.getInternalState(fileContents,
                "clangComments");
        final TextBlock textBlock = new Comment(new String[] {""}, 1, 1, 1);
        clangComments.put(1, Collections.singletonList(textBlock));
        clangComments.put(2, Collections.emptyList());

        assertWithMessage("Invalid results")
                .that(TestUtil.<Boolean>invokeMethod(fileContents,
                        "hasIntersectionWithBlockComment", 1, 1, 1, 1))
                .isTrue();
    }

    @Test
    public void testUnmodifiableGetSingleLineComment() {
        final FileContents cppComments = new FileContents(new FileText(new File("filename"),
                Arrays.asList("// comment ", " A + B ", " ")));
        cppComments.reportSingleLineComment(1, 0);
        final Map<Integer, TextBlock> comments = cppComments.getSingleLineComments();
        final Exception ex = getExpectedThrowable(UnsupportedOperationException.class,
                () -> comments.remove(0));
        assertWithMessage("Exception message not expected")
                .that(ex.getClass())
                .isEqualTo(UnsupportedOperationException.class);
    }

    @Test
    public void testUnmodifiableGetBlockComments() {
        final FileContents clangComments = new FileContents(new FileText(new File("filename"),
                Arrays.asList("/* comment ", " ", " comment */")));
        clangComments.reportBlockComment(1, 0, 3, 9);
        final Map<Integer, List<TextBlock>> comments = clangComments.getBlockComments();
        final Exception ex = getExpectedThrowable(UnsupportedOperationException.class,
                () -> comments.remove(0));
        assertWithMessage("Exception message not expected")
                .that(ex.getClass())
                .isEqualTo(UnsupportedOperationException.class);
    }
}
