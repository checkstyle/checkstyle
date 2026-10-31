/*
MissingNullCaseInSwitch


*/

// non-compiled with javac: Compilable with Java25

void main() {
    String value = "a";
    // violation below 'Switch using reference types should have a null case.'
    switch (value) {
        case "a" -> { }
        default -> { }
    }
    switch (value) {
        case "a" -> { }
        case null -> { }
        default -> { }
    }
}
