/*
InvalidJavadocPosition


*/

// non-compiled with javac: reference to non existent module

/**
 * Valid Javadoc on the module declaration.
 */
module com.example.mod {
    // violation below 'Javadoc comment is placed in the wrong location.'
    /** invalid - on a directive */
    requires com.nonexistent.module;

    // violation below 'Javadoc comment is placed in the wrong location.'
    /** invalid - before closing brace */
}
