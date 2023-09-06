/*
MethodLength
max = 2
countEmpty = false
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

public class InputMethodLengthTextBlocksCountEmpty {
    void longEmptyTextBlock() { // violation 'Method .* length is 21 lines (max allowed is 2).'







        String a = """
                      3
                      4
                      5
                      6
                      7
                      8
                      9
                      10
                      11
                      12
                      13
                      14
                      15
                      16
                      17
                      18
                      19
                      20
""";}

    void textBlock2() { // violation 'Method .* length is 10 lines (max allowed is 2).'
    String a = """
        3
        4
        5
        6

""";
    // comment
    int b = 1;
    /* block comment */

    }

    void textBlockWithIndent() { // violation 'Method .* length is 8 lines (max allowed is 2).'
        String a = """
        3
        4
        5
        6
        7
         """;}

    void textBlockNoIndent() { // violation 'Method .* length is 12 lines (max allowed is 2).'
        String a =
"""
        3
        4
        5
        6
        7

"""

            ;

    }

    void text2Lines() {String a = """
    """;}
}
