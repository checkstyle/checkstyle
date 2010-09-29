package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

/**
 * The unit-test for the <code>NestedForDepthCheck</code>-checkstyle enhancement.
 * @see com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck
 */
public class NestedForDepthCheckTest extends BaseCheckTestSupport {
    /**
     * Call the check allowing 2 layers of nested for-statements. This
     * means the top-level for can contain up to 2 levels of nested for
     * statements. As the testinput has 4 layers of for-statements below
     * the top-level for statement, this must cause 2 error-messages.
     *
     * @throws Exception necessary to fulfill JUnit's
     * interface-requirements for test-methods
     */
    @Test
    public void testNestedTooDeep() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedForDepthCheck.class);
        checkConfig.addAttribute("max", "2");

        final String[] expected = {
            "43:11: Nested for depth is 3 (max allowed is 2).",
            "44:13: Nested for depth is 4 (max allowed is 2).",
        };

        verify(checkConfig, getPath("coding/InputNestedForDepth.java"),
               expected);
    }

    /**
     * Call the check allowing 4 layers of nested for-statements. This
     * means the top-level for can contain up to 4 levels of nested for
     * statements. As the testinput has 4 layers of for-statements below
     * the top-level for statement, this must not cause an
     * error-message.
     *
     * @throws Exception necessary to fulfill JUnit's
     * interface-requirements for test-methods
     */
    @Test
    public void testNestedOk() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedForDepthCheck.class);
        checkConfig.addAttribute("max", "4");

        final String[] expected = {
        };

        verify(checkConfig, getPath("coding/InputNestedForDepth.java"),
               expected);
    }
}