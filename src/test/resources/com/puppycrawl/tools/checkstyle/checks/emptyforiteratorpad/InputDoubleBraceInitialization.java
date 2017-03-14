package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Properties;

public class InputDoubleBraceInitialization {
    public InputDoubleBraceInitialization() {
        new Properties() {{
            setProperty("double curly braces", "are not a style error");
        }};
        new Properties() {{
            setProperty("double curly braces", "are not a style error");}};
        new Properties() {{setProperty("double curly braces", "are not a style error");
        }};
        new Properties() {{setProperty("double curly braces", "are not a style error");}};
        new Properties() {{
            setProperty("double curly braces", "are not a style error");
        }private int i;};
    }
}
