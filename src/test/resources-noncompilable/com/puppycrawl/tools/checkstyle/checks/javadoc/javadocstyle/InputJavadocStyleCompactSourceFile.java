/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

/** Greeting message without punctuation */ // violation 'First sentence should end with a period'
String greeting = "Hello, World!";

/** Counter value without punctuation */ // violation 'First sentence should end with a period'
private int count;

/**
 * Entry point for compact source file.
 * Uses {@code this} implicitly.
 */
void main() {
    count++;
    printGreeting();
    printWithHtml();
    printWithExtraClose();
    printClean();
}

/**
 * Prints a greeting without proper punctuation
 *
 * @return the greeting text
 */
String greetingText() { // violation 5 lines above 'First sentence should end with a period'
    return greeting;
}

/**
 * Calls {@link #greetingText()} without proper punctuation
 */
void printGreeting() { // violation 3 lines above 'First sentence should end with a period'
    System.out.println(greetingText());
}

/**
 * Prints with an incomplete HTML tag.
 * <p
 */
void printWithHtml() { // violation 2 lines above 'Incomplete HTML tag found'
    System.out.println("html");
}

/**
 * Prints with an unclosed tag.
 * <div> content
 */
void printWithUnclosedDiv() { // violation 2 lines above 'Unclosed HTML tag found: .*'
    System.out.println("div");
}

/**
 * Prints with an extra closing tag.
 * </b>
 */
void printWithExtraClose() { // violation 2 lines above 'Extra HTML tag found: .*'
    System.out.println("extra close");
}

/**
 * First sentence is fine.
 * <p>Second sentence is also fine.</p>
 */
void printClean() {
    System.out.println("clean");
}

/**
 * First sentence misses punctuation
 * <table><thead></thead></table>
 */
int nextCount() { // violation 4 lines above 'First sentence should end with a period'
    return ++count;
}

/**
 * Describes parameters properly.
 *
 * @param delta increment amount
 * @return the updated value
 */
int add(int delta) {
    count += delta;
    return count;
}
