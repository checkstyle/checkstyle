/*
MultipleStringLiterals
allowedDuplicates = 2
ignoreStringsRegexp = ^\"skip\"$
ignoreOccurrenceContext =


*/

// non-compiled with javac: Compilable with Java25

String first = "thrice"; // violation 'The String \"thrice\" appears 3 times in the file.'

String second = "thrice";

String third = "thrice";

String fourth = "twice";

String fifth = "twice";

String sixth = "skip";

String seventh = "skip";

String eighth = "skip";

@MyAnnotation("anno") // violation 'The String \"anno\" appears 3 times in the file.'
void method1() {
}

@MyAnnotation("anno")
void method2() {
}

@MyAnnotation("anno")
void method3() {
}

@interface MyAnnotation {
    String value();
}
