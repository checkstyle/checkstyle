/*
RegexpSinglelineJava
format = System\\.out
message = Found a System.out call.
ignoreCase = true
minimum = 1
maximum = 1
ignoreComments = true


*/

// non-compiled with javac: Compilable with Java25

String greet() {
    System.out.println("one");
    // violation below 'Found a System.out call.'
    System.out.println("two");
    return "hi";
}

void main() {
    greet();
}
