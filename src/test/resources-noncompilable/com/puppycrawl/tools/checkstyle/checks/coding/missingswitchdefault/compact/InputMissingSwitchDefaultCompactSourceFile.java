/*
MissingSwitchDefault


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

enum Day {
    MONDAY,
    TUESDAY
}

sealed interface Shape permits Circle, Square { }

record Circle(int radius) implements Shape { }

record Square(int side) implements Shape { }

int field = switch (2) {
    case 1 -> 1;
    default -> 0;
};

void missingDefault(int value) {
    switch (value) { // violation 'switch without "default" clause'
        case 1: value++; break;
        case 2: value--; break;
    }
}

void withDefault(int value) {
    switch (value) {
        case 1: value++; break;
        case 2: value--; break;
        default: break;
    }
}

int switchExpression(int value) {
    return switch (value) {
        case 1 -> 10;
        default -> 0;
    };
}

void patternCaseLabel(Object obj) {
    switch (obj) {
        case String s -> System.out.println(s);
        case Object o -> System.out.println(o);
    }
}

void recordPatternCaseLabel(Shape shape) {
    switch (shape) {
        case Circle(int radius) -> System.out.println(radius);
        case Square(int side) -> System.out.println(side);
    }
}

void nullCaseLabel(Day day) {
    switch (day) {
        case MONDAY -> System.out.println("MONDAY");
        case TUESDAY -> System.out.println("TUESDAY");
        case null -> System.out.println("NULL");
    }
}

void switchInsideIf(int value, boolean flag) {
    if (flag)
        switch (value) { // violation 'switch without "default" clause'
            case 1: value++; break;
        }
}

void nestedSwitch(int value) {
    switch (value) {
        case 1:
            switch (value) { // violation 'switch without "default" clause'
                case 2: value++; break;
            }
            break;
        default: break;
    }
}
