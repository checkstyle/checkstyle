/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = MODULE_DEF


*/

// non-compiled with javac: reference to non existent modules and packages

@Deprecated @SuppressWarnings("CheckReturnValue")
// violation above 'Annotation 'SuppressWarnings' should be alone on line.'
module com.example.hello {
  requires java.sql;
}
