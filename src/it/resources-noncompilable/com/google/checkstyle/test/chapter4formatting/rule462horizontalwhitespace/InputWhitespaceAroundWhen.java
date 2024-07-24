//non-compiled with javac: Compilable with Java21
package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputWhitespaceAroundWhen {
    void test(Object o) {
        switch (o) {
            case Integer i when(i == 0) -> { }
            // 2 violations above:
            //              ''when' is not followed by whitespace'
            //              ''when' is not followed with whitespace'
            case String s when(
            // 2 violations above:
            //              ''when' is not followed by whitespace'
            //              ''when' is not followed with whitespace'
                    s.equals("a")) -> { }
            case Point(int x, int y) when!(x >= 0 && y >= 0) -> { }
            // 2 violations above:
            //              ''when' is not followed by whitespace'
            //              ''when' is not followed with whitespace'
            default -> { }
        }

        switch (o) {
            case Point(int x, int y)when(x >= 0 && y >= 0) -> { }
            // 3 violations above:
            //              ''when' is not followed by whitespace'
            //              ''when' is not followed with whitespace'
            //              ''when' is not preceded with whitespace.'
            case Point(int x, int y)when!(x >= 0 && y >= 0) -> { }
            // 3 violations above:
            //              ''when' is not followed by whitespace'
            //              ''when' is not followed with whitespace'
            //              ''when' is not preceded with whitespace.'
            default -> { }
        }

        switch (o) {
            case Integer i when (i == 0) -> { }
            case String s when (s.equals("a")) -> { }
            case Point(int x, int y) when (x >= 0 && y >= 0) -> { }
            default -> { }
        }
    }

    record Point(int x, int y) { }
}
