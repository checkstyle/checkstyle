package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck;

public class MyMethod
    extends MethodNameCheck {
    private static final String CUSTOM_FORMAT = "^[a-z][a-z0-9]*$";

    public MyMethod() {
        super.setFormat(Pattern.compile(CUSTOM_FORMAT));
    }
}
