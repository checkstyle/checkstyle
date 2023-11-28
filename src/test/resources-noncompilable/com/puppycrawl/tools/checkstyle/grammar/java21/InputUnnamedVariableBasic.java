//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.grammar.java21;

public class InputUnnamedVariableBasic {

    record Point(int x, int y) { }
    enum Color { RED, GREEN, BLUE }
    record ColoredPoint(Point p, Color c) { }

    void jep443examples(ColoredPoint r) {
        if (r instanceof ColoredPoint(Point(int x, int y), _)) { }
        if (r instanceof ColoredPoint(_, Color c)) { }
        if (r instanceof ColoredPoint(Point(int x, _), _)) { }
        if (r instanceof ColoredPoint(Point(int x, int _), Color _)) { }
        if (r instanceof ColoredPoint _) { }
    }

}
