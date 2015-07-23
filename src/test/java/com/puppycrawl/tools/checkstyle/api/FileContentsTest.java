package com.puppycrawl.tools.checkstyle.api;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileContentsTest {

    @Test
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
        o.reportCppComment(1,2);
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
