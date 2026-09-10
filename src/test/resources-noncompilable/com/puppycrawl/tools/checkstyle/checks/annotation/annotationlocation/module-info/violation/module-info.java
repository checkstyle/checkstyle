/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = MODULE_DEF


*/

// non-compiled with javac: reference to non existent modules and packages

// violation below 'Annotation 'SuppressWarnings' should be alone on line.'
@Deprecated @SuppressWarnings("CheckReturnValue")
module com.example.hello {
  requires java.sql;
}
