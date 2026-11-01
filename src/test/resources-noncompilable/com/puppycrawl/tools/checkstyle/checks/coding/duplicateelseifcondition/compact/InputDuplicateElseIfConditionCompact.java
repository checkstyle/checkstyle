/*
DuplicateElseIfCondition


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int x = 0, y = 1;
    if (x > 0) {
    }
    else if (y < 0) {
    }
    else if (x > 0) {
        // violation above 'Duplicate condition, identical to the condition at line '11'.'
    }
}
