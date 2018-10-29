package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

import java.util.Properties;

public class InputWhitespaceAroundDoubleBraceInitialization {
    public InputWhitespaceAroundDoubleBraceInitialization() {
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
