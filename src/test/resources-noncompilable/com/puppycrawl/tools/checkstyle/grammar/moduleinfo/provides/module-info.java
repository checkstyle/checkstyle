// non-compiled with javac: reference to non existent packages

/**
 * The descriptor of the jdk.charsets platform module.
 */
module jdk.charsets {
    provides java.nio.charset.spi.CharsetProvider with sun.nio.cs.ext.ExtendedCharsets;
}
