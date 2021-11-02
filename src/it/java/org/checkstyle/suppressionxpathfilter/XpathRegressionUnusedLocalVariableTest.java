package org.checkstyle.suppressionxpathfilter;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class XpathRegressionUnusedLocalVariableTest extends AbstractXpathTestSupport {
    private final String checkName = UnusedLocalVariableCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionUnusedLocalVariableOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedLocalVariableCheck.class);

        final String[] expectedViolation = {
                "6:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                        UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[" +
                        "@text='SuppressionXpathRegressionUnusedLocalVariableOne']]/OBJBLOCK/" +
                        "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[./IDENT[@text='a']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[" +
                        "@text='SuppressionXpathRegressionUnusedLocalVariableOne']]/OBJBLOCK/" +
                        "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[" +
                        "./IDENT[@text='a']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[" +
                        "@text='SuppressionXpathRegressionUnusedLocalVariableOne']]/OBJBLOCK/" +
                        "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[" +
                        "./IDENT[@text='a']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[" +
                        "@text='SuppressionXpathRegressionUnusedLocalVariableOne']]/OBJBLOCK/" +
                        "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[" +
                        "./IDENT[@text='a']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
