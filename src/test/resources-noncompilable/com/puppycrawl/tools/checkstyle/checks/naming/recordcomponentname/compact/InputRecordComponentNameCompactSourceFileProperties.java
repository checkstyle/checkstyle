/*
RecordComponentName
format = ^[A-Z][a-zA-Z0-9]*$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

record GoodRecord(String Good, int Value1) {
}

record BadRecord(String lower, // violation 'Name 'lower' must match pattern'
        int other) { // violation 'Name 'other' must match pattern'
}
