/*
RecordTypeParameterName
format = ^foo$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

record MyRecord<foo>(String str) {
}

record OtherRecord<T>(int x) { // violation 'Name 'T' must match pattern'
}

record AnotherRecord<other>(double d) { // violation 'Name 'other' must match pattern'
}
