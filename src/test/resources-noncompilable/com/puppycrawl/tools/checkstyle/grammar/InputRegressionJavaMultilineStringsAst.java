//non-compiled with javac: Compilable with Java13
package com.puppycrawl.tools.checkstyle.grammar;

public class InputRegressionJavaMultilineStringsAst {

    {
        String doubleQuotes = """
""
""";
        String singleQuote = """
"
""";
        String empty = """
""";
        String escape1 = """
\r
""";
        String escape2 = """
\t
""";
        String escape3 = """
\n
""";
        String escape4 = """
\u0032
""";
        String multilineText = """
here is my text!
""";
        String normal = "";
    }

}
