/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)

*/

// non-compiled with javac: Compilable with Java25

void checkSwitch(int value) {
    switch (value) {
        case 1:
            System.out.println("one");
        // violation below 'Fall\ through from previous branch of the switch'
        case 2:
            System.out.println("two");
            // fallthrough
        case 3:
            System.out.println("three");
            break;
        default:
            System.out.println("other");
    }
}

void main() {
}
