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
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWarningsFilterTest
    extends AbstractModuleTestSupport {

    private static final String[] ALL_MESSAGES = {
        "49:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "50:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "52:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "55:37: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "I", "^[a-z][a-zA-Z0-9]*$"),
        "57:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
        "58:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "K", "^[a-z][a-zA-Z0-9]*$"),
        "62:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
        "62:32: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "X", "^[a-z][a-zA-Z0-9]*$"),
        "67:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "68:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "73:10: "
            + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
        "77:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "86:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "91:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "100:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "103:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "104:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "108:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "109:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "113:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "114:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "118:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
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
            "57:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "62:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
            "67:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "73:10: "
                + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
            "86:9: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "100:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "104:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "109:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "114:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        verifySuppressedWithParser(getPath("InputSuppressWarningsFilter.java"), suppressed);
    }

    @Test
    public void testSuppressById() throws Exception {
        final String[] suppressedViolationMessages = {
            "50:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "52:5: "
                + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        final String[] expectedViolationMessages = {
            "47:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "50:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "52:5: "
                + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };

        verifySuppressedWithParser(getPath("InputSuppressWarningsFilterById.java"),
                expectedViolationMessages, suppressedViolationMessages);
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
