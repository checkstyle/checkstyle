package com.puppycrawl.tools.checkstyle.checks.imports;

import java.io.File;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ImportControlCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/importcontrol";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.io.File"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources-noncompilable/" + getPackageLocation());
        verifyWithInlineXmlConfig(getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Stream"),
            "17:1: " + getCheckMessage(
                    ImportControlCheck.MSG_DISALLOWED, "java.util.stream.Collectors"),
        };

        final String examplePath = new File("src/" + getResourceLocation()
                + "/resources-noncompilable/" + getPackageLocation() + "/"
                + "someImports/Example10.java").getCanonicalPath();

        System.setProperty("config.folder", "src/xdocs-examples/resources-noncompilable/" + getPackageLocation());
        verifyWithInlineXmlConfig(examplePath, expected);
    }

}
