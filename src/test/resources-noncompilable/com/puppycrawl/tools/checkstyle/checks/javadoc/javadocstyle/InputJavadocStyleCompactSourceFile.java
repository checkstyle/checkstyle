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

// violation 1 lines below 'First sentence should end with a period.'
/**
 * This method does something
 */
public void doSomething() {
    System.out.println("hello");
}

// violation 1 lines below 'First sentence should end with a period.'
/** no period at end */
public int value = 42;

/**
 * This is fine.
 */
public void okMethod() {
}

// violation 1 lines below 'First sentence should end with a period.'
/**
 * A top-level type without an ending period
 */
class Inner {
}

// violation 3 lines below 'Unclosed HTML tag found: <b>'
/**
 * Test HTML in Javadoc comment.
 * <b>
 */
void htmlMethod() {
}

void main() {
}
