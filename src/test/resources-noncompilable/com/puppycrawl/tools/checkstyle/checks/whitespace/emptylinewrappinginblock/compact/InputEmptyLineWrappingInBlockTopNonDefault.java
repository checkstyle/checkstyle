/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF, METHOD_DEF, RECORD_DEF, COMPACT_CTOR_DEF
topSeparator = empty_line_allowed
bottomSeparator = empty_line_allowed

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 1;
}

class EmptyClass{}

interface EmptyInterface{}

record Point(int x, int y) {

    Point {

        int a = 0;

    }

}

