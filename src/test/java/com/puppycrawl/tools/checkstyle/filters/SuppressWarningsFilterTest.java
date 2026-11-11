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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWarningsFilterTest extends AbstractModuleTestSupport {

    private static final String[] DEFAULT_MESSAGES = {
        "46:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "I"),
        "47:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "E"),
        "49:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "A"),
        "52:37: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "I", "^[a-z][a-zA-Z0-9]*$"),
        "54:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
        "55:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "K", "^[a-z][a-zA-Z0-9]*$"),
        "59:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
        "59:32: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "X", "^[a-z][a-zA-Z0-9]*$"),
        "64:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "65:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "70:10: "
            + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
        "74:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "83:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "88:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "AnEnum"),
    };

    private static final String[] UNCOMMENTED_MAIN_MESSAGES = {
        "29:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "32:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass1"),
        "34:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "38:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass2"),
        "40:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "44:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass3"),
        "46:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "51:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "Task"),
    };

    private static final String[] UNCOMMENTED_MAIN_MESSAGES_WITHOUT_FILTER = {
        "24:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "27:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass1"),
        "29:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "33:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass2"),
        "35:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "39:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "TestClass3"),
        "41:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "46:5: "
            + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING, "Task"),
    };

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswarningsfilter";
    }

    @Test
    public void testNoneDefault() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(
            getPath("InputSuppressWarningsFilterWithoutFilterDefault.java"),
            DEFAULT_MESSAGES, suppressed);
    }

    @Test
    public void testNoneUncommentedMain() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(
            getPath("InputSuppressWarningsFilterWithoutFilterUncommentedMain.java"),
            UNCOMMENTED_MAIN_MESSAGES_WITHOUT_FILTER, suppressed);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] suppressed = {
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "59:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
            "64:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "70:10: "
                + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
            "83:9: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressedWithParser(getPath("InputSuppressWarningsFilterDefault.java"),
            DEFAULT_MESSAGES, suppressed);
    }

    @Test
    public void testUncommentedMain() throws Exception {
        final String[] suppressed = {
            "29:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "34:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "40:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "46:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        verifySuppressedWithParser(getPath("InputSuppressWarningsFilterUncommentedMain.java"),
            UNCOMMENTED_MAIN_MESSAGES, suppressed);
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

    private void verifySuppressedWithParser(String fileName, String[] messages,
                                            String... suppressed)
            throws Exception {
        verifyFilterWithInlineConfigParser(fileName, messages,
                                           removeSuppressed(messages, suppressed));
    }

}
