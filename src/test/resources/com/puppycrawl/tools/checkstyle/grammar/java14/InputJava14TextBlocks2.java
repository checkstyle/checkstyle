
package com.puppycrawl.tools.checkstyle.grammar.java14;

public class InputJava14TextBlocks2
{
    private static final CharSequence type = "type";

    static {
        String sqlQuery = """
                SELECT `EMP_ID`, `LAST_NAME` FROM `EMPLOYEE_TB`
                WHERE `CITY` = 'INDIANAPOLIS'
                ORDER BY `EMP_ID`, `LAST_NAME`;
                """;
        String concat = """
                The quick brown fox""" + "  \n" + """
                jumps over the lazy dog
                """;
        String someCode = """
                function hello() {
                 print('"Hello, world"');
                }
                hello();
                """;
        String multiLineStr =
                """
                SELECT * FROM USERS
                WHERE USER_ID = '1'
                """;
        String textBlockCeption =
                """
                String text = \"""
                A text block inside a text block
                \""";
                """;
        String code1 = """
              public void print($type o) {
                  System.out.println(Objects.toString(o));
              }
              """.replace("$type", type);
        String code2 = String.format("""
              public void print(%s o) {
                  System.out.println(Objects.toString(o));
              }
              """, type);
    }

    public String getFormattedText(String parameter) {
        return """
            Some parameter: %s
            """.formatted(parameter);
    }

    public String getIgnoredNewLines() {
        return """
            This is a long test which looks to \
            have a newline but actually does not""";
    }

    public String getEscapedSpaces() {
        return """
            line 1·······
            line 2·······\s
            """;
    }

    void lineTerminators() {
        String s = """
                \u000D""";
        String s1 = """
                \n""";
        String s2 = """
                \n
                """;
        String s3 = """
                \
                """;
    }
}
