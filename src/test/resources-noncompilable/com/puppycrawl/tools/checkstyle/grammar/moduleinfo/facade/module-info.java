// non-compiled with javac: exported packages do not exist in this module

/**
 * A logging facade module, following the service loader pattern.
 */
module com.example.logging.facade {
    exports com.example.logging.facade;

    uses com.example.logging.facade.LoggerFactory;
}
