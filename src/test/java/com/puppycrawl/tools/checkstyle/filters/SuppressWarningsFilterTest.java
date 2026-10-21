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

package com.puppycrawl.tools.checkstyle.filters;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck.MSG_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck.MSG_INVALID_PATTERN;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck.MSG_REGEXP_EXCEEDED;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWarningsFilterTest extends AbstractModuleTestSupport {

    private static final String[] ALL_MESSAGES = {
        "50:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "I"),
        "51:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "E"),
        "53:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "A"),
        "56:37: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "I", "^[a-z][a-zA-Z0-9]*$"),
        "58:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
        "59:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "K", "^[a-z][a-zA-Z0-9]*$"),
        "63:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
        "63:32: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "X", "^[a-z][a-zA-Z0-9]*$"),
        "68:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "69:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "74:10: "
            + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
        "78:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "87:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "92:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "AnEnum"),
        "102:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "105:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass1"),
        "107:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "111:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass2"),
        "113:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "117:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass3"),
        "119:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "124:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "Task"),
    };

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswarningsfilter";
    }

    @Test
    public void testNone() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(
            getPath("InputSuppressWarningsFilterWithoutFilter.java"), suppressed);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] suppressed = {
            "58:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "63:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
            "68:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "74:10: "
                + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
            "87:9: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "102:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "107:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "113:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "119:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        verifySuppressedWithParser(getPath("InputSuppressWarningsFilter.java"), suppressed);
    }

    @Test
    public void testSuppressById() throws Exception {
        final String[] suppressedViolationMessages = {
            "51:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "54:5: "
                + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        final String[] expectedViolationMessages = {
            "48:1: "
                + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING,
                    "InputSuppressWarningsFilterById"),
            "51:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "54:5: "
                + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };

        verifySuppressedWithParser(getPath("InputSuppressWarningsFilterById.java"),
                expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressionsAreNotAppliedToNonJavaFile() throws Exception {
        final String[] expectedNonJavaFile = {
            "16: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "forbidde[n]"),
        };

        verifyWithInlineConfigParser(
            List.of(getPath("InputSuppressWarningsFilterSuppressAll.java"),
                getPath("InputSuppressWarningsFilterNonJavaFile.properties")),
            List.of(List.of(), List.of(expectedNonJavaFile)));
    }

    @Test
    public void testSuppressionsAreNotAppliedToUnparsableJavaFile() throws Exception {
        final String suppressAllFile = getPath("InputSuppressWarningsFilterSuppressAll.java");
        final String unparsableFile =
            getNonCompilablePath("InputSuppressWarningsFilterUnparsableFile.java");
        final String[] expectedUnparsableFile = {
            "1: " + getCheckMessage(TreeWalker.class, TreeWalker.PARSE_EXCEPTION_MSG,
                "IllegalStateException") + " occurred while parsing file "
                + unparsableFile + ".",
            "20: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "forbidde[n]"),
        };
        verifyWithInlineXmlConfig(unparsableFile, expectedUnparsableFile);

        final Configuration config =
            InlineConfigParser.parseWithXmlHeader(suppressAllFile).xmlConfiguration();
        final File[] files = {new File(suppressAllFile), new File(unparsableFile)};
        verify(createChecker(config), files, Map.of(
            suppressAllFile, List.of(),
            unparsableFile, List.of(expectedUnparsableFile)));
    }

    private void verifySuppressedWithParser(String fileName, String... suppressed)
            throws Exception {
        verifyFilterWithInlineConfigParser(fileName, ALL_MESSAGES,
                                           removeSuppressed(ALL_MESSAGES, suppressed));
    }

    private void verifySuppressedWithParser(String fileName, String[] messages,
                                            String... suppressed)
            throws Exception {
        verifyFilterWithInlineConfigParser(fileName, messages,
                                           removeSuppressed(messages, suppressed));
    }

}
