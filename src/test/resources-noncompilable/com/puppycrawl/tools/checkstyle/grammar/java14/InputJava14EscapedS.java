//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

public class InputJava14EscapedS {

    public static void main(String[] args) {
        String s1 = "\s";
        String s2 = """
            \s""";
        String s3 = "\t\n\n\n\\n\s\s";
        String s4 = """
            \t\s\t\n\\n\t\s\s\s\s""";
        String s5 = """
            \n\s\s""" + """
            \s\s\n""" +"\s";
    }

    static void test2() {
        if ('\s' != ' ') {
            throw new RuntimeException("Failed character escape-S");
        }
        assert("\s".equals(" "));
        assert("""
           \s
           """.equals(" \n"));
    }
}
