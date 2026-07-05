/*
JavadocStyle
checkEmptyJavadoc = (default)false
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = (default)private
violateExecutionOnNonTightHtml = (default)false
tokens = ANNOTATION_DEF, CLASS_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.utils.javadocutil;


// violation below 'First sentence should end with a period.'
/**classComment*/
class InputJavadocUtilTypeDefComments {
}
// violation below 'First sentence should end with a period.'
/**annotationClass*/
@Deprecated
class ClassAnnotationPath {
}

class ClassBodyCommentOnly {
    /**nope*/
    void method() {
    }
}


// violation below 'First sentence should end with a period.'
/**annotationDef*/
@interface AnnotationPath {
}
// violation below 'First sentence should end with a period.'
/**enumDef*/
enum EnumPath {
}
// violation below 'First sentence should end with a period.'
/**interfaceDef*/
interface InterfacePath {
}
// violation below 'First sentence should end with a period.'
/**recordDef*/
record RecordPath() {
}
/**dangling*/
// violation below 'First sentence should end with a period.'
/**real*/
class ClassDanglingReal {
}
