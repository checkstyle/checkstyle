/*
OpenjdkAnnotationLocation
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         RECORD_DEF, COMPACT_CTOR_DEF, MODULE_DEF

*/

// non-compiled with javac: reference to non existent modules and packages

// violation below 'Annotations must be on a separate line from 'app'.'
@Deprecated module com.example.app {
    requires java.base;
}
