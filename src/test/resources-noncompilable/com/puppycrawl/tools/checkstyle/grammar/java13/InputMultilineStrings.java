//non-compiled with javac: Compilable with Java13
package com.puppycrawl.tools.checkstyle.grammar.java13;

public class InputMultilineStrings {

    {
        String doubleQuotes = """
"" """;
        String oneDoubleQuote = """
" """;
        String empty = """
""";
        String oneSingleQuote = """
' """;
        String escape = """
                        <html>\u000D\u000A\n
                            <body>\u000D\u000A\n
                                <p>Hello, world</p>\u000D\u000A\n
                            </body>\u000D\u000A\n
                        </html>\u000D\u000A
                        """;
        String html = """
              <html>\r
                  <body>\r
                      <p>Hello, world</p>\r
                  </body>\r
              </html>\r
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
    }

}
