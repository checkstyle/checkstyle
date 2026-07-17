// non-compiled with javac: reference to non existent packages

/**
 * A logging facade module, following the service loader pattern.
 */
module com.example.logging.facade {
    exports com.example.logging.facade;

    uses com.example.logging.facade.LoggerFactory;
}
