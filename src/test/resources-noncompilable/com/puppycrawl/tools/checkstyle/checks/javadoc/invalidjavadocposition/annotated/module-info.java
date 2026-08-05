/*
InvalidJavadocPosition


*/

// non-compiled with javac: reference to non existent module

/**
 * Valid Javadoc on the annotated module declaration.
 */
@Deprecated // violation below 'Javadoc comment is placed in the wrong location.'
/** invalid, between annotation and module keyword */
module com.example.annotatedmod {
    requires com.nonexistent.module;
}
