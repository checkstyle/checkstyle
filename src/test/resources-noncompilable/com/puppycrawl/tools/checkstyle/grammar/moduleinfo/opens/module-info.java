// non-compiled with javac: reference to non existent packages

/**
 * The descriptor of the jdk.unsupported platform module.
 */
module jdk.unsupported {
    exports com.sun.nio.file;
    exports sun.misc;
    exports sun.reflect;

    opens sun.misc;
    opens sun.reflect;
}
