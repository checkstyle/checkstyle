////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressionSingleFilterTest extends AbstractModuleTestSupport {

    private static final String FORMAT = "TODO$";
    private static final String MESSAGE = getCheckMessage(RegexpSinglelineCheck.class,
        "regexp.exceeded", FORMAT);
    private static final String[] VIOLATION = {
        "25: " + MESSAGE,
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionsinglefilter";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);

        verifySuppressed(config, getPath("InputSuppressionSingleFilter2.java"), null,
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMatching() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("files", "InputSuppressionSingleFilter3");
        config.addProperty("checks", "RegexpSingleline");
        config.addProperty("lines", "25");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter3.java"), null,
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNonMatchingLineNumber() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("lines", "100");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter4.java"), null, VIOLATION);
    }

    @Test
    public void testNonMatchingColumnNumber() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("columns", "100");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter5.java"), null, VIOLATION);
    }

    @Test
    public void testNonMatchingFileRegexp() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("files", "BAD");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter6.java"), null, VIOLATION);
    }

    @Test
    public void testNonMatchingModuleId() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("id", "BAD");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter7.java"), null, VIOLATION);
    }

    @Test
    public void testMatchingModuleId() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("id", "id");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter10.java"), "id",
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNonMatchingChecks() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("checks", "BAD");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter8.java"), null, VIOLATION);
    }

    @Test
    public void testNotMatchingMessage() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("message", "BAD");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter9.java"), null,
                VIOLATION);
    }

    @Test
    public void testMatchMessage() throws Exception {
        final DefaultConfiguration config = createModuleConfig(SuppressionSingleFilter.class);
        config.addProperty("message", "(TODO)");

        verifySuppressed(config, getPath("InputSuppressionSingleFilter.java"), null,
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    private void verifySuppressed(DefaultConfiguration config, String fileName, String id,
            String... expectedViolations) throws Exception {
        final DefaultConfiguration regexpConfig = createModuleConfig(RegexpSinglelineCheck.class);
        regexpConfig.addProperty("format", FORMAT);
        regexpConfig.addProperty("minimum", "0");
        regexpConfig.addProperty("maximum", "0");

        if (id != null) {
            regexpConfig.addProperty("id", id);
        }

        final DefaultConfiguration checkerConfig = createRootConfig(regexpConfig);
        checkerConfig.addChild(config);

        verify(checkerConfig, fileName, expectedViolations);
        verifyFilterWithInlineConfigParser(
                fileName, VIOLATION, expectedViolations);
    }

}
