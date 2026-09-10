// non-compiled with javac: reference to non existent modules and packages

@Deprecated @SuppressWarnings("CheckReturnValue")
// 2 violations above:
//                    'Annotation 'Deprecated' should be alone on line.'
//                    'Annotation 'SuppressWarnings' should be alone on line.'
module com.example.hello {
  requires java.sql;
}
