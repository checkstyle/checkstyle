/*
WhenShouldBeUsed


*/

// non-compiled with javac: Compilable with Java25

record Point(int x, int y) {
}

void main() {
}

String describe(Object value) {
    switch (value) {
        case Point(int x, int y) -> { // violation ''when' expression should be used'
            if (x == y) {
                return "diagonal";
            }
        }
        case String text when text.isEmpty() -> {
            return "empty";
        }
        default -> {
            return "other";
        }
    }
    return "point";
}

int classify(Object value) {
    return switch (value) {
        case String text:
            System.out.println(text);
            if (text.isEmpty()) {
                yield 2;
            }
            yield 3;
        case Integer number:
            if (number > 0) {
                yield 1;
            }
        default:
            yield 0;
    };
}
