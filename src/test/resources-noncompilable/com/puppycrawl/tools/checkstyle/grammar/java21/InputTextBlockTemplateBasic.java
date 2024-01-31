//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.grammar.java21;

public class InputTextBlockTemplateBasic {

    String s = "my string";

    String s1 = STR."""
            """;
    String s2 = STR."""
            \{
            s
            }
            """;
    String s3 = STR."""
            \{
            s
            }
            \{
            s
            }
            """;

    String s4 = STR."""
        \{ s } \{ s } \{ s }""";

    String s5 = STR."""
        \{s}\{s}\{s}""";

    String s6 = STR." \{s}\{s}\{s}";
}
