/*
AvoidNestedBlocks
allowInSwitchCase = true


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 1;
    { // violation 'Avoid nested blocks.'
        a++;
    }
    switch (a) {
        case 1: {
            int b = 2;
            System.out.println(b);
        }
        case 2: { // violation 'Avoid nested blocks.'
            int c = 3;
            System.out.println(c);
        }
        break;
        default:
            break;
    }
    System.out.println(a);
}
