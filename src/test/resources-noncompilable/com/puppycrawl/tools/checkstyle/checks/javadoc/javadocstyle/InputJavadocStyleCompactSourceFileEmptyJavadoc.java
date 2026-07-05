/*
JavadocStyle
checkEmptyJavadoc = true
checkFirstSentence = false
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = (default)private
violateExecutionOnNonTightHtml = (default)false
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

// violation 1 lines below 'Javadoc has empty description section.'
/** */
public void emptyDoc() {
}

// violation 1 lines below 'Javadoc has empty description section.'
/**
 */
public int emptyField;

/**
 * This description is present.
 */
public void okMethod() {
}

void main() {
}
