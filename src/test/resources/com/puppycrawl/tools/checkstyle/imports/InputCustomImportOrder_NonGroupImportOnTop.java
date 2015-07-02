package com.puppycrawl.tools.checkstyle.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport; // warn, non-group import shoul be in the end

import java.io.File;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class InputCustomImportOrder_NonGroupImportOnTop {
}

/*
test: testNonGroupImportOnTop()
configuration:
        checkConfig.addAttribute("customImportOrderRules",
                "STANDARD_JAVA_PACKAGE");
*/
