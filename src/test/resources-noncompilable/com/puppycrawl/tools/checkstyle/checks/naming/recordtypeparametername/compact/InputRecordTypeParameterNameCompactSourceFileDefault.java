/*
RecordTypeParameterName
format = (default)^[A-Z]$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

record MyRecord<T>(String str) {
}

record OtherRecord<t>(int x) { // violation 'Name 't' must match pattern'
}

record AnotherRecord<foo>(double d) { // violation 'Name 'foo' must match pattern'
}
