///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocRegexpCheck.MSG_JAVADOC_REGEXP;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocRegexpCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocregexp";
    }

    @Test
    public void testDefault() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpDefault.java"), CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIgnoreMarkupTrue() throws Exception {
        final String format = "(^|\\W)(aka|input|e\\.g\\.|viz\\.)(\\W|$)";
        final String[] expected = {
            "22: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "29: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "36: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpIgnoreMarkupTrue.java"), expected);
    }

    @Test
    public void testBlockTag() throws Exception {
        final String format = "legacy|external";
        final String[] expected = {
            "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "25: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpBlockTag.java"), expected);
    }

    @Test
    public void testInlineTag() throws Exception {
        final String format = "temporary|beta";
        final String[] expected = {
            "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "23: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpInlineTag.java"), expected);
    }

    @Test
    public void testSnippetTag() throws Exception {
        final String format = "lang=\"e\\.g\\.\"|forbidden\\s*=";
        final String[] expected = {
            "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "25: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpSnippetTag.java"), expected);
    }

    @Test
    public void testIgnoreMarkupFalse() throws Exception {
        final String format = "<br\\s*[/]?>|aka";
        final String[] expected = {
            "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "23: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpIgnoreMarkupFalse.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpIgnoreCaseFalse.java"), CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String format = "AKA";
        final String[] expected = {
            "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpIgnoreCaseTrue.java"), expected);
    }

    @Test
    public void testTextSplitAcrossLines() throws Exception {
        final String format = "^first second";
        final String[] expected = {
            "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpTextSplitAcrossLines.java"), expected);
    }

    @Test
    public void testSetFormatAfterMatching() throws Exception {
        final String filePath = getPath("InputJavadocRegexpCacheFormat.java");
        final String alphaViolation = "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, "alpha");
        final String betaViolation = "22: " + getCheckMessage(MSG_JAVADOC_REGEXP, "beta");
        verifyWithInlineConfigParser(filePath, new String[] {alphaViolation});

        final JavadocRegexpCheck check = new JavadocRegexpCheck();
        final TreeWalker walker = createTreeWalker(check, filePath);
        try {
            assertWithMessage("Initial format should match alpha")
                    .that(processFile(walker, filePath)).containsExactly(alphaViolation);
            check.setFormat("beta");
            assertWithMessage("Updated format should match beta instead of alpha")
                    .that(processFile(walker, filePath)).containsExactly(betaViolation);
            check.setFormat("alpha");
            assertWithMessage("Restored format should match alpha again")
                    .that(processFile(walker, filePath)).containsExactly(alphaViolation);
        }
        finally {
            walker.destroy();
        }
    }

    @Test
    public void testSetIgnoreCaseAfterMatching() throws Exception {
        final String filePath = getPath("InputJavadocRegexpCacheIgnoreCase.java");
        final String uppercaseViolation = "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, "ALPHA");
        final String lowercaseViolation = "22: " + getCheckMessage(MSG_JAVADOC_REGEXP, "ALPHA");
        verifyWithInlineConfigParser(filePath, new String[] {uppercaseViolation});

        final JavadocRegexpCheck check = new JavadocRegexpCheck();
        final TreeWalker walker = createTreeWalker(check, filePath);
        try {
            assertWithMessage("Case-sensitive matching should only report uppercase text")
                    .that(processFile(walker, filePath)).containsExactly(uppercaseViolation);
            check.setIgnoreCase(true);
            assertWithMessage("Ignoring case should report both comments")
                    .that(processFile(walker, filePath))
                    .containsExactly(uppercaseViolation, lowercaseViolation).inOrder();
            check.setIgnoreCase(false);
            assertWithMessage("Restoring case sensitivity should stop reporting lowercase text")
                    .that(processFile(walker, filePath)).containsExactly(uppercaseViolation);
        }
        finally {
            walker.destroy();
        }
    }

    @Test
    public void testInvalidRegexpIsDeferredUntilJavadocProcessing() throws Exception {
        final String noJavadocPath = getPath("InputJavadocRegexpInvalidFormatNoJavadoc.java");
        final String javadocPath = getPath("InputJavadocRegexpCacheFormat.java");
        final JavadocRegexpCheck check = new JavadocRegexpCheck();
        final TreeWalker walker = createTreeWalker(check, noJavadocPath);
        try {
            assertWithMessage("Invalid regex should not fail without Javadoc")
                    .that(processFile(walker, noJavadocPath)).isEmpty();
            check.beginJavadocTree(null);
            final IllegalArgumentException exception = getExpectedThrowable(
                    IllegalArgumentException.class,
                    () -> check.finishJavadocTree(null),
                    "Invalid regex should fail at finishJavadocTree");
            assertWithMessage("Regex compilation should retain its error message")
                    .that(exception).hasMessageThat()
                    .isEqualTo("Failed to initialise regular expression [");
            assertWithMessage("Regex syntax error should remain the cause")
                    .that(exception).hasCauseThat().isInstanceOf(PatternSyntaxException.class);
            getExpectedThrowable(IllegalArgumentException.class,
                    () -> processFile(walker, javadocPath),
                    "Failed compilation should not leave a usable cached pattern");
            check.setFormat("alpha");
            assertWithMessage("A valid format should recover after failed compilation")
                    .that(processFile(walker, javadocPath))
                    .containsExactly("16: " + getCheckMessage(MSG_JAVADOC_REGEXP, "alpha"));
        }
        finally {
            walker.destroy();
        }
    }

    @Test
    public void testSameInstanceProcessesDifferentFiles() throws Exception {
        final String firstPath = getPath("InputJavadocRegexpCacheFormat.java");
        final String secondPath = getPath("InputJavadocRegexpCacheSecondFile.java");
        final String firstViolation = "16: " + getCheckMessage(MSG_JAVADOC_REGEXP, "alpha");
        final String secondViolation = "22: " + getCheckMessage(MSG_JAVADOC_REGEXP, "alpha");
        verifyWithInlineConfigParser(firstPath, new String[] {firstViolation});
        verifyWithInlineConfigParser(secondPath, new String[] {secondViolation});

        final JavadocRegexpCheck check = new JavadocRegexpCheck();
        final TreeWalker walker = createTreeWalker(check, firstPath);
        try {
            assertWithMessage("First file should report its matching comment")
                    .that(processFile(walker, firstPath)).containsExactly(firstViolation);
            assertWithMessage("Second file should use its own content and line numbers")
                    .that(processFile(walker, secondPath)).containsExactly(secondViolation);
            assertWithMessage("Returning to the first file should not retain second-file state")
                    .that(processFile(walker, firstPath)).containsExactly(firstViolation);
        }
        finally {
            walker.destroy();
        }
    }

    @Test
    public void testRegexpIsReusedAcrossFiles() throws Exception {
        final String firstPath = getPath("InputJavadocRegexpCacheFormat.java");
        final String secondPath = getPath("InputJavadocRegexpCacheSecondFile.java");
        final JavadocRegexpCheck check = new JavadocRegexpCheck();
        final TreeWalker walker = createTreeWalker(check, firstPath);
        try {
            processFile(walker, firstPath);
            final Pattern firstRegexp = TestUtil.getInternalState(check, "regexp", Pattern.class);
            assertWithMessage("Processing Javadoc should compile the pattern")
                    .that(firstRegexp).isNotNull();
            processFile(walker, secondPath);
            final Pattern secondRegexp = TestUtil.getInternalState(check, "regexp", Pattern.class);
            assertWithMessage("Different files should reuse the cached pattern")
                    .that(secondRegexp).isSameInstanceAs(firstRegexp);
        }
        finally {
            walker.destroy();
        }
    }

    private static TreeWalker createTreeWalker(JavadocRegexpCheck check, String filePath)
            throws Exception {
        final TreeWalker walker = new TreeWalker();
        walker.setModuleFactory(name -> check);
        walker.configure(InlineConfigParser.parse(filePath).createConfiguration().getChildren()[0]);
        return walker;
    }

    private static List<String> processFile(TreeWalker walker, String filePath) throws Exception {
        final File file = new File(filePath);
        final FileText text = new FileText(file, StandardCharsets.UTF_8.name());
        return walker.process(file, text).stream()
                .map(violation -> violation.getLineNo() + ": " + violation.getViolation())
                .toList();
    }

}
