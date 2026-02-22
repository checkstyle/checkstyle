package org.checkstyle.suppressionxpathfilter.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.JavaLineLengthCheck.MSG_KEY;

import java.io.File;
import java.util.List;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.sizes.JavaLineLengthCheck;
import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

public class XpathRegressionJavaLineLengthTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return JavaLineLengthCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/sizes/executablestatementcount";
    }

    @Test
    public void testDefault() throws Exception {
        final String filePath = getPath("InputXpathJavaLineLengthDefault.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavaLineLengthCheck.class);
        moduleConfig.addProperty("max", "50");

        final String[] expectedViolations = {
            "5:0: " + getCheckMessage(JavaLineLengthCheck.class, MSG_KEY, 50, 88),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathJavaLineLengthDefault']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }
}
