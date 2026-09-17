// non-compiled with javac: reference to non existent modules and packages

// 2 violations 3 lines below:
//                    'Annotation 'Deprecated' should be alone on line.'
//                    'Annotation 'SuppressWarnings' should be alone on line.'
@Deprecated @SuppressWarnings("CheckReturnValue")
module com.example.hello {
  requires java.sql;
}
