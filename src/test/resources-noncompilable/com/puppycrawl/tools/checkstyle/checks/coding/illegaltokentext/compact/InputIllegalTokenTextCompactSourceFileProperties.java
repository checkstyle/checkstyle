/*
IllegalTokenText
format = forbidden
ignoreCase = true
message = Do not use forbidden text
tokens = STRING_LITERAL


*/

// non-compiled with javac: Compilable with Java25

String title = "Forbidden value"; // violation 'Do not use forbidden text'

String greeting = "hello";

void main() {
}

String label(int count) {
    return "FORBIDDEN: " + count; // violation 'Do not use forbidden text'
}
