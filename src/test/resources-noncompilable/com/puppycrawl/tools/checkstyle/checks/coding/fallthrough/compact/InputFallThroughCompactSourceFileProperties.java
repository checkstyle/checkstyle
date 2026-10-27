/*
FallThrough
checkLastCaseGroup = true
reliefPattern = ok to continue

*/

// non-compiled with javac: Compilable with Java25

void checkSwitch(int value) {
    switch (value) {
        case 1:
            System.out.println("one");
            // ok to continue
        case 2:
            System.out.println("two");
        // violation below 'Fall\ through from previous branch of the switch'
        case 3:
            System.out.println("three");
        default:
        // 2 violations above:
        //                     'Fall\ through from previous branch of the switch'
        //                     'Fall\ through from the last branch of the switch'
            System.out.println("other");
    }
}

void main() {
}
