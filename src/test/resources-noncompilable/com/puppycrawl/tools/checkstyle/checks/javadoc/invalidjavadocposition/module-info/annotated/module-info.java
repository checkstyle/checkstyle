/*
InvalidJavadocPosition


*/

// non-compiled with javac: reference to non existent module

/**
 * Valid Javadoc on the annotated module declaration.
 */
@Deprecated
/** invalid, between annotation and module keyword */
module com.example.annotatedmod {
    // violation 2 lines above 'Javadoc comment is placed in the wrong location.'
    requires com.nonexistent.module;
}
