package org.checkstyle.suppressionxpathfilter;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class XpathRegressionConstructorsDeclarationGroupingTest extends AbstractXpathTestSupport{

    private final Class<ConstructorsDeclarationGroupingCheck> clazz =
            ConstructorsDeclarationGroupingCheck.class;
    @Override
    protected String getCheckName() {
        return clazz.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionConstructorsDeclarationGrouping1.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);

        final String[] expectedViolation = {
                "10:5: " + getCheckMessage(clazz,
                        ConstructorsDeclarationGroupingCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionConstructorsDeclarationGrouping1']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionConstructorsDeclarationGrouping1']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionConstructorsDeclarationGrouping1']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionConstructorsDeclarationGrouping1']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionConstructorsDeclarationGrouping1']]"
                        + "/OBJBLOCK/CTOR_DEF/IDENT"
                        + "[@text='SuppressionXpathRegressionConstructorsDeclarationGrouping1']"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
