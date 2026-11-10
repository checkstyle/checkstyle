/*
Regexp
duplicateLimit = 5
message = Found a System.out call.
errorLimit = 50
format = System\\.out
ignoreComments = true
illegalPattern = true


*/

// non-compiled with javac: Compilable with Java25

// violation 2 lines below 'Line matches the illegal pattern 'Found a System.out call.'.'
String greet() {
    System.out.println("hi");
    return "hi";
}

void main() {
    greet();
}
