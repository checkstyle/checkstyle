/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreStringsRegexp = (default)^\"\"$
ignoreOccurrenceContext = (default)ANNOTATION


*/

// non-compiled with javac: Compilable with Java25

String first = "hello"; // violation 'The String \"hello\" appears 2 times in the file.'

String second = "hello";

String empty1 = "";

String empty2 = "";

@MyAnnotation("dup")
void method1() {
}

@MyAnnotation("dup")
void method2() {
}

@interface MyAnnotation {
    String value();
}
