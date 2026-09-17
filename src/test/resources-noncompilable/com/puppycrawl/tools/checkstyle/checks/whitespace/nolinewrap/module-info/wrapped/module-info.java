/*
NoLineWrap
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, MODULE_DEF
skipAnnotations = (default)true

*/

// non-compiled with javac: reference to non existent packages

// violation below 'MODULE_DEF statement should not be line-wrapped.'
module com.example
        .app {
    requires java.base;
}
