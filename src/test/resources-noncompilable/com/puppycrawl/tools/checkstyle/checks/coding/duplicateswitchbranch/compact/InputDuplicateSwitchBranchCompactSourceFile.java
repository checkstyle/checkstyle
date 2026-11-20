/*
DuplicateSwitchBranch


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

int duplicateStatement(String value) {
    switch (value) {
        case "A":
            return 1;
        case "B": // violation 'identical to the branch at line 14'
            return 1;
        default:
            return 0;
    }
}

int duplicateExpression(String value) {
    return switch (value) {
        case "A" -> 1;
        case "B" -> 1; // violation 'identical to the branch at line 25'
        default -> 0;
    };
}

int noDuplicate(String value) {
    return switch (value) {
        case "A", "B" -> 1;
        default -> 0;
    };
}
