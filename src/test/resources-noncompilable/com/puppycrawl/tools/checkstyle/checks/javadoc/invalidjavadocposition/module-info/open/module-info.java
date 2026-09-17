/*
InvalidJavadocPosition


*/

// non-compiled with javac: reference to non existent module

/**
 * Valid Javadoc on the open module declaration.
 */
open /** invalid - after the open keyword */
module com.example.openmod {
    // violation 2 lines above 'Javadoc comment is placed in the wrong location.'
    requires com.nonexistent.module;
}
