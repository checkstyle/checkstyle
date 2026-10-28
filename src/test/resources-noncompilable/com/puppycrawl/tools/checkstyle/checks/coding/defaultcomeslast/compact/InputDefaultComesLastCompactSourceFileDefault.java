/*
DefaultComesLast
skipIfLastAndSharedWithCase = (default)false

*/

// non-compiled with javac: Compilable with Java25

void checkSwitchStatement(int value) {
    switch (value) {
        case 1:
            System.out.println("one");
            break;
        default: // violation 'Default should be last label in the switch.'
            System.out.println("other");
            break;
        case 2:
            System.out.println("two");
            break;
    }
}

void checkSharedCaseGroup(int value) {
    switch (value) {
        case 1:
        default: // violation 'Default should be last label in the switch.'
            break;
        case 2:
            System.out.println("two");
            break;
    }
}

void checkSwitchExpression(int value) {
    String result = switch (value) {
        case 1 -> "one";
        default -> "other"; // violation 'Default should be last label in the switch.'
        case 2 -> "two";
    };
    System.out.println(result);
}

void checkDefaultLast(int value) {
    switch (value) {
        case 1:
            System.out.println("one");
            break;
        default:
            System.out.println("other");
    }
}

void main() {
}
