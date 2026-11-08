/*
UseEnhancedSwitch


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// violation below 'Switch can be replaced with enhanced switch.'
int level = switch (2) {
    case 1:
        yield 10;
    default:
        yield 20;
};

int enhancedLevel = switch (2) {
    case 1 -> 10;
    default -> 20;
};

String describe(int code) {
    String result;
    // violation below 'Switch can be replaced with enhanced switch.'
    switch (code) {
        case 1:
            result = "one";
            break;
        default:
            result = "other";
    }
    return result;
}

int score(String grade) {
    // violation below 'Switch can be replaced with enhanced switch.'
    return switch (grade) {
        case "A":
            yield 4;
        default:
            yield 0;
    };
}

String describeEnhanced(int code) {
    return switch (code) {
        case 1 -> "one";
        default -> "other";
    };
}
