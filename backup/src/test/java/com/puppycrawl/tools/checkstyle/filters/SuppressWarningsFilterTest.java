///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
        "48:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "49:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "51:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "54:37: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "I", "^[a-z][a-zA-Z0-9]*$"),
        "56:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
        "57:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "K", "^[a-z][a-zA-Z0-9]*$"),
        "61:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
        "61:32: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "X", "^[a-z][a-zA-Z0-9]*$"),
        "66:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "67:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "72:10: "
            + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
        "76:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "85:9: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "90:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "99:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "102:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "103:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "107:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "108:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "112:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        "113:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        "117:5: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
    };

    @Override
    protected String getPackageLocation() {
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
            "56:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "61:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
            "66:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "72:10: "
                + getCheckMessage(ParameterNumberCheck.class, ParameterNumberCheck.MSG_KEY, 7, 8),
            "85:9: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "99:5: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "103:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "108:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
            "113:9: " + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        verifySuppressedWithParser(getPath("InputSuppressWarningsFilter.java"), suppressed);
    }

    @Test
    public void testSuppressById() throws Exception {
        final String[] suppressedViolationMessages = {
            "49:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "51:5: "
                + getCheckMessage(UncommentedMainCheck.class, UncommentedMainCheck.MSG_KEY),
        };
        final String[] expectedViolationMessages = {
            "46:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "49:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "51:5: "
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
