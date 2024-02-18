//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.grammar.java21;

import java.util.List;

public class InputTextBlockConsecutiveEscapes {
    String s = """
            \u03bc\t
            \u03bc\s
            \u03bc\s not all escaped chars
            \u03bc\n not all escaped chars
            \s\s\s\n
            \u03bc\s
            \s\s\s\n not all escaped chars
            \u03bc\s not all escaped chars
            \u03bc\n not all escaped chars
            l\u03bc\n
            \n       \u03bc\s
            \u03bc\
            \s\u03bc\
            """;

            final List<TestCase> testCases = List.of(
                new TestCase("""
                    {@snippet :
                    hello there //   @highlight   regex ="\t**"
                    }""",
                             """
                    error: snippet markup: invalid regex
                    hello there //   @highlight   regex ="\t**"
                                                          \t ^
                    """),
                new TestCase("""
                    {@snippet :
                    hello there //   @highlight   regex ="\\t**"
                    }""",
                        """
                    error: snippet markup: invalid regex
                    hello there //   @highlight   regex ="\\t**"
                                                             ^
                    """),
                new TestCase("""
                    {@snippet :
                    hello there // @highlight regex="\\.\\*\\+\\E"
                    }""",
                             """
                    error: snippet markup: invalid regex
                    hello there // @highlight regex="\\.\\*\\+\\E"
                                                     \s\s\s\s   ^
                    """),
                    // use \s to counteract shift introduced
                    // by \\ so as to visually align ^ right below E
                new TestCase("""
                    {@snippet :
                    hello there //   @highlight  type="italics" regex ="  ["
                    }""",
                        """
                    error: snippet markup: invalid regex
                    hello there //   @highlight  type="italics" regex ="  ["
                                                                          ^
                    """)
                );

    private record TestCase(String s1, String s2){}
}
