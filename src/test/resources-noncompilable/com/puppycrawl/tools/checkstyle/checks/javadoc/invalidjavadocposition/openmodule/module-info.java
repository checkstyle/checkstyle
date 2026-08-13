/*
InvalidJavadocPosition


*/

// non-compiled with javac: reference to non existent module

/**
 * Valid Javadoc on the open module declaration.
 */
// violation below 'Javadoc comment is placed in the wrong location.'
open /** invalid - after the open keyword */
module com.example.openmod {
    requires com.nonexistent.module;
}
