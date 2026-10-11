/*
AvoidNestedBlocks
allowInSwitchCase = (default)false


*/

// non-compiled with javac: Compilable with Java25

void helper() {
    int a = 1;
    { // violation 'Avoid nested blocks.'
        a++;
    }
    System.out.println(a);
}

void main() {
    switch (1) {
        case 1: { // violation 'Avoid nested blocks.'
            int b = 2;
            System.out.println(b);
        }
        default:
            break;
    }
    helper();
}
