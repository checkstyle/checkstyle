/*
DefaultComesLast
skipIfLastAndSharedWithCase = true

*/

// non-compiled with javac: Compilable with Java25

void checkSharedCaseGroupLastLabel(int value) {
    switch (value) {
        case 1:
        default:
            break;
        case 2:
            System.out.println("two");
            break;
    }
}

void checkSharedCaseGroupNotLastLabel(int value) {
    switch (value) {
        case 1:
            System.out.println("one");
            break;
        default: // violation 'Default should be last label in the case group.'
        case 2:
            break;
        case 3:
            System.out.println("three");
            break;
    }
}

void checkDefaultAloneNotLast(int value) {
    switch (value) {
        default: // violation 'Default should be last label in the switch.'
            System.out.println("other");
            break;
        case 1:
            System.out.println("one");
            break;
    }
}

void checkSwitchRule(int value) {
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
