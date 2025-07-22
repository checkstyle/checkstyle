package com.puppycrawl.tools.checkstyle.grammar.javadoc;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class JavadocCommentsAstRegressionTest extends AbstractTreeTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/javadoc/";
    }

    private String geSimplePath(String filename) throws IOException {
        return getPath("simple" + File.separator + filename);
    }

    @Test
    public void testEmptyJavadoc() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedEmptyJavadoc.txt"),
                geSimplePath("InputEmptyJavadoc.javadoc"));
    }

    @Test
    public void testEmptyJavadocWithTabs() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedEmptyJavadocWithTabs.txt"),
                geSimplePath("InputEmptyJavadocWithTabs.javadoc"));
    }

    @Test
    public void testEmptyJavadocStartsWithNewline() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedEmptyJavadocStartsWithNewline.txt"),
                geSimplePath("InputEmptyJavadocStartsWithNewline.javadoc"));
    }

    @Test
    public void testSimpleJavadocWithText() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedSimpleJavadocWithText.txt"),
                geSimplePath("InputSimpleJavadocWithText.javadoc"));
    }

    @Test
    public void testSimpleJavadocWithText2() throws Exception {
        verifyJavadocTree(geSimplePath("ExpectedSimpleJavadocWithText2.txt"),
                geSimplePath("InputSimpleJavadocWithText2.javadoc"));
    }
}
