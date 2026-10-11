/*
OpenjdkAnnotationLocation
tokens = MODULE_DEF

*/

// non-compiled with javac: reference to non existent modules and packages

@Helper @Deprecated
@Special module com.example.app2 {
    // 2 violations above:
    //   'Annotations must be on a separate line from 'app2'.'
    //   'Annotations on 'app2' must be all on one line or all on separate lines.'
    requires java.base;
}
