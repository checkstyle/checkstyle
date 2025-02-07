package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter.example6files;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck;

public class MyMethod
    extends MethodNameCheck {
    private static final String CUSTOM_FORMAT = "^[a-z](_?[a-zA-Z0-9]+)*$";

    public MyMethod() {
        super.setFormat(Pattern.compile(CUSTOM_FORMAT));
    }
}
