//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

public class InputJava14TextBlocks
{
    private static final CharSequence type = "type";

    static {
        String doubleQuotes = """
                ""
                """;
        String oneDoubleQuote = """
                "
                "
                """;
        String empty = """
                """;
        String oneSingleQuote = """
                '
                """;
        String escape = """
                <html>\u000D\u000A\n
                    <body>\u000D\u000A\n
                        <p>Hello, world</p>\u000D\u000A\n
                    </body>\u000D\u000A\n
                </html>\u000D\u000A
                """;
        String testMoreEscapes = """
                fun with\n
                whitespace\t\r
                and other escapes \"""
                """;
        String evenMoreEscapes = """
                \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
                \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
                \\uffff \\' \\\' \'
                """;
        String lotsMoreEscapes = """
                         0  '\\u0000'    '\\\\x00' '\\\\0'
                         1  '\\u0001'    '\\\\x01' '\\\\1'
                         2  '\\u0002'    '\\\\x02' '\\\\2'
                         3  '\\u0003'    '\\\\x03' '\\\\3'
                         4  '\\u0004'    '\\\\x04' '\\\\4'
                         5  '\\u0005'    '\\\\x05' '\\\\5'
                         6  '\\u0006'    '\\\\x06' '\\\\6'
                         7  '\\u0007'    '\\\\x07' '\\\\7'
                         8  '\\b'        '\\\\x08' '\\\\10'
                         9  '\\t'        '\\\\x09' '\\\\11'
                        10  '\\n'        '\\\\x0a' '\\\\12'
                        11  '\\u000b'    '\\\\x0b' '\\\\13'
                        12  '\\f'        '\\\\x0c' '\\\\14'
                        13  '\\r'        '\\\\x0d' '\\\\15'
                        14  '\\u000e'    '\\\\x0e' '\\\\16'
                        15  '\\u000f'    '\\\\x0f' '\\\\17'
                        16  '\\u0010'    '\\\\x10' '\\\\20'
                        17  '\\u0011'    '\\\\x11' '\\\\21'
                        18  '\\u0012'    '\\\\x12' '\\\\22'
                        19  '\\u0013'    '\\\\x13' '\\\\23'
                        20  '\\u0014'    '\\\\x14' '\\\\24'
                        21  '\\u0015'    '\\\\x15' '\\\\25'
                        22  '\\u0016'    '\\\\x16' '\\\\26'
                        23  '\\u0017'    '\\\\x17' '\\\\27'
                        24  '\\u0018'    '\\\\x18' '\\\\30'
                        25  '\\u0019'    '\\\\x19' '\\\\31'
                        26  '\\u001a'    '\\\\x1a' '\\\\32'
                        27  '\\u001b'    '\\\\x1b' '\\\\33'
                        28  '\\u001c'    '\\\\x1c' '\\\\34'
                        29  '\\u001d'    '\\\\x1d' '\\\\35'
                        30  '\\u001e'    '\\\\x1e' '\\\\36'
                        31  '\\u001f'    '\\\\x1f' '\\\\37'
                        32  ' '         '\\\\x20' '\\\\40'
                        33  '!'         '\\\\x21' '\\\\41'
                        34  '"'         '\\\\x22' '\\\\42'
                        35  '#'         '\\\\x23' '\\\\43'
                        36  '$'         '\\\\x24' '\\\\44'
                        37  '%'         '\\\\x25' '\\\\45'
                        38  '&'         '\\\\x26' '\\\\46'
                        39  '\\'         '\\\\x27' '\\\\47'
                        40  '('         '\\\\x28' '\\\\50'
                        41  ')'         '\\\\x29' '\\\\51'
                        42  '*'         '\\\\x2a' '\\\\52'
                        43  '+'         '\\\\x2b' '\\\\53'
                        44  ','         '\\\\x2c' '\\\\54'
                        45  '-'         '\\\\x2d' '\\\\55'
                        46  '.'         '\\\\x2e' '\\\\56'
                        47  '/'         '\\\\x2f' '\\\\57'
                        48  '0'         '\\\\x30' '\\\\60'
                        49  '1'         '\\\\x31' '\\\\61'
                        50  '2'         '\\\\x32' '\\\\62'
                        51  '3'         '\\\\x33' '\\\\63'
                        52  '4'         '\\\\x34' '\\\\64'
                        53  '5'         '\\\\x35' '\\\\65'
                        54  '6'         '\\\\x36' '\\\\66'
                        55  '7'         '\\\\x37' '\\\\67'
                        56  '8'         '\\\\x38' '\\\\70'
                        57  '9'         '\\\\x39' '\\\\71'
                        58  ':'         '\\\\x3a' '\\\\72'
                        59  ';'         '\\\\x3b' '\\\\73'
                        60  '<'         '\\\\x3c' '\\\\74'
                        61  '='         '\\\\x3d' '\\\\75'
                        62  '>'         '\\\\x3e' '\\\\76'
                        63  '?'         '\\\\x3f' '\\\\77'
                """;
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
