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

import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Tests to ensure that default message bundle is determined correctly.
 *
 */
class AbstractViolationReporterTest {

    private final AbstractCheck emptyCheck = new EmptyCheck();

    protected static DefaultConfiguration createModuleConfig(Class<?> clazz) {
        return new DefaultConfiguration(clazz.getName());
    }

    @Test
    void getMessageBundleWithPackage() throws Exception {
        assertWithMessage("violation bundle differs from expected")
                .that(TestUtil.invokeStaticMethod(AbstractViolationReporter.class,
                        "getMessageBundle", String.class,
                        "com.mycompany.checks.MyCoolCheck"))
                .isEqualTo("com.mycompany.checks.messages");
    }

    @Test
    void getMessageBundleWithoutPackage() throws Exception {
        assertWithMessage("violation bundle differs from expected")
                .that(TestUtil.invokeStaticMethod(AbstractViolationReporter.class,
                        "getMessageBundle", String.class, "MyCoolCheck"))
                .isEqualTo("messages");
    }

    @Test
    void customId() {
        emptyCheck.setId("MyId");
        assertWithMessage("Id differs from expected")
                .that(emptyCheck.getId())
                .isEqualTo("MyId");
    }

    @Test
    void severity() throws Exception {
        final DefaultConfiguration config = createModuleConfig(emptyCheck.getClass());
        config.addMessage("severity", "error");
        emptyCheck.configure(config);

        assertWithMessage("Invalid severity level")
                .that(emptyCheck.getSeverityLevel())
                .isEqualTo(SeverityLevel.ERROR);
        assertWithMessage("Invalid severity")
                .that(emptyCheck.getSeverity())
                .isEqualTo("error");
    }

    @Test
    void customMessage() throws Exception {
        final DefaultConfiguration config = createModuleConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom violation.");
        emptyCheck.configure(config);

        emptyCheck.log(1, "msgKey");

        final SortedSet<Violation> messages = emptyCheck.getViolations();

        assertWithMessage("Amount of messages differs from expected")
                .that(messages)
                .hasSize(1);
        assertWithMessage("violation differs from expected")
                .that(messages.first().getViolation())
                .isEqualTo("This is a custom violation.");
    }

    @Test
    void customMessageWithParameters() throws Exception {
        final DefaultConfiguration config = createModuleConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom violation with {0}.");
        emptyCheck.configure(config);

        emptyCheck.log(1, "msgKey", "TestParam");
        final SortedSet<Violation> messages = emptyCheck.getViolations();

        assertWithMessage("Amount of messages differs from expected")
                .that(messages)
                .hasSize(1);

        assertWithMessage("violation differs from expected")
                .that(messages.first().getViolation())
                .isEqualTo("This is a custom violation with TestParam.");
    }

    @Test
    void customMessageWithParametersNegative() throws Exception {
        final DefaultConfiguration config = createModuleConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom violation {0.");
        emptyCheck.configure(config);

        try {
            emptyCheck.log(1, "msgKey", "TestParam");
            assertWithMessage("exception expected")
                    .fail();
        }
        catch (IllegalArgumentException exc) {
            assertWithMessage("Error violation is unexpected")
                    .that(exc.getMessage())
                    .isEqualTo("Unmatched braces in the pattern.");
        }
    }

    public static class EmptyCheck extends AbstractCheck {

        @Override
        public int[] getDefaultTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getRequiredTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

    }

}
