/*
RecordComponentName
format = (default)^[a-z][a-zA-Z0-9]*$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

record GoodRecord(String good, int value1) {
}

record BadRecord(String Bad, // violation 'Name 'Bad' must match pattern'
        int Values) { // violation 'Name 'Values' must match pattern'
}
