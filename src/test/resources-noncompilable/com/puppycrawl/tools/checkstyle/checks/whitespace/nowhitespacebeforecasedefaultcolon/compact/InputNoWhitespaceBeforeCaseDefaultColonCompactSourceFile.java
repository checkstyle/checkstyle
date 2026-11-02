/*
NoWhitespaceBeforeCaseDefaultColon


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int value = 1;
    switch (value) {
        case 1:
            break;
        case 2 : // violation '':' is preceded with whitespace.'
            break;
        default:
            break;
    }

    switch (value) {
        case 3:
            break;
        default : // violation '':' is preceded with whitespace.'
            break;
    }
}

class Nested {
    void test(int x) {
        switch (x) {
            case 1:
                break;
            case 2 : // violation '':' is preceded with whitespace.'
                break;
            default:
                break;
        }
    }
}
