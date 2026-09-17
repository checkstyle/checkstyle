/*
NoLineWrap
tokens = MODULE_DEF
skipAnnotations = false

*/

// non-compiled with javac: reference to non existent packages

// violation below 'MODULE_DEF statement should not be line-wrapped.'
@Deprecated
module com.example.app {
    requires java.base;
}
